package de.atb.context.services;

/*
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.services.interfaces.IService;
import de.atb.context.tools.ontology.AmIMonitoringConfiguration;

/**
 * $Id
 *
 * @author scholze
 */
@WebService(name = "AmIMonitoringService", targetNamespace = "http://atb-bremen.de/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@XmlSeeAlso({AmIMonitoringConfiguration.class})
public interface IAmIMonitoringService extends IService {

    @WebMethod(operationName = "getReposData")
    ServiceInfo getReposData();
}
