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
 * Copyright (C) 2016 - 2020 ATB
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
