package de.atb.context.common.io;

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


import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Output a Java object graph in JSON format. This code handles cyclic
 * references and can serialize any Object graph without requiring a class to be
 * 'Serializeable' or have any specific methods on it.
 * <ul>
 * <li>
 * Call the static method: {@code JsonWriter.toJson(employee)}. This will
 * convert the passed in 'employee' instance into a JSON String.</li>
 * <li>Using streams:
  * <pre>
 * JsonWriter writer = new JsonWriter(stream);
 * writer.write(employee);
 * writer.close();
 * </pre>
 * This will write the 'employee' object to the passed in OutputStream.</li>
 * </ul>
 *
 * That's it. This can be used as a debugging tool. Output an object graph using
 * the above code. You can copy that JSON output into this site which formats it
 * with a lot of whitespace to make it human readable:
 * http://jsonformatter.curiousconcept.com
 *
 * This will output any object graph deeply (or null). Object references are
 * properly handled. For example, if you had A-&gt;B, B-&gt;C, and C-&gt;A, then A will
 * be serialized with a B object in it, B will be serialized with a C object in
 * it, and then C will be serialized with a reference to A (ref), not a
 * redefinition of A.
 *
 * @author John DeRegnaucourt (jdereg@gmail.com)
 *         Copyright (c) Cedar Software LLC
 *
 *         Licensed under the Apache License, Version 2.0 (the "License"); you
 *         may not use this file except in compliance with the License. You may
 *         obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *         implied. See the License for the specific language governing
 *         permissions and limitations under the License.
 */
@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
public class JsonWriter implements Closeable, Flushable {
	public static final String DATE_FORMAT = "DATE_FORMAT";
	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
	public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String TYPE = "TYPE";
	public static final String PRETTY_PRINT = "PRETTY_PRINT";
	private static final Map<String, ClassMeta> _classMetaCache = new HashMap<>();
	private static final List<Object[]> _writers = new ArrayList<>();
	private static final Set<Class> _notCustom = new HashSet<>();
	private static Object[] _byteStrings = new Object[256];
	private static final String newLine = System.getProperty("line.separator");
	private final Map<Object, Long> _objVisited = new IdentityHashMap<>();
	private final Map<Object, Long> _objsReferenced = new IdentityHashMap<>();
	private final Writer _out;
	private long _identity = 1;
	private int depth = 0;
	// _args is using ThreadLocal so that static inner classes can have access
	// to them
	static final ThreadLocal<Map<String, Object>> _args = ThreadLocal.withInitial(HashMap::new);
	static final ThreadLocal<SimpleDateFormat> _dateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));

	static { // Add customer writers (these make common classes more succinct)
		addWriter(String.class, new JsonStringWriter());
		addWriter(Date.class, new DateWriter());
		addWriter(BigInteger.class, new BigIntegerWriter());
		addWriter(BigDecimal.class, new BigDecimalWriter());
		addWriter(java.sql.Date.class, new DateWriter());
		addWriter(Timestamp.class, new TimestampWriter());
		addWriter(Calendar.class, new CalendarWriter());
		addWriter(TimeZone.class, new TimeZoneWriter());
		addWriter(Locale.class, new LocaleWriter());
		addWriter(Class.class, new ClassWriter());
		addWriter(StringBuilder.class, new StringBuilderWriter());
		addWriter(StringBuffer.class, new StringBufferWriter());
	}

	static {
		for (short i = -128; i <= 127; i++) {
			char[] chars = Integer.toString(i).toCharArray();
			_byteStrings[i + 128] = chars;
		}
	}

	static class ClassMeta extends LinkedHashMap<String, Field> {
	}

	/**
	 * @param item object to be converted
	 * @return returns an JSON String
	 * @throws java.io.IOException If an I/O error occurs
	 * @see JsonWriter#objectToJson(Object, java.util.Map)
	 */
	public static String objectToJson(final Object item) throws IOException {
		return objectToJson(item, new HashMap<>());
	}

	/**
	 * Convert a Java Object to a JSON String.
	 *
	 * @param item
	 *            Object to convert to a JSON String.
	 * @param optionalArgs
	 *            (optional) Map of extra arguments indicating how dates are
	 *            formatted, what fields are written out (optional). For Date
	 *            parameters, use the public static DATE_TIME key, and then use
	 *            the ISO_DATE or ISO_DATE_TIME indicators. Or you can specify
	 *            your own custom SimpleDateFormat String, or you can associate
	 *            a SimpleDateFormat object, in which case it will be used. This
	 *            setting is for both java.util.Date and java.sql.Date. If the
	 *            DATE_FORMAT key is not used, then dates will be formatted as
	 *            longs. This long can be turned back into a date by using 'new
	 *            Date(longValue)'.
	 * @return String containing JSON representation of passed in object.
	 * @throws java.io.IOException
	 *             If an I/O error occurs
	 */
	public static String objectToJson(final Object item,
			final Map<String, Object> optionalArgs) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try (JsonWriter writer = new JsonWriter(stream, optionalArgs)) {
			writer.write(item);
		}
		return new String(stream.toByteArray(), "UTF-8");
	}

	/**
	 * Format the passed in JSON string in a nice, human readable format.
	 * 
	 * @param json
	 *            String input JSON
	 * @return String containing equivalent JSON, formatted nicely for human
	 *         readability.
	 * @throws java.io.IOException If an I/O error occurs
	 */
	public static String formatJson(final String json) throws IOException {
		Map map = JsonReader.jsonToMaps(json);
		Map args = new HashMap();
		args.put(PRETTY_PRINT, "true");
		return objectToJson(map, args);
	}

	/**
	 * @param out writes to this stream
	 * @throws java.io.IOException If an I/O error occurs
	 * @see JsonWriter#JsonWriter(java.io.OutputStream, java.util.Map)
	 */
	public JsonWriter(final OutputStream out) throws IOException {
		this(out, new HashMap<>());
	}

	/**
	 * @param out
	 *            OutputStream to which the JSON output will be written.
	 * @param optionalArgs
	 *            (optional) Map of extra arguments indicating how dates are
	 *            formatted, what fields are written out (optional). For Date
	 *            parameters, use the public static DATE_TIME key, and then use
	 *            the ISO_DATE or ISO_DATE_TIME indicators. Or you can specify
	 *            your own custom SimpleDateFormat String, or you can associate
	 *            a SimpleDateFormat object, in which case it will be used. This
	 *            setting is for both java.util.Date and java.sql.Date. If the
	 *            DATE_FORMAT key is not used, then dates will be formatted as
	 *            longs. This long can be turned back into a date by using 'new
	 *            Date(longValue)'.
	 * @throws IOException If an I/O error occurs
	 */
	public JsonWriter(final OutputStream out, final Map<String, Object> optionalArgs)
			throws IOException {
		_args.get().clear();
		_args.get().putAll(optionalArgs);

		try {
			_out = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IOException(
					"Unsupported encoding.  Get a JVM that supports UTF-8", e);
		}
	}

	public interface JsonClassWriter {
		void write(Object o, boolean showType, Writer out) throws IOException;

		boolean hasPrimitiveForm();

		void writePrimitiveForm(Object o, Writer out) throws IOException;
	}

	public final boolean isPrettyPrint() {
		Object setting = _args.get().get(PRETTY_PRINT);
		if (setting instanceof Boolean) {
			return Boolean.TRUE.equals(setting);
		} else if (setting instanceof String) {
			return "true".equalsIgnoreCase((String) setting);
		} else if (setting instanceof Number) {
			return ((Number) setting).intValue() != 0;
		}

		return false;
	}

	private void tabIn(final Writer out) throws IOException {
		if (!isPrettyPrint()) {
			return;
		}
		out.write(newLine);
		depth++;
		for (int i = 0; i < depth; i++) {
			out.write("  ");
		}
	}

	private void newLine(final Writer out) throws IOException {
		if (!isPrettyPrint()) {
			return;
		}
		out.write(newLine);
		for (int i = 0; i < depth; i++) {
			out.write("  ");
		}
	}

	private void tabOut(final Writer out) throws IOException {
		if (!isPrettyPrint()) {
			return;
		}
		out.write(newLine);
		depth--;
		for (int i = 0; i < depth; i++) {
			out.write("  ");
		}
	}

	public static int getDistance(final Class a, final Class b) {
		Class curr = b;
		int distance = 0;

		while (curr != a) {
			distance++;
			curr = curr.getSuperclass();
			if (curr == null) {
				return Integer.MAX_VALUE;
			}
		}

		return distance;
	}

	public final boolean writeIfMatching(final Object o, final boolean showType, final Writer out)
			throws IOException {
		Class c = o.getClass();
		if (_notCustom.contains(c)) {
			return false;
		}

		return writeCustom(c, o, showType, out);
	}

	public final boolean writeArrayElementIfMatching(final Class arrayComponentClass,
			final Object o, final boolean showType, final Writer out) throws IOException {
		if (!o.getClass().isAssignableFrom(arrayComponentClass)
				|| _notCustom.contains(o.getClass())) {
			return false;
		}

		return writeCustom(arrayComponentClass, o, showType, out);
	}

	private boolean writeCustom(final Class arrayComponentClass, final Object o,
			final boolean showType, final Writer out) throws IOException {
		JsonClassWriter closestWriter = null;
		int minDistance = Integer.MAX_VALUE;

		for (Object[] item : _writers) {
			Class clz = (Class) item[0];
			if (clz == arrayComponentClass) {
				closestWriter = (JsonClassWriter) item[1];
				break;
			}
			int distance = getDistance(clz, arrayComponentClass);
			if (distance < minDistance) {
				minDistance = distance;
				closestWriter = (JsonClassWriter) item[1];
			}
		}

		if (closestWriter == null) {
			return false;
		}

		if (writeOptionalReference(o)) {
			return true;
		}

		boolean referenced = _objsReferenced.containsKey(o);

		if ((!referenced && !showType && closestWriter.hasPrimitiveForm())
				|| closestWriter instanceof JsonStringWriter) {
			closestWriter.writePrimitiveForm(o, out);
			return true;
		}

		out.write('{');
		tabIn(out);
		if (referenced) {
			writeId(getId(o));
			if (showType) {
				out.write(',');
				newLine(out);
			}
		}

		if (showType) {
			writeType(o, out);
		}

		if (referenced || showType) {
			out.write(',');
			newLine(out);
		}

		closestWriter.write(o, showType || referenced, out);
		tabOut(out);
		out.write('}');
		return true;
	}

	public static void addWriter(final Class c, final JsonClassWriter writer) {
		for (Object[] item : _writers) {
			Class clz = (Class) item[0];
			if (clz == c) {
				item[1] = writer; // Replace writer
				return;
			}
		}
		_writers.add(new Object[] { c, writer });
	}

	public static void addNotCustomWriter(final Class c) {
		_notCustom.add(c);
	}

	public static class TimeZoneWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			TimeZone cal = (TimeZone) obj;
			out.write("\"zone\":\"");
			out.write(cal.getID());
			out.write('"');
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return false;
		}

		@Override
		public void writePrimitiveForm(final Object o, final Writer out) throws IOException {
		}
	}

	public static class CalendarWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			Calendar cal = (Calendar) obj;
			_dateFormat.get().setTimeZone(cal.getTimeZone());
			out.write("\"time\":\"");
			out.write(_dateFormat.get().format(cal.getTime()));
			out.write("\",\"zone\":\"");
			out.write(cal.getTimeZone().getID());
			out.write('"');
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return false;
		}

		@Override
		public void writePrimitiveForm(final Object o, final Writer out) throws IOException {
		}
	}

	public static class DateWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			Date date = (Date) obj;
			Object dateFormat = _args.get().get(DATE_FORMAT);
			if (dateFormat instanceof String) { // Passed in as String, turn
												// into a SimpleDateFormat
												// instance to be used
												// throughout this stream write.
				dateFormat = new SimpleDateFormat((String) dateFormat);
				_args.get().put(DATE_FORMAT, dateFormat);
			}
			if (showType) {
				out.write("\"value\":");
			}

			if (dateFormat instanceof Format) {
				out.write("\"");
				out.write(((Format) dateFormat).format(date));
				out.write("\"");
			} else {
				out.write(Long.toString(((Date) obj).getTime()));
			}
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return true;
		}

		@Override
		public final void writePrimitiveForm(final Object o, final Writer out) throws IOException {
			if (_args.get().containsKey(DATE_FORMAT)) {
				write(o, false, out);
			} else {
				out.write(Long.toString(((Date) o).getTime()));
			}
		}
	}

	public static class TimestampWriter implements JsonClassWriter {
		@Override
		public final void write(final Object o, final boolean showType, final Writer out)
				throws IOException {
			Timestamp tstamp = (Timestamp) o;
			out.write("\"time\":\"");
			out.write(Long.toString((tstamp.getTime() / 1000) * 1000));
			out.write("\",\"nanos\":\"");
			out.write(Integer.toString(tstamp.getNanos()));
			out.write('"');
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return false;
		}

		@Override
		public void writePrimitiveForm(final Object o, final Writer out) throws IOException {
		}
	}

	public static class ClassWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			String value = ((Class) obj).getName();
			out.write("\"value\":");
			writeJsonUtf8String(value, out);
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return true;
		}

		@Override
		public final void writePrimitiveForm(final Object o, final Writer out) throws IOException {
			writeJsonUtf8String(((Class) o).getName(), out);
		}
	}

	public static class JsonStringWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			out.write("\"value\":");
			writeJsonUtf8String((String) obj, out);
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return true;
		}

		@Override
		public final void writePrimitiveForm(final Object o, final Writer out) throws IOException {
			writeJsonUtf8String((String) o, out);
		}
	}

	public static class LocaleWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			Locale locale = (Locale) obj;

			out.write("\"language\":\"");
			out.write(locale.getLanguage());
			out.write("\",\"country\":\"");
			out.write(locale.getCountry());
			out.write("\",\"variant\":\"");
			out.write(locale.getVariant());
			out.write('"');
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return false;
		}

		@Override
		public void writePrimitiveForm(final Object o, final Writer out) throws IOException {
		}
	}

	public static class BigIntegerWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			BigInteger big = (BigInteger) obj;
			out.write("\"value\":\"");
			out.write(big.toString(10));
			out.write('"');
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return true;
		}

		@Override
		public final void writePrimitiveForm(final Object o, final Writer out) throws IOException {
			BigInteger big = (BigInteger) o;
			out.write('"');
			out.write(big.toString(10));
			out.write('"');
		}
	}

	public static class BigDecimalWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			BigDecimal big = (BigDecimal) obj;
			out.write("\"value\":\"");
			out.write(big.toPlainString());
			out.write('"');
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return true;
		}

		@Override
		public final void writePrimitiveForm(final Object o, final Writer out) throws IOException {
			BigDecimal big = (BigDecimal) o;
			out.write('"');
			out.write(big.toPlainString());
			out.write('"');
		}
	}

	public static class StringBuilderWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			StringBuilder builder = (StringBuilder) obj;
			out.write("\"value\":\"");
			out.write(builder.toString());
			out.write('"');
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return true;
		}

		@Override
		public final void writePrimitiveForm(final Object o, final Writer out) throws IOException {
			StringBuilder builder = (StringBuilder) o;
			out.write('"');
			out.write(builder.toString());
			out.write('"');
		}
	}

	public static class StringBufferWriter implements JsonClassWriter {
		@Override
		public final void write(final Object obj, final boolean showType, final Writer out)
				throws IOException {
			StringBuffer buffer = (StringBuffer) obj;
			out.write("\"value\":\"");
			out.write(buffer.toString());
			out.write('"');
		}

		@Override
		public final boolean hasPrimitiveForm() {
			return true;
		}

		@Override
		public final void writePrimitiveForm(final Object o, final Writer out) throws IOException {
			StringBuffer buffer = (StringBuffer) o;
			out.write('"');
			out.write(buffer.toString());
			out.write('"');
		}
	}

	public final void write(final Object obj) throws IOException {
		traceReferences(obj);
		_objVisited.clear();
		writeImpl(obj, true);
		flush();
		_objVisited.clear();
		_objsReferenced.clear();
		_args.get().clear();
		_args.remove();
	}

	private void traceReferences(final Object root) {
		LinkedList<Object> stack = new LinkedList<>();
		stack.addFirst(root);
		final Map<Object, Long> visited = _objVisited;
		final Map<Object, Long> referenced = _objsReferenced;

		while (!stack.isEmpty()) {
			Object obj = stack.removeFirst();
			if (obj == null) {
				continue;
			}

			if (!JsonReader.isPrimitive(obj.getClass())
					&& !(obj instanceof String) && !(obj instanceof Date)) {
				Long id = visited.get(obj);
				if (id != null) { // Only write an object once.
					referenced.put(obj, id);
					continue;
				}
				visited.put(obj, _identity++);
			}

			final Class clazz = obj.getClass();

			if (clazz.isArray()) {
				Class compType = clazz.getComponentType();
				if (!JsonReader.isPrimitive(compType)
						&& compType != String.class
						&& !Date.class.isAssignableFrom(compType)) { // Speed
																		// up:
																		// do
																		// not
																		// traceReferences
																		// of
																		// primitives,
																		// they
																		// cannot
																		// reference
																		// anything
					final int len = Array.getLength(obj);

					for (int i = 0; i < len; i++) {
						Object o = Array.get(obj, i);
						if (o != null) { // Slight perf gain (null is legal)
							stack.addFirst(o);
						}
					}
				}
			} else {
				traceFields(stack, obj);
			}
		}
	}

	private static void traceFields(final LinkedList<Object> stack, final Object obj) {
		final ClassMeta fields = getDeepDeclaredFields(obj.getClass());

		for (Field field : fields.values()) {
			try {
				final Class<?> type = field.getType();
				if (JsonReader.isPrimitive(type) || String.class == type
						|| Date.class.isAssignableFrom(type)) { // speed up:
																// primitives
																// (Dates/Strings
																// considered
																// primitive by
																// json-io)
																// cannot
																// reference
																// another
																// object
					continue;
				}

				Object o = field.get(obj);
				if (o != null) {
					stack.addFirst(o);
				}
			} catch (IllegalAccessException | IllegalArgumentException ignored) {
			}
		}
	}

	private boolean writeOptionalReference(final Object obj) throws IOException {
		final Writer out = _out;
		if (_objVisited.containsKey(obj)) { // Only write (define) an object
											// once in the JSON stream,
											// otherwise emit a @ref
			String id = getId(obj);
			if (id == null) { // Test for null because of Weak/Soft references
								// being gc'd during serialization.
				return false;
			}
			out.write("{\"@ref\":");
			out.write(id);
			out.write('}');
			return true;
		}

		// Mark the object as visited by putting it in the Map (this map is
		// re-used / clear()'d after walk()).
		_objVisited.put(obj, null);
		return false;
	}

	private void writeImpl(final Object obj, final boolean showType) throws IOException {
		if (obj == null) {
			_out.write("null");
			return;
		}

		if (obj.getClass().isArray()) {
			writeArray(obj, showType);
		} else if (obj instanceof Collection) {
			writeCollection((Collection) obj, showType);
		} else if (obj instanceof JsonObject) { // symmetric support for writing
												// Map of Maps representation
												// back as identical JSON
												// format.
			JsonObject jObj = (JsonObject) obj;
			if (jObj.isArray()) {
				writeJsonObjectArray(jObj, showType);
			} else if (jObj.isCollection()) {
				writeJsonObjectCollection(jObj, showType);
			} else if (jObj.isMap()) {
				writeJsonObjectMap(jObj, showType);
			} else {
				writeJsonObjectObject(jObj, showType);
			}
		} else if (obj instanceof Map) {
			writeMap((Map) obj, showType);
		} else {
			writeObject(obj, showType);
		}
	}

	private void writeId(final String id) throws IOException {
		_out.write("\"@id\":");
		_out.write(id == null ? "0" : id);
	}

	private static void writeType(final Object obj, final Writer out) throws IOException {
		out.write("\"@type\":\"");
		Class c = obj.getClass();

		if (Boolean.class == c) {
			out.write("boolean");
		} else if (Byte.class == c) {
			out.write("byte");
		} else if (Short.class == c) {
			out.write("short");
		} else if (Integer.class == c) {
			out.write("int");
		} else if (Long.class == c) {
			out.write("long");
		} else if (Double.class == c) {
			out.write("double");
		} else if (Float.class == c) {
			out.write("float");
		} else if (Character.class == c) {
			out.write("char");
		} else if (Date.class == c) {
			out.write("date");
		} else if (Class.class == c) {
			out.write("class");
		} else if (String.class == c) {
			out.write("string");
		} else {
			out.write(c.getName());
		}
		out.write('"');
	}

	private void writePrimitive(final Object obj) throws IOException {
		if (obj instanceof Character) {
			writeJsonUtf8String(String.valueOf(obj), _out);
		} else {
			_out.write(obj.toString());
		}
	}

	private void writeArray(final Object array, final boolean showType) throws IOException {
		if (writeOptionalReference(array)) {
			return;
		}

		Class arrayType = array.getClass();
		int len = Array.getLength(array);
		boolean referenced = _objsReferenced.containsKey(array);
		// boolean typeWritten = showType && !(Object[].class == arrayType); //
		// causes IDE warning in NetBeans 7/4 Java 1.7
		boolean typeWritten = showType && !(arrayType.equals(Object[].class));

		final Writer out = _out; // performance opt: place in final local for
									// quicker access
		if (typeWritten || referenced) {
			out.write('{');
			tabIn(out);
		}

		if (referenced) {
			writeId(getId(array));
			out.write(',');
			newLine(out);
		}

		if (typeWritten) {
			writeType(array, out);
			out.write(',');
			newLine(out);
		}

		if (len == 0) {
			if (typeWritten || referenced) {
				out.write("\"@items\":[]");
				tabOut(out);
				out.write('}');
			} else {
				out.write("[]");
			}
			return;
		}

		if (typeWritten || referenced) {
			out.write("\"@items\":[");
		} else {
			out.write('[');
		}
		tabIn(out);

		final int lenMinus1 = len - 1;

		// Intentionally processing each primitive array type in separate
		// custom loop for speed. All of them could be handled using
		// reflective Array.get() but it is slower. I chose speed over code
		// length.
		if (byte[].class == arrayType) {
			writeByteArray((byte[]) array, lenMinus1);
		} else if (char[].class == arrayType) {
			writeJsonUtf8String(new String((char[]) array), out);
		} else if (short[].class == arrayType) {
			writeShortArray((short[]) array, lenMinus1);
		} else if (int[].class == arrayType) {
			writeIntArray((int[]) array, lenMinus1);
		} else if (long[].class == arrayType) {
			writeLongArray((long[]) array, lenMinus1);
		} else if (float[].class == arrayType) {
			writeFloatArray((float[]) array, lenMinus1);
		} else if (double[].class == arrayType) {
			writeDoubleArray((double[]) array, lenMinus1);
		} else if (boolean[].class == arrayType) {
			writeBooleanArray((boolean[]) array, lenMinus1);
		} else {
			final Class componentClass = array.getClass().getComponentType();
			final boolean isPrimitiveArray = JsonReader
					.isPrimitive(componentClass);
			final boolean isObjectArray = Object[].class == arrayType;

			for (int i = 0; i < len; i++) {
				final Object value = Array.get(array, i);

				if (value == null) {
					out.write("null");
				} else if (isPrimitiveArray || value instanceof Boolean
						|| value instanceof Long || value instanceof Double) {
					writePrimitive(value);
				} else if (writeArrayElementIfMatching(componentClass, value,
						false, out)) {
				} else if (isObjectArray) {
					if (writeIfMatching(value, true, out)) {
					} else {
						writeImpl(value, true);
					}
				} else { // Specific Class-type arrays - only force type when
							// the instance is derived from array base class.
					boolean forceType = !(value.getClass() == componentClass);
					writeImpl(value, forceType || alwaysShowType());
				}

				if (i != lenMinus1) {
					out.write(',');
					newLine(out);
				}
			}
		}

		tabOut(out);
		out.write(']');
		if (typeWritten || referenced) {
			tabOut(out);
			out.write('}');
		}
	}

	/**
	 * @return true if the user set the 'TYPE' flag to true, indicating to
	 *         always show type.
	 */
	private boolean alwaysShowType() {
		return Boolean.TRUE.equals(_args.get().get(TYPE));
	}

	private void writeBooleanArray(final boolean[] booleans, final int lenMinus1)
			throws IOException {
		final Writer out = _out;
		for (int i = 0; i < lenMinus1; i++) {
			out.write(booleans[i] ? "true," : "false,");
		}
		out.write(Boolean.toString(booleans[lenMinus1]));
	}

	private void writeDoubleArray(final double[] doubles, final int lenMinus1)
			throws IOException {
		final Writer out = _out;
		for (int i = 0; i < lenMinus1; i++) {
			out.write(Double.toString(doubles[i]));
			out.write(',');
		}
		out.write(Double.toString(doubles[lenMinus1]));
	}

	private void writeFloatArray(final float[] floats, final int lenMinus1)
			throws IOException {
		final Writer out = _out;
		for (int i = 0; i < lenMinus1; i++) {
			out.write(Double.toString(floats[i]));
			out.write(',');
		}
		out.write(Float.toString(floats[lenMinus1]));
	}

	private void writeLongArray(final long[] longs, final int lenMinus1) throws IOException {
		final Writer out = _out;
		for (int i = 0; i < lenMinus1; i++) {
			out.write(Long.toString(longs[i]));
			out.write(',');
		}
		out.write(Long.toString(longs[lenMinus1]));
	}

	private void writeIntArray(final int[] ints, final int lenMinus1) throws IOException {
		final Writer out = _out;
		for (int i = 0; i < lenMinus1; i++) {
			out.write(Integer.toString(ints[i]));
			out.write(',');
		}
		out.write(Integer.toString(ints[lenMinus1]));
	}

	private void writeShortArray(final short[] shorts, final int lenMinus1)
			throws IOException {
		final Writer out = _out;
		for (int i = 0; i < lenMinus1; i++) {
			out.write(Integer.toString(shorts[i]));
			out.write(',');
		}
		out.write(Integer.toString(shorts[lenMinus1]));
	}

	private void writeByteArray(final byte[] bytes, final int lenMinus1) throws IOException {
		final Writer out = _out;
		final Object[] byteStrs = _byteStrings;
		for (int i = 0; i < lenMinus1; i++) {
			out.write((char[]) byteStrs[bytes[i] + 128]);
			out.write(',');
		}
		out.write((char[]) byteStrs[bytes[lenMinus1] + 128]);
	}

	private void writeCollection(final Collection col, final boolean showType)
			throws IOException {
		if (writeOptionalReference(col)) {
			return;
		}

		final Writer out = _out;
		boolean referenced = _objsReferenced.containsKey(col);
		boolean isEmpty = col.isEmpty();

		if (referenced || showType) {
			out.write('{');
			tabIn(out);
		} else if (isEmpty) {
			out.write('[');
		}

		if (referenced) {
			writeId(getId(col));
		}

		if (showType) {
			if (referenced) {
				out.write(',');
				newLine(out);
			}
			writeType(col, out);
		}

		if (isEmpty) {
			if (referenced || showType) {
				tabOut(out);
				out.write('}');
			} else {
				out.write(']');
			}
			return;
		}

		if (showType || referenced) {
			out.write(',');
			newLine(out);
			out.write("\"@items\":[");
		} else {
			out.write('[');
		}
		tabIn(out);

		Iterator i = col.iterator();

		while (i.hasNext()) {
			writeCollectionElement(i.next());

			if (i.hasNext()) {
				out.write(',');
				newLine(out);
			}

		}

		tabOut(out);
		out.write(']');
		if (showType || referenced) { // Finished object, as it was output as an
										// object if @id or @type was output
			tabOut(out);
			out.write("}");
		}
	}

	private void writeJsonObjectArray(final JsonObject jObj, final boolean showType)
			throws IOException {
		if (writeOptionalReference(jObj)) {
			return;
		}

		int len = jObj.getLength();
		String type = jObj.type;
		Class arrayClass;

		if (type == null || Object[].class.getName().equals(type)) {
			arrayClass = Object[].class;
		} else {
			arrayClass = JsonReader.classForName2(type);
		}

		final Writer out = _out;
		final boolean isObjectArray = Object[].class == arrayClass;
		final Class componentClass = arrayClass.getComponentType();
		boolean referenced = _objsReferenced.containsKey(jObj) && jObj.hasId();
		boolean typeWritten = showType && !isObjectArray;

		if (typeWritten || referenced) {
			out.write('{');
			tabIn(out);
		}

		if (referenced) {
			writeId(Long.toString(jObj.id));
			out.write(',');
			newLine(out);
		}

		if (typeWritten) {
			out.write("\"@type\":\"");
			out.write(arrayClass.getName());
			out.write("\",");
			newLine(out);
		}

		if (len == 0) {
			if (typeWritten || referenced) {
				out.write("\"@items\":[]");
				tabOut(out);
				out.write("}");
			} else {
				out.write("[]");
			}
			return;
		}

		if (typeWritten || referenced) {
			out.write("\"@items\":[");
		} else {
			out.write('[');
		}
		tabIn(out);

		Object[] items = (Object[]) jObj.get("@items");
		final int lenMinus1 = len - 1;

		for (int i = 0; i < len; i++) {
			final Object value = items[i];

			if (value == null) {
				out.write("null");
			} else if (Character.class == componentClass
					|| char.class == componentClass) {
				writeJsonUtf8String((String) value, out);
			} else if (value instanceof Boolean || value instanceof Long
					|| value instanceof Double) {
				writePrimitive(value);
			} else if (value instanceof String) { // Have to specially treat
													// String because it could
													// be referenced, but we
													// still want inline (no
													// @type, value:)
				writeJsonUtf8String((String) value, out);
			} else if (isObjectArray) {
				if (writeIfMatching(value, true, out)) {
				} else {
					writeImpl(value, true);
				}
			} else if (writeArrayElementIfMatching(componentClass, value,
					false, out)) {
			} else { // Specific Class-type arrays - only force type when
						// the instance is derived from array base class.
				boolean forceType = !(value.getClass() == componentClass);
				writeImpl(value, forceType || alwaysShowType());
			}

			if (i != lenMinus1) {
				out.write(',');
				newLine(out);
			}
		}

		tabOut(out);
		out.write(']');
		if (typeWritten || referenced) {
			tabOut(out);
			out.write('}');
		}
	}

	private void writeJsonObjectCollection(final JsonObject jObj, final boolean showType)
			throws IOException {
		if (writeOptionalReference(jObj)) {
			return;
		}

		String type = jObj.type;
		Class colClass = JsonReader.classForName2(type);
		boolean referenced = _objsReferenced.containsKey(jObj) && jObj.hasId();
		final Writer out = _out;
		int len = jObj.getLength();

		if (referenced || showType || len == 0) {
			out.write('{');
			tabIn(out);
		}

		if (referenced) {
			writeId(String.valueOf(jObj.id));
		}

		if (showType) {
			if (referenced) {
				out.write(',');
				newLine(out);
			}
			out.write("\"@type\":\"");
			out.write(colClass.getName());
			out.write('"');
		}

		if (len == 0) {
			tabOut(out);
			out.write('}');
			return;
		}

		if (showType || referenced) {
			out.write(',');
			newLine(out);
			out.write("\"@items\":[");
		} else {
			out.write('[');
		}
		tabIn(out);

		Object[] items = (Object[]) jObj.get("@items");
		final int itemsLen = items.length;
		final int itemsLenMinus1 = itemsLen - 1;

		for (int i = 0; i < itemsLen; i++) {
			writeCollectionElement(items[i]);

			if (i != itemsLenMinus1) {
				out.write(',');
				newLine(out);
			}
		}

		tabOut(out);
		out.write("]");
		if (showType || referenced) {
			tabOut(out);
			out.write('}');
		}
	}

	private void writeJsonObjectMap(final JsonObject jObj, final boolean paramShowType)
			throws IOException {
		boolean showType = paramShowType;
		if (writeOptionalReference(jObj)) {
			return;
		}

		boolean referenced = _objsReferenced.containsKey(jObj) && jObj.hasId();
		final Writer out = _out;

		out.write('{');
		tabIn(out);
		if (referenced) {
			writeId(String.valueOf(jObj.getId()));
		}

		if (showType) {
			if (referenced) {
				out.write(',');
				newLine(out);
			}
			String type = jObj.getType();
			if (type != null) {
				Class mapClass = JsonReader.classForName2(type);
				out.write("\"@type\":\"");
				out.write(mapClass.getName());
				out.write('"');
			} else { // type not displayed
				showType = false;
			}
		}

		if (jObj.isEmpty()) { // Empty
			tabOut(out);
			out.write('}');
			return;
		}

		if (showType) {
			out.write(',');
			newLine(out);
		}

		out.write("\"@keys\":[");
		tabIn(out);
		Iterator i = jObj.keySet().iterator();

		while (i.hasNext()) {
			writeCollectionElement(i.next());

			if (i.hasNext()) {
				out.write(',');
				newLine(out);
			}
		}

		tabOut(out);
		out.write("],");
		newLine(out);
		out.write("\"@items\":[");
		tabIn(out);
		i = jObj.values().iterator();

		while (i.hasNext()) {
			writeCollectionElement(i.next());

			if (i.hasNext()) {
				out.write(',');
				newLine(out);
			}
		}

		tabOut(out);
		out.write(']');
		tabOut(out);
		out.write('}');
	}

	private void writeJsonObjectObject(final JsonObject jObj, final boolean paramShowType)
			throws IOException {
		boolean showType = paramShowType;
		if (writeOptionalReference(jObj)) {
			return;
		}

		final Writer out = _out;
		boolean referenced = _objsReferenced.containsKey(jObj) && jObj.hasId();
		showType = showType && jObj.type != null;
		Class type = null;

		out.write('{');
		tabIn(out);
		if (referenced) {
			writeId(String.valueOf(jObj.id));
		}

		if (showType) {
			if (referenced) {
				out.write(',');
				newLine(out);
			}
			out.write("\"@type\":\"");
			out.write(jObj.type);
			out.write('"');
			try {
				type = JsonReader.classForName2(jObj.type);
			} catch (IOException ignored) {
				type = null;
			}
		}

		if (jObj.isEmpty()) {
			tabOut(out);
			out.write('}');
			return;
		}

		if (showType || referenced) {
			out.write(',');
			newLine(out);
		}

		Iterator<Map.Entry<String, Object>> i = jObj.entrySet().iterator();

		while (i.hasNext()) {
			Map.Entry<String, Object> entry = i.next();
			final String fieldName = entry.getKey();
			out.write('"');
			out.write(fieldName);
			out.write("\":");
			Object value = entry.getValue();

			if (value == null) {
				out.write("null");
			} else if (value instanceof BigDecimal
					|| value instanceof BigInteger) {
				writeImpl(value,
						!doesValueTypeMatchFieldType(type, fieldName, value));
			} else if (value instanceof Number || value instanceof Boolean) {
				out.write(value.toString());
			} else if (value instanceof String) {
				writeJsonUtf8String((String) value, out);
			} else if (value instanceof Character) {
				writeJsonUtf8String(String.valueOf(value), out);
			} else {
				writeImpl(value,
						!doesValueTypeMatchFieldType(type, fieldName, value));
			}
			if (i.hasNext()) {
				out.write(',');
				newLine(out);
			}
		}
		tabOut(out);
		out.write('}');
	}

	private static boolean doesValueTypeMatchFieldType(final Class type,
			final String fieldName, final Object value) {
		if (type != null) {
			ClassMeta meta = getDeepDeclaredFields(type);
			Field field = meta.get(fieldName);
			return field != null && (value.getClass() == field.getType());
		}
		return false;
	}

	private void writeMap(final Map map, final boolean showType) throws IOException {
		if (writeOptionalReference(map)) {
			return;
		}

		final Writer out = _out;
		boolean referenced = _objsReferenced.containsKey(map);

		out.write('{');
		tabIn(out);
		if (referenced) {
			writeId(getId(map));
		}

		if (showType) {
			if (referenced) {
				out.write(',');
				newLine(out);
			}
			writeType(map, out);
		}

		if (map.isEmpty()) {
			tabOut(out);
			out.write('}');
			return;
		}

		if (showType || referenced) {
			out.write(',');
			newLine(out);
		}

		out.write("\"@keys\":[");
		tabIn(out);
		Iterator i = map.keySet().iterator();

		while (i.hasNext()) {
			writeCollectionElement(i.next());

			if (i.hasNext()) {
				out.write(',');
				newLine(out);
			}
		}

		tabOut(out);
		out.write("],");
		newLine(out);
		out.write("\"@items\":[");
		tabIn(out);
		i = map.values().iterator();

		while (i.hasNext()) {
			writeCollectionElement(i.next());

			if (i.hasNext()) {
				out.write(',');
				newLine(out);
			}
		}

		tabOut(out);
		out.write(']');
		tabOut(out);
		out.write('}');
	}

	/**
	 * Write an element that is contained in some type of collection or Map.
	 * 
	 * @param o
	 *            Collection element to output in JSON format.
	 * @throws IOException
	 *             if an error occurs writing to the output stream.
	 */
	private void writeCollectionElement(final Object o) throws IOException {
		if (o == null) {
			_out.write("null");
		} else if (o instanceof Boolean || o instanceof Long
				|| o instanceof Double) {
			_out.write(o.toString());
		} else if (o instanceof String) {
			writeJsonUtf8String((String) o, _out);
		} else {
			writeImpl(o, true);
		}
	}

	/**
	 * @param obj
	 *            Object to be written in JSON format
	 * @param showType
	 *            boolean true means show the "@type" field, false eliminates
	 *            it. Many times the type can be dropped because it can be
	 *            inferred from the field or array type.
	 * @throws IOException
	 *             if an error occurs writing to the output stream.
	 */
	private void writeObject(final Object obj, final boolean showType) throws IOException {
		if (writeIfMatching(obj, showType, _out)) {
			return;
		}

		if (writeOptionalReference(obj)) {
			return;
		}

		final Writer out = _out;

		out.write('{');
		tabIn(out);
		boolean referenced = _objsReferenced.containsKey(obj);
		if (referenced) {
			writeId(getId(obj));
		}

		ClassMeta classInfo = getDeepDeclaredFields(obj.getClass());

		if (referenced && showType) {
			out.write(',');
			newLine(out);
		}

		if (showType) {
			writeType(obj, out);
		}

		boolean first = !showType;
		if (referenced && !showType) {
			first = false;
		}

		for (Map.Entry<String, Field> entry : classInfo.entrySet()) {
			Field field = entry.getValue();
			if ((field.getModifiers() & Modifier.TRANSIENT) != 0) { // Do not
																	// write
																	// transient
																	// fields
				continue;
			}
			if (first) {
				first = false;
			} else {
				out.write(',');
				newLine(out);
			}

			writeJsonUtf8String(entry.getKey(), out);
			out.write(':');

			Object o;
			try {
				o = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException ignored) {
				o = null;
			}

			if (o == null) { // don't quote null
				out.write("null");
				continue;
			}

			Class type = field.getType();
			boolean forceType = o.getClass() != type; // If types are not
														// exactly the same,
														// write "@type" field

			if (JsonReader.isPrimitive(type)) {
				writePrimitive(o);
			} else if (writeIfMatching(o, forceType, out)) {
			} else {
				writeImpl(o, forceType || alwaysShowType());
			}
		}

		tabOut(out);
		out.write('}');
	}

	/**
	 * Write out special characters "\b, \f, \t, \n, \r", as such, backslash as
	 * \\ quote as \" and values less than an ASCII space (20hex) as "\\u00xx"
	 * format, characters in the range of ASCII space to a '~' as ASCII, and
	 * anything higher in UTF-8.
	 *
	 * @param s String to be written in utf8 format on the output stream.
	 * @param out the output will be written to this writer
	 * @throws IOException if an error occurs writing to the output stream.
	 */
	public static void writeJsonUtf8String(final String s, final Writer out)
			throws IOException {
		out.write('\"');
		int len = s.length();

		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);

			if (c < ' ') { // Anything less than ASCII space, write either in
							// \\u00xx form, or the special \t, \n, etc. form
				if (c == '\b') {
					out.write("\\b");
				} else if (c == '\t') {
					out.write("\\t");
				} else if (c == '\n') {
					out.write("\\n");
				} else if (c == '\f') {
					out.write("\\f");
				} else if (c == '\r') {
					out.write("\\r");
				} else {
					String hex = Integer.toHexString(c);
					out.write("\\u");
					int pad = 4 - hex.length();
					for (int k = 0; k < pad; k++) {
						out.write('0');
					}
					out.write(hex);
				}
			} else if (c == '\\' || c == '"') {
				out.write('\\');
				out.write(c);
			} else { // Anything else - write in UTF-8 form (multi-byte encoded)
						// (OutputStreamWriter is UTF-8)
				out.write(c);
			}
		}
		out.write('\"');
	}

	/**
	 * @param c
	 *            Class instance
	 * @return ClassMeta which contains fields of class. The results are cached
	 *         internally for performance when called again with same Class.
	 */
	static ClassMeta getDeepDeclaredFields(final Class c) {
		ClassMeta classInfo = _classMetaCache.get(c.getName());
		if (classInfo != null) {
			return classInfo;
		}

		classInfo = new ClassMeta();
		Class curr = c;

		while (curr != null) {
			try {
				Field[] local = curr.getDeclaredFields();

				for (Field field : local) {
					if ((field.getModifiers() & Modifier.STATIC) == 0) { // speed
																			// up:
																			// do
																			// not
																			// process
																			// static
																			// fields.
						if (!field.isAccessible()) {
							try {
								field.setAccessible(true);
							} catch (SecurityException ignored) {
							}
						}
						if (classInfo.containsKey(field.getName())) {
							classInfo.put(
									curr.getName() + '.' + field.getName(),
									field);
						} else {
							classInfo.put(field.getName(), field);
						}
					}
				}
			} catch (ThreadDeath t) {
				throw t;
			} catch (SecurityException ignored) {
			}

			curr = curr.getSuperclass();
		}

		_classMetaCache.put(c.getName(), classInfo);
		return classInfo;
	}

	@Override
	public final void flush() {
		try {
			if (_out != null) {
				_out.flush();
			}
		} catch (IOException ignored) {
		}
	}

	@Override
	public final void close() {
		try {
			_out.close();
		} catch (IOException ignore) {
		}
	}

	private String getId(final Object o) {
		if (o instanceof JsonObject) {
			long id = ((JsonObject) o).id;
			if (id != -1) {
				return String.valueOf(id);
			}
		}
		Long id = _objsReferenced.get(o);
		return id == null ? null : Long.toString(id);
	}
}
