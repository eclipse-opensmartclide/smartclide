package pt.uninova.context.tools.ontology;

/*-
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
