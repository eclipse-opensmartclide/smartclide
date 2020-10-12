/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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


import de.atb.context.kmb.KMBApi;
import de.atb.context.services.faults.ContextFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 *
 * @author Guilherme
 */
public class ConfigurationsUtilities {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConfigurationsUtilities.class);
    static String deployableConfigStringId = "Task_";
    static KMBApi kmbapi = new KMBApi();
    static String configNamespace = ("ProSEco_Ontological_Model:");

    private ConfigurationsUtilities() {}

    public static boolean writeDeployableConfiguration(String pesId, String configId, String configJsonString, KMBConfigsVocabulary voc, String classSimpleName) {
        try {
            kmbapi.writeElementInformation(pesId, unifyStringForKMBInformation(voc.getSearchName(), configId));
            kmbapi.writeElementConfiguration(configId, placeNameSpaceBeforeName(classSimpleName), configJsonString);
            return true;
        } catch (ContextFault e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }



    public static String unifyStringForKMBInformation(String ontName, String configId) {
        String res = new StringBuilder("{\"").append(ontName).append("\":\"").append(configId).append("\"}").toString();
        res = res.replace("\"","\"\"");
        return res;
    }

    public static String createConfigurationIdForDeployableService() {
        return new StringBuilder(UUID.randomUUID().toString()).toString();
    }

    public static String placeNameSpaceBeforeName(String classSimpleName) {
        return configNamespace + classSimpleName;
    }
}
