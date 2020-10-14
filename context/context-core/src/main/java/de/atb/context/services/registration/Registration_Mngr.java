package de.atb.context.services.registration;

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


import de.atb.context.ui.config.ConfigurationDeployerDialog;
import de.atb.context.ui.util.interfaces.ICommunicationBetweenUIs;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.modules.Deployer;
import de.atb.context.modules.config.DeployerConfigurationObject;
import de.atb.context.services.infrastructure.IServiceRegistryService;
import de.atb.context.services.manager.ServiceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

/**
 *
 * @author Guilherme
 */
public class Registration_Mngr {

	private final Logger logger = LoggerFactory
			.getLogger(Registration_Mngr.class);

	protected static final String homeConfigPath = System
			.getProperty("user.home")
			+ File.separator
			+ ".context"
			+ File.separator;
	protected static final String configurationFileName = "resources" + File.separator + "registry-config.xml";
	protected static final String DefaultFileName = "registry-config.xml";
	private Deployer deployer;
	protected SWRegistrationConfig regBean;
	private ICommunicationBetweenUIs CommInterface;
	ConfigurationDeployerDialog cdd;
	ArrayList<String> PingList = new ArrayList<>();

	public Registration_Mngr() {
		PingList.clear();
	}

	public Registration_Mngr(final Deployer deploy, final ConfigurationDeployerDialog cdd,
			final ICommunicationBetweenUIs CommInterface) {
		this.deployer = deploy;
		this.cdd = cdd;
		this.CommInterface = CommInterface;
	}

	public final void init() {
		File file = new File(homeConfigPath + configurationFileName);
		InputStream is = null;
		String where = "";
		if (file.exists()) {
			try {
				where = file.getAbsolutePath();
				Serializer serializer = new Persister();
				logger.info("Trying to load configuration from '" + where + "'");
				is = new FileInputStream(file);
				this.regBean = serializer.read(SWRegistrationConfig.class, is);
				is.close();

			} catch (FileNotFoundException ex) {
				java.util.logging.Logger.getLogger(
						Registration_Mngr.class.getName()).log(Level.SEVERE,
						null, ex);
			} catch (Exception ex) {
				this.regBean = new SWRegistrationConfig();
			}

			if (!regBean.dcoList.isEmpty()) {
				logger.info("Previous entries of Service registration found");
				this.updatePingState();
				// cdd.start_autoRegDialog();
			} else {
				logger.info("No previous entries of Service registration found");
			}
		} else {
			this.saveRegistryCommConfig();
			logger.info("Could not open registry Configuration file in: "
					+ Registration_Mngr.homeConfigPath + configurationFileName);
		}
	}

	private String saveRegistryCommConfig() {
		logger.info("Creating configuration...");

		File source = new File(homeConfigPath + File.separator + "resources"
				+ File.separator + DefaultFileName);
		File sourceLocation = new File(homeConfigPath + File.separator + "resources");
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
				// FileUtils.copyFile(new File(configurationFileName), source);
				source.createNewFile();
				logger.info(source.getAbsolutePath()
						+ " Configuration file saved!");
				this.regBean = new SWRegistrationConfig();
			} else {
				logger.warn(source.getAbsolutePath()
						+ " Configuration file could not be saved");
				return null;
			}
		} catch (Exception e) {
			// logger.error("Could not save the " + this.configurationName +
			// " file: " + this.RegistryCommConfigurationFileName, e);
			return null;
		}
		return source.getAbsolutePath();
	}

	public final boolean autoConnect() {
		Iterator<DeployerConfigurationObject> it = regBean.dcoList.iterator();
		while (it.hasNext()) {
			DeployerConfigurationObject dco = it.next();
			if (testConnection(dco)) {
				logger.info("Starting connection from loaded configuration...");
				this.deployer.setDeployerConfigurationObject(dco);
				this.CommInterface.setConnected();
				this.deployer.registerServices(dco.getURL(),
						Integer.valueOf(dco.getPort()),
						IServiceRegistryService.class);
				logger.info("Starting connection from loaded configuration...");
				new_registry(dco.getURL(), Integer.valueOf(dco.getPort()));
				return true;
			}
		}
		logger.info("All previous entries of Service registry unavailable");
		return false;
	}

	public final boolean testConnection(final DeployerConfigurationObject dco) {
		logger.trace("Testing connection to Server");
		IServiceRegistryService client = ServiceManager.getWebservice(
				dco.getURL(), Integer.valueOf(dco.getPort()),
				IServiceRegistryService.class);
		if (ServiceManager.isPingable(client)) {
			return true;
		}
		return false;
	}

	public final void new_registry(final String URL, final int port) {
		String address = String.format("http://%s:%d", URL.trim(), port);
		if (this.regBean == null) {
			this.regBean = new SWRegistrationConfig();
		}
		List<SWRegister> regList = this.regBean.getRegisters();
		// if (!regList.isEmpty()) {
		Iterator<SWRegister> it = regList.iterator();
		SWRegister srw;
		String date = getCurrentTime();
		while (it.hasNext()) {
			srw = it.next();
			if (srw.getAddress().equals(address)) {
				srw.setDate(date);
				this.regBean.createIdMappings();
				logger.info("Updated existing entry in registry file");
				saveFile();
				return;
			}
		}
		logger.info("Adding new entry to registry file");
		srw = new SWRegister();
		srw.setAddress(address);
		srw.setDate(date);
		srw.setURL(URL);
		srw.setPort(String.valueOf(port));
		regList.add(0, srw);
		this.regBean.createIdMappings();
		logger.info("Updated entry in registry file");
		saveFile();
	}

	private String getCurrentTime() {
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("y / MM / d  HH:mm:ss");
		return (sdf.format(date));
	}

	public final String saveFile() {
		logger.info("Saving configuration...");
		Serializer serializer = new Persister();
		File file = new File(homeConfigPath + configurationFileName);
		try {
			if ((file.exists() || file.createNewFile())/*
														 * &&
														 * (sourceManager.exists
														 * () || sourceManager.
														 * createNewFile())
														 */) {
				serializer.write(this.regBean, file);
				// serializer.write(SWServiceConfiguration.getSettingByString(SWServiceConfiguration.getDefaultMangerFileName()).configurationBean,
				// sourceManager);
				logger.info("" + configurationFileName /*
														 * + "and " +
														 * SWServiceConfiguration
														 * .
														 * getDefaultMangerFileName
														 * ()
														 */
						+ " saved! at directory: " + homeConfigPath);
			} else {
				logger.warn("" + configurationFileName
						+ " could not be saved, because the file "
						+ file.getAbsolutePath() + " could not be created!");
				return null;
			}
		} catch (Exception e) {
			logger.error("Could not save the "
					+ Registration_Mngr.configurationFileName + " file: "
					+ Registration_Mngr.configurationFileName, e);
			return null;
		}
		return file.getAbsolutePath();
	}

	public final void updatePingState() {
        this.PingList.clear();
        if(this.regBean.dcoList != null){
            for (DeployerConfigurationObject dcoOb : this.regBean.dcoList) {
                if (testConnection(dcoOb)){
                    this.PingList.add("Online");
                }else{
                    this.PingList.add("Offline");
                }
            }
        }            
    }
}
