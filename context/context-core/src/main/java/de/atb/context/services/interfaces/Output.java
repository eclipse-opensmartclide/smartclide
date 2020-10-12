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


/**
 *
 * @author Giovanni
 */
public class Output {

	private DataOutput dataOutput;

	public Output() {
	}

	public Output(final DataOutput dataOutput) {
		this.dataOutput = dataOutput;
	}

	public final DataOutput getDataOutput() {
		return dataOutput;
	}

	public final void setDataOutput(final DataOutput dataOutput) {
		this.dataOutput = dataOutput;
	}

}
