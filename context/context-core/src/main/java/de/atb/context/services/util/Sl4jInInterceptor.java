package de.atb.context.services.util;

/*
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

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Sl4jInInterceptor
 *
 * @author Florian Meyer
 * @version $LastChangedRevision: 417 $
 */
public class Sl4jInInterceptor extends AbstractPhaseInterceptor<Message> {

	private final Logger logger = LoggerFactory
			.getLogger(Sl4jInInterceptor.class);

	public Sl4jInInterceptor(final String phase) {
		super(phase);
	}

	public Sl4jInInterceptor() {
		super(Phase.RECEIVE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message
	 * .Message)
	 */
	@Override
	public final void handleMessage(final Message message) throws Fault {
		if (logger.isDebugEnabled()) {
			final InputStream is = message.getContent(InputStream.class);
			if (is == null) {
				return;
			}

			final CachedOutputStream bos = new CachedOutputStream();
			try {
				IOUtils.copy(is, bos);
				is.close();
				bos.close();
				logger.debug("Inbound Message\n"
						+ "--------------------------------------\n"
						+ bos.getOut().toString()
						+ "\n--------------------------------------");
				message.setContent(InputStream.class, bos.getInputStream());

			} catch (final IOException e) {
				throw new Fault(e);
			}
		}
	}
}
