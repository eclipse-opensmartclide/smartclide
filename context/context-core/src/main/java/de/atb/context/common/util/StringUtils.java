package de.atb.context.common.util;

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

/**
 * StringUtils
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public class StringUtils {

	private StringUtils() {}

	public static String capitalize(final String str) {
		if (str == null) {
			return null;
		}
		char ch;
		char prevCh;
		int i;
		prevCh = '.';
		final StringBuilder builder = new StringBuilder(str.length());
		for (i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (Character.isLetter(ch) && !Character.isLetter(prevCh)) {
				builder.append(Character.toUpperCase(ch));
			} else {
				builder.append(ch);
			}
			prevCh = ch;
		}
		return builder.toString();
	}

	public static boolean isEmpty(final String str) {
		return (str == null) || (str.trim().length() == 0);
	}
}
