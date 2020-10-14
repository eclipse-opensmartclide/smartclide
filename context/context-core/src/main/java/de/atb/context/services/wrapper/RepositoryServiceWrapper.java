package de.atb.context.services.wrapper;

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


import de.atb.context.services.faults.ContextFault;
import de.atb.context.services.interfaces.IRepositoryService;
import de.atb.context.services.manager.ServiceManager;
import de.atb.context.tools.datalayer.models.OutputDataModel;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Giovanni
 */
public class RepositoryServiceWrapper<Service extends IRepositoryService> {

    protected Service service;

    public RepositoryServiceWrapper(final Service service) {
        this.service = service;
        if (!ServiceManager.isPingable(service)) {
            throw new IllegalArgumentException("Service is not pingable!");
        }
    }

    public final void start() {
        service.start();
    }

    public final void stop() {
        service.stop();
    }

    public final void restart() {
        service.restart();
    }

    public final String ping() {
        return service.ping();
    }

    public final String store(Object Element) {
        return service.store(Element);
    }

    public final Object remove(final Object Element) {
        return service.remove(Element);
    }

    public final String invokeForData(final String ElementId) {
        return service.invokeForData(ElementId);
    }

    public void setOutputIds(ArrayList<String> outputIds) throws ContextFault {
        service.setOutputIds(outputIds);
    }

    public boolean setupRepos(String host, int port, String className,String pesId, OutputDataModel model, ArrayList<String> outIds, String serviceId) throws ContextFault {
        return this.service.setupRepos(host, port, className,pesId, model, outIds, serviceId);
    }

    public boolean startPES(Timestamp startTime) throws ContextFault {
        return this.service.startPES();
    }

}
