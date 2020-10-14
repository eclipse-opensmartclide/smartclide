package de.atb.context.kmb;

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

import de.atb.context.common.io.ResourceLoader;
import de.atb.context.services.faults.ContextFault;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.cert.X509Certificate;

/**
 * KMB API Wrapper
 *
 * @author scholze
 * @version $LastChangedRevision: 176 $
 */
public class KMBApi {
	private static final Logger logger = LoggerFactory
			.getLogger(ResourceLoader.class);
	private final String baseURL = "https://apiproseco.repcon.es/repcon/r_call/proseco_kmb_api/_/r/";
	private final String encoding = "UTF-8";
	private URL theUrl;
	private HttpsURLConnection theConnection;

	public KMBApi() {
		KMBApi.initializeSecurity();
	}

	private final void connect() throws ContextFault {
		try {
			theUrl = new URL(baseURL);
			theConnection = (HttpsURLConnection) theUrl.openConnection();
			theConnection.setConnectTimeout(10000);
			theConnection.setReadTimeout(30000);
			theConnection.setDoOutput(true); // Triggers POST method.
			theConnection.setRequestProperty("content-type",
					"application/x-www-form-urlencoded; charset=" + encoding);
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
	}

	private final void disconnect() {
		theConnection.disconnect();
	}

        private final boolean writeWentOK() throws IOException {
            InputStreamReader inputReader = new InputStreamReader(theConnection.getInputStream(), encoding);

            StringWriter writer = new StringWriter();
            IOUtils.copy(inputReader, writer);
            String returnValue = writer.toString();
            KMBApi.logger.info(returnValue);

            writer.close();
            inputReader.close();
            return returnValue.startsWith("OK");
        }
    
	/**
	 * Retrieves the text information associated to this element in PROSECO
	 * Knowledge DATABASE.
	 * 
	 * @param id
	 *            Unique identifier of the element (for instance unique
	 *            identifier of the PES). To be sent between single quotes.
	 *            (e.g. PES_example_12345)
	 * @throws ContextFault Thrown in any case of error
	 * @return String
	 */
	public final String readElementConfiguration(final String id)
			throws ContextFault {
		String returnValue = "";
		OutputStream theOutputStream;
		InputStreamReader inputReader;
		StringWriter writer;
		int returnCode;
		String urlEncoded;
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			urlEncoded = URLEncoder.encode("['" + id + "']", encoding);
			theOutputStream
			.write(("predicate=readElementConfiguration&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();
			// TODO handling of return codes is missing
			KMBApi.logger.info("test call readElementConfiguration got code "
					+ returnCode);
			inputReader = new InputStreamReader(theConnection.getInputStream(),
					encoding);

			writer = new StringWriter();
			IOUtils.copy(inputReader, writer);
			returnValue = writer.toString();
			KMBApi.logger.info(returnValue);

			theOutputStream.close();
			writer.close();
			inputReader.close();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
		return returnValue;
	}

	/**
	 * Retrieves all the configurations associated to the passed PES in
	 * PROSECO Knowledge DATABASE.
	 * 
	 * @param id
	 *            Unique identifier of the element (for instance unique
	 *            identifier of the PES). To be sent between single quotes.
	 *            (e.g. PES_example_12345)
	 * @throws ContextFault Thrown in any case of error
	 * @return String
	 */
	public final String readAllElementConfigurations(final String id)
			throws ContextFault {
		String returnValue = "";
		OutputStream theOutputStream;
		InputStreamReader inputReader;
		StringWriter writer;
		int returnCode;
		String urlEncoded;
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			urlEncoded = URLEncoder.encode("['" + id + "']", encoding);
			theOutputStream
			.write(("predicate=readAllElementConfigurations&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();
			// TODO handling of return codes is missing
			KMBApi.logger.info("test call readAllElementConfigurations got code "
					+ returnCode);
			inputReader = new InputStreamReader(theConnection.getInputStream(),
					encoding);

			writer = new StringWriter();
			IOUtils.copy(inputReader, writer);
			returnValue = writer.toString();
			KMBApi.logger.info(returnValue);

			theOutputStream.close();
			writer.close();
			inputReader.close();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
		return returnValue;
	}

	/**
	 * @param id
	 *            (e.g. PES_example_12345)
	 * @param jsonText
	 *            (e.g. {\
	 *            "\"PES_example_12345\"\":{},\"\"Machine_example_234\"\":{},\\\n\"\"selected
	 *            _ S e n s o r s \ " \ " : [ \ \ \ n \ \ \ t {
	 *            \"\"name\"\":\"\"Actor
	 *            Position\"\",\"\"description\"\":\"\"Actor Position Sensor
	 *            example\
	 *            "\",\"\"id\"\":\"\"1f2153b2-50c2-4d1e-a23d-f533a2ece76e\"\",\"\"output_value\"\":{\"\"NativeType\"\":\"\"float\"\",\"\"unit\"\":\"\"meter\"\",\"\"De
	 *            s c r i p t i o n \ " \ " : \ " \ " A c t o r Position Sensor
	 *            value Example\"\"},\"\"type\"\":\"\"simpleValue\"\"}]})
	 * @throws ContextFault Thrown in any case of error
	 * @return true or false
	 */
	public final boolean writeElementConfiguration(final String id,
			final String jsonText) throws ContextFault {
		OutputStream theOutputStream;
		int returnCode;
                boolean result = false;
		String urlEncoded;
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			final String pesId = "'" + id + "'";
			final String configText = "'" + jsonText + "'";
			urlEncoded = URLEncoder.encode(
					"[" + pesId + "," + configText + "]", encoding);
			theOutputStream
			.write(("predicate=writeElementConfiguration&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();

                        KMBApi.logger.info("test call writeElementConfiguration got code "
					+ returnCode);
                        result = writeWentOK();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
                return result;
	}

	/**
	 * @param id
	 *            (e.g. PES_example_12345)
	 * @param clazz
	 *            (e.g. kmb_specific_subclass)
	 * @param jsonText
	 *            (e.g. {\
	 *            "\"PES_example_12345\"\":{},\"\"Machine_example_234\"\":{},\\\n\"\"selected
	 *            _ S e n s o r s \ " \ " : [ \ \ \ n \ \ \ t {
	 *            \"\"name\"\":\"\"Actor
	 *            Position\"\",\"\"description\"\":\"\"Actor Position Sensor
	 *            example\
	 *            "\",\"\"id\"\":\"\"1f2153b2-50c2-4d1e-a23d-f533a2ece76e\"\",\"\"output_value\"\":{\"\"NativeType\"\":\"\"float\"\",\"\"unit\"\":\"\"meter\"\",\"\"De
	 *            s c r i p t i o n \ " \ " : \ " \ " A c t o r Position Sensor
	 *            value Example\"\"},\"\"type\"\":\"\"simpleValue\"\"}]})
	 * @throws ContextFault ContextFault
	 * @return true or false
	 */
	@SuppressWarnings("javadoc")
	public final boolean writeElementConfiguration(final String id,
			final String clazz, final String jsonText) throws ContextFault {
		OutputStream theOutputStream;
		int returnCode;
                boolean result = false;
		String urlEncoded;
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			final String pesId = "'" + id + "'";
			final String pesClass = "'" + clazz + "'";
			final String configText = "'" + jsonText + "'";
			urlEncoded = URLEncoder.encode("[" + pesId + "," + pesClass + ","
					+ configText + "]", encoding);
			theOutputStream
			.write(("predicate=writeElementConfiguration&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();

                        KMBApi.logger.info("test call writeElementConfiguration got code "
					+ returnCode);
                        result = writeWentOK();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
                return result;
	}

	/**
	 * Writes the information of the related element into the PROSECO Knowledge
	 * DATABASE. This information is name-value pairs in json format If the
	 * element did not exist before the call, it is created as an instance of
	 * the passed class. Otherwise, the existing element is updated. Property
	 * and class names should be from the ontology (common vocabulary) although
	 * this is not checked at the time of this writing.
	 *
	 * @param id
	 *            Unique identifier of the element (for instance unique
	 *            identifier of the PES). To be sent between single quotes.
	 *            (e.g. ID_PES_123456777)
	 * @param jsonText
	 *            Text containing the properties of the element in Json format.
	 *            Each property is added or updated into the element, depending
	 *            on its previous existence. To be sent between single quotes.
	 *            (e.g. {\"\"CreatedBy\"\":\"\"FranÇois
	 *            Cerdeña\"\",\"\"Status\"\":\"\"Under development\"\"})
	 * @throws ContextFault Thrown in any case of error
	 * @return true or false
	 */
	public final boolean writeElementInformation(final String id,
			final String jsonText) throws ContextFault {
		OutputStream theOutputStream;
		int returnCode;
                boolean result = false;
		String urlEncoded;
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			urlEncoded = URLEncoder.encode("['" + id + "','" + jsonText + "']",
					encoding);
			theOutputStream
			.write(("predicate=writeElementInformation&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();
			// TODO handling of return codes is missing
			KMBApi.logger.info("test call writeElementInformation got code "
					+ returnCode);

                        result = writeWentOK();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
                return result;
	}

	/**
	 * Writes the information of the related element into the PROSECO Knowledge
	 * DATABASE. This information is name-value pairs in json format If the
	 * element did not exist before the call, it is created as an instance of
	 * the passed class. Otherwise, the existing element is updated. Property
	 * and class names should be from the ontology (common vocabulary) although
	 * this is not checked at the time of this writing.
	 *
	 * @param id
	 *            Unique identifier of the element (for instance unique
	 *            identifier of the PES). To be sent between single quotes.
	 *            (e.g. ID_PES_123456777)
	 * @param clazz
	 *            Name of the class of the element, with the namespace prefix.
	 *            For example:
	 *            'ProSEco_Ontological_Model:PES_Deployable_Solution'. To be
	 *            sent between single quotes. (e.g. kmb_specific_subclass)
	 * @param jsonText
	 *            jsonText Text containing the properties of the element in Json
	 *            format. Each property is added or updated into the element,
	 *            depending on its previous existence. To be sent between single
	 *            quotes. (e.g. {\"\"CreatedBy\"\":\"\"FranÇois
	 *            Cerdeña\"\",\"\"Status\"\":\"\"Under development\"\"})
	 * @throws ContextFault Thrown in any case of error
	 * @return true or false
	 */
	public final boolean writeElementInformation(final String id,
			final String clazz, final String jsonText) throws ContextFault {
		OutputStream theOutputStream;
		int returnCode;
                boolean result = false;
		String urlEncoded;
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			urlEncoded = URLEncoder.encode("['" + id + "','" + clazz + "','"
					+ jsonText + "']", encoding);
			theOutputStream
			.write(("predicate=writeElementInformation&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();

                        KMBApi.logger.info("test call writeElementInformation got code "
					+ returnCode);
                        result = writeWentOK();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
                return result;
	}

	/**
	 * Adds the information of the related element into the PROSECO Knowledge
	 * DATABASE. This information is name-value pairs in json format If the
	 * element did not exist before the call, it is created as an instance of
	 * the passed class. Otherwise, the existing element is updated. Property
	 * and class names should be from the ontology (common vocabulary) although
	 * this is not checked at the time of this writing.
	 * The difference with writeElementInformation is that, if a property is already assigned, it
	 * creates a new arc with a new value for this property, instead of overwriting it.
	 *
	 * @param id
	 *            Unique identifier of the element (for instance unique
	 *            identifier of the PES). To be sent between single quotes.
	 *            (e.g. ID_PES_123456777)
	 * @param jsonText
	 *            Text containing the properties of the element in Json format.
	 *            Each property is added or updated into the element, depending
	 *            on its previous existence. To be sent between single quotes.
	 *            (e.g. {\"\"CreatedBy\"\":\"\"FranÇois
	 *            Cerdeña\"\",\"\"Status\"\":\"\"Under development\"\"})
	 * @throws ContextFault Thrown in any case of error
	 * @return true or false
	 */
	public final boolean addElementInformation(final String id,
			final String jsonText) throws ContextFault {
		OutputStream theOutputStream;
		int returnCode;
                boolean result = false;
		String urlEncoded;
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			urlEncoded = URLEncoder.encode("['" + id + "','" + jsonText + "']",
					encoding);
			theOutputStream
			.write(("predicate=addElementInformation&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();
			// TODO handling of return codes is missing
			KMBApi.logger.info("test call addElementInformation got code "
					+ returnCode);

                        result = writeWentOK();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
                return result;
	}

	/**
	 * Writes the information of the related element into the PROSECO Knowledge
	 * DATABASE. This information is name-value pairs in json format If the
	 * element did not exist before the call, it is created as an instance of
	 * the passed class. Otherwise, the existing element is updated. Property
	 * and class names should be from the ontology (common vocabulary) although
	 * this is not checked at the time of this writing.
	 * The difference with writeElementInformation is that, if a property is already assigned, it
	 * creates a new arc with a new value for this property, instead of overwriting it.	 
	 *
	 * @param id
	 *            Unique identifier of the element (for instance unique
	 *            identifier of the PES). To be sent between single quotes.
	 *            (e.g. ID_PES_123456777)
	 * @param clazz
	 *            Name of the class of the element, with the namespace prefix.
	 *            For example:
	 *            'ProSEco_Ontological_Model:PES_Deployable_Solution'. To be
	 *            sent between single quotes. (e.g. kmb_specific_subclass)
	 * @param jsonText
	 *            jsonText Text containing the properties of the element in Json
	 *            format. Each property is added or updated into the element,
	 *            depending on its previous existence. To be sent between single
	 *            quotes. (e.g. {\"\"CreatedBy\"\":\"\"FranÇois
	 *            Cerdeña\"\",\"\"Status\"\":\"\"Under development\"\"})
	 * @throws ContextFault Thrown in any case of error
	 * @return true or false
	 */
	public final boolean addElementInformation(final String id,
			final String clazz, final String jsonText) throws ContextFault {
		OutputStream theOutputStream;
		int returnCode;
                boolean result = false;
		String urlEncoded;
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			urlEncoded = URLEncoder.encode("['" + id + "','" + clazz + "','"
					+ jsonText + "']", encoding);
			theOutputStream
			.write(("predicate=addElementInformation&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();

                        KMBApi.logger.info("test call addElementInformation got code "
					+ returnCode);
                        result = writeWentOK();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
                return result;
	}

	/**
	 * Returns the value of a specific property of the element in PROSECO
	 * Knowledge DATABASE.
	 *
	 * @param id
	 *            Unique identifier of the element (for instance unique
	 *            identifier of the PES). To be sent between single quotes.
	 *            (e.g. ID_PES_123456777)
	 * @param property
	 *            The name of the property. To be sent between single quotes.
	 *            (e.g. \"\"Status\"\")
	 * @throws ContextFault Thrown in any case of error
	 * @return String
	 */
	public final String readElementPropertyValue(final String id,
			final String property) throws ContextFault {
		OutputStream theOutputStream;
		InputStreamReader inputReader;
		StringWriter writer;
		String returnValue = "";
		int returnCode;
		String urlEncoded;
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			urlEncoded = URLEncoder.encode("['" + id + "','" + property + "']",
					encoding);
			theOutputStream
			.write(("predicate=readElementPropertyValue&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();
			// TODO handling of return codes is missing
			KMBApi.logger.info("test call readElementPropertyValue got code "
					+ returnCode);
			inputReader = new InputStreamReader(theConnection.getInputStream(),
					encoding);

			writer = new StringWriter();
			IOUtils.copy(inputReader, writer);
			returnValue = writer.toString();
			KMBApi.logger.info(returnValue);

			theOutputStream.close();
			writer.close();
			inputReader.close();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
		return returnValue;
	}

	/**
	 * Reads every name-value pair of the related element in PROSECO Knowledge
	 * DATABASE and returns them in json format.
	 *
	 * @param id
	 *            Unique identifier of the element (for instance unique
	 *            identifier of the PES). To be sent between single quotes.
	 *            (e.g. ID_PES_123456777)
	 * @throws ContextFault Thrown in any case of error
	 * @return String
	 */
	public final String readElementInformation(final String id)
			throws ContextFault {
		OutputStream theOutputStream;
		InputStreamReader inputReader;
		StringWriter writer;
		int returnCode;
		String urlEncoded;
		String returnValue = "";
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			urlEncoded = URLEncoder.encode("['" + id + "']", encoding);
			theOutputStream
			.write(("predicate=readElementInformation&arg0=" + urlEncoded)
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();
			// TODO handling of return codes is missing
			KMBApi.logger.info("test call readElementInformation got code "
					+ returnCode);
			inputReader = new InputStreamReader(theConnection.getInputStream(),
					encoding);

			writer = new StringWriter();
			IOUtils.copy(inputReader, writer);
			returnValue = writer.toString();
			KMBApi.logger.info(returnValue);

			theOutputStream.close();
			writer.close();
			inputReader.close();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
		return returnValue;
	}

	/**
	 * Retrieves the instances of the
	 * 'ProSEco_Ontological_Model:PES_Deployable_Solution' class and returns
	 * them in json format, with all their properties.
	 *
	 * @return returns a JSON String
	 * @throws ContextFault Thrown in any case of error
	 */
	public String listElements() throws ContextFault {
		OutputStream theOutputStream;
		InputStreamReader inputReader;
		StringWriter writer;
		int returnCode;
		String returnValue = "";
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			theOutputStream
			.write(("predicate=listElements&arg0=[]").getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();
			// TODO handling of return codes is missing
			KMBApi.logger.info("test call listElements got code " + returnCode);
			inputReader = new InputStreamReader(theConnection.getInputStream(),
					encoding);
			writer = new StringWriter();
			IOUtils.copy(inputReader, writer);
			returnValue = writer.toString();
			KMBApi.logger.info(returnValue);

			theOutputStream.close();
			writer.close();
			inputReader.close();
			theConnection.disconnect();
			System.out.println();
			System.out.println();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
		return returnValue;
	}

	/**
	 * Retrieves the instances of the passed class and returns them in json
	 * format, with all their properties. If there are no instances of the given
	 * class, empty list is returned: {[]}
	 *
	 * @param clazz
	 *            Name of the class of the elements to be retrieved, with the
	 *            namespace prefix. For example:
	 *            'ProSEco_Ontological_Model:PES_Deployable_Solution'. To be
	 *            sent between single quotes.
	 * @return returns a JSON String
	 * @throws ContextFault Thrown in any case of error
	 */
	public String listElements(final String clazz) throws ContextFault {
		OutputStream theOutputStream;
		InputStreamReader inputReader;
		StringWriter writer;
		int returnCode;
		String returnValue = "";
		connect();
		try {
			theOutputStream = theConnection.getOutputStream();
			theOutputStream
			.write(("predicate=listElements&arg0=['" + clazz + "']")
					.getBytes());
			theOutputStream.close();
			returnCode = theConnection.getResponseCode();
			// TODO handling of return codes is missing
			KMBApi.logger.info("test call listElements got code " + returnCode);
			inputReader = new InputStreamReader(theConnection.getInputStream(),
					encoding);
			writer = new StringWriter();
			IOUtils.copy(inputReader, writer);
			returnValue = writer.toString();
			KMBApi.logger.info(returnValue);

			theOutputStream.close();
			writer.close();
			inputReader.close();
			theConnection.disconnect();
			System.out.println();
			System.out.println();
		} catch (final IOException e) {
			throw new ContextFault(e.getMessage(), e.getCause());
		}
		disconnect();
		return returnValue;
	}

	/**
	 * This method installs a TrustManager that accepts any certificate it is
	 * presented with.
	 *
	 * DO NOT USE this mechanism in production code.
	 */
	private static void initializeSecurity() {
		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(final X509Certificate[] certs,
					final String authType) {
			}

			@Override
			public void checkServerTrusted(final X509Certificate[] certs,
					final String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			final SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
			.setDefaultSSLSocketFactory(sc.getSocketFactory());
			final HostnameVerifier hv = (urlHostName, session) -> {
                KMBApi.logger.warn("Warning: URL Host: " + urlHostName
                        + " vs. " + session.getPeerHost());
                return true;
            };
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
		final CookieManager manager = new CookieManager();
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
		CookieHandler.setDefault(manager);
	}
}
