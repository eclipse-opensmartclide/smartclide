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


import java.util.List;

/**
 *
 * @author Giovanni
 */
public class ConnectedServices {
	private ServiceInfo deployer;
	private List<ServiceInfo> config;

	public ConnectedServices() {
	}

	public ConnectedServices(final ServiceInfo deployer, final List<ServiceInfo> config) {
		this.deployer = deployer;
		this.config = config;
	}

	public final ServiceInfo getDeployer() {
		return deployer;
	}

	public final void setDeployer(final ServiceInfo deployer) {
		this.deployer = deployer;
	}

	public final List<ServiceInfo> getConfig() {
		return config;
	}

	public final void setConfig(final List<ServiceInfo> config) {
		this.config = config;
	}

}
