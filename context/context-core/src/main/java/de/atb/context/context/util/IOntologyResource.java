package de.atb.context.context.util;

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
