package de.atb.context.services.interfaces;

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
