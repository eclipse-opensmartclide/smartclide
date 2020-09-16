/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.services.interfaces;

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


import org.slf4j.LoggerFactory;
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.modules.broker.utils.Constants;
import de.atb.context.services.manager.ServiceManager;
import de.atb.context.services.wrapper.RepositoryServiceWrapper;
import de.atb.context.tools.datalayer.models.OutputDataModel;

/**
 *
 * @author Guilherme
 */
public class DataInfo {
    
    private static final org.slf4j.Logger logger = LoggerFactory
            .getLogger(DataInfo.class);

    
    ServiceInfo reposLocation;
    String dataId;
    int period;
    RepositoryServiceWrapper<IRepositoryService> service;
    String flowType;
    OutputDataModel outPutModel;

    public OutputDataModel getOutPutModel() {
        return outPutModel;
    }

    public void setOutPutModel(OutputDataModel outPutModel) {
        this.outPutModel = outPutModel;
    }
    
    
    public ServiceInfo getReposLocation() {
        return reposLocation;
    }

    public void setReposLocation(ServiceInfo reposLocation) {
        this.reposLocation = reposLocation;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public int getDelay() {
        return period;
    }

    public void setDelay(int delay) {
        this.period = delay;
    }

    public RepositoryServiceWrapper<IRepositoryService> getService() {
        return service;
    }

    public void setService(RepositoryServiceWrapper<IRepositoryService> service) {
        this.service = service;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }
    
    
    
    public boolean intanceWrapperFromServiceInfo(){
 
        Class<? extends IRepositoryService> clazz;
        try {
            //obtain subclass of Configuration
            clazz = (Class<? extends IRepositoryService>) Class.forName(this.reposLocation.getProxy());
            String uri = String.format(Constants.SERVICE_PATTERN, this.reposLocation.getLocation(), this.reposLocation.getName());
            //Establish Client
            IRepositoryService cli = ServiceManager.getWebservice(uri, this.reposLocation.getName(), clazz);
            try {
                this.service = new RepositoryServiceWrapper<>(cli);
                logger.info("Connection established to Repository Service: " + this.reposLocation.getProxy());
                return true;
            } catch (IllegalArgumentException e) {
                this.service = null;
                logger.info("Unable to connect to repository Service: " + this.reposLocation.getProxy());
            }
        } catch (ClassNotFoundException ex) {
            logger.info("This Service ProxyClass is unkown: " + this.reposLocation.getProxy());
        }
        return false;
    } 
    
}
