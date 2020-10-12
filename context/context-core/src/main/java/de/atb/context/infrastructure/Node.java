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
public class Node {
	private ConnectedDeployer deployer;

	public Node() {
	}

	public Node(final ConnectedDeployer deployer) {
		this.deployer = deployer;
	}

	public final ConnectedDeployer getDeployer() {
		return deployer;
	}

	public final void setDeployer(final ConnectedDeployer deployer) {
		this.deployer = deployer;
	}

}
