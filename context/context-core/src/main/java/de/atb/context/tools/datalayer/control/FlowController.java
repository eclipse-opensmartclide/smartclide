package de.atb.context.tools.datalayer.control;

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
