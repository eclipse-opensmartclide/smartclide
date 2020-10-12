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
import de.atb.context.services.registration.RegCellRender;
import de.atb.context.services.registration.RegistrationPopUp;
import de.atb.context.services.registration.RegistrationTableModel;
import de.atb.context.services.registration.Registration_Mngr;
import de.atb.context.ui.util.Icon;
import de.atb.context.ui.util.interfaces.ICommunicationBetweenUIs;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.modules.Deployer;
import de.atb.context.modules.config.DeployerConfigurationObject;
import de.atb.context.services.config.models.SWService;
import de.atb.context.services.infrastructure.IServiceRegistryService;
import de.atb.context.services.manager.ServiceManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Giovanni
 */
public class ConfigurationDeployerDialog extends JDialog implements
		ListSelectionListener, DocumentListener, ActionListener, ChangeListener {

	private static final long serialVersionUID = 4400246774187060315L;
	private final Logger logger = LoggerFactory
			.getLogger(ConfigurationDeployerDialog.class);

	private RegistrationPopUp regPopUp;

	private final Deployer deployer;
	private ICommunicationBetweenUIs CommInterface = null;
	private boolean servicesListUpdatePending = false;
	private boolean currentServiceUpdatePending = false;
	private boolean ignoreServiceFieldChanges = false;
	private Registration_Mngr regMng;
	private JXTable RegTable;
	private Integer oldSelectionIndex = null;
	private JButton btnOk, btnCancel, btnTest, btnPingUpdate;
	private JLabel lblWarnPort, lblWarnUrl, lblConnection;
	private JList<?> lstServices;
	private JTextField txtURL;
	private JSpinner txtPort;
	private JTree tree;

	public ConfigurationDeployerDialog(final Deployer deployer,
			final ICommunicationBetweenUIs CommInterface) {
		this.deployer = deployer;
		this.CommInterface = CommInterface;
		this.regMng = new Registration_Mngr();
		initializeComponents();
	}

	protected final void initializeComponents() {
		regMng.init();

		btnOk = new JButton("Done", Icon.Ok_16.getIcon());
		btnOk.addActionListener(this);
		btnCancel = new JButton("Cancel", Icon.Cancel_16.getIcon());
		btnCancel.addActionListener(this);
		btnPingUpdate = new JButton("Update", Icon.Update_2_32.getIcon());
		btnPingUpdate.addActionListener(this);
		btnTest = new JButton("Test Connection", Icon.Rdf_16.getIcon());
		btnTest.addActionListener(this);
		DeployerConfigurationObject config = this.deployer.getConfiguration();
		if (config != null) {
			txtURL = new JTextField(config.getURL());
			txtPort = new JSpinner(new SpinnerNumberModel(
					Integer.parseInt(config.getPort()), -50, 65535, 1));
		} else {
			txtURL = new JTextField("localhost");
			txtPort = new JSpinner(new SpinnerNumberModel(1024, -50, 65535, 1));
		}
		txtURL.getDocument().addDocumentListener(this);
		txtPort.addChangeListener(this);

		ImageIcon i = Icon.Warning_16.getIcon();
		lblWarnPort = new JLabel(i);
		lblWarnPort.setVisible(false);
		lblWarnUrl = new JLabel(i);
		lblWarnUrl.setVisible(false);
		lblConnection = new JLabel();
		lblConnection.setVisible(false);

		setLayout(new BorderLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		FormLayout layout = new FormLayout(
				"right:max(55dlu;p), 4dlu, p:grow, 4dlu, p, p",
				"p, 4dlu, max(35dlu;p), 8dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 8dlu, p, 8dlu, p, 4dlu, 80dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		JComponent comp = builder.addSeparator(
				"Deployer configuration for Server communication:",
				CC.xyw(1, 1, 6, CC.FILL, CC.FILL));
		comp.getComponent(0).setFont(comp.getFont().deriveFont(Font.BOLD));

		builder.add(new JLabel("URL:"), CC.xy(1, 3));
		builder.add(txtURL, CC.xy(3, 3));
		builder.add(lblWarnUrl, CC.xy(6, 3));
		builder.add(new JLabel("Port:"), CC.xy(1, 5));
		builder.add(txtPort, CC.xy(3, 5, CC.LEFT, CC.FILL));
		builder.add(lblWarnPort, CC.xy(6, 5));
		builder.add(lblConnection, CC.xy(3, 7));
		builder.add(btnTest, CC.xy(5, 7));
		builder.addSeparator("", CC.xyw(1, 15, 6, CC.FILL, CC.FILL));
		builder.add(btnOk, CC.xy(3, 17, CC.RIGHT, CC.FILL));
		builder.add(btnCancel, CC.xyw(5, 17, 2, CC.FILL, CC.FILL));
		builder.add(btnPingUpdate, CC.xy(1, 17, CC.RIGHT, CC.FILL));
		builder.addSeparator("", CC.xyw(1, 19, 6, CC.FILL, CC.FILL));

		RegistrationTableModel model = new RegistrationTableModel(regMng);
		RegTable = new JXTable(model);
		JScrollPane scroll = new JScrollPane(RegTable);

		RegTable.setShowGrid(false, false);
		RegTable.setRowSelectionAllowed(true);
		RegTable.setColumnSelectionAllowed(false);
		RegTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		RegTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent evt) {
				int row = RegTable.rowAtPoint(evt.getPoint());
				if (row > -1) {
					try {
						updateFrameLabels(new URL((String) RegTable.getValueAt(
								row, 0)));
					} catch (MalformedURLException ex) {
						// java.util.logging.Logger.getLogger(ConfigurationDeployerDialog.class.getName()).log(Level.SEVERE,
						// null, ex);
						logger.info("Unable to update labels");
					}
				}
			}
		});
		RegTable.setDefaultRenderer(Object.class, new RegCellRender());

		builder.add(scroll, CC.xyw(1, 21, 6));
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
		boolean validPort = validatePort(logErrors);
		boolean validLocation = validateLocation(logErrors);
		boolean validURL = validateURL(logErrors, txtURL.getText(),
				(Integer) txtPort.getValue());
		return validPort && validLocation && validURL;
	}

	protected final void updateService() {
		logger.trace("Updating service");
		String loc = String.format("http://%s:%d", txtURL.getText().trim(),
				txtPort.getValue());
		this.deployer
				.setDeployerConfigurationObject(new DeployerConfigurationObject(
						txtPort.getValue().toString(), txtURL.getText(), loc));
		currentServiceUpdatePending = false;
		servicesListUpdatePending = true;
		lblConnection.setText("");
		this.registerServices();
	}

	protected final void testConnection() {
		logger.trace("Testing connection to Server");
		String loc = String.format("http://%s:%d", txtURL.getText().trim(),
				txtPort.getValue());
		IServiceRegistryService client = ServiceManager.getWebservice(
				txtURL.getText(), (int) txtPort.getValue(),
				IServiceRegistryService.class);
		if (!ServiceManager.isPingable(client)) {
			lblConnection.setForeground(Color.RED);
			lblConnection.setText("Connection not possible");
			lblConnection.setVisible(true);
			logger.info("Connection not possible");
		} else {
			lblConnection.setForeground(Color.green);
			lblConnection
					.setText("Connection established: Press OK to Register");
			lblConnection.setVisible(true);
			logger.info("Connection established: Press OK to Register Deployer in Service Registry");
		}
	}

	protected final void registerServices() {
		if (this.deployer.registerServices(txtURL.getText(),
				(int) txtPort.getValue(), IServiceRegistryService.class)) {
			this.CommInterface.setConnected();
			regMng.new_registry(txtURL.getText(), (int) txtPort.getValue());
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public final void valueChanged(final ListSelectionEvent e) {
		// if (e.getValueIsAdjusting()) {
		// if (currentServiceUpdatePending) {
		// int answer = JOptionPane.showConfirmDialog(this, "The service '" +
		// currentService.getName()
		// +
		// "' has pending changes, do you want to apply these before selecting another service?",
		// "Apply changes?",
		// JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		// if (answer == JOptionPane.YES_OPTION) {
		// boolean valid = validateServiceFields();
		// if (valid) {
		// updateService();
		// oldSelectionIndex = lstServices.getSelectedIndex();
		// }
		// } else if (answer == JOptionPane.NO_OPTION) {
		// currentServiceUpdatePending = false;
		// }
		// }
		// }
		if (!currentServiceUpdatePending) {
			SWService service = (SWService) lstServices.getSelectedValue();
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
		if (e.getSource() == btnOk) {
			if (validateServiceFields()) {
				updateService();
				servicesListUpdatePending = false;
				close();
			} else {
				logger.info("Cannot update service fields invalid!");
				JOptionPane
						.showMessageDialog(
								this,
								"Cannot update the service as there are some invalid values. Please correct all fields with a warning symbol.",
								"Cannot update service",
								JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == btnCancel) {
			close();
		} else if (e.getSource() == btnTest) {
			if (validateServiceFields()) {
				this.testConnection();
			} else {
				logger.info("Cannot test the connection service fields not valid!");
			}
		} else if (e.getSource() == btnPingUpdate) {
			updateTableValues();
		}
	}

	private void close() {
		boolean exit = servicesListUpdatePending || currentServiceUpdatePending ? JOptionPane.OK_OPTION == JOptionPane
				.showConfirmDialog(
						ConfigurationDeployerDialog.this,
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

	public final void start_auto_reg() {
		regMng = new Registration_Mngr(deployer, this, CommInterface);
		logger.info("Starting automatic connect to Services Registry");
		regMng.init();
	}

	public final void start_autoRegDialog() {
		if (this.regPopUp == null) {
			this.regPopUp = new RegistrationPopUp(this, this.regMng);
			this.regPopUp.setVisible(true);
		}
	}

	public final void close_autoReg() {
		dispose();
	}

	public final void updateTableValues() {
		regMng.updatePingState();
		RegistrationTableModel model = new RegistrationTableModel(regMng);
		RegTable.setModel(model);
	}

	protected final void updateFrameLabels(final URL url) {
		txtURL.setText(url.getHost());
		txtPort.setValue(url.getPort());
	}
}

/*************** UNUSED IMPLEMENTED FUNCTIONS **************************/
/*
 * private static class ServiceRenderer extends DefaultListCellRenderer {
 * 
 * private static final long serialVersionUID = -7214238324806146528L; private
 * ImageIcon icon;
 * 
 * public ServiceRenderer() { icon = Icon.Connection_16.getIcon(); }
 * 
 * @Override public Component getListCellRendererComponent(JList list, Object
 * value, int index, boolean isSelected, boolean hasFocus) { JLabel label =
 * (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
 * hasFocus); label.setIcon(icon); return (label); } }
 */
