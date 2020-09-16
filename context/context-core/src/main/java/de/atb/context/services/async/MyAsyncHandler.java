package de.atb.context.services.async;

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
