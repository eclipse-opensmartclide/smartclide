package de.atb.context.context.util;

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

import de.atb.context.common.util.BusinessCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Namespace
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 */
public class OntologyNamespace implements IOntologyResource {

    /**
     * Contains the prefix for the Namespace.
     */
    private String localName;
    private int ordinal;
    private String absoluteUri;
    private BusinessCase businessCase;

    private static final String NS_FORMAT = "PREFIX %1$s: <%2$s>\n";
    private static volatile Map<String, OntologyNamespace> settings = new HashMap<String, OntologyNamespace>();

    public static OntologyNamespace getInstance() {
        if (settings.get("base") == null) {
            settings.put("base", new OntologyNamespace("", BusinessCase.getInstance(BusinessCase.NS_BASE_ID, BusinessCase.NS_BASE_URL),
                    "http://www.atb-bremen.de/ontologies/context.owl"));
        }
        if (settings.get("ical") == null) {
            settings.put("ical", new OntologyNamespace("ical", BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL), "http://www.w3.org/2002/12/cal/ical"))
            ;
        }
        if (settings.get("time") == null) {
            settings.put("time", new OntologyNamespace("time", BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL), "http://www.w3.org/2006/time"))
            ;
        }
        if (settings.get("tzont") == null) {
            settings.put("tzont", new OntologyNamespace("tzont", BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL), "http://www.w3.org/2006/timezone"));
        }
        return settings.get("base");
    }

    public static OntologyNamespace getInstance(final String localName, final BusinessCase bc,
                                                final String absoluteUri) {
        if (settings.get(localName) == null) {
            settings.put(localName, new OntologyNamespace(localName, bc, absoluteUri));
        }
        return settings.get(bc);
    }

    private OntologyNamespace(final String localName, final BusinessCase bc,
                              final String absoluteUri) {
        this.localName = localName;
        this.absoluteUri = absoluteUri;
        businessCase = bc;
    }

    @Override
    public String getLocalName() {
        return localName;
    }

    @Override
    public String getNameSpacePrefix() {
        return getLocalName();
    }

    @Override
    public String getNameSpace() {
        return this.getAbsoluteUri();
    }

    public String getAbsoluteUri() {
        return absoluteUri;
    }

    public String getOntologyLocation() {
        // TODO keep them local!
        return absoluteUri;
    }

    public static OntologyNamespace[] values() {
        return settings.values().toArray(new OntologyNamespace[0]);
    }

    public BusinessCase getBusinessCase() {
        return businessCase;
    }

    public static String getOntologyLocation(final BusinessCase bc) {
        for (final OntologyNamespace ns : OntologyNamespace.values()) {
            if ((ns.getBusinessCase() != null)
                    && ns.getBusinessCase().equals(bc)) {
                return ns.getOntologyLocation();
            }
        }
        return null;
    }

    public static String getAbsoluteUri(final BusinessCase bc) {
        for (final OntologyNamespace ns : OntologyNamespace.values()) {
            if ((ns.getBusinessCase() != null)
                    && ns.getBusinessCase().equals(bc)) {
                return ns.getAbsoluteUri();
            }
        }
        return null;
    }

    public static String prepareSparqlQuery(final String query) {
        return String.format("%1$s%n%2$s",
                OntologyNamespace.getSparqlPrefixes(), query);
    }

    public static String getSparqlPrefixes() {
        final StringBuilder builder = new StringBuilder();

        // add some default namespaces
        builder.append(String.format(OntologyNamespace.NS_FORMAT, "rdf",
                "http://www.w3.org/1999/02/22-rdf-syntax-ns#"));
        builder.append(String.format(OntologyNamespace.NS_FORMAT, "xsd",
                "http://www.w3.org/2001/XMLSchema#"));
        builder.append(String.format(OntologyNamespace.NS_FORMAT, "owl",
                "http://www.w3.org/2002/07/owl#"));
        builder.append(String.format(OntologyNamespace.NS_FORMAT, "swrl",
                "http://www.w3.org/2003/11/swrl#"));
        builder.append(String.format(OntologyNamespace.NS_FORMAT, "rdfs",
                "http://www.w3.org/2000/01/rdf-schema#"));

        for (final OntologyNamespace ns : OntologyNamespace.values()) {
            builder.append(String.format(OntologyNamespace.NS_FORMAT,
                    ns.getNameSpacePrefix(), ns.getAbsoluteUri() + "#"));
        }
        return builder.toString();
    }

    @Override
    public String getURI() {
        return absoluteUri;
    }
}
