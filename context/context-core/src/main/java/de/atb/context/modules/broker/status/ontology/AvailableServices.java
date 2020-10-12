package de.atb.context.modules.broker.status.ontology;

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


import de.atb.context.infrastructure.Nodes;

/**
 *
 * @author Giovanni
 */
public class AvailableServices {
	private Nodes services = new Nodes();

	public AvailableServices() {
	}

	public AvailableServices(final Nodes services) {
		this.services = services;
	}

	public final Nodes getServices() {
		return services;
	}

	public final void setServices(final Nodes services) {
		this.services = services;
	}

}
