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

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

/**
 * SPARQLHelper
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public class SPARQLHelper {
	private SPARQLHelper() {}

	public static String appendDefaultPrefixes(final String sparqlQuery) {
		final StringBuilder builder = new StringBuilder();
		builder.append(SPARQLPrefixMappings.getAllAsPrefixString());
		builder.append(sparqlQuery);
		return builder.toString();
	}

	public static synchronized <T> String getRdfNamespace(
			final Class<T> clazz) {
		final Namespace namespaceAnnotation = clazz
				.getAnnotation(Namespace.class);
		if (namespaceAnnotation == null) {
			throw new IllegalArgumentException(
					"Clazz must be annotated with thewebsemantic.Namespace annotation.");
		}
		return namespaceAnnotation.value().endsWith("/") ? namespaceAnnotation
				.value() : namespaceAnnotation.value() + "/";
	}

	public static synchronized <T> String getRdfType(final Class<T> clazz) {
		final RdfType typeAnnotation = clazz.getAnnotation(RdfType.class);
		if (typeAnnotation == null) {
			throw new IllegalArgumentException(
					"Clazz must be annotated with thewebsemantic.RdfType annotation.");
		}
		return typeAnnotation.value();
	}

	public static synchronized <T> String getRdfPropertyQualifier(
            final Class<T> clazz, final String propertyName) {
		return String.format("<%s%s>", SPARQLHelper.getRdfNamespace(clazz),
				propertyName);
	}

	public static synchronized <T> Property createProperty(
            final Class<T> clazz, final String propertyName) {
		return ResourceFactory.createProperty(String.format("%s%s",
				SPARQLHelper.getRdfNamespace(clazz), propertyName));
	}

	public static synchronized <T> String getRdfClassQualifier(
			final Class<T> clazz) {
		return String.format("<%s%s>", SPARQLHelper.getRdfNamespace(clazz),
				SPARQLHelper.getRdfType(clazz));
	}

	public static synchronized <T> String getRdfClassQualifierWithoutBraces(
			final Class<T> clazz) {
		return String.format("%s%s", SPARQLHelper.getRdfNamespace(clazz),
				SPARQLHelper.getRdfType(clazz));
	}
}
