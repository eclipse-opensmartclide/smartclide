package de.atb.context.services.infrastructure.response;

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


import de.atb.context.services.interfaces.Output;

/**
 *
 * @author Giovanni
 */
public class InvokeResponse {
	Output testValue;

	public final Output getTestValue() {
		return testValue;
	}

	public final void setTestValue(final Output testValue) {
		this.testValue = testValue;
	}

}
