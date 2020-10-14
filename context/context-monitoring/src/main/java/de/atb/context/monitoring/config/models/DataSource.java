package de.atb.context.monitoring.config.models;

/*
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import de.atb.context.monitoring.config.models.datasources.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * DataSource
 *
 * @author scholze
 * @version $LastChangedRevision: 156 $
 */
@Element
@RdfType("DataSource")
@Namespace("http://atb-bremen.de/")
public abstract class DataSource implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6260730665728114554L;

    private final Logger logger = LoggerFactory.getLogger(DataSource.class);

    /**
     * Represents the Id of a DataSource. This is used for mapping keys, to
     * lookup DataSource for a certain Monitoring and Interpreter component.
     */
    @Attribute
    protected String id;

    /**
     * Represents the type of DataSource. This is internaly a String, but getter
     * and setter provide DataSourceType enum.
     */
    @Attribute
    protected String type;

    /**
     * Represents the name of the Class that shall monitor this DataSource.
     */
    @Attribute
    protected String monitor;

    /**
     * Represents the URI of this DataSource (may be file- or web-based or
     * whatever).
     */
    @Attribute
    protected String uri;

    /**
     * Represents several options that are validated by subclasses of a
     * DataSource.
     */
    @Attribute(required = false)
    protected String options;

    /**
     * Gets the Id of the DataSource.
     *
     * @return the Id of the DataSource.
     */
    public final String getId() {
        return this.id;
    }

    /**
     * Sets the Id of the DataSource.
     *
     * @param id the Id of the DataSource.
     */
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the type of the DataSource. Depending on the type there are several
     * more options available. A FileSystemDataSource for example may for
     * example provide options to include hidden files etc.
     *
     * @return the type of the DataSource.
     */
    public DataSourceType getType() {
        return DataSourceType.valueOf(this.type.toUpperCase(Locale.ENGLISH));
    }

    /**
     * Sets the type of the DataSource.
     *
     * @param type the type of the DataSource.
     */
    public final void setType(final DataSourceType type) {
        this.type = type.toString().toLowerCase(Locale.ENGLISH);
    }

    /**
     * Gets the name of the Class that will be instanciated to monitor the
     * DataSource.
     *
     * @return the name of the Class that will be instanciated to monitor the
     */
    public final String getMonitor() {
        return this.monitor;
    }

    /**
     * Sets the name of the Class that will be instanciated to monitor the
     *
     * @param monitor the name of the Class that will be instanciated to monitor the
     */
    public final void setMonitor(final String monitor) {
        this.monitor = monitor;
    }

    /**
     * Gets the URI of the DataSource that will be monitored.
     *
     * @return the URI of the DataSource that will be monitored.
     */
    public final String getUri() {
        return this.uri;
    }

    /**
     * Sets the URI of the DataSource that will be monitored.
     *
     * @param uri the URI of the DataSource that will be monitored.
     */
    public final void setUri(final String uri) {
        this.uri = uri;
    }

    /**
     * Gets miscellaneous options for the DataSource as a String.
     * <p>
     * The option String will be in the format
     * <code>key=value&amp;key=value&amp;...</code>.
     *
     * @return miscellaneous options for the DataSource as a String.
     */
    public final String getOptions() {
        return this.options;
    }

    /**
     * Sets miscellaneous options for the DataSource as a String.
     * <p>
     * The option String has to be in the format
     * <code>key=value&amp;key=value&amp;...</code>.
     *
     * @param options miscellaneous options for the DataSource as a String.
     */
    public final void setOptions(final String options) {
        this.options = options;
    }

    /**
     * Converts this DataSource to the given class, that has to extend a
     * DataSource class.
     *
     * @param <T>   the type to convert the DataSource to.
     * @param clazz the class of the type to convert the DataSource to.
     * @return a (more specific) DataSource that extends the DataSource and has
     * the given Class.
     */
    public final <T extends DataSource> T convertTo(final Class<? extends T> clazz) {
        return getType().convertTo(clazz, this);
    }

    /**
     * Converts this DataSource to the given class, that has to extend a
     * DataSource class.
     *
     * @param <T>  the type to convert the DataSource to.
     * @param type the DataSourceType containing the class to convert the
     *             DataSource to.
     * @return a (more specific) DataSource that extends the DataSource and has
     * the given Class.
     */
    @SuppressWarnings("unchecked")
    public final <T extends DataSource> T convertTo(final DataSourceType type) {
        if (type == DataSourceType.FileSystem) {
            return (T) convertTo(FileSystemDataSource.class);
        } else if (type == DataSourceType.FilePairSystem) {
            return (T) convertTo(FilePairSystemDataSource.class);
        } else if (type == DataSourceType.FileTripletSystem) {
            return (T) convertTo(FileTripletSystemDataSource.class);
        } else if (type == DataSourceType.WebService) {
            return (T) convertTo(WebServiceDataSource.class);
        } else if (type == DataSourceType.Database) {
            return (T) convertTo(DatabaseDataSource.class);
        } else {
            return null;
        }
    }

    @Override
    public final String toString() {
        return String.format("%s [%s] -> %s (%s)", this.id, getType(), this.uri, this.monitor);
    }

    /**
     * Gets a value from the DataSource options with the given information.
     * These include the key to find in the option String and the class of the
     * value to be returned.
     *
     * @param <T>   the Class of the value to be returned from the DataSource
     *              options.
     * @param value information about the value to be retrieved, containing it's
     *              key and the Class of the value to be returned.
     * @return a value from the DataSource options or <code>null</code> if no
     * value was found for the given key/type information.
     */
    public final <T extends Serializable> T getOptionValue(final IDataSourceOptionValue value) {
        return parseOptionValue(getOptionValue(value.getKeyName(), false), value.getValueType());
    }

    /**
     * Gets a value from the DataSource options with the given information.
     * These include the key to find in the option String and the class of the
     * value to be returned.
     *
     * @param <T>       the Class of the value to be returned from the DataSource
     *                  options.
     * @param value     information about the value to be retrieved, containing it's
     *                  key and the Class of the value to be returned.
     * @param urlDecode whether to try to tecode the given value via an URLDecoder.
     * @return a value from the DataSource options or <code>null</code> if no
     * value was found for the given key/type information.
     */
    public final <T extends Serializable> T getOptionValue(final IDataSourceOptionValue value, final boolean urlDecode) {
        return parseOptionValue(getOptionValue(value.getKeyName(), urlDecode), value.getValueType());
    }

    /**
     * Extracts the corresponding value for the given key from the DataSources
     * option String.
     * <p>
     * This will perform a lookup for the given key in the option String. For
     * this purpose the option string will be tokenized by the
     * <code>&amp;</code> delimiter. Each token then will be seperated by
     * <code>=</code> into key and value. If the given key is found, it's
     * corresponding value will be returned as a String.
     *
     * @param key The key to look for in the option string.
     * @return the value for the given key or <code>null</code> if no such key
     * or a correspondig value exists.
     */
    private String getOptionValue(final String key, final boolean decode) {
        if ((this.options == null) || (this.options.trim().length() == 0)) {
            return null;
        }
        StringTokenizer tokenizer = new StringTokenizer(this.options, "&");
        String value = null;
        while (tokenizer.hasMoreTokens()) {
            value = getValueFromPair(tokenizer.nextToken(), key, decode);
            if (value != null) {
                return value;
            }
        }
        return value;
    }

    private String getValueFromPair(final String pair, final String key, final boolean decode) {
        int index = pair.indexOf('=');
        if (index <= 0) {
            return null;
        }

        String left = pair.substring(0, index);
        if (left.trim().length() == 0 || !left.equals(key)) {
            return null;
        }

        String value = pair.substring(index + 1);
        if (decode) {
            try {
                return URLDecoder.decode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.warn(e.getMessage(), e);
            }
        }
        return value;
    }

    /**
     * Converts the given String value into the given Class value.
     *
     * @param <T>   the type of Class to convert the value into.
     * @param value the value to be converted.
     * @param clazz the Class to convert the value into.
     * @return the String given converted into the specified Class.
     */
    @SuppressWarnings("unchecked")
    private <T extends Serializable> T parseOptionValue(final String value, final Class<? extends Serializable> clazz) {
        if (value == null) {
            return null;
        }
        if (clazz == Boolean.class) {
            return (T) Boolean.valueOf(value);
        } else if (clazz == Long.class) {
            return (T) Long.valueOf(value);
        } else if (clazz == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (clazz == Float.class) {
            return (T) Float.valueOf(value);
        } else if (clazz == Double.class) {
            return (T) Double.valueOf(value);
        } else if (clazz == String.class) {
            return (T) value;
        } else if ((clazz == Character.class) && (value.length() > 0)) {
            return (T) Character.valueOf(value.charAt(0));
        }
        logger.warn("Could not cast '" + value + "' to class " + clazz);
        return null;
    }
}
