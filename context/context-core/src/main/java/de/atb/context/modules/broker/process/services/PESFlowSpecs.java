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


import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.tools.datalayer.models.OutputDataModel;

/**
 *
 * @author Guilherme
 */
public class PESFlowSpecs {

    String flowId;
    String sourceId;
    String dataId;
    ServiceInfo sourceInfo;
    String flowtype;
    int period;
    int hours;
    int minutes;
    int seconds;
    int delayTimeFromSource;
    int delayTimeOfPesStart;
    boolean settled;
    OutputDataModel outputModel;

    public OutputDataModel getOutputModel() {
        return outputModel;
    }

    public void setOutputModel(OutputDataModel outputModel) {
        this.outputModel = outputModel;
    }
    
    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
    
    public PESFlowSpecs() {
        this.period = 1;
        this.flowtype = null;
        this.settled = false;
        this.delayTimeOfPesStart = 0;
        this.delayTimeFromSource = 0;
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        this.sourceInfo = new ServiceInfo();
    }
    
    public int getDelayTimeOfPesStart() {
        return delayTimeOfPesStart;
    }

    public void setDelayTimeOfPesStart(int delayTimeOfPesStart) {
        this.delayTimeOfPesStart = delayTimeOfPesStart;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getFlowtype() {
        return flowtype;
    }

    public void setFlowtype(String flowtype) {
        this.flowtype = flowtype;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
    

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getDelayTimeFromSource() {
        return delayTimeFromSource;
    }

    public void setDelayTimeFromSource(int delayTimeFromSource) {
        this.delayTimeFromSource = delayTimeFromSource;
    }

    public void setDelayFromTimes() {
        setDelayTimeFromSource((3600 * hours) + (60 * minutes) + seconds);
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public ServiceInfo getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(ServiceInfo sourceInfo) {
        this.sourceInfo = sourceInfo;
    }
    
    
}
