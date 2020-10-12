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


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public class DeploymentStatus {
	private List<Deployment> deployments;

	public DeploymentStatus() {
		this.deployments = new ArrayList<>();
	}

	public final void setDeployments(final List<Deployment> deployments) {
		this.deployments = deployments;
	}

	public final List<Deployment> getDeployments() {
		return this.deployments;
	}

}
