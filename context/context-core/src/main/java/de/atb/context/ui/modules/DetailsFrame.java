package de.atb.context.ui.modules;

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
import de.atb.context.ui.util.interfaces.IUserInterfaceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.services.config.models.SWService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public class DetailsFrame extends JDialog implements ListSelectionListener,
		ActionListener {

	private final Logger logger = LoggerFactory.getLogger(DetailsFrame.class);

	private JButton btnOk;
	private JButton btnCancel;
	private JLabel lblWarnServer, lblWarnProxy, lblWarnPort, lblWarnUrl;
	private JList<?> lstServices;
	private JTextField txtURL;
	private JTextField txtPort;
	private JTextField txtServerClass;
	private JTextField txtProxyClass;
	private IUserInterfaceStatus associatedFrame;
	private final SWService[] SWServiceArray;
	private SWService currentService;

	public DetailsFrame(final IUserInterfaceStatus associatedFrame,
			final List<SWService> LSWService) {
		this.SWServiceArray = intializeArray(LSWService);
		this.associatedFrame = associatedFrame;
		initializeComponents();
		if (lstServices.getModel().getSize() > 0) {
			lstServices.setSelectedIndex(0);
		}

	}

	private SWService[] intializeArray(final List<SWService> LSWService) {
		List<SWService> aux = new ArrayList<>();
		if (LSWService != null) {
			for (SWService service : LSWService) {
				if (!service.getId().contains("service-manager")) {
					aux.add(service);
				}
			}
		}
		SWService[] aux2 = new SWService[aux.size()];
		return aux.toArray(aux2);
	}

	protected final void initializeComponents() {
		lstServices = new JList<Object>(this.SWServiceArray);
		btnOk = new JButton("Done", de.atb.context.ui.util.Icon.Ok_16.getIcon());
		btnOk.addActionListener(this);
		btnCancel = new JButton("Cancel", Icon.Cancel_16.getIcon());
		btnCancel.addActionListener(this);
		txtURL = new JTextField("localhost");
		txtURL.setEditable(false);
		txtPort = new JTextField("Port");
		txtPort.setEditable(false);
		txtServerClass = new JTextField();
		txtServerClass.setEditable(false);
		txtProxyClass = new JTextField();
		txtProxyClass.setEditable(false);

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
		lstServices.setCellRenderer(new DetailsFrame.ServiceRenderer());

		setLayout(new BorderLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		FormLayout layout = new FormLayout(
				"right:max(55dlu;p), 4dlu, p:grow, 4dlu, p, 4dlu",
				"p, 4dlu, max(35dlu;p), 8dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 8dlu, p, 4dlu, p");
		PanelBuilder builder = new PanelBuilder(layout);
		JComponent comp = builder.addSeparator("Services:",
				CC.xyw(1, 1, 6, CC.FILL, CC.FILL));
		comp.getComponent(0).setFont(comp.getFont().deriveFont(Font.BOLD));

		builder.add(lstServicesScroller, CC.xyw(1, 3, 6, CC.FILL, CC.FILL));
		builder.add(new JLabel("URL:"), CC.xy(1, 5));
		builder.add(txtURL, CC.xy(3, 5));
		// builder.add(lblWarnUrl, CC.xy(6, 5));
		builder.add(new JLabel("Port:"), CC.xy(1, 7));
		builder.add(txtPort, CC.xy(3, 7, CC.LEFT, CC.FILL));
		// builder.add(lblWarnPort, CC.xy(6, 7));
		builder.add(new JLabel("Server Class:"), CC.xy(1, 9));
		builder.add(txtServerClass, CC.xyw(3, 9, 3));
		// builder.add(lblWarnServer, CC.xy(6, 9));
		builder.add(new JLabel("Proxy Class:"), CC.xy(1, 11));
		builder.add(txtProxyClass, CC.xyw(3, 11, 3));
		// builder.add(lblWarnProxy, CC.xy(6, 11));
		builder.addSeparator("", CC.xyw(1, 15, 6, CC.FILL, CC.FILL));
		builder.add(btnOk, CC.xy(3, 17, CC.RIGHT, CC.FILL));
		builder.add(btnCancel, CC.xy(5, 17, CC.FILL, CC.FILL));
		builder.border(new EmptyBorder(4, 4, 4, 4));

		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void windowClosing(final WindowEvent e) {
				associatedFrame.setDetailsFrameStatus(null);
				close();
			}
		});

		setResizable(false);
		setMinimumSize(new Dimension(450, 250));
		add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
	}

	protected final void populateServiceEditControls(final SWService service) {
		if (service != null) {
			currentService = service;
			logger.trace("Now editing service " + service + "!");
			URL location = service.getLocation();
			if (location != null) {
				txtURL.setText(location.getHost() != null ? service
						.getLocation().getHost() : "localhost");
				txtPort.setText(Integer.toString(location.getPort()));
			} else {
				txtURL.setText("localhost");
				txtPort.setText(Integer.toString(65535));
			}
			txtServerClass.setText(service.getServerClass() != null ? service
					.getServerClass().getName() : "");
			txtProxyClass.setText(service.getProxyClass() != null ? service
					.getProxyClass().getName() : "");
		} else {
			logger.warn("Cannot edit service that is null!");
		}
	}

	@Override
	public final void actionPerformed(final ActionEvent ae) {
		if (ae.getSource() == btnOk) {
			associatedFrame.setDetailsFrameStatus(null);
			dispose();
		} else if (ae.getSource() == btnCancel) {
			associatedFrame.setDetailsFrameStatus(null);
			dispose();
		}
	}

	private void close() {
		dispose();
	}

	@Override
	public final void valueChanged(final ListSelectionEvent lse) {
		SWService service = (SWService) lstServices.getSelectedValue();
		populateServiceEditControls(service);
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

}
