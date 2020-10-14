package de.atb.context.tools.ontology;

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
