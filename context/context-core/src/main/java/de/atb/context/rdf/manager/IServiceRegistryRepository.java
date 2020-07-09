/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.rdf.manager;

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


import de.atb.context.infrastructure.Node;
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.modules.broker.status.ontology.StatusVocabulary;

import java.util.List;

/**
 * @author Giovanni
 */
public interface IServiceRegistryRepository {

    public boolean initializeRepository();

    public boolean insert(Node node);

    public boolean delete(Node node);

    Node selectforID(String id);

    List<ServiceInfo> selectforServiceType(String typeID);

    List<Node> selectAllConnectedDeployers();

    public boolean updateStatus(List<String> id, StatusVocabulary status);

    List<ServiceInfo> selectForFreeServiceByType(String typeID);

    public boolean updateSingleStatusById(String id, StatusVocabulary status);

    public boolean setStatusByIds(List<String> id, StatusVocabulary status);

    public boolean updateSingleStatusByLocation(String location, StatusVocabulary status);
}
