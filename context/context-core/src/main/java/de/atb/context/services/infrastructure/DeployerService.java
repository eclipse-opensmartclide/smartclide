package de.atb.context.services.infrastructure;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */


import de.atb.context.services.faults.ContextFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.infrastructure.ConnectedServices;
import de.atb.context.modules.Deployer;
import de.atb.context.services.manager.ServiceManager;

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
