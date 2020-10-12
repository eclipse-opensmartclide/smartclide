/*
 * @(#)ConfigurationRepositoryService.java
 *
 * $Id: ConfigurationRepositoryService.java 719 2017-05-05 10:20:28Z gsimoes $
 * 
 * $Rev:: 719                  $ 	last change revision
 * $Date:: 2017-05-05 12:20:28#$	last change date
 * $Author:: gsimoes             $	last change author
 * 
 * Copyright 2011-15 Sebastian Scholze (ATB). All rights reserved.
 *
 */
package de.atb.context.services;

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


import de.atb.context.common.configuration.ApplicationScenarioConfiguration;
import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.persistence.ConfigurationRepository;
import de.atb.context.services.faults.ContextFault;
import de.atb.context.services.dataLayer.RepositoryService;
import de.atb.context.services.infrastructure.response.InvokeResponse;
import de.atb.context.services.interfaces.Input;
import de.atb.context.services.interfaces.Output;
import de.atb.context.services.manager.ServiceManager;
import de.atb.context.tools.ontology.Configuration;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import java.sql.Timestamp;
import java.util.concurrent.Future;

/**
 * ConfigurationRepositoryService
 * 
 * @author scholze
 * @version $LastChangedRevision: 719 $
 * 
 */
public class ConfigurationRepositoryService extends RepositoryService implements
		IConfigurationRepositoryService {

	private volatile ConfigurationRepository repository;

	protected final synchronized ConfigurationRepository getRepository() {
		if (this.repository == null) {
			this.repository = ConfigurationRepository.getInstance();
		}
		return this.repository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.ISelfLearningService#start()
	 */
	@Override
	public void start() throws ContextFault {
		//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.ISelfLearningService#stop()
	 */
	@Override
	public void stop() throws ContextFault {
		//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.ISelfLearningService#restart()
	 */
	@Override
	public void restart() throws ContextFault {
		//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.ISelfLearningService#ping()
	 */
	@Override
	public final String ping() throws ContextFault {
		return ServiceManager.PING_RESPONSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.IConfigurationRepositoryService
	 * #getConfiguration(de.atb.context.common.util.ApplicationScenario,
	 * java.lang.String)
	 */
	@Override
	public final String getConfiguration(final ApplicationScenario appScenario,
			final String clazzName) {
		String ret = "";
		ApplicationScenarioConfiguration<?> confg = getRepository()
				.getConfiguration(appScenario, clazzName);
		if (confg != null) {
			ret = confg.serialize();
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.IConfigurationRepositoryService
	 * #getConfiguration(de.atb.context.common.util.ApplicationScenario)
	 */
	@Override
	public final synchronized String getConfiguration(final ApplicationScenario appScenario)
			throws ContextFault {
		String ret = "";
		ApplicationScenarioConfiguration<?> confg = getRepository()
				.getConfiguration(appScenario,
						appScenario.getConfigurationClass());
		if (confg != null) {
			ret = confg.serialize();
		}
		return ret;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.IConfigurationRepositoryService
	 * #persistConfiguration(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final void persistConfiguration(final String serializedConfiguration,
			final String clazzName) {
		if ((serializedConfiguration != null)
				&& !serializedConfiguration.trim().isEmpty()) {
			Class<ApplicationScenarioConfiguration<?>> clazz;
			try {
				clazz = (Class<ApplicationScenarioConfiguration<?>>) Class
						.forName(clazzName);
				ApplicationScenarioConfiguration<?> config = ApplicationScenarioConfiguration
						.deserialize(serializedConfiguration, clazz);
				getRepository().persistConfiguration(config);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			logger.warn("Cannot persist empty applicationscenario configuration!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.IConfigurationRepositoryService
	 * #clearBusinessCaseDirectory(de.atb.context.common.util.BusinessCase)
	 */
	@Override
	public final boolean clearBusinessCaseDirectory(final BusinessCase businessCase) {
		return getRepository().clearBaseDirectory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.IConfigurationRepositoryService
	 * #deleteApplicationScenarioConfiguration
	 * (de.atb.context.common.util.ApplicationScenario)
	 */
	@Override
	public final boolean deleteApplicationScenarioConfiguration(
			final ApplicationScenario scenario) {
		return getRepository().deleteApplicationScenarioConfiguration(scenario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.services.interfaces.IConfigurationRepositoryService
	 * #clearBaseDirectory()
	 */
	@Override
	public final boolean clearBaseDirectory() {
		return getRepository().clearBaseDirectory();
	}

	@Override
	public final String store(final Object element) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public final boolean remove(final Object element) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	
	public final <T extends Configuration> boolean configureService(final T Configuration) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	public final Output invokeS(final Input input) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	public final Output invokeA(final Input input) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	public final Future<?> invokeAAsync(final Input input,
			final AsyncHandler<InvokeResponse> asyncHandler) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	public final Response<InvokeResponse> invokeAAsync(final Input input) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

    @Override
    public Object get(String elementId, Timestamp stamp, String pesId, String serviceId) throws ContextFault {
         logger.info(String.format("Call to Get data from %s", this.getClass()
                .getSimpleName()));
        return ("return from: " + this.getClass());
    }

}
