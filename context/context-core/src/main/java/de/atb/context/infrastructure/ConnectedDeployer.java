package de.atb.context.infrastructure;

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


/**
 *
 * @author Giovanni
 */
public class ConnectedDeployer {
	private ConnectedServices services;
	private ServiceInfo config;

	public ConnectedDeployer() {
	}

	public ConnectedDeployer(final ConnectedServices services, final ServiceInfo config) {
		this.services = services;
		this.config = config;
	}

	public final ConnectedServices getServices() {
		return services;
	}

	public final void setServices(final ConnectedServices services) {
		this.services = services;
	}

	public final ServiceInfo getConfig() {
		return config;
	}

	public final void setConfig(final ServiceInfo config) {
		this.config = config;
	}

}
