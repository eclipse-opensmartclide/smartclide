package de.atb.context.common.util;

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
