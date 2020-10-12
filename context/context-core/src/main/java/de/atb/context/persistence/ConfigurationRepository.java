package de.atb.context.persistence;

/*
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * ConfigurationRepository
 *
 * @author scholze
 * @version $LastChangedRevision: 703 $
 */
public class ConfigurationRepository {

    private static final Logger logger = LoggerFactory
            .getLogger(ConfigurationRepository.class);

    protected String basicLocation = "configurations";

    protected ConfigurationRepository() {
    }

    protected ConfigurationRepository(final String baseLocation) {
        basicLocation = baseLocation;
    }

    public static ConfigurationRepository getInstance() {
        return new ConfigurationRepository();
    }

    public static ConfigurationRepository getInstance(final String location) {
        return new ConfigurationRepository(location);
    }

    @SuppressWarnings("unchecked")
    public final synchronized <T extends ApplicationScenarioConfiguration<?>> T getConfiguration(
            final ApplicationScenario appScenario) {
        return (T) getConfiguration(appScenario,
                appScenario.getConfigurationClass());
    }

    @SuppressWarnings("unchecked")
    public final synchronized <T extends ApplicationScenarioConfiguration<?>> T getConfiguration(
            final ApplicationScenario appScenario, final String clazzName) {
        try {
            return getConfiguration(appScenario,
                    (Class<T>) Class.forName(clazzName));
        } catch (final ClassNotFoundException e) {
            ConfigurationRepository.logger.error(e.getMessage(), e);
        }
        return null;
    }

    public final synchronized <T extends ApplicationScenarioConfiguration<?>> T getConfiguration(
            final ApplicationScenario appScenario, final Class<T> clazz) {
        try {
            final T obj = clazz.newInstance();
            obj.load(getLocationForBusinessCase(appScenario.getBusinessCase()));
            return obj;
        } catch (final SecurityException|IllegalArgumentException|IllegalAccessException|InstantiationException e) {
            ConfigurationRepository.logger.error(e.getMessage(), e);
        }
        return null;
    }

    public final synchronized void persistConfiguration( // TODO this should maybe replace by a call to DRM API
                                                         final ApplicationScenarioConfiguration<?> config) {
        if (config != null) {
            final String path = getLocationForBusinessCase(config
                    .getApplicationScenario().getBusinessCase());
            final File dir = new File(path);
            dir.mkdirs();
            config.save(path);
        }
    }

    public final synchronized boolean clearBaseDirectory() { // TODO this should maybe replace by a call to DRM API
        for (final BusinessCase bc : BusinessCase.values()) {
            clearBusinessCaseDirectory(bc);
        }
        return ConfigurationRepository.clearDirectory(new File(basicLocation));
    }

    public final synchronized boolean clearBusinessCaseDirectory( // TODO this should maybe replace by a call to DRM API
                                                                  final BusinessCase businessCase) {
        return ConfigurationRepository.clearDirectory(new File(
                getLocationForBusinessCase(businessCase)));
    }

    public final synchronized boolean deleteApplicationScenarioConfiguration( // TODO this should maybe replace by a call to DRM API
                                                                              final ApplicationScenario scenario) {
        final String path = getLocationForBusinessCase(scenario
                .getBusinessCase());
        ConfigurationRepository.logger.info(String.format(
                "Deleting all .xml configurations in path '%s'", path));
        final File dir = new File(path);
        if (!dir.exists() && !dir.mkdirs()) {
            ConfigurationRepository.logger.warn(String.format(
                    "Could not create configuration folder '%s'",
                    dir.getAbsolutePath()));
        }
        if (dir.exists() && (dir.listFiles() != null)) {
            for (final File file : dir.listFiles()) {
                if ((file.getName() != null)
                        && file.getName().toLowerCase().endsWith("xml")) {
                    if (file.getName().equalsIgnoreCase(
                            scenario.toString() + ".xml")) {
                        ConfigurationRepository.logger.info(String.format(
                                "Deleting Configuration '%s'",
                                file.getAbsolutePath()));
                        return file.delete();
                    }
                } else {
                    ConfigurationRepository.logger.debug(String.format(
                            "Skipping file '%s'", file.getAbsolutePath()));
                }
            }
        }
        return false;
    }

    protected static synchronized boolean clearDirectory(final File dir) { // TODO this should maybe replace by a call to DRM API
        if (dir.isDirectory()) {
            for (final String file : dir.list()) {
                final boolean success = ConfigurationRepository
                        .clearDirectory(new File(dir, file));
                if (!success) {
                    ConfigurationRepository.logger.warn("Could not delete %s", dir.toString() + File.separator + file);
                    return false;
                }
            }
            ConfigurationRepository.logger.info("Cleared directory "
                    + dir.getAbsolutePath());
        } else if (dir.isFile()) {
            return dir.delete();
        } else {
            ConfigurationRepository.logger.warn("File or directory "
                    + dir.getAbsolutePath()
                    + " does not exist, creating directory!");
            return dir.mkdirs();
        }
        return false;
    }

    protected final synchronized String getBasicLocation() {
        return basicLocation;
    }

    protected final synchronized String getLocationForBusinessCase(
            final BusinessCase bc) {
        return ConfigurationRepository.getLocationForBusinessCase(
                basicLocation, bc);
    }

    protected static synchronized String getLocationForBusinessCase(
            final String baseUri, final BusinessCase bc) {
        return String.format("%s%s%s", baseUri, File.separator,
                String.valueOf(bc));
    }

}
