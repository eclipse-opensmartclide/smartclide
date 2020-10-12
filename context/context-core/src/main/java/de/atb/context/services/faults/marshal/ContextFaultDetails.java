package de.atb.context.services.faults.marshal;

/*
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SelfLearningFaultDetails
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ContextFaultDetails implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1807012641467806983L;

	private static final Logger logger = LoggerFactory
			.getLogger(ContextFaultDetails.class);

	@XmlElement
	protected String message;

	@XmlElement
	protected ArrayList<ContextStackTraceElement> stackTrace;

	@XmlElement
	protected String causeName;

	protected ContextFaultDetails() {
		this(null, null);
	}

	public ContextFaultDetails(final String message) {
		this(message, null);
	}

	public ContextFaultDetails(final Throwable cause) {
		this(null, cause);
	}

	public ContextFaultDetails(final String message, final Throwable cause) {
		this.message = message;
		if (cause != null) {
			causeName = cause.getClass().getName();
			stackTrace = new ArrayList<>(
					ContextStackTraceElement.convert(cause.getStackTrace()));
		}
	}

	@SuppressWarnings("unchecked")
	public final Throwable asThrowable() {
		Throwable t = new Throwable(message);
		try {
			final Class<? extends Throwable> clazz = (Class<? extends Throwable>) Class
					.forName(getCauseName());
			t = clazz.newInstance();
		} catch (final ClassNotFoundException|InstantiationException|IllegalAccessException e) {
			ContextFaultDetails.logger.warn(e.getMessage(), e);
		}
		t.setStackTrace(ContextStackTraceElement
				.toStackTraceElementsArray(stackTrace));
		return t;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(final String message) {
		this.message = message;
	}

	public final List<ContextStackTraceElement> getStackTrace() {
		return stackTrace;
	}

	public final void setStackTrace(
			final List<ContextStackTraceElement> stackTrace) {
		this.stackTrace = new ArrayList<>(stackTrace);
	}

	public final String getCauseName() {
		return causeName;
	}

	public final void setCauseName(final String causeName) {
		this.causeName = causeName;
	}

	/**
	 * Returns a short description of this throwable. The result is the
	 * concatenation of:
	 * <ul>
	 * <li>the {@linkplain Class#getName() name} of the class of this object
	 * <li>": " (a colon and a space)
	 * <li>the result of invoking this object's {getLocalizedMessage} method
	 * </ul>
	 * If <tt>getLocalizedMessage</tt> returns <tt>null</tt>, then just the
	 * class name is returned.
	 *
	 * @return a string representation of this throwable.
	 */
	@Override
	public final String toString() {
		final String s = (causeName != null) ? causeName : this.getClass()
				.getName();
		final String msg = getMessage();
		return (msg != null) ? (s + ": " + msg) : s;
	}

	public final void printStackTrace() {
		printStackTrace(System.err);
	}

	/**
	 * Prints this throwable and its backtrace to the specified print stream.
	 *
	 * @param s
	 *            <code>PrintStream</code> to use for output
	 */
	public final void printStackTrace(final PrintStream s) {
		Object lockObj = new Object();
		synchronized (lockObj) {
			s.println(this);
			if (stackTrace != null) {
				for (final ContextStackTraceElement element : stackTrace) {
					s.println("\tat " + element);
				}
			}
		}
	}

	/**
	 * Prints this throwable and its backtrace to the specified print writer.
	 *
	 * @param s
	 *            <code>PrintWriter</code> to use for output
	 */
	public final void printStackTrace(final PrintWriter s) {
        Object lockObj = new Object();
		synchronized (lockObj) {
			s.println(this);
			if (stackTrace != null) {
				for (final ContextStackTraceElement element : stackTrace) {
					s.println("\tat " + element);
				}
			}
		}
	}
}
