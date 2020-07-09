/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.infrastructure;

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
public class ServiceInfo {
	private String id;
	private String name;
	private String host;
	private String proxy;
	private String server;
	private String location;
	private String type;
	private String status;

	public ServiceInfo() {
		//
	}

	public final String getId() {
		return id;
	}

	public final void setId(final String id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	public final String getHost() {
		return host;
	}

	public final void setHost(final String host) {
		this.host = host;
	}

	public final String getProxy() {
		return proxy;
	}

	public final void setProxy(final String proxy) {
		this.proxy = proxy;
	}

	public final String getServer() {
		return server;
	}

	public final void setServer(final String server) {
		this.server = server;
	}

	public final String getLocation() {
		return location;
	}

	public final void setLocation(final String location) {
		this.location = location;
	}

	public final String getType() {
		return type;
	}

	public final void setType(final String type) {
		this.type = type;
	}

	public final String getStatus() {
		return status;
	}

	public final void setStatus(final String status) {
		this.status = status;
	}

}
