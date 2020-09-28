package de.atb.context.ui.util;

/*-
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
