package de.atb.context.ui.util;

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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.net.URL;

/**
 *
 * @author Giovanni
 */
public class UIHelper {

	private static final ImageIcon EMPTY_ICON = new ImageIcon();

	private static final Logger logger = LoggerFactory
			.getLogger(UIHelper.class);

	public static ImageIcon getIcon(final Icon icon) {
		return getIcon(icon.getName());
	}

	public static ImageIcon getIcon(final String name) {
		final String path = "/resources/img/" + name;
		URL url = UIHelper.class.getResource(path);
		if (url == null) {
			logger.info("URL '" + path + " could not be found!");
			return EMPTY_ICON;
		}
		return new ImageIcon(url);
	}
}
