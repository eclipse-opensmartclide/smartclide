package pt.uninova.context.services.async;

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


import pt.uninova.context.services.infrastructure.response.InvokeResponse;
import pt.uninova.context.services.interfaces.Output;

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
