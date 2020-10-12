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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * SelfLearningStackTraceElement
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ContextStackTraceElement implements Serializable {

	private static final long serialVersionUID = 2392243641467267123L;

	// Normally initialized by VM (public constructor added in 1.5)
	@XmlElement
	protected String declaringClass;
	@XmlElement
	protected String methodName;
	@XmlElement
	protected String fileName;
	@XmlElement
	protected int lineNumber;

	protected ContextStackTraceElement() {
	}

	/**
	 * Creates a stack trace element representing the specified execution point.
	 *
	 * @param declaringClass
	 *            the fully qualified name of the class containing the execution
	 *            point represented by the stack trace element
	 * @param methodName
	 *            the name of the method containing the execution point
	 *            represented by the stack trace element
	 * @param fileName
	 *            the name of the file containing the execution point
	 *            represented by the stack trace element, or <tt>null</tt> if
	 *            this information is unavailable
	 * @param lineNumber
	 *            the line number of the source line containing the execution
	 *            point represented by this stack trace element, or a negative
	 *            number if this information is unavailable. A value of -2
	 *            indicates that the method containing the execution point is a
	 *            native method
	 * @throws NullPointerException
	 *             if <tt>declaringClass</tt> or <tt>methodName</tt> is null
	 */
	public ContextStackTraceElement(final String declaringClass,
                                    final String methodName, final String fileName, final int lineNumber) {
		if (declaringClass == null) {
			throw new NullPointerException("Declaring class is null");
		}
		if (methodName == null) {
			throw new NullPointerException("Method name is null");
		}

		this.declaringClass = declaringClass;
		this.methodName = methodName;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
	}

	/**
	 * Returns the name of the source file containing the execution point
	 * represented by this stack trace element. Generally, this corresponds to
	 * the <tt>SourceFile</tt> attribute of the relevant <tt>class</tt> file (as
	 * per <i>The Java Virtual Machine Specification</i>, Section 4.7.7). In
	 * some systems, the name may refer to some source code unit other than a
	 * file, such as an entry in source repository.
	 *
	 * @return the name of the file containing the execution point represented
	 *         by this stack trace element, or <tt>null</tt> if this information
	 *         is unavailable.
	 */
	public final String getFileName() {
		return fileName;
	}

	/**
	 * Returns the line number of the source line containing the execution point
	 * represented by this stack trace element. Generally, this is derived from
	 * the <tt>LineNumberTable</tt> attribute of the relevant <tt>class</tt>
	 * file (as per <i>The Java Virtual Machine Specification</i>, Section
	 * 4.7.8).
	 *
	 * @return the line number of the source line containing the execution point
	 *         represented by this stack trace element, or a negative number if
	 *         this information is unavailable.
	 */
	public final int getLineNumber() {
		return lineNumber;
	}

	/**
	 * Returns the fully qualified name of the class containing the execution
	 * point represented by this stack trace element.
	 *
	 * @return the fully qualified name of the <tt>Class</tt> containing the
	 *         execution point represented by this stack trace element.
	 */
	public final String getClassName() {
		return declaringClass;
	}

	/**
	 * Returns the name of the method containing the execution point represented
	 * by this stack trace element. If the execution point is contained in an
	 * instance or class initializer, this method will return the appropriate
	 * <i>special method name</i>, <tt>&lt;init&gt;</tt> or
	 * <tt>&lt;clinit&gt;</tt>, as per Section 3.9 of <i>The Java Virtual
	 * Machine Specification</i>.
	 *
	 * @return the name of the method containing the execution point represented
	 *         by this stack trace element.
	 */
	public final String getMethodName() {
		return methodName;
	}

	/**
	 * Returns true if the method containing the execution point represented by
	 * this stack trace element is a native method.
	 *
	 * @return <tt>true</tt> if the method containing the execution point
	 *         represented by this stack trace element is a native method.
	 */
	public final boolean isNativeMethod() {
		return lineNumber == -2;
	}

	/**
	 * Returns a string representation of this stack trace element. The format
	 * of this string depends on the implementation, but the following examples
	 * may be regarded as typical:
	 * <ul>
	 * <li>
	 * <tt>"MyClass.mash(MyClass.java:9)"</tt> - Here, <tt>"MyClass"</tt> is the
	 * <i>fully-qualified name</i> of the class containing the execution point
	 * represented by this stack trace element, <tt>"mash"</tt> is the name of
	 * the method containing the execution point, <tt>"MyClass.java"</tt> is the
	 * source file containing the execution point, and <tt>"9"</tt> is the line
	 * number of the source line containing the execution point.
	 * <li>
	 * <tt>"MyClass.mash(MyClass.java)"</tt> - As above, but the line number is
	 * unavailable.
	 * <li>
	 * <tt>"MyClass.mash(Unknown Source)"</tt> - As above, but neither the file
	 * name nor the line number are available.
	 * <li>
	 * <tt>"MyClass.mash(Native Method)"</tt> - As above, but neither the file
	 * name nor the line number are available, and the method containing the
	 * execution point is known to be a native method.
	 * </ul>
	 *
	 * @see Throwable#printStackTrace()
	 */
	@Override
	public final String toString() {
		return getClassName()
				+ "."
				+ methodName
				+ (isNativeMethod() ? "(Native Method)" : ((fileName != null)
						&& (lineNumber >= 0) ? "(" + fileName + ":"
						+ lineNumber + ")" : (fileName != null ? "(" + fileName
						+ ")" : "(Unknown Source)")));
	}

	/**
	 * Returns true if the specified object is another
	 * <tt>StackTraceElement</tt> instance representing the same execution point
	 * as this instance. Two stack trace elements <tt>a</tt> and <tt>b</tt> are
	 * equal if and only if:
	 *
	 * <pre>
	 *     equals(a.getFileName(), b.getFileName()) &amp;&amp;
	 *     a.getLineNumber() == b.getLineNumber()) &amp;&amp;
	 *     equals(a.getClassName(), b.getClassName()) &amp;&amp;
	 *     equals(a.getMethodName(), b.getMethodName())
	 * </pre>
	 *
	 * where <tt>equals</tt> is defined as:
	 *
	 * <pre>
	 * static boolean equals(Object a, Object b) {
	 * 	return a == b || (a != null &amp;&amp; a.equals(b));
	 * }
	 * </pre>
	 *
	 * @param obj
	 *            the object to be compared with this stack trace element.
	 * @return true if the specified object is another
	 *         <tt>StackTraceElement</tt> instance representing the same
	 *         execution point as this instance.
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ContextStackTraceElement)) {
			return false;
		}
		final ContextStackTraceElement e = (ContextStackTraceElement) obj;
		return e.declaringClass.equals(declaringClass)
				&& (e.lineNumber == lineNumber)
				&& ContextStackTraceElement.eq(methodName, e.methodName)
				&& ContextStackTraceElement.eq(fileName, e.fileName);
	}

	private static boolean eq(final Object a, final Object b) {
		return (a == b) || ((a != null) && a.equals(b));
	}

	/**
	 * Returns a hash code value for this stack trace element.
	 */
	@Override
	public final int hashCode() {
		int result = 31 * declaringClass.hashCode() + methodName.hashCode();
		result = 31 * result + (fileName == null ? 0 : fileName.hashCode());
		result = 31 * result + lineNumber;
		return result;
	}

	public static ContextStackTraceElement convert(
			final StackTraceElement element) {
		return new ContextStackTraceElement(element.getClassName(),
				element.getMethodName(), element.getFileName(),
				element.getLineNumber());
	}

	public static List<ContextStackTraceElement> convert(
			final Collection<StackTraceElement> elements) {
		final List<ContextStackTraceElement> convertedElements = new ArrayList<>();
		for (final StackTraceElement element : elements) {
			convertedElements.add(ContextStackTraceElement.convert(element));
		}
		return convertedElements;
	}

	public static List<ContextStackTraceElement> convert(
			final StackTraceElement[] stackTrace) {
		return ContextStackTraceElement.convert(Arrays.asList(stackTrace));
	}

	public static StackTraceElement toStackTraceElement(
			final ContextStackTraceElement element) {
		return new StackTraceElement(element.getClassName(),
				element.getMethodName(), element.getFileName(),
				element.getLineNumber());
	}

	public static List<StackTraceElement> toStackTraceElementsList(
			final Collection<ContextStackTraceElement> elements) {
		final List<StackTraceElement> convertedElements = new ArrayList<>();
		for (final ContextStackTraceElement element : elements) {
			convertedElements.add(ContextStackTraceElement
					.toStackTraceElement(element));
		}
		return convertedElements;
	}

	public static StackTraceElement[] toStackTraceElementsArray(
			final Collection<ContextStackTraceElement> elements) {
		final List<StackTraceElement> convertedElements = new ArrayList<>();
		for (final ContextStackTraceElement element : elements) {
			convertedElements.add(ContextStackTraceElement
					.toStackTraceElement(element));
		}
		return convertedElements
				.toArray(new StackTraceElement[elements.size()]);
	}

	public static List<StackTraceElement> toStackTraceElements(
			final ContextStackTraceElement[] stackTrace) {
		return ContextStackTraceElement.toStackTraceElementsList(Arrays
				.asList(stackTrace));
	}

}
