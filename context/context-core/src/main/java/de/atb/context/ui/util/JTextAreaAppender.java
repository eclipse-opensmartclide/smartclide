package de.atb.context.ui.util;

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


import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Giovanni
 */
public class JTextAreaAppender extends AppenderSkeleton {

	private final PatternLayout layout;
	private final JTextArea text;
	private final JScrollPane container;
	private PrintStream osErr, osOut;
	private OutputStream stdErr;
	private OutputStream stdOut;

	public JTextAreaAppender(final JTextArea text, final JScrollPane container) {
		this.layout = new PatternLayout(
				"%d{dd.MM HH:mm} %-5p %c{1}.%M(%F:%L) - %m%n");
		this.text = text;
		stdErr = System.err;
		stdOut = System.out;
		this.container = container;
		osErr = new PrintStream(new BufferedOutputStream(new OutputStream() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void write(final int b) throws IOException {
				JTextAreaAppender.this.text.append(String.valueOf((char) b));
				stdErr.write(b);
				JTextAreaAppender.this.container
						.getVerticalScrollBar()
						.setValue(container.getVerticalScrollBar().getMaximum());
			}

			@SuppressWarnings("synthetic-access")
			@Override
			public void write(final byte[] b) throws IOException {
				JTextAreaAppender.this.text.append(new String(b));
				stdErr.write(b);
				JTextAreaAppender.this.container
						.getVerticalScrollBar()
						.setValue(container.getVerticalScrollBar().getMaximum());
			}

			@SuppressWarnings("synthetic-access")
			@Override
			public void write(final byte[] b, final int off, final int len) throws IOException {
				JTextAreaAppender.this.text.append(new String(b, off, len));
				stdErr.write(b, off, len);
				JTextAreaAppender.this.container
						.getVerticalScrollBar()
						.setValue(container.getVerticalScrollBar().getMaximum());
			}
		}), true);
		osOut = new PrintStream(new BufferedOutputStream(new OutputStream() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void write(final int b) throws IOException {
				JTextAreaAppender.this.text.append(String.valueOf((char) b));
				stdOut.write(b);
				JTextAreaAppender.this.container
						.getVerticalScrollBar()
						.setValue(container.getVerticalScrollBar().getMaximum());
			}

			@SuppressWarnings("synthetic-access")
			@Override
			public void write(final byte[] b) throws IOException {
				JTextAreaAppender.this.text.append(new String(b));
				stdOut.write(b);
				JTextAreaAppender.this.container
						.getVerticalScrollBar()
						.setValue(container.getVerticalScrollBar().getMaximum());
			}

			@SuppressWarnings("synthetic-access")
			@Override
			public void write(final byte[] b, final int off, final int len) throws IOException {
				JTextAreaAppender.this.text.append(new String(b, off, len));
				stdOut.write(b, off, len);
				JTextAreaAppender.this.container
						.getVerticalScrollBar()
						.setValue(container.getVerticalScrollBar().getMaximum());
			}
		}), true);
		System.setErr(osErr);
		System.setOut(osOut);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#close()
	 */
	@Override
	public void close() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	@Override
	public final boolean requiresLayout() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent
	 * )
	 */
	@Override
	protected final void append(final LoggingEvent event) {
		if (text != null) {
			text.append(layout.format(event));
			if (event.getThrowableInformation() != null) {
				for (String str : event.getThrowableStrRep()) {
					text.append(str + "\n");
				}
			}
			JTextAreaAppender.this.container.getVerticalScrollBar().setValue(
					container.getVerticalScrollBar().getMaximum());
		}
	}

}
