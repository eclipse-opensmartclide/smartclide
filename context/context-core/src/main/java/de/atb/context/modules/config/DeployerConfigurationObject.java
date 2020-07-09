package pt.uninova.context.modules.config;

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
public class DeployerConfigurationObject {
	private String port;
	private String url;
	private String address;

	public DeployerConfigurationObject(final String port, final String url, final String address) {
		this.port = port;
		this.url = url;
		this.address = address;
	}

	public final String getPort() {
		return port;
	}

	public final void setPort(final String port) {
		this.port = port;
	}

	public final String getURL() {
		return url;
	}

	public final void setURL(final String url) {
		this.url = url;
	}

	public final String getAddress() {
		return address;
	}

	public final void setAddress(final String address) {
		this.address = address;
	}

}
