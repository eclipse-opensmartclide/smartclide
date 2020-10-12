/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.modules.broker.status.ontology;

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


/**
 *
 * @author Giovanni
 */
public class ServerHealth {
	private String name;
	private String uptime;
	private String status;
	private String host;
	private String port;

	public ServerHealth() {
	}

	public ServerHealth(final String name, final String uptime, final String status, final String host,
			final String port) {
		this.name = name;
		this.uptime = uptime;
		this.status = status;
		this.host = host;
		this.port = port;
	}

	public final String getName() {
		return name;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	public final String getUptime() {
		return uptime;
	}

	public final void setUptime(final String Uptime) {
		this.uptime = Uptime;
	}

	public final String getStatus() {
		return status;
	}

	public final void setStatus(final String status) {
		this.status = status;
	}

	public final String getHost() {
		return host;
	}

	public final void setHost(final String host) {
		this.host = host;
	}

	public final String getPort() {
		return port;
	}

	public final void setPort(final String port) {
		this.port = port;
	}

}
