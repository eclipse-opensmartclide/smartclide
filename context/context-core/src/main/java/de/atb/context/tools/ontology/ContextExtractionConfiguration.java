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
