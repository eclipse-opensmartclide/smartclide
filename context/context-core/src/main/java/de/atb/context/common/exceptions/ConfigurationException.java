package de.atb.context.common.exceptions;

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

/**
 * ConfigurationException
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public class ConfigurationException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -1289806516067641033L;

	/**
	 * Constructs a new configuration exception with the specified detail
	 * message. The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 *
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
	public ConfigurationException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is
	 * <i>not</i> automatically incorporated in this exception's detail message.
	 *
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 * @since 1.4
	 */
	public ConfigurationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new configuration exception with the specified detail
	 * message. The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 *
	 * @param format
	 *            the format string for the detail message (which is saved for
	 *            later retrieval by the {@link #getMessage()} method).
	 * @param args
	 *            arguments to be passed to the format message string.
	 */
	public ConfigurationException(final String format, final Object... args) {
		super(String.format(format, args));
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is
	 * <i>not</i> automatically incorporated in this exception's detail message.
	 *
	 * @param format
	 *            the format string for the detail message (which is saved for
	 *            later retrieval by the {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 * @param args
	 *            arguments to be passed to the format message string.
	 */
	public ConfigurationException(final String format, final Throwable cause,
			final Object... args) {
		super(String.format(format, args), cause);
	}
}
