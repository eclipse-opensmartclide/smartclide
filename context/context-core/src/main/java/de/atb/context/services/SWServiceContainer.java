package pt.uninova.context.services;

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


import pt.uninova.context.services.config.SWServiceConfiguration;
import pt.uninova.context.services.config.models.ISWService;
import pt.uninova.context.services.config.models.SWService;
import pt.uninova.context.services.interfaces.IService;

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
