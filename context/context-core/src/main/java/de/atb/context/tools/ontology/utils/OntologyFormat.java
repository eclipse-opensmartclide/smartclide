package pt.uninova.context.tools.ontology.utils;

/*-
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
