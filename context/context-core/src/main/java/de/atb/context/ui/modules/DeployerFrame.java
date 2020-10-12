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
import de.atb.context.ui.util.Icon;
import de.atb.context.ui.util.interfaces.ICommunicationBetweenUIs;
import de.atb.context.ui.util.interfaces.IUserInterfaceStatus;
import org.slf4j.LoggerFactory;
import de.atb.context.modules.Deployer;
import de.atb.context.services.config.models.SWService;
import de.atb.context.services.SWServiceContainer;
import de.atb.context.services.manager.ServiceManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
public class DeployerFrame extends JFrame implements ActionListener,
		IUserInterfaceStatus {

	private static final long serialVersionUID = 1L;
	private final org.slf4j.Logger logger = LoggerFactory
			.getLogger(DeployerFrame.class);

	private Deployer deployer = null;
	private ICommunicationBetweenUIs CommInterface = null;
	private DetailsFrame deployerDetailsFrame = null;

	private final JFrame window;
	private JButton btnDiscovery;
	private JButton btnSeeDetails;
	private JButton btnConnect;
	private JButton btnExit;
	private JTree tree;

	private List<SWService> references;

	public DeployerFrame() {
		this.window = new JFrame("Deployer Interface");
		init();
	}

	public DeployerFrame(final Deployer deployer,
			final ICommunicationBetweenUIs CommInterface) {
		this.window = new JFrame("Deployer Interface");
		this.deployer = deployer;
		this.CommInterface = CommInterface;
		init();
	}

	@Override
	public final void show() {
		window.setVisible(true);
	}

	@Override
	public final void dispose() {
		window.dispose();
	}

	public final Component getWindow() {
		return this.window;
	}

	public final void init() {
		// TODO start asynchronous download of heavy resources
		window.setIconImage(de.atb.context.ui.util.Icon.ProSEco_32.getImage());
		window.setLayout(new BorderLayout());
		FormLayout layout = new FormLayout("p, 4dlu, p, 4dlu, p:grow, 4dlu, p",
				"p");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.border(Borders.DIALOG);

		Border border = new EmptyBorder(5, 5, 5, 5);

		btnDiscovery = new JButton("Discover Local Services",
				Icon.Discovery_32.getIcon());
		btnDiscovery.setHorizontalAlignment(SwingConstants.LEFT);
		btnDiscovery.setBorder(border);
		builder.add(btnDiscovery, CC.xy(1, 1, CC.LEFT, CC.TOP));

		btnSeeDetails = new JButton("Details", Icon.Details_32.getIcon());
		btnSeeDetails.setHorizontalAlignment(SwingConstants.LEFT);
		btnSeeDetails.setBorder(border);
		builder.add(btnSeeDetails, CC.xy(3, 1, CC.LEFT, CC.TOP));

		btnConnect = new JButton("Connect", Icon.Connect_32.getIcon());
		btnConnect.setHorizontalAlignment(SwingConstants.LEFT);
		btnConnect.setBorder(border);
		builder.add(btnConnect, CC.xy(5, 1, CC.LEFT, CC.TOP));

		if (this.deployer == null) {
			btnDiscovery.setEnabled(false);
			btnSeeDetails.setEnabled(false);
			btnConnect.setEnabled(false);
		}

		btnExit = new JButton("Exit", Icon.Exit_32.getIcon());
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setBorder(border);
		builder.add(btnExit, CC.xy(7, 1, CC.RIGHT, CC.TOP));

		tree = new JTree();
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("Services");
		tree.setModel(new DefaultTreeModel(treeNode));
		JScrollPane scroll = new JScrollPane(tree,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		window.add(builder.getPanel(), BorderLayout.NORTH);
		window.add(scroll, BorderLayout.CENTER);
		window.setMinimumSize(new Dimension(800, 600));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		initializeListeners();

	}

	private void initializeListeners() {
		btnExit.addActionListener(this);
		btnDiscovery.addActionListener(this);
		btnSeeDetails.addActionListener(this);
		btnConnect.addActionListener(this);

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				// deployer.disposeDeployerFrames();
				// deployer.setFrame(null);
				window.setVisible(false);
			}
		});
	}

	@Override
	public final void actionPerformed(final ActionEvent ae) {
		if (ae.getSource() == btnExit) {
			// deployer.setFrame(null);
			window.setVisible(false);
			// this.deployer.disposeDeployerFrames();
		} else if (ae.getSource() == btnDiscovery) {
			references = Discovery();
			if (!references.isEmpty()) {
				this.fillTreeAvailableServices4Client();
			}
		} else if (ae.getSource() == btnSeeDetails) {
			if (deployerDetailsFrame == null) {
				deployerDetailsFrame = new DetailsFrame(this, this.references);
			} else {
				logger.info("Details Frame already active!");
			}
			deployerDetailsFrame.setVisible(true);
		} else if (ae.getSource() == btnConnect) {
			this.deployer.startConfigurationDialog(this.CommInterface);
		}

	}

	private List<SWService> Discovery() {
		List<SWService> DiscoveredServices = new ArrayList<>();
		for (SWServiceContainer container : ServiceManager.getLSWServiceContainer()) {
			DiscoveredServices.add(container.getService());
		}

		// TODO add your handling code here:
		// WSDiscoveryClient client = new WSDiscoveryClient();
		// ProbeType pt = new ProbeType();
		// pt.getAny();
		// ProbeMatchesType pmt = client.probe(pt);
		// List<EndpointReference> DiscoveredReferences = client.probe();
		// client.close();
		// for (EndpointReference reference : references) {
		// if (reference instanceof W3CEndpointReference) {
		// W3CEndpointReference refW3C = (W3CEndpointReference) reference;
		// String text = reference.toString();
		// String address = text.substring(text.indexOf("<Address>"),
		// text.indexOf("</Address>")).replace("<Address>", "");
		//
		// }
		//
		// }
		return DiscoveredServices;
	}

	private void fillTreeAvailableServices4Client() {

		if (this.references.size() > 1) {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.tree
					.getModel().getRoot();
			root.removeAllChildren();

			for (SWService element : this.references) {
				String wsdlAddress = element.getLocation().toString() + "/"
						+ element.getName() + "?wsdl";
				if (!wsdlAddress.contains("Deployer")) {
					DefaultMutableTreeNode refNode = new DefaultMutableTreeNode(
							wsdlAddress);
					root.add(refNode);
				}
			}

			DefaultTreeModel model = new DefaultTreeModel(root);
			this.tree.setModel(model);
		} else {
			// there is only the discovery/deployer connected
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.tree
					.getModel().getRoot();
			root.removeAllChildren();
			DefaultTreeModel model = new DefaultTreeModel(root);
			this.tree.setModel(model);
		}
	}

	// private String getSelected(EndpointReference reference) {
	// String textReference = reference.toString();
	// String address =
	// textReference.substring(textReference.indexOf("<Address>"),
	// textReference.indexOf("</Address>")).replace("<Address>", "");
	// return address;
	// }
	private String getServiceName(final String address) {
		String[] splittedAddress = address.split("/");
		return splittedAddress[splittedAddress.length - 1];
	}

	public final void unregisterRegistry() {
		this.CommInterface.setDisconnected();
	}

	public final boolean DetailsFrameOn() {
		return deployerDetailsFrame != null;
	}

	public final void DetailsFrameDispose() {
		deployerDetailsFrame.dispose();
	}

	@Override
	public final void setDetailsFrameStatus(final DetailsFrame frame) {
		this.deployerDetailsFrame = frame;
	}

}
