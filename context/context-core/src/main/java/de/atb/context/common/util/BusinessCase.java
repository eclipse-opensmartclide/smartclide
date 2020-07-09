package de.atb.context.common.util;

/*
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
