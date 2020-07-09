package de.atb.context.common;

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

/**
 * Version
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public enum Version {

	PROSECO(0, 8, 15),

	INDEXER(0, 8, 15),

	MONITORING_DATA(0, 8, 15),

	;

	private final int major;
	private final int minor;
	private final int build;

	private Version(final int major, final int minor, final int build) {
		this.major = major;
		this.minor = minor;
		this.build = build;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public int getBuild() {
		return build;
	}

	public String getVersionString() {
		return String.format("%d.%d.%d", Integer.valueOf(major),
				Integer.valueOf(minor), Integer.valueOf(build));
	}

}
