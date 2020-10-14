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


import java.util.UUID;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * BaseMonitoringClasses
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public enum BaseMonitoringClasses implements IMonitoringDataResource {

    /**
     * Represents the MonitoringDataHeader Class from the base namespace.
     */
    MonitoringDataHeader("monitoringDataHeader"),

    ;

    /**
     * Gets the local name that identifies the Resource within the ontology.
     */
    private String localName;

    /**
     * Gets the Namespace this Class belongs to.
     */
    private final MonitoringDataNamespace namespace = MonitoringDataNamespace.Base;

    private int creationCount = 0;

    BaseMonitoringClasses(final String localName) {
        this.localName = localName;
    }

    /**
     * Creates an instance of this Class in the given Model with the given Id.
     *
     * @param model The Model where the created instance should be contained in.
     * @param id    Id (namespace) of the new instance. Must be unique for the
     *              Model.
     * @return the newly created Resource (instance).
     */
    public Resource createInstance(final Model model, final String id) {
        UUID uid = UUID.randomUUID();
        Resource res;
        if (id == null) {
            this.creationCount++;
            String myId = getNameSpace(model)
                + String.format("%s-%d-%s", this.localName, this.creationCount, uid.toString());

            res = model.createResource(myId);
        } else {
            if (id.trim().length() == 0) {
                res = model.createResource(getUri(model));
            } else {
                res = model.createResource(getUri(model) + "#" + id);
            }
        }
        return res;
    }

    /**
     * Returns the Resource Type of this Class in the given Model.
     *
     * @param model The rdf model where the Resource should be contained in.
     * @return the Resource.
     */
    public Resource createResource(final Model model) {
        return model.createResource(this.getUri(model));
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

}
