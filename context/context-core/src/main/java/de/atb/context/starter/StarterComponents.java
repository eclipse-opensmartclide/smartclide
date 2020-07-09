package de.atb.context.starter;

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


import de.atb.context.ui.starter.Starter;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giovanni
 */
public class StarterComponents extends Starter {

	private boolean UIAlive = false;

	/**
	 * @param args
	 *            the command line arguments
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 * @throws URISyntaxException
	 */

	/**
	 *
	 * @return returns true if the UI is alive, false otherwise
	 */
	public final boolean getUIAlive() {
		return UIAlive;
	}

	public final void setUIAlive() {
		UIAlive = false;
	}

	public static StarterComponents start(final String[] args, final String type)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			URISyntaxException {
		// TODO code application logic here
		// File file = new
		// File(AbsolutefilePath.concat("\\resources\\services-config.xml"));

		String homeConfigPath = System.getProperty("user.home")
				+ File.separator + ".context" + File.separator
				+ "resources" + File.separator + "services-config.xml";
		File file = new File(homeConfigPath);
		// InputStream i =
		// StarterUI.class.getResourceAsStream("/resources/services-config.xml");
		// File file = new File(/*new URI(url.toString())*/
		// "resources/services-config.xml");
		String filepath = file.getPath();
		if (!file.exists()) {
			// Try in the project folder
			File fileRuntime = new File(
					/* new URI(url.toString()) */"resources\\services-config.xml");
			filepath = fileRuntime.getPath();
			if (!fileRuntime.exists()) {
				// Try relative to the jar
				File fileRelative = new File(
						/* new URI(url.toString()) */"..\\resources\\services-config.xml");
				filepath = fileRelative.getPath();
				if (!fileRelative.exists()) {
					System.exit(0);
				}
			}
		}

		String[] config = { filepath };
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// String type;
		/* Uncomment to choose desired services */
		// String[] ServiceIds = {"AmI-monitoring",
		// "deployer","AmI-repository","registry","registry-repository"};
		// type=UIType.Deployer.getName();
		// String[] ServiceIds = {"AmI-monitoring",
		// "deployer","AmI-repository"}; type=UIType.Deployer.getName();
		// String[] ServiceIds = { "registry","registry-repository"};
		// type=UIType.Registry.getName();
		StarterComponents starterDeployerUI = null;
		if ((args != null) && args.length > 0) {
			starterDeployerUI = new StarterComponents(config, args, type);
		} else {
			String[] ServiceIds = { "registry", "registry-repository", "broker"};
			starterDeployerUI = new StarterComponents(config,
					ServiceIds, type);
		}
		// type = UIType.Deployer.getName();
		/* StarterDeployerUI */

		try {
			starterDeployerUI.start();
		} catch (InterruptedException | InvocationTargetException ex) {
			Logger.getLogger(StarterComponents.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return starterDeployerUI;
	}

	public StarterComponents(final String[] args, final String[] ServiceIds,
							 final String type) {
		super(args, ServiceIds);
		this.frame = new StarterUIFrame(this, type);
		this.UIAlive = true;
		reloadConfiguration();
	}

	@Override
	public final void validateAdditionalStartupParameters() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
