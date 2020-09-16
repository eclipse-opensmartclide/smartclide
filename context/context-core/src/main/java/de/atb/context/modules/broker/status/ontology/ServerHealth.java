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
 * Copyright (C) 2020 ATB
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
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
