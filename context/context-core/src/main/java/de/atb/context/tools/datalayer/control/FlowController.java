package de.atb.context.tools.datalayer.control;

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
