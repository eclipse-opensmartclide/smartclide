package de.atb.context.monitoring.rdf.util;

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


import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * BaseProperties
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public enum BaseMonitoringProperties implements IMonitoringDataResource, IMonitoringPropertyProvider<Property> {

    BelongsToBusinessCase("belongsToBusinessCase", String.class),

    ImplementsDataClass("implementsDataClass", String.class),

    MonitoringDataVersion("monitoringDataVersion", String.class),

    DocumentUri("documentUri", String.class),

    IndexId("indexId", String.class),

    MonitoredAt("monitoredAt", Date.class),

    ;

    private final Logger logger = LoggerFactory.getLogger(BaseMonitoringProperties.class);

    /**
     * Gets the local name that identifies the Resource within the model.
     */
    private String localName;

    /**
     * Gets the Namespace this Property belongs to.
     */
    private final MonitoringDataNamespace namespace = MonitoringDataNamespace.Base;

    private Class<? extends Serializable> dataType;

    BaseMonitoringProperties(final String localName) {
        this.localName = localName;
    }

    BaseMonitoringProperties(final String localName, final Class<? extends Serializable> type) {
        this.localName = localName;
        this.dataType = type;
    }

    /*
     * (non-Javadoc)
     *
     * @see IMonitoringPropertyProvider#
     * getProperty(com.hp.hpl.jena.rdf.model.Model)
     */
    @Override
    public Property getProperty(final Model model) {
        return model.getProperty(getUri(model));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringDataResource#getLocalName
     * ()
     */
    @Override
    public String getLocalName() {
        return this.localName;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringDataResource#getURI
     * (com.hp.hpl.jena.rdf.model.Model)
     */
    @Override
    public String getUri(final Model model) {
        return model.getNsPrefixURI(this.namespace.getLocalName()) + this.localName;
    }

    /*
     * (non-Javadoc)
     *
     * @see IMonitoringDataResource#
     * getNameSpacePrefix()
     */
    @Override
    public String getNameSpacePrefix() {
        return this.namespace.getLocalName();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringDataResource#getNameSpace
     * (com.hp.hpl.jena.rdf.model.Model)
     */
    @Override
    public String getNameSpace(final Model model) {
        return model.getNsPrefixURI(this.namespace.getLocalName());
    }

    @Override
    public String toString() {
        return this.localName;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringPropertyProvider#getValue
     * (com.hp.hpl.jena.rdf.model.Resource)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <O> O getValue(final Resource resource) {
        if (this.dataType == String.class) {
            return (O) resource.getProperty(getProperty(resource.getModel())).getString();
        } else if (this.dataType == Integer.class) {
            return (O) Integer.valueOf(resource.getProperty(getProperty(resource.getModel())).getInt());
        } else if (this.dataType == Date.class) {
            try {
                return (O) DateFormat.getDateTimeInstance().parse(resource.getProperty(getProperty(resource.getModel())).getString());
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        } else if (this.dataType == Float.class) {
            return (O) Float.valueOf(resource.getProperty(getProperty(resource.getModel())).getFloat());
        } else {
            return null;
        }
    }

}
