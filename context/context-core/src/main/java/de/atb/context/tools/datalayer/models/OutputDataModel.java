package pt.uninova.context.tools.datalayer.models;

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


import pt.uninova.context.tools.datalayer.models.output.Output;
import pt.uninova.context.tools.datalayer.models.structure.ModelField;
import pt.uninova.context.tools.datalayer.models.structure.RootModelField;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class OutputDataModel {

    String associatedPesId;
    String serviceType;
    String repositoryType;
    String associatedConfigId;
    RootModelField root;

    public OutputDataModel(String pesId, String serviceType, String repositoryType, String id) {
        this.associatedPesId = pesId;
        this.serviceType = serviceType;
        this.repositoryType = repositoryType;
        this.associatedConfigId = id;
    }

    public OutputDataModel() {

    }

    public String getImplementingClass() {
        if (this.getRoot() == null) {
            return null;
        }
        return this.getRoot().getImplementingClass();
    }
    
    public String getAssociatedPesId() {
        return associatedPesId;
    }

    public void setAssociatedPesId(String Associated_PesId) {
        this.associatedPesId = Associated_PesId;
    }

    public String getAssociatedConfigId() {
        return associatedConfigId;
    }

    public void setAssociatedConfigId(String Associated_ConfigId) {
        this.associatedConfigId = Associated_ConfigId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getRepositoryType() {
        return repositoryType;
    }

    public void setRepositoryType(String repositoryType) {
        this.repositoryType = repositoryType;
    }

    public RootModelField getRoot() {
        return root;
    }

    public void setRoot(RootModelField root) {
        this.root = root;
    }

    public Output CreateOutputStruct() {
        OutputParser parser = new OutputParser(this);
        Output output = parser.generateOutput();
        return output;
    }

    public Output AssignDataToOutput(Object data) {
        return null;
    }

    public boolean addListOfFieldsToField(String id, List<ModelField> list) {
        return this.root.addListOfFieldsToField(id, list);
    }

}
