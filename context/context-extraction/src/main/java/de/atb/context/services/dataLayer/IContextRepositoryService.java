package de.atb.context.services.dataLayer;

/*
 * #%L
 * ATB Context Extraction Core Service
 * %%
 * Copyright (C) 2018 - 2019 ATB
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import de.atb.context.services.interfaces.IRepositoryService;
import de.atb.context.services.interfaces.IRepositoryService;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 *
 * @author Giovanni
 */
@WebService(name = "ContextRepositoryService", targetNamespace = "http://www.atb-bremen.de")
@SOAPBinding(style = Style.DOCUMENT)
public interface IContextRepositoryService extends IRepositoryService {
    
}
