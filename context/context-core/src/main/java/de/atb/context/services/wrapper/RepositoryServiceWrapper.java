package pt.uninova.context.services.wrapper;

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


import de.atb.context.services.faults.ContextFault;
import pt.uninova.context.services.interfaces.IRepositoryService;
import pt.uninova.context.services.manager.ServiceManager;
import pt.uninova.context.tools.datalayer.models.OutputDataModel;

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
