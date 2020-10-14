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
import org.simpleframework.xml.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.services.interfaces.IService;

import java.net.URL;

/**
 *
 * @author Giovanni
 */
public class SWService implements ISWService {

	private final Logger logger = LoggerFactory.getLogger(SWService.class);

	/**
	 * Represents the Id of an Service. This is used for mapping keys, to lookup
	 * Services.
	 */
	@Attribute
	protected String id;

	@Element(name = "host")
	protected String host;

	/**
	 * Represents the physical location of the Service (usually this is a URL).
	 */
	@Element(name = "location")
	protected URL location;

	/**
	 * Represents the name of the Service which serves as the name of the
	 * ServletMapping.
	 */
	@Element(name = "name")
	protected String name;

	/**
	 * Represents the name of the class that is used for creating a server for
	 * the service.
	 */
	@Element(name = "server")
	protected String serverClass;

	/**
	 * Represents the name of the class that is used for creating a client proxy
	 * for the service.
	 */
	@Element(name = "proxy")
	protected String proxyClass;

	/**
	 * Gets the Id of the Service.
	 *
	 * @return the Id of the Service.
	 */
	@Override
	public final String getId() {
		return this.id;
	}

	/**
	 * Sets the Id of the Service.
	 *
	 * @param id
	 *            the Id of the Service.
	 */
	public final void setId(final String id) {
		this.id = id;
	}

	@Override
	public final String getHost() {
		return host;
	}

	public final void setHost(final String host) {
		this.host = host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.selflearning.services.config.models.IService#getLocation()
	 */
	@Override
	public final URL getLocation() {
		return this.location;
	}

	public final void setLocation(final URL location) {
		this.location = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.selflearning.services.config.models.IService#getServerClass()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final Class<? extends IService> getServerClass() {
		try {
			return (Class<? extends IService>) Class
					.forName(this.serverClass);
		} catch (ClassNotFoundException e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}

	public final void setServerClass(final Class<? extends IService> serverClass) {
		this.serverClass = serverClass.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.selflearning.services.config.models.IService#getProxyClass()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final Class<? extends IService> getProxyClass() {
		try {
			return (Class<? extends IService>) Class
					.forName(this.proxyClass);
		} catch (ClassNotFoundException e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}

	public final void setProxyClass(final Class<? extends IService> proxyClass) {
		this.proxyClass = proxyClass.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.selflearning.services.config.models.IService#getName()
	 */
	@Override
	public final String getName() {
		return this.name;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	@Override
	public final String toString() {
		return this.name + " (" + this.location + ")";
	}
}
