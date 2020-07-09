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
