package de.atb.context.services.registration;

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


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;
import de.atb.context.modules.config.DeployerConfigurationObject;

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
