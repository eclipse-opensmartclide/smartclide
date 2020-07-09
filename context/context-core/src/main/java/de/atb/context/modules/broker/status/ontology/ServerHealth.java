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
