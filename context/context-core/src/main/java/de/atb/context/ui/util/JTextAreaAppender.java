package de.atb.context.ui.util;

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
