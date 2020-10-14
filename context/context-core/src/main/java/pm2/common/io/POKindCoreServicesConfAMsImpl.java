package pm2.common.io;

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
import pm2.common.application.ObjectKinds;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class POKindCoreServicesConfAMsImpl implements
		POKindCoreServicesConfAMs {

	// currently it only checks whether the file exists. In case it exists
	// returns true
	public boolean POKindCoreServicesConfOpenAM(String name, String path,
			ObjectKinds kind) {
		File file = new File(path + File.separator + name);
		return file.exists();
	}

	public boolean POKindCoreServicesConfCloseAM(String name, ObjectKinds kind) {
		return true;
	}

	// TODO currently count and offset are not used
	public byte[] POKindCoreServicesConfReadAM(String name, String path,
			ObjectKinds kind, int count, int offset) {
		try {
			return Files.readAllBytes(Paths.get(path + File.separator + name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean POKindCoreServicesConfWriteAM(String name, ObjectKinds kind,
			byte[] buf) {
		return true;
	}
}
