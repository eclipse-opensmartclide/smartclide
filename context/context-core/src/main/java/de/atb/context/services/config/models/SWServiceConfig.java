/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.services.config.models;

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
