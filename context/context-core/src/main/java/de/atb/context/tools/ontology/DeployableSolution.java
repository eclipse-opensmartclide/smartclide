package pt.uninova.context.tools.ontology;

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


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java class for DeployableSolution complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="DeployableSolution"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hasConfigurations" type="{http://xml.netbeans.org/schema/newXmlSchema}Configuration" maxOccurs="unbounded"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeployableSolution", propOrder = {"hasConfigurations",
    "status", "version"})
public class DeployableSolution extends Solution {

    @XmlElement(required = true)
    protected List<Configuration> hasConfigurations = new ArrayList<>();
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "Version", required = true)
    protected String version;

    /**
     * Gets the value of the hasConfigurations property.
     *
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * {@code set} method for the hasConfigurations property.
     *
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getHasConfigurations().add(newItem);
     * </pre>
     *
     *
     * Objects of the following type(s) are allowed in the list
	 * {@link Configuration }
     *
     *
     * @return returns a list of Configuration objects
     */
    public final List<Configuration> getHasConfigurations() {
       return this.hasConfigurations;
    }
    
    public final <T extends Configuration> boolean addConfiguration(T configuration){
        return this.hasConfigurations.add(configuration);
    }
    

    /**
     * Gets the value of the status property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public final void setStatus(final String value) {
        this.status = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public final void setVersion(final String value) {
        this.version = value;
    }

    /**
     *
     * @param hasConfigurations The list of configurations to be used
     */
    public void setHasConfigurations(List<Configuration> hasConfigurations) {
        this.hasConfigurations = hasConfigurations;
    }

    
}
