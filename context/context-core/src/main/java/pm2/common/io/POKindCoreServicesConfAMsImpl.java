package pm2.common.io;

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
