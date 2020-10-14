package de.atb.context.services.config.models;

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


import de.atb.context.services.interfaces.IService;

import java.net.URL;

/**
 *
 * @author Giovanni
 */
public interface ISWService {

	String getId();

	String getName();

	String getHost();

	URL getLocation();

	Class<? extends IService> getProxyClass();

	Class<? extends IService> getServerClass();

}
