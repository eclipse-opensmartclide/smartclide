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


import java.util.List;

/**
 *
 * @author scholze
 * @version $LastChangedRevision: 144 $
 */
public class AmIMonitoringConfiguration extends Configuration {
    private DeployableSolution associatedPES;
    private Product associatedProduct;
    private List<AmISensor> selectedSensors;
    private String serviceConfiguration;

    public AmIMonitoringConfiguration() {
        super(AmIMonitoringConfiguration.class.getName());
    }

    public AmIMonitoringConfiguration(DeployableSolution associatedPES, Product associatedProduct, List<AmISensor> selectedSensors) {
        super(AmIMonitoringConfiguration.class.getName());
        this.associatedPES = associatedPES;
        this.associatedProduct = associatedProduct;
        this.selectedSensors = selectedSensors;
    }

    public DeployableSolution getAssociatedPES() {
        return associatedPES;
    }

    public void setAssociatedPES(DeployableSolution associatedPES) {
        this.associatedPES = associatedPES;
    }

    public Product getAssociatedProduct() {
        return associatedProduct;
    }

    public void setAssociatedProduct(Product associatedProduct) {
        this.associatedProduct = associatedProduct;
    }

    public List<AmISensor> getSelectedSensors() {
        return selectedSensors;
    }

    public void setSelectedSensors(List<AmISensor> selectedSensors) {
        this.selectedSensors = selectedSensors;
    }

	public String getServiceConfiguration() {
		return serviceConfiguration;
	}

	public void setServiceConfiguration(String serviceConfiguration) {
		this.serviceConfiguration = serviceConfiguration;
	}
     
}
