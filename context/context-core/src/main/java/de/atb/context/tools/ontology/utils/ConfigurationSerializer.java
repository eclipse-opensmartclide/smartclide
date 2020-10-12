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


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.atb.context.tools.ontology.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationSerializer {
    private ConfigurationSerializer() {}

    /**
     *
     * @param <T> Specific Configuration Extension
     * @param config The Configuration to be used
     * @return returns a new specific Configuration object
     */
    
  
    public static <T extends Configuration> T setConfigurationToJsonAndAddToValue(T config) {

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String stringfyJSON = gson.toJson(config);
        config.setValue(stringfyJSON);
        return config; 
    }

    public static <T extends Configuration> String setConfigurationToJson(T config) {

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(config);
    }

    /**
     *
     * @param <T> Specific Configuration Extension
     * @param config The Configuration to be used
     * @return returns a new Configuration object
     */
    public static <T extends Configuration> T getConfigurationFromJson(Configuration config) {
        try {
            Gson gson = new Gson();
            T newConfig = gson.fromJson(config.getValue(), (Class<T>) Class.forName(config.getType()));
            newConfig.setValue(config.getValue());
            return newConfig;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConfigurationSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param jsonConfig the json config to be used as configuration
     * @return returns a new Configuration object
     */
    public static Configuration newConfigurationFromJson(String jsonConfig) {

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(jsonConfig, JsonObject.class);
        String type = jsonObj.get("type").getAsString();
        String belongTo = jsonObj.get("belongTo").getAsString();
        Configuration newConfig = new Configuration(type, belongTo);
        newConfig.setValue(jsonConfig);
        return newConfig;
    }

}
