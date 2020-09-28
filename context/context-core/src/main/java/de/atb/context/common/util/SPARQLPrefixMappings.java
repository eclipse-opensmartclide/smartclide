package de.atb.context.common.util;

/*
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

/**
 * SPARQLPrefixMappings
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public enum SPARQLPrefixMappings {

	XSD("xsd", "http://www.w3.org/2001/XMLSchema#"),

	RDF("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#"),

	RDFS("rdfs", "http://www.w3.org/2000/01/rdf-schema#"),

	;

	private String prefix;
    private String url;

	private SPARQLPrefixMappings(final String prefix, final String url) {
		this.prefix = prefix;
		this.url = url;
	}

	@Override
	public String toString() {
		return String.format("PREFIX %s: <%s>", prefix, url);
	}

	public static String getAllAsPrefixString() {
		final StringBuilder builder = new StringBuilder();
		for (final SPARQLPrefixMappings mapping : SPARQLPrefixMappings.values()) {
			builder.append(mapping.toString());
			builder.append("\n");
		}
		return builder.toString();
	}

}
