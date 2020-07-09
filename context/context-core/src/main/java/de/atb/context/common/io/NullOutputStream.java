package de.atb.context.common.io;

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

import java.io.IOException;
import java.io.OutputStream;

/**
 * NullOutputStream
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public class NullOutputStream extends OutputStream {

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(final int b) throws IOException {
		//
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(final byte[] b) throws IOException {
		//
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(final byte[] b, final int off, final int len)
			throws IOException {
		//
	}
}
