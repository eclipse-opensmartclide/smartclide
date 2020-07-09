package pt.uninova.context.services.infrastructure;

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


import de.atb.context.services.faults.ContextFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.uninova.context.infrastructure.ConnectedServices;
import pt.uninova.context.modules.Deployer;
import pt.uninova.context.services.manager.ServiceManager;

/**
 *
 * @author Giovanni
 */
public final class DeployerService implements IDeployerService {

	private static final Logger logger = LoggerFactory
			.getLogger(DeployerService.class.getName());
	private final Deployer deployer = new Deployer();

	public DeployerService() {
		initializeServices();
	}

	@Override
	public void start() throws ContextFault {
		logger.info(String.format("Starting %s ...", this.getClass()
				.getSimpleName()));
	}

	@Override
	public void stop() throws ContextFault {
		logger.info(String.format("Stopping %s ...", this.getClass()
				.getSimpleName()));
	}

	@Override
	public void restart() throws ContextFault {
		logger.info(String.format("Restarting %s ...", this.getClass()
				.getSimpleName()));
	}

	@Override
	public String ping() throws ContextFault {
		logger.info(String.format("%s was pinged", this.getClass()
				.getSimpleName()));
		return ServiceManager.PING_RESPONSE;
	}

	private synchronized void initializeServices() {
		// deployer.startDeployerFrame();
	}

	@Override
	public ConnectedServices getConnectedServices() {
		// for (ConnectedServicesResponse element :
		// this.deployer.getMapServiceManagerConnectedServices()) {
		// for (SWService serviceElement : element.getLSWService()) {
		// if (serviceElement.getLocation().equals(service.getLocation())) {
		// return
		// this.deployer.getMapServiceManagerConnectedServices().remove(element);
		// }
		// }
		// }
		return null;
	}

	public Deployer getDeployerInstance() {
		return this.deployer;
	}

	@Override
	public boolean unregisterRegistry() {
		this.deployer.unregisterRegistry();
		return true;
	}

}
