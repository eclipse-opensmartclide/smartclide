package de.atb.context.services.util;

/*
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
