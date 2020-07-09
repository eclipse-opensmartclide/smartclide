package de.atb.context.services.registration;

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


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;
import pt.uninova.context.modules.config.DeployerConfigurationObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme
 */

@Root
public class SWRegistrationConfig implements ISWRegistrationConfig {

	@ElementList(name = "registers", entry = "register")
	protected List<SWRegister> registers = new ArrayList<SWRegister>();

	protected List<DeployerConfigurationObject> dcoList = new ArrayList<>();

	@Commit
	protected final void createIdMappings() {
		this.dcoList.clear();
		for (SWRegister register : this.registers) {
			DeployerConfigurationObject dco = new DeployerConfigurationObject(
					register.port, register.URL, register.address);
			this.dcoList.add(dco);
		}
	}

	@Override
	public final List<SWRegister> getRegisters() {
		return this.registers;
	}
}