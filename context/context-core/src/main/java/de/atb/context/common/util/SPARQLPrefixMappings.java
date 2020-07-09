package de.atb.context.common.util;

/*
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2016 - 2020 ATB
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
