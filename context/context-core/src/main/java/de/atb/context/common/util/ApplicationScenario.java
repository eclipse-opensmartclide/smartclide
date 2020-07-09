package de.atb.context.common.util;

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

import de.atb.context.common.configuration.ApplicationScenarioConfiguration;
import de.atb.context.common.configuration.IConfigurationBean;
import de.atb.context.learning.models.IModelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ApplicationScenario
 *
 * @author scholze, huesig
 * @version $LastChangedRevision: 701 $
 */
public class ApplicationScenario implements IModelInitializer {
    private final Logger logger = LoggerFactory
            .getLogger(ApplicationScenario.class);

    private BusinessCase businessCase;
    private String modelInitializerClassName;
    private String configurationClassName;
    private String configurationDialogClassName;
    private Class<? extends ApplicationScenarioConfiguration<?>> configurationClass;
    private IModelInitializer initializer;
    private static volatile Map<String, ApplicationScenario> settings = new HashMap<String, ApplicationScenario>();

    public static ApplicationScenario getInstance() {
        if (settings.get("DUMMY_SCENARIO") == null) {
            settings.put("DUMMY_SCENARIO", new ApplicationScenario(BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL)));
        }
        return settings.get("DUMMY_SCENARIO");
    }

    public static ApplicationScenario getInstance(BusinessCase businessCase) {
        if (settings.get(businessCase) == null) {
            settings.put(businessCase.toString(), new ApplicationScenario(businessCase));
        }
        return settings.get(businessCase.toString());
    }

    public static ApplicationScenario getInstance(BusinessCase businessCase, String modelInitializerClassName) {
        if (settings.get(businessCase) == null) {
            settings.put(businessCase.toString(), new ApplicationScenario(businessCase, modelInitializerClassName));
        }
        return settings.get(businessCase.toString());
    }

    ApplicationScenario(final BusinessCase businessCase) {
        this(businessCase, "java.lang.Object");
    }

    ApplicationScenario(final BusinessCase businessCase,
                        final String modelInitializerClassName) {
        this(businessCase, modelInitializerClassName, null, null);
    }

    ApplicationScenario(final BusinessCase businessCase,
                        final String modelInitializerClassName,
                        final String configurationClassName) {
        this(businessCase, modelInitializerClassName, configurationClassName,
                null);
    }

    ApplicationScenario(final BusinessCase businessCase,
                        final String modelInitializerClassName,
                        final String configurationClassName,
                        final String configDialogClassName) {
        this.businessCase = businessCase;
        this.modelInitializerClassName = modelInitializerClassName;
        this.configurationClassName = configurationClassName;
        configurationDialogClassName = configDialogClassName;
    }

    public BusinessCase getBusinessCase() {
        return businessCase;
    }

    public static ApplicationScenario[] values() {
        return settings.values().toArray(new ApplicationScenario[0]);
    }

    public static ApplicationScenario[] values(final BusinessCase businessCase) {
        final ApplicationScenario[] scenarios = ApplicationScenario.values();
        final List<ApplicationScenario> filteredScenarios = new ArrayList<>(
                scenarios.length);
        for (final ApplicationScenario scenario : scenarios) {
            if (scenario.getBusinessCase() == businessCase) {
                filteredScenarios.add(scenario);
            }
        }
        return filteredScenarios
                .toArray(new ApplicationScenario[filteredScenarios.size()]);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.learning.models.IModelInitializer#getScenario()
     */
    @Override
    public ApplicationScenario getScenario() {
        if ((initializer != null) || createInitializer()) {
            return initializer.getScenario();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.learning.models.IModelInitializer#initializeModel()
     */
    @Override
    public boolean initializeModel(final String filePath) {
        if ((initializer != null) || createInitializer()) {
            return initializer.initializeModel(filePath);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends IConfigurationBean> Class<? extends ApplicationScenarioConfiguration<T>> getConfigurationClass() {
        if (configurationClass == null) {
            createConfigurationClass();
        }
        return (Class<? extends ApplicationScenarioConfiguration<T>>) configurationClass;
    }

    public IModelInitializer getInitializer() {
        if (initializer == null) {
            createInitializer();
        }
        return initializer;
    }

    @SuppressWarnings("unchecked")
    protected boolean createConfigurationClass() {
        try {
            if (configurationClass == null) {
                final Class<? extends ApplicationScenarioConfiguration<?>> clazz = (Class<? extends ApplicationScenarioConfiguration<?>>) Class
                        .forName(configurationClassName);
                configurationClass = clazz;
            }
            return true;
        } catch (final ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    protected boolean createInitializer() {
        try {
            if (initializer == null) {
                final Class<? extends IModelInitializer> clazz = (Class<? extends IModelInitializer>) Class
                        .forName(modelInitializerClassName);
                initializer = clazz.newInstance();
            }
            return true;
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }
}
