package de.atb.context.tools.ontology.utils;

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


import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Guilherme
 */
public class OntologyFormat {
    private static final String SERVICE = "Service";
    private OntologyFormat() {}

    public static String serviceNameToOntology(String serviceName) {
        String res = "";
        String simpleClassName = getSimpleNameFromFullName(serviceName);
        if (StringUtils.countMatches(simpleClassName, SERVICE) == 1 && !StringUtils.startsWith(simpleClassName, SERVICE)) {
            res = simpleClassName.replace(SERVICE, "");
        }
        return res;
    }

    public static String ontologyToServiceName(String ontology) {
        return ontology + SERVICE;
    }

    public static String getSimpleNameFromFullName(String fullName) {
        return ClassUtils.getShortClassName(fullName);
    }

    public static String insertSpaceBeforeCaps(String ontology) {
        String res = ontology.replaceAll("([A-Z])", " $1");
        return StringUtils.trim(res);
    }

    public static String getSpacedBeforeCapsFromFullName(String fullName) {
        String simpleName = getSimpleNameFromFullName(fullName);
        String ontology = serviceNameToOntology(simpleName);
        String res = insertSpaceBeforeCaps(ontology);
        return StringUtils.trim(res);
    }

    public static String getOntologyFromSpacedName(String spacedName) {
        return StringUtils.deleteWhitespace(spacedName);
    }

    public static String getServiceNameFromSpacedOntology(String spacedOntology) {
        String ontology = getOntologyFromSpacedName(spacedOntology);
        String simpleName = ontologyToServiceName(ontology);
        return StringUtils.trim(simpleName);
    }

    public static String getKMBVocabularyValueFromConfigurationName(String configName) {
        String res = "";
        if (configName.contains("Configuration")) {
            res = configName.replace("Configuration", "");
        }
        return res;
    }
}
