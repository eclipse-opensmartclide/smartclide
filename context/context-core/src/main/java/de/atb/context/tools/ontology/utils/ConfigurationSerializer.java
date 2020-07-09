/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uninova.context.tools.ontology.utils;

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
import com.google.gson.JsonObject;
import pt.uninova.context.tools.ontology.Configuration;

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
