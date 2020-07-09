/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.modules.broker.utils;

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


import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Guilherme
 */
public class Constants {

    public static final String SERVICE_PATTERN = "%s/%s";
    public static final String PERIODIC_FLOW = "Periodic";
    public static final String ONDEMAND_FLOW = "OnDemand";

    //LAUCHING RUNTIME AGENTS
    public static final String ONTOLOGY_RUNTIME_LAUNCH_RESULT = "run_launch_res";
    public static final String ONTOLOGY_LAUNCHING_RUNTIME_AGENTS = "launching_runtime";
    public static final String ONTOLOGY_DELETE_RUNTIME_AGENTS = "delete_runtime_agents";
    public static final String ONTOLOGY_SETUP_RUNTIME_AGENT = "setup_runtime_agent";

    //SETUP REPOSDATAINFO
    public static final String ONTOLOGY_REPOSDATAINFO = "reposdatainfo";
    public static final String ONTOLOGY_REPOSDATAINFO_FAIL = "reposdatainfo_fail";
    public static final String ONTOLOGY_REPOSDATAINFO_SUCCESS = "reposdatainfo_success";
    public static final String ONTOLOGY_REPOSDATAINFO_THROW = "reposdatainfo_throw";

    //SETUP SEVICES STARTER
    public static final String ONTOLOGY_SETUP_SERVICE = "run_setup";
    public static final String ONTOLOGY_SETUP_SERVICE_SUCCESS = "run_setup_success";
    public static final String ONTOLOGY_SETUP_SERVICE_FAIL = "run_setup_fail";
    public static final String ONTOLOGY_SETUP_SERVICE_THROW = "run_setup_throw";

    //CONFIG SERVICES
    public static final String ONTOLOGY_CONFIG_SERVICE = "run_config";
    public static final String ONTOLOGY_CONFIG_SERVICE_FAIL = "config_service_fail";
    public static final String ONTOLOGY_CONFIG_SERVICE_SUCCESS = "config_service_success";
    public static final String ONTOLOGY_CONFIG_SERVICE_THROW = "config_service_throw";
    
    
    
    public static final String ONTOLOGY_START_SERVICE = "startservice";
    public static final String ONTOLOGY_START_SERVICE_THROW = "startservice_throw";
    public static final String ONTOLOGY_START_SERVICE_SUCCESS = "startservice_succcess";
    
    
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    
    
    public static final String ONTOLOGY_REGISTRY_CONNECTION_LOST="registry_connection_lost";
    public static final String ONTOLOGY_SERVICE_UNAVAILABLE="service_unavailable_on_registry";
    
    public static final String ONTOLOGY_START_RUNTIME="start_runtime";
    public static final String ONTOLOGY_DELETE_PROCESS_AGENT = "delete_process_agents";
    public static final String ONTOLOGY_LAUNCHING_PROCESS_AGENT = "launching_process";
    public static final String ONTOLOGY_REQUEST_DELETE_PROCESS_AGENT="request_delete_process";

    private Constants() {}
}
