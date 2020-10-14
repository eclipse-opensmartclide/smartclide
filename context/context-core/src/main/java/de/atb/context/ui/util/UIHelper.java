package de.atb.context.ui.util;

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
