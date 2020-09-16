package de.atb.context.tools.ontology;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
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
