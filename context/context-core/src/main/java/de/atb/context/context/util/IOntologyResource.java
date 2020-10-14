package de.atb.context.context.util;

/*
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
 * IResourceable
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public interface IOntologyResource {

	/**
	 * Returns the name of this resource within its namespace.
	 *
	 * @return The name of this property within its namespace.
	 */
	String getLocalName();

	/**
	 * Return the URI of the resource (in relation to the given model), or
	 * {@code null} if it's a bnode or does not exist in the given model.
	 *
	 * @return the URI of the resource (in relation to the given model), or
	 *         {@code null} if it's a bnode or does not exist in the given
	 *         model.
	 */
	String getURI();

	/**
	 * Returns the namespace prefix associated with this resource.
	 *
	 * @return the namespace prefix associated with this resource.
	 */
	String getNameSpacePrefix();

	/**
	 * Returns the namespace associated with this resource (in relation to the
	 * given model) or {@code null} if there is no namespace for the
	 * Resource within the given ontology model.
	 *
	 * @return the namespace associated with this resource (in relation to the
	 *         given model) or {@code null} if there is no namespace for
	 *         the Resource within the given ontology model.
	 */
	String getNameSpace();
}
