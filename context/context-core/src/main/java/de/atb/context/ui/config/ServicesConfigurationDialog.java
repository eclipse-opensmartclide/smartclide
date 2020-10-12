package de.atb.context.ui.config;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import de.atb.context.ui.util.Icon;
import de.atb.context.ui.util.interfaces.ICommunicationBetweenUIs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.services.config.SWServiceConfiguration;
import de.atb.context.services.config.models.SWService;
import de.atb.context.services.interfaces.IPrimitiveService;
import de.atb.context.services.interfaces.IService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 *
 * @author Giovanni
 */
public class ServicesConfigurationDialog extends JDialog implements
		ListSelectionListener, DocumentListener, ActionListener, ChangeListener {

	private static final long serialVersionUID = 4400246774187060315L;
	private final Logger logger = LoggerFactory
			.getLogger(ServicesConfigurationDialog.class);

	private final SWServiceConfiguration serviceConfig;
	private final SWService[] services;
	private SWService currentService;
	private boolean servicesListUpdatePending = false;
	private boolean currentServiceUpdatePending = false;
	private boolean ignoreServiceFieldChanges = false;

	private Integer oldSelectionIndex = null;

	private JButton btnOk, btnCancel, btnUpdate;
	private JLabel lblWarnServer, lblWarnProxy, lblWarnPort, lblWarnUrl;
	private JList<?> lstServices;
	private JTextField txtURL;
	private JSpinner txtPort;
	private JTextField txtServerClass;
	private JTextField txtProxyClass;

	protected ICommunicationBetweenUIs comm;

	public ServicesConfigurationDialog() {
		this.serviceConfig = SWServiceConfiguration.getInstance();
		this.services = serviceConfig.getServicesArray();
		;

		initializeComponents();
		if (lstServices.getModel().getSize() > 0) {
			lstServices.setSelectedIndex(0);
		}
	}

	protected final void initializeComponents() {
		lstServices = new JList<Object>(services);
		btnOk = new JButton("Done", Icon.Ok_16.getIcon());
		btnOk.addActionListener(this);
		btnUpdate = new JButton("Update", Icon.Update_16.getIcon());
		btnUpdate.addActionListener(this);
		btnCancel = new JButton("Cancel", Icon.Cancel_16.getIcon());
		btnCancel.addActionListener(this);
		txtURL = new JTextField("localhost");
		txtURL.getDocument().addDocumentListener(this);
		txtPort = new JSpinner(new SpinnerNumberModel(1024, -50, 65535, 1));
		txtPort.addChangeListener(this);
		txtServerClass = new JTextField();
		txtServerClass.getDocument().addDocumentListener(this);
		txtProxyClass = new JTextField();
		txtProxyClass.getDocument().addDocumentListener(this);

		ImageIcon i = Icon.Warning_16.getIcon();
		lblWarnServer = new JLabel(i);
		lblWarnServer.setVisible(false);
		lblWarnProxy = new JLabel(i);
		lblWarnProxy.setVisible(false);
		lblWarnPort = new JLabel(i);
		lblWarnPort.setVisible(false);
		lblWarnUrl = new JLabel(i);
		lblWarnUrl.setVisible(false);
		JScrollPane lstServicesScroller = new JScrollPane(lstServices);
		lstServices.addListSelectionListener(this);
		lstServices.setCellRenderer(new ServiceRenderer());

		setLayout(new BorderLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		FormLayout layout = new FormLayout(
				"right:max(55dlu;p), 4dlu, p:grow, 4dlu, p, 15dlu",
				"p, 4dlu, max(35dlu;p), 8dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 8dlu, p, 4dlu, p");
		PanelBuilder builder = new PanelBuilder(layout);
		JComponent comp = builder.addSeparator("Services:",
				CC.xyw(1, 1, 6, CC.FILL, CC.FILL));
		comp.getComponent(0).setFont(comp.getFont().deriveFont(Font.BOLD));

		builder.add(lstServicesScroller, CC.xyw(1, 3, 6, CC.FILL, CC.FILL));
		builder.add(new JLabel("URL:"), CC.xy(1, 5));
		builder.add(txtURL, CC.xy(3, 5));
		builder.add(lblWarnUrl, CC.xy(6, 5));
		builder.add(new JLabel("Port:"), CC.xy(1, 7));
		builder.add(txtPort, CC.xy(3, 7, CC.LEFT, CC.FILL));
		builder.add(lblWarnPort, CC.xy(6, 7));
		builder.add(new JLabel("Server Class:"), CC.xy(1, 9));
		builder.add(txtServerClass, CC.xyw(3, 9, 3));
		builder.add(lblWarnServer, CC.xy(6, 9));
		builder.add(new JLabel("Proxy Class:"), CC.xy(1, 11));
		builder.add(txtProxyClass, CC.xyw(3, 11, 3));
		builder.add(lblWarnProxy, CC.xy(6, 11));
		builder.add(btnUpdate, CC.xy(5, 13));
		builder.addSeparator("", CC.xyw(1, 15, 6, CC.FILL, CC.FILL));
		builder.add(btnOk, CC.xy(3, 17, CC.RIGHT, CC.FILL));
		builder.add(btnCancel, CC.xyw(5, 17, 2, CC.FILL, CC.FILL));
		builder.border(new EmptyBorder(4, 4, 4, 4));

		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void windowClosing(final WindowEvent e) {
				close();
			}
		});

		setResizable(false);
		setMinimumSize(new Dimension(450, 250));
		add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		validateServiceFields(false);
		setLocationRelativeTo(null);
	}

	protected final void toggleFieldValidation(final JComponent field, final boolean isValid,
			final String toolTipText, final JComponent warnLabel) {
		final Color c = isValid ? SystemColor.controlText : Color.red;
		final String toolTip = !isValid ? toolTipText : null;

		field.setForeground(c);
		field.setToolTipText(toolTip);
		if (warnLabel != null) {
			warnLabel.setVisible(!isValid);
			warnLabel.setToolTipText(toolTip);
		}
	}

	protected final boolean validatePort(final boolean log) {
		Integer port = (Integer) txtPort.getValue();
		if ((port != null)
				&& ((port.intValue() > 0) && (port.intValue() < 65536))) {
			if (log) {
				logger.trace("Port " + port + " is valid");
			}
			toggleFieldValidation(txtPort, true, null, lblWarnPort);
			return true;
		} else {
			final String msg = "Port has an invalid value '" + port + "'";
			if (log) {
				logger.info(msg);
			}
			toggleFieldValidation(txtPort, false, msg, lblWarnPort);
			return false;
		}
	}

	protected final boolean validateServerClass(final boolean log) {
		if (!validateClass(txtServerClass.getText(), IService.class)
				&& !validateClass(txtServerClass.getText(),
						IPrimitiveService.class)) {
			final String msg = "Server class '" + txtServerClass.getText()
					+ "' does not exist or is not implementing "
					+ IService.class.getName();
			if (log) {
				logger.info(msg);
			}
			toggleFieldValidation(txtServerClass, false, msg, lblWarnServer);
			return false;
		} else {
			if (log) {
				logger.trace("Server class name '" + txtProxyClass.getText()
						+ "' is valid");
			}
			toggleFieldValidation(txtServerClass, true, null, lblWarnServer);
			return true;
		}
	}

	protected final boolean validateProxyClass(final boolean log) {
		if (!validateClass(txtProxyClass.getText(), IService.class)
				&& !validateClass(txtServerClass.getText(),
						IPrimitiveService.class)) {
			final String msg = "Proxy class '" + txtProxyClass.getText()
					+ "' does not exist or is not implementing "
					+ IService.class.getName();
			if (log) {
				logger.info(msg);
			}
			toggleFieldValidation(txtProxyClass, false, msg, lblWarnProxy);
			return false;
		} else {
			if (log) {
				logger.trace("Poxy class name '" + txtProxyClass.getText()
						+ "' is valid");
			}
			toggleFieldValidation(txtProxyClass, true, null, lblWarnProxy);

			return true;
		}
	}

	protected final boolean validateLocation(final boolean log) {
		String txtUrl = (txtURL.getText() != null)
				&& (txtURL.getText().trim().length() > 0) ? txtURL.getText()
				: "";
		if ((txtUrl == null) || "".equals(txtUrl)) {
			final String msg = "Location with value '" + txtUrl
					+ "' is invalid.";
			if (log) {
				logger.info(msg);
			}
			toggleFieldValidation(txtURL, false, msg, lblWarnUrl);
			return false;
		} else {
			if (log) {
				logger.trace("Location with value '" + txtUrl + "' is valid.");
			}
			toggleFieldValidation(txtURL, true, null, lblWarnUrl);
			return true;
		}
	}

	protected final boolean validateClass(final String name, final Class<?> implementingClass) {
		try {
			if ((name != null) && (name.trim().length() > 0)) {
				Class<?> myClazz = Class.forName(name);
				if (implementingClass != null) {
					return (implementingClass.isAssignableFrom(myClazz));
				}
				return true;
			}
		} catch (ClassNotFoundException e) {
			logger.trace(e.getMessage(), e);
		}
		return false;
	}

	protected final boolean validateURL(final boolean log, final String location, final Integer port) {
		if ((location != null) && (location.trim().length() > 0)
				&& (port != null)) {
			String loc = String.format("http://%s:%d", location.trim(), port);
			try {
				URL url = new URL(loc);
				if (log) {
					logger.trace("URL " + url + " is valid.");
				}
				return true;
			} catch (MalformedURLException e) {
				if (log) {
					logger.info(e.getMessage(), e);
				}
			}
		}
		if (log) {
			logger.info("Location '" + location + "' and Port '" + port
					+ "' do not form valid URL.'");
		}
		return false;
	}

	private void ignoreServiceChanges() {
		ignoreServiceFieldChanges = true;
	}

	private void detectServiceChanges() {
		ignoreServiceFieldChanges = false;
	}

	protected final boolean validateServiceFields() {
		return validateServiceFields(true);
	}

	protected final boolean validateServiceFields(final boolean logErrors) {
		boolean validProxyClass = validateProxyClass(logErrors);
		boolean validServerClass = validateServerClass(logErrors);
		boolean validPort = validatePort(logErrors);
		boolean validLocation = validateLocation(logErrors);
		boolean validURL = validateURL(logErrors, txtURL.getText(),
				(Integer) txtPort.getValue());
		return validProxyClass && validServerClass && validPort
				&& validLocation && validURL;
	}

	protected final void populateServiceEditControls(final SWService service) {
		if (service != null) {
			ignoreServiceChanges();
			currentService = service;
			logger.trace("Now editing service " + service + "!");
			URL location = service.getLocation();
			if (location != null) {
				txtURL.setText(location.getHost() != null ? service
						.getLocation().getHost() : "localhost");
				txtPort.setValue(location.getPort() > 0 ? Integer
						.valueOf(location.getPort()) : Integer.valueOf(65535));
			} else {
				txtURL.setText("localhost");
				txtPort.setValue(Integer.valueOf(65535));
			}
			txtServerClass.setText(service.getServerClass() != null ? service
					.getServerClass().getName() : "");
			txtProxyClass.setText(service.getProxyClass() != null ? service
					.getProxyClass().getName() : "");
			validateServiceFields();
			detectServiceChanges();
		} else {
			logger.warn("Cannot edit service that is null!");
		}
	}

	@SuppressWarnings("unchecked")
	protected final void updateService() {
		logger.trace("Updating service");
		String loc = String.format("http://%s:%d", txtURL.getText().trim(),
				txtPort.getValue());
		String host = txtURL.getText().trim();
		try {
			Class<? extends IService> serverClass = (Class<? extends IService>) Class
					.forName(txtServerClass.getText().trim());
			Class<? extends IService> proxyClass = (Class<? extends IService>) Class
					.forName(txtProxyClass.getText().trim());
			currentService.setHost(host);
			currentService.setLocation(new URL(loc));
			currentService.setProxyClass(proxyClass);
			currentService.setServerClass(serverClass);
			for (SWService service : this.services) {
				if (!service.getId().equals(currentService.getId())) {
					String location = String.format("http://%s:%d", txtURL
							.getText().trim(), service.getLocation().getPort());
					service.setHost(host);
					service.setLocation(new URL(location));
				}
			}
			lstServices.repaint();
			currentServiceUpdatePending = false;
			servicesListUpdatePending = true;
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

	// public static void main(String[] args) {
	// ServicesConfigurationDialog c = new ServicesConfigurationDialog();
	// c.setVisible(true);
	// }
	@SuppressWarnings("boxing")
	@Override
	public final void valueChanged(final ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			if (currentServiceUpdatePending) {
				int answer = JOptionPane
						.showConfirmDialog(
								this,
								"The service '"
										+ currentService.getName()
										+ "' has pending changes, do you want to apply these before selecting another service?",
								"Apply changes?",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					boolean valid = validateServiceFields();
					if (valid) {
						updateService();
						oldSelectionIndex = lstServices.getSelectedIndex();
					}
				} else if (answer == JOptionPane.NO_OPTION) {
					currentServiceUpdatePending = false;
				}
			}
		}
		if (!currentServiceUpdatePending) {
			SWService service = (SWService) lstServices.getSelectedValue();
			populateServiceEditControls(service);
			oldSelectionIndex = lstServices.getSelectedIndex();
		} else {
			lstServices.removeListSelectionListener(this);
			lstServices.setSelectedIndex(oldSelectionIndex.intValue());
			lstServices.addListSelectionListener(this);
		}
	}

	@Override
	public final void insertUpdate(final DocumentEvent e) {
		if (!ignoreServiceFieldChanges) {
			currentServiceUpdatePending = true;
		}
		validateServiceFields(false);
	}

	@Override
	public final void removeUpdate(final DocumentEvent e) {
		if (!ignoreServiceFieldChanges) {
			currentServiceUpdatePending = true;
		}
		validateServiceFields(false);
	}

	@Override
	public final void changedUpdate(final DocumentEvent e) {
		if (!ignoreServiceFieldChanges) {
			currentServiceUpdatePending = true;
		}
		validateServiceFields(false);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		if ((e.getSource() == btnUpdate) && (currentService != null)) {
			if (validateServiceFields()) {
				updateService();
			} else {
				logger.info("Cannot update service " + currentService
						+ " fields invalid!");
				JOptionPane
						.showMessageDialog(
								this,
								"Cannot update the service "
										+ currentService.getName()
										+ " as there are some invalid values. Please correct all fields with a warning symbol.",
								"Cannot update service",
								JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == btnOk) {
			if (validateServiceFields()) {
				updateService();
				servicesListUpdatePending = false;
				serviceConfig.setServices(Arrays.asList(this.services));
				String filepath = serviceConfig.save();
				if (filepath != null && this.comm != null) {
					this.comm.setConfigFilePath(filepath);
				}
				close();
			} else {
				logger.info("Cannot update service " + currentService
						+ " fields invalid!");
				JOptionPane
						.showMessageDialog(
								this,
								"Cannot update the service "
										+ currentService.getName()
										+ " as there are some invalid values. Please correct all fields with a warning symbol.",
								"Cannot update service",
								JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == btnCancel) {
			close();
		}
	}

	private void close() {
		boolean exit = servicesListUpdatePending || currentServiceUpdatePending ? JOptionPane.OK_OPTION == JOptionPane
				.showConfirmDialog(
						ServicesConfigurationDialog.this,
						"Services were changed, do you really want to exit the configuration?",
						"Exit without saving?", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE)
				: true;
		if (exit) {
			dispose();
		}
	}

	@Override
	public final void stateChanged(final ChangeEvent e) {
		if (!ignoreServiceFieldChanges) {
			currentServiceUpdatePending = true;
		}
		validateServiceFields(false);
	}

	private static class ServiceRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = -7214238324806146528L;
		private ImageIcon icon;

		public ServiceRenderer() {
			icon = Icon.Connection_16.getIcon();
		}

		@Override
		public Component getListCellRendererComponent(final JList<?> list,
				final Object value, final int index, final boolean isSelected, final boolean hasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list,
					value, index, isSelected, hasFocus);
			label.setIcon(icon);
			return (label);
		}
	}

	public final void setComm(final ICommunicationBetweenUIs comm) {
		this.comm = comm;
	}

}
