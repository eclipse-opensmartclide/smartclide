package de.atb.context.services.async;

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


import de.atb.context.services.infrastructure.response.InvokeResponse;
import de.atb.context.services.interfaces.Output;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giovanni
 */
public class MyAsyncHandler implements AsyncHandler<InvokeResponse> {

	private InvokeResponse reply;

	@Override
	public final void handleResponse(final Response<InvokeResponse> rspns) {
		try {
			System.err.println("handleResponse called");
			reply = rspns.get();
		} catch (InterruptedException | ExecutionException ex) {
			Logger.getLogger(MyAsyncHandler.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public final Output getResponse() {
		return reply.getTestValue();
	}

}
