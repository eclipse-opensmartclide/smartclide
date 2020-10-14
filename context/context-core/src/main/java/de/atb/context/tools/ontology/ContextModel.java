package de.atb.context.tools.ontology;

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


import java.net.URI;

/**
 * @author scholze
 * @version $LastChangedRevision: 241 $
 */
public class ContextModel {
	private String name;
	private URI location;
	/**
	 * @param name The name of the context model to be used
	 * @param location The location of the context model to be used
	 */
	public ContextModel(final String name, final URI location) {
		super();
		this.name = name;
		this.location = location;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the location
	 */
	public URI getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(URI location) {
		this.location = location;
	}
}
