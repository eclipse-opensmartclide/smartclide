package de.atb.context.services;

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


import de.atb.context.services.config.SWServiceConfiguration;
import de.atb.context.services.config.models.ISWService;
import de.atb.context.services.config.models.SWService;
import de.atb.context.services.interfaces.IService;

import java.net.URL;

/**
 *
 * @author Giovanni
 */
public class SWServiceContainer implements ISWService {

	private final String id;
	private final String configFileName;
	private SWService service;

	public SWServiceContainer(final String id, final String configFileName) {
		this.id = id;
		this.configFileName = configFileName;
	}

	protected final boolean hasService() {
		if (this.service == null) {
			SWServiceConfiguration i;
			if (configFileName == null) {
				i = SWServiceConfiguration.getInstance();
			} else {
				i = SWServiceConfiguration.getInstance(this.configFileName);
			}
			if (i != null) {
				this.service = i.getService(this.id);
				if (this.service != null) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public final String getId() {
		return this.id;
	}

	@Override
	public final String getName() {
		if (hasService()) {
			return service.getName();
		}
		return null;
	}

	@Override
	public final String getHost() {
		if (hasService()) {
			return service.getHost();
		}
		return null;
	}

	@Override
	public final URL getLocation() {
		if (hasService()) {
			return service.getLocation();
		}
		return null;
	}

	@Override
	public final Class<? extends IService> getProxyClass() {
		if (hasService()) {
			return service.getProxyClass();
		}
		return null;
	}

	@Override
	public final Class<? extends IService> getServerClass() {
		if (hasService()) {
			return service.getServerClass();
		}
		return null;
	}

	public final SWService getService() {
		if (hasService()) {
			return service;
		}
		return null;
	}

}
