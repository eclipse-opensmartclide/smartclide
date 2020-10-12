package de.atb.context.services.registration;

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

/**
 *
 * @author Guilherme
 */
public class SWRegister implements ISWRegister {

	@Attribute
	protected String date;

	@Element(name = "port")
	protected String port;

	@Element(name = "URL")
	protected String URL;

	@Element(name = "address")
	protected String address;

	@Override
	public final String getDate() {
		return this.date;
	}

	@Override
	public final String getPort() {
		return this.port;
	}

	@Override
	public final String getURL() {
		return this.URL;
	}

	@Override
	public final String getAddress() {
		return this.address;
	}

	@Override
	public final void setDate(final String date) {
		this.date = date;
	}

	@Override
	public final void setURL(final String url) {
		this.URL = url;
	}

	@Override
	public final void setPort(final String port) {
		this.port = port;
	}

	@Override
	public final void setAddress(final String address) {
		this.address = address;
	}
}
