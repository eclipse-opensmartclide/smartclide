package de.atb.context.common.util;

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
