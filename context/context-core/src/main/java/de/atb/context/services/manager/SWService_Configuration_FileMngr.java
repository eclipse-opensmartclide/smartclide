/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.services.manager;

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


import de.atb.context.common.io.ResourceLoader;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.services.config.SWServiceConfiguration;

import javax.naming.ConfigurationException;
import java.io.*;
import java.net.URI;

/**
 *
 * @author Giovanni
 */
public abstract class SWService_Configuration_FileMngr<T> {

	private final Logger logger = LoggerFactory
			.getLogger(SWService_Configuration_FileMngr.class);

	protected static final String HOME_CONFIG_PATH = System
			.getProperty("user.home")
			+ File.separator
			+ ".context"
			+ File.separator;
	protected Class<? extends T> configurationClass;
	protected String configurationLookupPath;
	protected static String configurationFileName = "resources" + File.separator + "services-config.xml";
	protected String infrastructureConfigFileName;
	protected String configurationName;
	protected T configurationBean;

	protected SWService_Configuration_FileMngr(final String configFileName,
			final String configurationLookupPath, final Class<? extends T> clazz,
			final String configurationName) {
		this.configurationLookupPath = configurationLookupPath;
		this.configurationClass = clazz;
		SWService_Configuration_FileMngr.configurationFileName = configFileName;
		this.configurationName = configurationName;
		logger.info("Loading " + configurationName + "...");
		InputStream is = null;
		try {
			File homeConfig = new File(HOME_CONFIG_PATH
					+ SWService_Configuration_FileMngr.configurationFileName);
			File runConfig = new File(
					SWService_Configuration_FileMngr.configurationFileName);
			Serializer serializer = new Persister();
			String where = "";

			if (configurationLookupPath != null) {
				where = String.valueOf(configurationLookupPath);
				File f = new File(where);
				where = f.getAbsolutePath();
				if (f.exists()) {
					logger.info("Trying to load configuration from '" + where
							+ "'");
					is = new FileInputStream(f);
					this.configurationBean = serializer.read(
							this.configurationClass, is);
					is.close();
					logger.info("" + configurationName + " loaded from '"
							+ where + "'!");
					return;
				}
			}
			if (homeConfig.exists()) {
				where = homeConfig.getAbsolutePath();
				logger.info("Trying to load configuration from '" + where
						+ "'");
				is = new FileInputStream(homeConfig);
				this.configurationBean = serializer.read(
						this.configurationClass, is);
				is.close();
			} else if (runConfig.exists()) {
				where = runConfig.getAbsolutePath();
				logger.info("Trying to load configuration from '" + where
						+ "'");
				is = new FileInputStream(runConfig);
				this.configurationBean = serializer.read(
						this.configurationClass, is);
				is.close();
			} else {
				try {
					URI uri = ResourceLoader
							.getResourceURI(SWService_Configuration_FileMngr.configurationFileName);
					logger.info("Trying to load configuration via URI '%s'", String.valueOf(uri));
					where = new File(uri).getAbsolutePath();
					logger.info("Trying to load configuration from '%s'", where);
					is = ResourceLoader
							.getResourceStream(SWService_Configuration_FileMngr.configurationFileName);
					this.configurationBean = serializer.read(
							this.configurationClass, is);
					is.close();
				} catch (Exception e) {
					try {
						is = ResourceLoader
								.getResourceStream(SWService_Configuration_FileMngr.configurationFileName);
						this.configurationBean = serializer.read(
								this.configurationClass, is);
						is.close();
					} catch (NullPointerException ex) {
						where = new File(
								SWService_Configuration_FileMngr.configurationFileName)
								.getAbsolutePath();
						logger.info("Trying to load configuration from '"
								+ where + "'");
						is = ResourceLoader
								.getResourceStream(SWService_Configuration_FileMngr.configurationFileName);
						this.configurationBean = serializer
								.read(this.configurationClass,
										new File(
												SWService_Configuration_FileMngr.configurationFileName));
						is.close();
					}
				}
			}
			logger.info("%s loaded from '%s'", configurationName, where);
		} catch (FileNotFoundException fnfe) {
			logger.error("Could not open the " + configurationName + " file: "
					+ SWService_Configuration_FileMngr.configurationFileName,
					fnfe);
		} catch (Exception e) {
			logger.error("Could not serialize the " + configurationName
					+ " file: "
					+ SWService_Configuration_FileMngr.configurationFileName, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

	}

	public final String save() {
		logger.info("Saving configuration...");
		Serializer serializer = new Persister();

		File source = new File(HOME_CONFIG_PATH
				+ SWServiceConfiguration.getDefaultFileName());
		File sourceLocation = new File(System.getProperty("user.home")
				+ File.separator + ".context" + File.separator + "resources");
		if (!sourceLocation.mkdirs() && !sourceLocation.exists()) {
			logger.warn("Could not create directory "
					+ sourceLocation.getAbsolutePath()
					+ " for saving configuration file!");
		}
		try {
			if ((source.exists() || source.createNewFile())/*
															 * &&
															 * (sourceManager.
															 * exists() ||
															 * sourceManager
															 * .createNewFile())
															 */) {
				serializer.write(getConfig(), source);
				logger.info("%s saved!", this.configurationName);
			} else {
				logger.warn(" %s could not be saved, because the file %s could not be created!", this.configurationName, source.getAbsolutePath());
				return null;
			}
		} catch (Exception e) {
			logger.error("Could not save the " + this.configurationName
					+ " file: "
					+ SWService_Configuration_FileMngr.configurationFileName, e);
			return null;
		}
		return source.getAbsolutePath();
	}

	public final void refresh() {
		InputStream is = null;
		logger.info("Reloading %s ...", this.configurationName);
		try {
			String filePath = this.configurationLookupPath == null ? HOME_CONFIG_PATH
					+ SWService_Configuration_FileMngr.configurationFileName
					: this.configurationLookupPath;

			Serializer serializer = new Persister();
			is = new FileInputStream(new File(filePath));
			this.configurationBean = serializer.read(this.configurationClass,
					is);
			logger.info("%s re-loaded!", this.configurationName);
		} catch (FileNotFoundException fnfe) {
			logger.error("Could not open the " + this.configurationName
					+ " file: " + HOME_CONFIG_PATH
					+ SWService_Configuration_FileMngr.configurationFileName,
					fnfe);

		} catch (Exception e) {
			logger.error("Could not serialize the " + this.configurationName
					+ " file: " + HOME_CONFIG_PATH
					+ SWService_Configuration_FileMngr.configurationFileName, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	protected abstract void checkConsistency() throws ConfigurationException;

	protected final T getConfig() {
		return this.configurationBean;
	}
}
