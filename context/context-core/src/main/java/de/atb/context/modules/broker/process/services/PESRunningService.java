package de.atb.context.modules.broker.process.services;

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


import de.atb.context.tools.datalayer.models.OutputDataModel;
import de.atb.context.tools.ontology.Configuration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Guilherme
 */
public class PESRunningService<T extends Configuration> {
    

    //Id of the Service inside the PES   
    String serviceID;

    T pesConfig;
    //Not used - only for info
    boolean isRestartable;
    boolean continuosOperationMode; //true -> continuous _ false -> static 
    boolean isStarter; 
    HashMap <String,PESFlowSpecs> flowspecs;
    ArrayList<String> dataOutputIds;
    ArrayList<String> receivers;
    OutputDataModel outputModel;
    
    boolean delayFromStartSettled;
    int delayFromStart;
    
    public PESRunningService() {
        this.dataOutputIds = new ArrayList<>();
        this.isStarter = false;
        this.isRestartable = false;
        this.delayFromStartSettled = false;
        this.delayFromStart = 0;
        this.receivers = new ArrayList<>();
        this.flowspecs=new HashMap<>();
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public T getPesConfig() {
        return pesConfig;
    }

    public void setPesConfig(T pesConfig) {
        this.pesConfig = pesConfig;
    }

    public boolean isIsRestartable() {
        return isRestartable;
    }

    public void setIsRestartable(boolean isRestartable) {
        this.isRestartable = isRestartable;
    }

    public boolean isContinuosOperationMode() {
        return continuosOperationMode;
    }

    public void setContinuosOperationMode(boolean continuosOperationMode) {
        this.continuosOperationMode = continuosOperationMode;
    }

    public HashMap<String, PESFlowSpecs> getFlowspecs() {
        return flowspecs;
    }

    public void setFlowspecs(HashMap<String, PESFlowSpecs> flowspecs) {
        this.flowspecs = flowspecs;
    }

    public boolean getIsStarter() {
        return isStarter;
    }

    public void setIsStarter(boolean isStarter) {
        this.isStarter = isStarter;
    }

    public ArrayList<String> getDataOutputIds() {
        return dataOutputIds;
    }

    public void setDataOutputIds(ArrayList<String> dataOutputIds) {
        this.dataOutputIds = dataOutputIds;
    }

    public void addDataOutputId(String outputId) {
        this.dataOutputIds.add(outputId);
    }
    
    
    public ArrayList<String> getReceivers() {
        return receivers;
    }
    
    public void setReceivers(ArrayList<String> receivers) {
        this.receivers = receivers;
    }

    public void addReceiver(String serviceID) {
        this.receivers.add(serviceID);
    }

    public boolean isDelayFromStartSettled() {
        return delayFromStartSettled;
    }

    public void setDelayFromStartSettled(boolean delayFromStartSettled) {
        this.delayFromStartSettled = delayFromStartSettled;
    }

    public int getDelayFromStart() {
        return delayFromStart;
    }

    public void setDelayFromStart(int delayFromStart) {
        this.delayFromStart = delayFromStart;
    }

    public OutputDataModel getOutputModel() {
        return outputModel;
    }

    public void setOutputModel(OutputDataModel outputModel) {
        this.outputModel = outputModel;
    }
}
