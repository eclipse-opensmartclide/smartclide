package de.atb.context.tools.ontology.utils;

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


/**
 *
 * @author Guilherme
 */
public enum KMBConfigsVocabulary {

    AMI_MONITORING("AmIMonitoring"),
    CONTEXT_EXTRACTION("ContextExtraction"),
    DATA_MINING("DataMining"),
    KNOWLEDGE_PROVISIONING("KnowledgeProvisioning"),
    ECO_AND_OPTIMIZATION("EcoAndOptimisation"),
    SERVICE_COMPOSITION("ServiceComposition"),
    SECURITY("Security"),
    APPLICATION_SPECIFIC("ApplicationSpecific");

    private final String searchName;


    KMBConfigsVocabulary(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchName() {
        return ConfigurationsUtilities.placeNameSpaceBeforeName("has"+searchName+"Configuration");
    }
    
    public String getServiceSimpleName(){
        return searchName+"Service";
    }
    
    public String getOntology(){
       return searchName;
    }
    
    public String getConfigurationSimpleName(){
        return searchName+"Configuration";
    }
}
