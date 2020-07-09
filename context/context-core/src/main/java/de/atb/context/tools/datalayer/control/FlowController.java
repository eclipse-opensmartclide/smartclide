package de.atb.context.tools.datalayer.control;

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

import java.sql.Timestamp;

/**
 *
 * @author Guilherme
 */
public abstract class FlowController {

    String flowId;
    OutputDataModel inputModel;
    Timestamp laststep;

    public Timestamp getLaststep() {
        return laststep;
    }

    public void setLaststep(Timestamp laststep) {
        this.laststep = laststep;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public OutputDataModel getInputModel() {
        return inputModel;
    }

    public void setInputModel(OutputDataModel inputModel) {
        this.inputModel = inputModel;
    }

    private String getImplementingClass() {
        if (this.inputModel == null) {
            return null;
        }
        return this.inputModel.getImplementingClass();
    }
}
