package de.atb.context.tools.ontology;

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
