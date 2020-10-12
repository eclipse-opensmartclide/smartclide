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


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public class Nodes {
	private List<Node> nodes = new ArrayList();

	public Nodes() {
	}

	public Nodes(final List<Node> nodes) {
		this.nodes = nodes;
	}

	public final List<Node> getNodes() {
		return nodes;
	}

	public final void setNodes(final List<Node> nodeList) {
		this.nodes = nodeList;
	}

}
