package pt.uninova.context.services.dataLayer;

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


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.atb.context.services.faults.ContextFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.uninova.context.infrastructure.ServiceInfo;
import pt.uninova.context.services.interfaces.IRepositoryService;
import pt.uninova.context.tools.datalayer.control.FlowController;
import pt.uninova.context.tools.datalayer.control.GenericTimeController;
import pt.uninova.context.tools.datalayer.models.OutputDataModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Guilherme
 */
public abstract class RepositoryService<T> /*<T extends FlowController>*/ implements IRepositoryService {

    public final Logger logger = LoggerFactory
            .getLogger(RepositoryService.class);

    protected ServiceInfo repositoryConfigData;
    ArrayList<String> dataOutputIds;
    OutputDataModel model;
    HashMap<String, FlowController> flowControls;
    Timestamp startTime;
    String currentPesId;
    String associatedServiceId;

    public RepositoryService() {
        this.dataOutputIds = new ArrayList<>();
        this.flowControls = new HashMap<>();
    }

    public ArrayList<String> getDataOutputIds() {
        return dataOutputIds;
    }

    public void setDataOutputIds(ArrayList<String> dataOutputIds) {
        this.dataOutputIds = dataOutputIds;
    }

    @Override
    public void setOutputIds(ArrayList<String> outputIds) throws ContextFault {
        this.dataOutputIds = outputIds;
    }

    @Override
    public void setOutputModel(OutputDataModel model) throws ContextFault {
        this.model = model;
    }

    public OutputDataModel getModel() {
        return model;
    }
    

    public abstract T get(String elementId, Timestamp stamp, String pesId, String serviceId) throws ContextFault;

    @Override
    public boolean startPES() throws ContextFault {
        this.startTime = new Timestamp(System.currentTimeMillis());
        for (FlowController val : this.flowControls.values()) {
            val.setLaststep(startTime);
        }
        return true;
    }

    @Override
    public boolean setupRepos(String host, int port, String className, String pesId, OutputDataModel model, ArrayList<String> outIds, String serviceId) throws ContextFault {
        this.currentPesId = pesId;
        this.associatedServiceId=serviceId;
        this.model = model;
        this.dataOutputIds = outIds;
        if (outIds != null) {
            for (String id : outIds) {
                GenericTimeController cont = new GenericTimeController();
                cont.setFlowId(id);
                cont.setInputModel(model);
                this.flowControls.put(id, cont);
            }
        }
        return true;
    }

    public String setDataObjectToJson(T data, FlowController controller) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String stringfyJSON;
        String implementingClassName;
        if (controller.getInputModel()==null){
            stringfyJSON = gson.toJson(data);
        }else if (controller.getInputModel().getImplementingClass()==null || controller.getInputModel().getImplementingClass().equals("")){
            stringfyJSON = gson.toJson(data);
        }else{
            implementingClassName=controller.getInputModel().getImplementingClass();
            try{
                stringfyJSON = gson.toJson(data,implementingClassName.getClass());
            }catch(Exception e){
                stringfyJSON = gson.toJson(data);
            }
        }
        return stringfyJSON;
    }

    @Override
    public String invokeForData(String elementId) throws ContextFault {
        FlowController control = this.flowControls.get(elementId);
        // Erro if theres no controller for this flowId
        if (control==null)
            return null;
        ////
        Timestamp requestTime = new Timestamp(System.currentTimeMillis());
        T data = get(elementId, requestTime, this.currentPesId ,this.associatedServiceId);
        if (data==null){
            control.setLaststep(requestTime);
            return null;
        }
        String dataAsJson = setDataObjectToJson(data, control);
        control.setLaststep(requestTime);
        return dataAsJson;
    }

    private Object createOutput(Object data, String elementId) {
        FlowController control = this.flowControls.get(elementId);
        control.setLaststep(new Timestamp(System.currentTimeMillis()));
        return data;
    }
}
