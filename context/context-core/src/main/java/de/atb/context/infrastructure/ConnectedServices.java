package de.atb.context.infrastructure;

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


import java.util.List;

/**
 *
 * @author Giovanni
 */
public class ConnectedServices {
	private ServiceInfo deployer;
	private List<ServiceInfo> config;

	public ConnectedServices() {
	}

	public ConnectedServices(final ServiceInfo deployer, final List<ServiceInfo> config) {
		this.deployer = deployer;
		this.config = config;
	}

	public final ServiceInfo getDeployer() {
		return deployer;
	}

	public final void setDeployer(final ServiceInfo deployer) {
		this.deployer = deployer;
	}

	public final List<ServiceInfo> getConfig() {
		return config;
	}

	public final void setConfig(final List<ServiceInfo> config) {
		this.config = config;
	}

}
