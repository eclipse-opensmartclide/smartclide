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
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import de.atb.context.ui.modules.table.ServicesTableCellRender;
import de.atb.context.ui.modules.table.ServicesTableModel;
import de.atb.context.ui.util.Icon;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.CompoundHighlighter;
import org.jdesktop.swingx.decorator.FontHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.slf4j.LoggerFactory;
import de.atb.context.infrastructure.Node;
import de.atb.context.infrastructure.Nodes;
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.modules.Server;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public final class ServerFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final org.slf4j.Logger logger = LoggerFactory
			.getLogger(ServerFrame.class);

	private Server server = null;

	private JFrame window;
	private JXTable servicesTable;
	private JButton btnExit;
	private JButton btnUpdate;

	public ServerFrame() {
		this.window = new JFrame("Server Interface");
		// init();
	}

	public ServerFrame(final Server server) {
		this.window = new JFrame("Server Interface");
		this.server = server;
		init();
	}

	@Override
	public void show() {
		window.setVisible(true);
	}

	@Override
	public void dispose() {
		window.dispose();
	}

	public Component getWindow() {
		return this.window;
	}

	public void init() {
		// TODO start asynchronous download of heavy resources
		window.setIconImage(Icon.ProSEco_32.getImage());
		window.setLayout(new BorderLayout());
		FormLayout layout = new FormLayout("p, p, 4dlu, p:grow, 4dlu, p, p",
				"p, 4dlu, 105dlu:grow,4dlu, p");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.border(Borders.DIALOG);

		// Border border = new EmptyBorder(5, 5, 5, 5);
		Nodes deployers = this.server.getServices();
		List<ServiceInfo> serviceConfigs = new ArrayList<>();
		if (deployers.getNodes() != null) {
			for (Node deployer : deployers.getNodes()) {
				serviceConfigs.addAll(deployer.getDeployer().getServices()
						.getConfig());
			}
		}
		ServicesTableModel model = new ServicesTableModel(serviceConfigs);
		servicesTable = new JXTable(model);
		servicesTable.setShowGrid(false, false);
		servicesTable.setRowSelectionAllowed(true);
		servicesTable.setColumnSelectionAllowed(false);
		servicesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		servicesTable.setDefaultRenderer(Object.class,
				new ServicesTableCellRender());

		JScrollPane servicesTableContainer = new JScrollPane(servicesTable);
		builder.addSeparator("<html><b>Services:</b></html>", CC.xyw(1, 1, 4));
		builder.add(servicesTableContainer, CC.xyw(2, 3, 6, CC.FILL, CC.FILL));

		FontHighlighter fh = new FontHighlighter(HighlightPredicate.IS_FOLDER,
				servicesTable.getFont().deriveFont(Font.BOLD));
		FontHighlighter fh2 = new FontHighlighter(HighlightPredicate.IS_LEAF,
				servicesTable.getFont().deriveFont(Font.PLAIN));
		servicesTable.setHighlighters(new CompoundHighlighter(
				HighlighterFactory.createAlternateStriping(), fh, fh2));

		Border border = new EmptyBorder(5, 5, 5, 5);

		btnExit = new JButton("Exit", Icon.Exit_32.getIcon());
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setBorder(border);
		builder.add(btnExit, CC.xy(7, 1, CC.RIGHT, CC.TOP));

		btnUpdate = new JButton("Update", Icon.Update_3_32.getIcon());
		btnUpdate.setHorizontalAlignment(SwingConstants.LEFT);
		btnUpdate.setBorder(border);
		builder.add(btnUpdate, CC.xy(6, 1, CC.RIGHT, CC.TOP));

		window.add(builder.getPanel(), BorderLayout.CENTER);
		window.setPreferredSize(new Dimension(800, 600));
		window.setMinimumSize(new Dimension(800, 600));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.pack();
		servicesTable.setFillsViewportHeight(true);
		window.setVisible(true);
		initializeListeners();

	}

	private void initializeListeners() {
		btnExit.addActionListener(this);
		btnUpdate.addActionListener(this);
		// btnDiscovery.addActionListener(this);
		// btnSeeDetails.addActionListener(this);
		// btnConnect.addActionListener(this);
		//
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				server.setFrame(null);
				window.dispose();
			}
		});
	}

	@Override
	public void actionPerformed(final ActionEvent ae) {
		if (ae.getSource() == btnExit) {
			server.setFrame(null);
			window.dispose();
		} else if (ae.getSource() == btnUpdate) {
			this.updateTable();
		}
	}

	private void updateTable() {
		Nodes deployers = this.server.getServices();
		List<ServiceInfo> serviceConfigs = new ArrayList<>();
		if (deployers != null && deployers.getNodes() != null) {
			for (Node deployer : deployers.getNodes()) {
				serviceConfigs.addAll(deployer.getDeployer().getServices()
						.getConfig());
			}
		}
		ServicesTableModel model = new ServicesTableModel(serviceConfigs);
		servicesTable.setModel(model);
	}

}
