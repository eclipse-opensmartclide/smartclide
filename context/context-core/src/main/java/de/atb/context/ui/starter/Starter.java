package de.atb.context.ui.starter;

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


import de.atb.context.ui.util.UIType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.cxf.endpoint.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.uninova.context.services.SWServiceContainer;
import pt.uninova.context.services.manager.ServiceManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public abstract class Starter {

	private final Logger logger = LoggerFactory.getLogger(Starter.class);

	protected String configFilePath;
	private String[] ServiceIds;

	protected boolean initialized = false;
	protected boolean configuredService = false;
	private boolean running = false;

	protected StarterFrame frame;

	private List<Server> servers = new ArrayList<>();

	protected Starter(final StarterFrame frame, final String configFilePath) {
		this.frame = frame;
		this.configFilePath = configFilePath;
		reloadConfiguration();
	}

	protected Starter(final String[] args, final String[] ServiceIds) {
		this.ServiceIds = ServiceIds;
		if (args.length > 0) {
			this.configFilePath = args[0];
		} else {
			System.err
					.println("Please specify configuration xml file as first argument!");
			System.exit(-1);
		}
		System.out.println(this.configFilePath);
	}

	public final void validateStartupParameters() {
		if ((configFilePath == null) || configFilePath.isEmpty()) {
			exitWithErrorMessage("The monitoring configuration file is not specified! Please provide a path / filename as first command line argument, quitting now!");
		}
		validateAdditionalStartupParameters();
	}

	public abstract void validateAdditionalStartupParameters();

	protected final void exitWithErrorMessage(final String message) {
		System.err.println(message);
		System.exit(-1);
	}

	public final void start() throws InterruptedException, InvocationTargetException {
		// SwingUtilities.invokeAndWait(new Runnable() {
		// @Override
		// public void run() {
		// frame.show();
		// }
		// });
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				frame.show();
			}
		});
		t.run();
	}

	private void init() {
		// Start the manager first
		if (this.ServiceIds.length > 0) {
			// Start repositories Services
			String[] reducedArray = ArrayUtils.clone(ServiceIds);
			for (String serviceId : this.ServiceIds) {
				if (serviceId.contains("repository")||serviceId.contains("Repository")) {
					SWServiceContainer service = new SWServiceContainer(
							serviceId, this.configFilePath);
					// AtomicReference<JaxWsServerFactoryBean> ref = new
					// AtomicReference<>();
					servers.add(ServiceManager.registerWebservice(service));
					ServiceManager.getLSWServiceContainer().add(service);
					reducedArray = ArrayUtils.removeElement(reducedArray,
							serviceId);
				}

			}
			// Start Services
			for (String serviceId : reducedArray) {
				SWServiceContainer service = new SWServiceContainer(serviceId,
						this.configFilePath);
				// AtomicReference<JaxWsServerFactoryBean> ref = new
				// AtomicReference<>();
				Server server = ServiceManager.registerWebservice(service);
				if (server != null) {
					servers.add(server);
					ServiceManager.getLSWServiceContainer().add(service);
				}
			}
		}
		// SWServiceContainer AmIService = new
		// SWServiceContainer("AmI-monitoring");
		// servers.add(ServiceManager.registerWebservice(AmIService));
		// SWServiceContainer ContextService = new
		// SWServiceContainer("context-extraction");
		// servers.add(ServiceManager.registerWebservice(ContextService));
		// SWServiceContainer DataMiningService = new
		// SWServiceContainer("DataMining-process");
		// servers.add(ServiceManager.registerWebservice(DataMiningService));
		// SWServiceContainer EcoService = new
		// SWServiceContainer("EcoAndOptimisation-process");
		// servers.add(ServiceManager.registerWebservice(EcoService));
		// SWServiceContainer ProvisionService = new
		// SWServiceContainer("knowledge-provisioning");
		// servers.add(ServiceManager.registerWebservice(ProvisionService));
		// servers.add(ServiceManager.registerWebservice(ContextExtractionService.class));
		// servers.add(ServiceManager.registerWebservice(KnowledgeProvisioningService.class));
		// servers.add(ServiceManager.registerWebservice(EcoAndOptimisationService.class));
		// servers.add(ServiceManager.registerWebservice(DataMiningService.class));

		// this.contextRepository = new ContextRepositoryServiceWrapper(
		// (IContextRepositoryService)
		// ServiceManager.getWebservice(Service.ContextRepositoryService));
		// this.monitoringDataRepository = (MonitoringDataRepository<Output>)
		// MonitoringDataRepository.getInstance();
		// this.adapterService =
		// ServiceManager.getWebservice(Service.AdapterService);
		initialized = true;
		// addListeners();
	}

	private void startProcess() {
		if (!initialized) {
			this.init();
		}
		reloadConfiguration();
	}

	protected void reloadConfiguration() {

	}

	public final void configClicked() {
		logger.debug("config clicked!");
	}

	public final void exitClicked(final String type) {
		logger.debug("Quitting, stopping all running processes!");

		if (running) {
			/* Notifications upon exitclicked or closing window */
			if (type.equals(UIType.Deployer.getName())) {
				this.InformServer();
				running = false;
			} else if (type.equals(UIType.Registry.getName())) {
				// ////Search for Deployers and send notification of closing
				// registry///
				this.InformDeployers();
				running = false;
			}
		}
		frame.starter.setUIAlive();
		frame.dispose();
		this.shutdown();
	}

	public final void startStopClicked(final String type) {
		logger.info("Startstop clicked!");
		if (!running) {
			frame.startProcess();
			this.startProcess();
			running = true;
		} else {
			frame.stopProcess();
			if (type.equals(UIType.Deployer.getName())) {
				this.InformServer();
				if (ServiceManager.getDeployer().isDeployerFrameOn()) {
					ServiceManager.getDeployer().disposeDeployerFrames();
				}
			} else if (type.equals(UIType.Registry.getName())) {
				// ////Search for Deployers and send notification of closing
				// registry///
				this.InformDeployers();
			}
			this.stopServers();
			frame.setDisconnected();
			this.servers.clear();
			ServiceManager.getLSWServiceContainer().clear();
			running = false;
			configuredService = false;
			initialized = false;
		}
	}

	public final void startDeployerFrame() {
		if (ServiceManager.getDeployer() != null) {
			ServiceManager.getDeployer().startDeployerFrame(this.frame);
		}
	}

	public final void startServerFrame() {
		if (ServiceManager.getServer() != null) {
			ServiceManager.getServer().startServerFrame();
		}
	}

	private void shutdown() {
		for (Server server : servers) {
			ServiceManager.shutdownServiceAndEngine(server);
		}

		// System.exit(0);
	}

	private void InformServer() {
		ServiceManager.getDeployer().unregisterServices();
	}

	private void stopServers() {
		for (Server server : servers) {
			ServiceManager.shutdownServiceAndEngine(server);
		}

	}

	private void InformDeployers() {
		ServiceManager.getServer().notifyDeployers();
	}

}
