/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.modules;

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


import de.atb.context.ui.modules.ServerFrame;
import org.slf4j.LoggerFactory;
import de.atb.context.services.infrastructure.IDeployerService;
import de.atb.context.infrastructure.Node;
import de.atb.context.infrastructure.Nodes;
import de.atb.context.services.manager.ServiceManager;
import de.atb.context.services.wrapper.ServiceRegistryRepositoryServiceWrapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giovanni
 */
public final class Server {
	private static final long serialVersionUID = 1L;
	private final org.slf4j.Logger logger = LoggerFactory
			.getLogger(Server.class);
	private ServerFrame frame = null;
	private IDeployerService client = null;
	private ServiceRegistryRepositoryServiceWrapper repos;

	public Server() {
		//
	}

	public Nodes getServices() {
		return repos.getConnectedServices();
	}

	public void startServerFrame() {
		if (frame == null) {
			frame = new ServerFrame(this);
		} else {
			logger.info("Server Frame already active");
		}
		frame.show();
	}

	public void setRepos(final ServiceRegistryRepositoryServiceWrapper repos) {
		this.repos = repos;
	}

	public void setFrame(final ServerFrame frame) {
		this.frame = frame;
	}

	public void notifyDeployers() {
		/*
		 * only for 1 deployer connected. to be changed if several deployers
		 * with same serviceID are able to be connected
		 */
		Nodes connectedDeployers = this.repos.getConnectedServices();
		if (connectedDeployers.getNodes() == null) {
			logger.info("No deployers connected to the registry");
		} else {
			logger.info("Number of Deployers to notify = "
					+ connectedDeployers.getNodes().size());
			List<Node> deployers = connectedDeployers.getNodes();
			for (Node deployer : deployers) {
				try {
					URL location = new URL(deployer.getDeployer().getConfig()
							.getLocation());
					this.client = ServiceManager
							.getWebservice(location.getHost(),
									location.getPort(), IDeployerService.class);
					if (client != null) {
						client.unregisterRegistry();
					} else {
						logger.info("Unable to get Deployer Service");
					}
				} catch (MalformedURLException ex) {
					Logger.getLogger(Server.class.getName()).log(Level.SEVERE,
							null, ex);
				}
			}
		}
		client = null;
		logger.info("finished Deployers notifications!!");
	}
}
