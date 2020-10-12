package de.atb.context.common;

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
