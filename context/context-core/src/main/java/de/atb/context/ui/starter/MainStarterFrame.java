package de.atb.context.ui.starter;

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
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import de.atb.context.starter.StarterComponents;
import de.atb.context.ui.config.ConfigurationServicesToStart;
import de.atb.context.ui.config.ServicesConfigurationDialog;
import de.atb.context.ui.util.Icon;
import de.atb.context.ui.util.UIType;
import de.atb.context.ui.util.interfaces.ICommunicationBetweeMainStarterConfigurationServices;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public class MainStarterFrame extends JFrame implements ActionListener,
		ICommunicationBetweeMainStarterConfigurationServices {

	private static final long serialVersionUID = 1L;

	private final org.slf4j.Logger logger = LoggerFactory
			.getLogger(MainStarterFrame.class);

	private JButton btnRegistry;
	private JButton btnDeployer;
	private JButton btnConfig;
	private JButton btnExit;
	private final JFrame window;

	private ConfigurationServicesToStart serviceSelectorFrame = null;
	private List<String> selectedServicesToStart;
	protected StarterComponents serviceFrame = null;
	protected StarterComponents registryFrame = null;

	public MainStarterFrame() {
		this.window = new JFrame("Main Starter Interface");
		init();
	}

	public final void init() {
		this.window.setIconImage(Icon.ProSEco_32.getImage());
		window.setLayout(new BorderLayout());
		FormLayout layout = new FormLayout(
				"p, 4dlu, p, 4dlu, p, 4dlu, p:grow, 4dlu, 6dlu, p", "p");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.border(Borders.DIALOG);

		Border border = new EmptyBorder(5, 5, 5, 5);

		btnDeployer = new JButton("Deployer Starter",
				Icon.DeployerUI_32.getIcon());
		btnDeployer.setHorizontalAlignment(SwingConstants.LEFT);
		btnDeployer.setBorder(border);
		builder.add(btnDeployer, CC.xy(1, 1, CC.LEFT, CC.TOP));

		btnConfig = new JButton("Configure", Icon.Settings_32.getIcon());
		btnConfig.setHorizontalAlignment(SwingConstants.LEFT);
		btnConfig.setBorder(border);
		builder.add(btnConfig, CC.xy(5, 1, CC.LEFT, CC.TOP));

		btnRegistry = new JButton("Service Registry Starter",
				Icon.ServerUI_32.getIcon());
		btnRegistry.setHorizontalAlignment(SwingConstants.LEFT);
		btnRegistry.setBorder(border);
		builder.add(btnRegistry, CC.xy(3, 1, CC.LEFT, CC.TOP));

		btnExit = new JButton("Exit", Icon.Exit_32.getIcon());
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setBorder(border);
		builder.add(btnExit, CC.xy(10, 1, CC.RIGHT, CC.TOP));

		window.add(builder.getPanel(), BorderLayout.NORTH);
		window.setMinimumSize(new Dimension(800, 100));
		initializeListeners();

	}

	private void initializeListeners() {
		btnExit.addActionListener(this);
		btnRegistry.addActionListener(this);
		btnConfig.addActionListener(this);
		btnDeployer.addActionListener(this);
		window.addWindowListener(new CloseMe(this));
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		if (e.getSource() == btnExit) {
			if (serviceFrame != null) {
				serviceFrame.exitClicked(UIType.Deployer.getName());
			}
			if (registryFrame != null) {
				registryFrame.exitClicked(UIType.Registry.getName());
			}
			this.shutdown();
		} else if (e.getSource() == btnRegistry) {
			if (registryFrame == null || !registryFrame.getUIAlive()) {
				try {
					registryFrame = StarterComponents.start(null,
							UIType.Registry.getName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException | URISyntaxException ex) {
					logger.debug(ex.getMessage());
				}
			}
		} else if (e.getSource() == btnConfig) {
			ServicesConfigurationDialog scd = new ServicesConfigurationDialog();
			// scd.setComm(this);
			scd.setVisible(true);
		} else if (e.getSource() == btnDeployer) {
			if (this.serviceSelectorFrame == null
					&& (serviceFrame == null || !serviceFrame.getUIAlive())) {
				this.selectedServicesToStart = new ArrayList<String>();
				this.serviceSelectorFrame = new ConfigurationServicesToStart(
						this.selectedServicesToStart, this);
				this.serviceSelectorFrame.show();
			}
		}
	}

	@Override
	public final void show() {
		window.setVisible(true);
	}

	@Override
	public final void dispose() {
		window.dispose();
	}

	private void shutdown() {
		this.dispose();
		System.exit(0);
	}

	@Override
	public final void ServiceSelected() {
		if (this.selectedServicesToStart.size() > 0) {
			try {
				String[] ids = this.selectedServicesToStart
						.toArray(new String[this.selectedServicesToStart.size() + 1]);
				ids[ids.length - 1] = "deployer";
				this.serviceFrame = StarterComponents.start(ids,
						UIType.Deployer.getName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException
					| URISyntaxException ex) {
				logger.debug(ex.getMessage());
				this.serviceSelectorFrame = null;
			}
			this.serviceSelectorFrame.close();
			this.serviceSelectorFrame = null;
		}
	}

	@Override
	public final void SetNotAlive() {
		this.serviceSelectorFrame = null;
	}

	class CloseMe extends WindowAdapter {

		private MainStarterFrame frame;

		public CloseMe(final MainStarterFrame frame) {
			this.frame = frame;
		}

		@Override
		public void windowClosing(final WindowEvent e) {
			if (frame.serviceFrame != null) {
				frame.serviceFrame.exitClicked(UIType.Deployer.getName());
			}
			if (frame.registryFrame != null) {
				frame.registryFrame.exitClicked(UIType.Registry.getName());
			}
			window.dispose();
		}

	}

}
