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

import java.util.HashMap;
import java.util.Map;

/**
 * BusinessCase
 *
 * @author scholze
 * @version $LastChangedRevision: 634 $
 *
 */
public class BusinessCase {
	public static final String NS_BASE_ID = "context";
	public static final String NS_BASE_URL = "http://atb-bremen.de/";

	public static final String NS_DUMMY_ID = "dummy";
	public static final String NS_DUMMY_URL = BusinessCase.NS_BASE_URL + "bc-dummy/";

	private static volatile Map<String, BusinessCase> settings = new HashMap<String, BusinessCase>();
	private String id;
	private String url;

	public static void getInstance() {
		if (settings.get(NS_DUMMY_ID) == null) {
			settings.put(NS_DUMMY_ID, new BusinessCase(NS_DUMMY_ID, NS_DUMMY_URL));
		}
		if (settings.get(NS_BASE_ID) == null) {
			settings.put(NS_BASE_ID, new BusinessCase(NS_BASE_ID, NS_BASE_URL));
		}
	}

	public static BusinessCase getInstance(String id, String url) {
		if (settings.get(id) == null) {
			settings.put(id, new BusinessCase(id, url));
		}
		return settings.get(id);
	}

	public BusinessCase(String id, String url) {
		this.id = id;
		this.url = url;
	}

	public BusinessCase getBusinessCase(String id) {
		return settings.get(id);
	}

	@Override
	public String toString() {
		return id;
	}

	public static BusinessCase[] values() {
		return settings.values().toArray(new BusinessCase[0]);
	}
}
