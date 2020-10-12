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
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import de.atb.context.ui.modules.DetailsFrame;
import de.atb.context.ui.util.Icon;
import de.atb.context.ui.util.interfaces.ICommunicationBetweeMainStarterConfigurationServices;
import de.atb.context.ui.util.interfaces.IUserInterfaceStatus;
import org.slf4j.LoggerFactory;
import de.atb.context.services.config.SWServiceConfiguration;
import de.atb.context.services.config.models.SWService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public class ConfigurationServicesToStart extends JFrame implements
		ActionListener, IUserInterfaceStatus {

	private static final long serialVersionUID = 1L;
	private final org.slf4j.Logger logger = LoggerFactory
			.getLogger(ConfigurationServicesToStart.class);

	private final JFrame window;
	private JButton btnShowLocaServices;
	private JButton btnSeeDetails;
	private JButton btnExit;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnSubmit;
	private JButton btnCancel;
	private JTree treeAvailable;
	private JTree treeSelected;

	private List<SWService> references;
	private final List<String> selectedServicesToStart;
	private final ICommunicationBetweeMainStarterConfigurationServices comm;
	private DetailsFrame detailsFrame;

	public ConfigurationServicesToStart(final List<String> selectedServicesToStart,
			final ICommunicationBetweeMainStarterConfigurationServices comm) {
		this.window = new JFrame("Service Selector");
		this.selectedServicesToStart = selectedServicesToStart;
		this.comm = comm;
		init();
	}

	public final void init() {
		// TODO start asynchronous download of heavy resources
		window.setIconImage(Icon.ProSEco_32.getImage());
		window.setLayout(new BorderLayout());
		FormLayout layout = new FormLayout(
				"p, 4dlu, p, 4dlu, p:grow, 150dlu, 4dlu, p",
				"35dlu, 15dlu, p, 5dlu, p, 10dlu, p");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.border(Borders.DIALOG);

		Border border = new EmptyBorder(5, 5, 5, 5);

		btnShowLocaServices = new JButton(
				"Check Services from Configuration File",
				Icon.Folder_32.getIcon());
		btnShowLocaServices.setHorizontalAlignment(SwingConstants.LEFT);
		btnShowLocaServices.setBorder(border);
		builder.add(btnShowLocaServices, CC.xy(1, 1, CC.LEFT, CC.TOP));

		btnSeeDetails = new JButton("Details", Icon.Details_32.getIcon());
		btnSeeDetails.setHorizontalAlignment(SwingConstants.LEFT);
		btnSeeDetails.setBorder(border);
		builder.add(btnSeeDetails, CC.xy(3, 1, CC.LEFT, CC.TOP));

		btnAdd = new JButton(">>", Icon.Add_16.getIcon());
		btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
		btnAdd.setBorder(border);
		builder.add(btnAdd, CC.xy(3, 5, CC.RIGHT, CC.TOP));

		btnRemove = new JButton("<<", Icon.Remove_16.getIcon());
		btnRemove.setHorizontalAlignment(SwingConstants.LEFT);
		btnRemove.setBorder(border);
		builder.add(btnRemove, CC.xy(6, 5, CC.LEFT, CC.TOP));

		btnExit = new JButton("Exit", Icon.Exit_32.getIcon());
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setBorder(border);
		builder.add(btnExit, CC.xy(8, 1, CC.RIGHT, CC.TOP));

		btnSubmit = new JButton("Submit", Icon.Submit_16.getIcon());
		btnSubmit.setHorizontalAlignment(SwingConstants.LEFT);
		btnSubmit.setBorder(border);
		builder.add(btnSubmit, CC.xy(1, 7, CC.LEFT, CC.TOP));

		btnCancel = new JButton("Cancel", Icon.Cancel_16.getIcon());
		btnCancel.setHorizontalAlignment(SwingConstants.LEFT);
		btnCancel.setBorder(border);
		builder.add(btnCancel, CC.xy(8, 7, CC.RIGHT, CC.TOP));

		treeAvailable = new JTree();
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(
				"Available Services");
		treeAvailable.setModel(new DefaultTreeModel(treeNode));
		JScrollPane scroll = new JScrollPane(treeAvailable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		treeSelected = new JTree();
		DefaultMutableTreeNode treeNode2 = new DefaultMutableTreeNode(
				"Selected Services");
		treeSelected.setModel(new DefaultTreeModel(treeNode2));
		JScrollPane scroll2 = new JScrollPane(treeSelected,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		builder.add(scroll, CC.xyw(1, 3, 3, CC.FILL, CC.FILL));
		builder.add(scroll2, CC.xyw(6, 3, 3, CC.FILL, CC.FILL));
		builder.addSeparator("Service Selector",
				CC.xyw(1, 2, 8, CC.FILL, CC.FILL));
		builder.addSeparator("Commands", CC.xyw(1, 6, 8, CC.FILL, CC.FILL));

		window.add(builder.getPanel(), BorderLayout.NORTH);
		// window.add(scroll, BorderLayout.CENTER);
		// window.add(scroll2, BorderLayout.CENTER);
		window.setMinimumSize(new Dimension(800, 550));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		initializeListeners();

	}

	private void initializeListeners() {
		btnExit.addActionListener(this);
		btnAdd.addActionListener(this);
		btnRemove.addActionListener(this);
		btnSeeDetails.addActionListener(this);
		btnShowLocaServices.addActionListener(this);
		btnSubmit.addActionListener(this);
		btnCancel.addActionListener(this);

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				// deployer.disposeDeployerFrames();
				// deployer.setFrame(null);
				window.dispose();
			}
		});
	}

	private List<SWService> Discovery() {
		SWServiceConfiguration fileManager = SWServiceConfiguration
				.getInstance();
		List<SWService> DiscoveredServices = fileManager.getServices();
		return DiscoveredServices;
	}

	private void AddService() {
		if (this.references.size() > 1) {
			DefaultMutableTreeNode rootSelected = (DefaultMutableTreeNode) this.treeSelected
					.getModel().getRoot();
			// DefaultMutableTreeNode root = (DefaultMutableTreeNode)
			// this.treeAvailable.getModel().getRoot();
			// DefaultMutableTreeNode selectednode = (DefaultMutableTreeNode)
			// this.treeAvailable.getLastSelectedPathComponent();
			TreePath[] paths = this.treeAvailable.getSelectionPaths();
			for (TreePath path : paths) {
				DefaultMutableTreeNode selectednode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				rootSelected.add(selectednode);
				addServiceToStart(selectednode);
			}
			DefaultTreeModel model = new DefaultTreeModel(rootSelected);
			this.treeSelected.setModel(model);

		} else {
			// there is only the discovery/deployer connected
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.treeAvailable
					.getModel().getRoot();
			root.removeAllChildren();
			DefaultTreeModel model = new DefaultTreeModel(root);
			this.treeAvailable.setModel(model);
		}

	}

	private void RemoveService() {
		if (this.references.size() > 1) {
			DefaultMutableTreeNode rootSelected = (DefaultMutableTreeNode) this.treeSelected
					.getModel().getRoot();
			// DefaultMutableTreeNode root = (DefaultMutableTreeNode)
			// this.treeAvailable.getModel().getRoot();
			// DefaultMutableTreeNode selectednode = (DefaultMutableTreeNode)
			// this.treeAvailable.getLastSelectedPathComponent();
			TreePath[] paths = this.treeSelected.getSelectionPaths();
			for (TreePath path : paths) {
				DefaultMutableTreeNode selectednode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				rootSelected.remove(selectednode);
				removeServiceToStart(selectednode);
			}
			DefaultTreeModel model = new DefaultTreeModel(rootSelected);
			this.treeSelected.setModel(model);

		} else {
			// there is only the discovery/deployer connected
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.treeSelected
					.getModel().getRoot();
			root.removeAllChildren();
			DefaultTreeModel model = new DefaultTreeModel(root);
			this.treeSelected.setModel(model);
		}

	}

	private void fillTreeAvailableServices4Client() {

		if (this.references.size() > 1) {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.treeAvailable
					.getModel().getRoot();
			root.removeAllChildren();

			for (SWService element : this.references) {
				String Address = element.getLocation().toString() + "/"
						+ element.getName();
				if (!Address.contains("Deployer")
						&& !Address.contains("Registry")
						&& !Address.contains("Broker")) {
					DefaultMutableTreeNode refNode = new DefaultMutableTreeNode(
							Address);
					root.add(refNode);
				}
			}

			DefaultTreeModel model = new DefaultTreeModel(root);
			this.treeAvailable.setModel(model);
		} else {
			// there is only the discovery/deployer connected
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.treeAvailable
					.getModel().getRoot();
			root.removeAllChildren();
			DefaultTreeModel model = new DefaultTreeModel(root);
			this.treeAvailable.setModel(model);
		}
	}

	private void addServiceToStart(final DefaultMutableTreeNode element) {
		String elementString = (String) element.getUserObject();
		for (SWService service : this.references) {
			if (elementString.contains(service.getLocation().toString())) {
				this.selectedServicesToStart.add(service.getId());
			}
		}
	}

	private void removeServiceToStart(final DefaultMutableTreeNode element) {
		String elementString = (String) element.getUserObject();
		for (SWService service : this.references) {
			if (elementString.contains(service.getLocation().toString())) {
				this.selectedServicesToStart.remove(service.getId());
			}
		}
	}

	private void submit() {
		this.comm.ServiceSelected();

	}

	@Override
	public final void show() {
		this.window.setVisible(true); // To change body of generated methods,
										// choose Tools | Templates.
	}

	public final void close() {
		this.window.dispose();
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		if (e.getSource() == btnExit | e.getSource() == btnCancel) {
			// deployer.setFrame(null);
			window.dispose();
			this.selectedServicesToStart.clear();
			comm.SetNotAlive();
			// this.deployer.disposeDeployerFrames();
		} else if (e.getSource() == btnShowLocaServices) {
			references = Discovery();
			if (!references.isEmpty()) {
				this.fillTreeAvailableServices4Client();
			}
		} else if (e.getSource() == btnSeeDetails) {
			if (detailsFrame == null) {
				detailsFrame = new DetailsFrame(this, this.references);
			} else {
				logger.info("Details Frame already active!");
			}
			detailsFrame.setVisible(true);
		} else if (e.getSource() == btnAdd) {
			this.AddService();
		} else if (e.getSource() == btnRemove) {
			this.RemoveService();
		} else if (e.getSource() == btnSubmit) {
			this.submit();
		}
	}

	@Override
	public final void setDetailsFrameStatus(final DetailsFrame frame) {
		this.detailsFrame = frame;
	}

}
