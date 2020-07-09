/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uninova.context.services.config.models;

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


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

import java.util.*;

/**
 *
 * @author Giovanni
 */
@Root
public class SWServiceConfig implements ISWServiceConfiguration {

	@Attribute(required = false)
	protected String schemaLocation;

	/**
	 * Represents the List of Services from the XML configuration file.
	 */
	@ElementList(name = "services", entry = "service")
	protected List<SWService> services = new ArrayList<>();

	/**
	 * Maintains a Map of Services, Services are identified by their ids.
	 */
	protected Map<String, SWService> servicesMap = new HashMap<>();

	public final void setServices(final Collection<SWService> services) {
		this.services = new ArrayList<>(services);
		createIdMappings();
	}

	@Override
	public final List<SWService> getServices() {
		return this.services;
	}

	@Override
	public final SWService getService(final String id) {
		return this.servicesMap.get(id);
	}

	/**
	 * Maps all Lists retrieved from the XML configuration file to Maps, so that
	 * FileSets, DataSources and Indexes are accessible via their ids.
	 */
	@Commit
	protected final void createIdMappings() {
		this.servicesMap.clear();
		for (SWService service : this.services) {
			this.servicesMap.put(service.getId(), service);
		}
	}

}
