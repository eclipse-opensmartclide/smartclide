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


import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import de.atb.context.monitoring.config.models.datasources.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DataSourceType
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */

public enum DataSourceType {

    /**
     * Classifies a DataSource as a FileSystemDataSource.
     */
    FileSystem(FileSystemDataSource.class),

    /**
     * Classifies a DataSource as a FilePairSystemDataSource.
     */
    FilePairSystem(FilePairSystemDataSource.class),

    /**
     * Classifies a DataSource as a FileTripletSystemDataSource.
     */
    FileTripletSystem(FileTripletSystemDataSource.class),

    /**
     * Classifies a DataSource as a WebServiceDataSource.
     */
    WebService(WebServiceDataSource.class),

    /**
     * Classifies a DataSource as a DatabaseDataSource.
     */
    Database(DatabaseDataSource.class),

    /**
     * Classifies a DataSource as a DatabaseDataSource.
     */
//    Kafka(KafkaDataSource.class),
    ;

    private final Logger logger = LoggerFactory.getLogger(DataSourceType.class);
    private Class<? extends DataSource> clazz;

    DataSourceType(final Class<? extends DataSource> clazz) {
        this.clazz = clazz;
    }

    /**
     * Returns the (DataSource) Class this type maps to.
     *
     * @return the (DataSource) Class this type maps to.
     */
    public Class<? extends DataSource> getClassType() {
        return this.clazz;
    }

    /**
     * Converts the given base DataSource to the given class, that has to extend
     * a DataSource class.
     *
     * @param <T>   the type to convert the DataSource to.
     * @param clazz the class of the type to convert the DataSource to.
     * @param base  the base DataSource containing the information for the new
     *              DataSource.
     * @return a (more specific) DataSource that extends the DataSource and has
     * the given Class.
     */
    @SuppressWarnings("unchecked")
    public <T extends DataSource> T convertTo(final Class<? extends T> clazz, final DataSource base) {
        if (this.clazz.equals(clazz)) {
            try {
                int modifier = clazz.getModifiers();

                if (!Modifier.isAbstract(modifier) && !Modifier.isInterface(modifier) && !Modifier.isStatic(modifier)) {
                    Constructor<?> constructor = clazz.getConstructor(DataSource.class);
                    return (T) constructor.newInstance(new Object[]{base});
                }
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * Converts this DataSource to the given class, that has to extend a
     * DataSource class.
     *
     * @param <T>  the type to convert the DataSource to.
     * @param type the DataSourceType containing the class to convert the
     *             DataSource to.
     * @param base the base DataSource containing the information for the new
     *             DataSource.
     * @return a (more specific) DataSource that extends the DataSource and has
     * the given Class.
     */
    @SuppressWarnings("unchecked")
    public <T extends DataSource> T convertTo(final DataSourceType type, final DataSource base) {
        if (type == DataSourceType.FileSystem) {
            return (T) convertTo(FileSystemDataSource.class, base);
        } else if (type == DataSourceType.FilePairSystem) {
            return (T) convertTo(FilePairSystemDataSource.class, base);
        } else if (type == DataSourceType.FileTripletSystem) {
            return (T) convertTo(FileTripletSystemDataSource.class, base);
        } else if (type == DataSourceType.WebService) {
            return (T) convertTo(WebServiceDataSource.class, base);
        } else if (type == DataSourceType.Database) {
            return (T) convertTo(DatabaseDataSource.class, base);
//        } else if (type == DataSourceType.Kafka) {
//            return (T) convertTo(KafkaDataSource.class, base);
        } else {
            return null;
        }
    }

}
