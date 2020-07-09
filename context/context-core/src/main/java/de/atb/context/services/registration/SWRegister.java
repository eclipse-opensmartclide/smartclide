package de.atb.context.services.registration;

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
