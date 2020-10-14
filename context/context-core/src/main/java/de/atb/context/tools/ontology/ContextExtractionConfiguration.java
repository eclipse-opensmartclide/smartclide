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



/**
 * @author scholze
 * @version $LastChangedRevision: 144 $
 */
public class ContextExtractionConfiguration extends Configuration {
	private DeployableSolution associatedPES;
    private DeployableSolution associatedAmIConfiguration;
    private Product associatedProduct;
    private ContextModel selectedContextModel;

    public ContextExtractionConfiguration() {
        super(ContextExtractionConfiguration.class.getName());
    }

    
    
    public ContextExtractionConfiguration(DeployableSolution associatedPES, Product associatedProduct, DeployableSolution associatedAmIConfiguration, ContextModel selectedContextModel) {
        super(ContextExtractionConfiguration.class.getName());
        this.associatedPES = associatedPES;
        this.associatedProduct = associatedProduct;
        this.associatedAmIConfiguration = associatedAmIConfiguration;
        this.selectedContextModel = selectedContextModel;
    }
    /**
	 * @return the associatedPES
	 */
	public DeployableSolution getAssociatedPES() {
		return associatedPES;
	}

	/**
	 * @param associatedPES the associatedPES to set
	 */
	public void setAssociatedPES(DeployableSolution associatedPES) {
		this.associatedPES = associatedPES;
	}

	/**
	 * @return the associatedAmIConfiguration
	 */
	public DeployableSolution getAssociatedAmIConfiguration() {
		return associatedAmIConfiguration;
	}

	/**
	 * @param associatedAmIConfiguration the associatedAmIConfiguration to set
	 */
	public void setAssociatedAmIConfiguration(
			DeployableSolution associatedAmIConfiguration) {
		this.associatedAmIConfiguration = associatedAmIConfiguration;
	}

	/**
	 * @return the associatedProduct
	 */
	public Product getAssociatedProduct() {
		return associatedProduct;
	}

	/**
	 * @param associatedProduct the associatedProduct to set
	 */
	public void setAssociatedProduct(Product associatedProduct) {
		this.associatedProduct = associatedProduct;
	}
}
