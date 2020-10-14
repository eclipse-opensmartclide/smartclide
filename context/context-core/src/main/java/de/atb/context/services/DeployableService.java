/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.services;

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
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.modules.broker.process.services.PESFlowSpecs;
import de.atb.context.services.interfaces.DataInfo;
import de.atb.context.services.interfaces.IService;
import de.atb.context.tools.datalayer.models.OutputDataModel;
import de.atb.context.tools.ontology.Configuration;

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
