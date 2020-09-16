package de.atb.context.modules.config;

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
