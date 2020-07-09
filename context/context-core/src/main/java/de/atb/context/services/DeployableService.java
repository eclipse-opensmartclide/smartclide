/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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


import de.atb.context.services.faults.ContextFault;
import pt.uninova.context.infrastructure.ServiceInfo;
import pt.uninova.context.modules.broker.process.services.PESFlowSpecs;
import pt.uninova.context.services.interfaces.DataInfo;
import pt.uninova.context.services.interfaces.IService;
import pt.uninova.context.tools.datalayer.models.OutputDataModel;
import pt.uninova.context.tools.ontology.Configuration;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Guilherme
 */
public abstract class DeployableService implements IService {

    protected HashMap<String, DataInfo> dataInputsMap;
    ArrayList<String> dataOutputIds;
    String currentPesId;
    String currentServiceId;
    protected ServiceInfo repositoryConfigData;
    OutputDataModel outModel;
    Timestamp startTime;
    

    public DeployableService() {
        this.repositoryConfigData = new ServiceInfo();
        this.dataInputsMap = new HashMap<>();
    }

    public HashMap<String, DataInfo> getDataInputsMap() {
        return dataInputsMap;
    }

    public void setDataInputsMap(HashMap<String, DataInfo> DataInputsMap) {
        this.dataInputsMap = DataInputsMap;
    }

    //Broker Notifier
    @Override
    public boolean setupRuntimeSpecs(String host, int port, String className, ArrayList<String> dataOutputIds, String pesId, HashMap<String, PESFlowSpecs> flowSpecs, String serviceId, OutputDataModel outmodel) throws ContextFault {
        this.currentPesId = pesId;
        this.dataOutputIds = dataOutputIds;
        this.currentServiceId = serviceId;
        this.outModel=outmodel;
        for (PESFlowSpecs flow : flowSpecs.values()) {
            DataInfo dataInfo = new DataInfo();
            dataInfo.setReposLocation(flow.getSourceInfo());
            dataInfo.setDataId(flow.getFlowId());
            dataInfo.setDelay(flow.getDelayTimeFromSource());
            dataInfo.setFlowType(flow.getFlowtype());
            dataInfo.setPeriod(flow.getPeriod());
            dataInfo.setOutPutModel(flow.getOutputModel());
            if (dataInfo.intanceWrapperFromServiceInfo()) {
                //change return to String so that can be information of the error
            }
            dataInputsMap.put(dataInfo.getDataId(), dataInfo);
        }
        return true;
    }

    @Override
    public abstract <T extends Configuration> boolean configureService(T configuration) throws ContextFault;

    @Override
    public abstract void start() throws ContextFault;

    @Override
    public abstract void stop() throws ContextFault;

    @Override
    public abstract void restart() throws ContextFault;

    @Override
    public abstract String ping() throws ContextFault;

    @Override
    public ServiceInfo getReposInfo() {
        return repositoryConfigData;
    }

    @Override
    public abstract boolean runtimeInvoke(String flowId) throws ContextFault;
}
