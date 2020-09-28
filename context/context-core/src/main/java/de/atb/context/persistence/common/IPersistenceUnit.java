package de.atb.context.persistence.common;

/*
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
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
