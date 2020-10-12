package de.atb.context.tools.ontology.utils;

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
