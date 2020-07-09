package de.atb.context.persistence.common;

/*
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

import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.IApplicationScenarioProvider;
import de.atb.context.persistence.processors.IPersistencePostProcessor;
import de.atb.context.persistence.processors.IPersistencePreProcessor;
import de.atb.context.services.faults.ContextFault;

/**
 * IPersistenceUnit
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 * @param <T>
 *            Param
 *
 */
public interface IPersistenceUnit<T extends IApplicationScenarioProvider> {

	void persist(T object) throws ContextFault;

	boolean addPersistencePreProcessor(ApplicationScenario scenario,
                                       IPersistencePreProcessor<T> preProcessor) throws ContextFault;

	boolean addPersistencePostProcessor(ApplicationScenario scenario,
                                        IPersistencePostProcessor<T> postProcessor) throws ContextFault;

	boolean removePersistencePreProcessor(ApplicationScenario scenario,
                                          IPersistencePreProcessor<T> preProcessor) throws ContextFault;

	boolean removePersistencePostProcessor(ApplicationScenario scenario,
                                           IPersistencePostProcessor<T> postProcessor) throws ContextFault;

	boolean removePersistencePreProcessor(ApplicationScenario scenario,
                                          String id) throws ContextFault;

	boolean removePersistencePostProcessor(ApplicationScenario scenario,
                                           String id) throws ContextFault;

	void triggerPreProcessors(ApplicationScenario scenario, T object)
			throws ContextFault;

	void triggerPostProcessors(ApplicationScenario scenario, T object)
			throws ContextFault;

}