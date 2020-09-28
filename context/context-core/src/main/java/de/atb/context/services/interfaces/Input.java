package de.atb.context.services.interfaces;

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


import java.util.List;

/**
 *
 * @author Giovanni
 */
public class Input {

	private List<DataInput> dataInput;

	public Input() {
	}

	public Input(final List<DataInput> dataInput) {
		this.dataInput = dataInput;
	}

	public final List<DataInput> getDataInput() {
		return dataInput;
	}

	public final void setDataInput(final List<DataInput> dataInput) {
		this.dataInput = dataInput;
	}
        
        public final boolean addDataInput(DataInput dataInput){
            return this.dataInput.add(dataInput);
        }

}
