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


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java class for Product complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Product"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hasPES" type="{http://xml.netbeans.org/schema/newXmlSchema}Solution"/&gt;
 *         &lt;element name="hasProduct" type="{http://xml.netbeans.org/schema/newXmlSchema}Product" maxOccurs="unbounded"/&gt;
 *         &lt;element name="Variant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hasAmIElements" type="{http://xml.netbeans.org/schema/newXmlSchema}AmISensor" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product", propOrder = { "hasPES", "hasProduct", "variant",
		"description", "id", "hasAmIElements" })
public class Product {

	@XmlElement(required = true)
	protected Solution hasPES;
	@XmlElement(required = true)
	protected List<Product> hasProduct;
	@XmlElement(name = "Variant", required = true)
	protected String variant;
	@XmlElement(name = "Description", required = true)
	protected String description;
	@XmlElement(name = "Id", required = true)
	protected String id;
	@XmlElement(required = true)
	protected List<AmISensor> hasAmIElements;

	/**
	 * Gets the value of the hasPES property.
	 * 
	 * @return possible object is {@link Solution }
	 * 
	 */
	public final Solution getHasPES() {
		return hasPES;
	}

	/**
	 * Sets the value of the hasPES property.
	 * 
	 * @param value
	 *            allowed object is {@link Solution }
	 * 
	 */
	public final void setHasPES(final Solution value) {
		this.hasPES = value;
	}

	/**
	 * Gets the value of the hasProduct property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * {@code set} method for the hasProduct property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getHasProduct().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list: Product
	 * @return list of Product objects
	 * 
	 * 
	 */
	public final List<Product> getHasProduct() {
		if (hasProduct == null) {
			hasProduct = new ArrayList<>();
		}
		return this.hasProduct;
	}

	/**
	 * Gets the value of the variant property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public final String getVariant() {
		return variant;
	}

	/**
	 * Sets the value of the variant property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public final void setVariant(final String value) {
		this.variant = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public final void setDescription(final String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public final String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public final void setId(final String value) {
		this.id = value;
	}

	/**
	 * Gets the value of the hasAmIElements property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * {@code set} method for the hasAmIElements property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getHasAmIElements().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link AmISensor }
	 * @return list of AmISensor objects
	 * 
	 * 
	 */
	public final List<AmISensor> getHasAmIElements() {
		if (hasAmIElements == null) {
			hasAmIElements = new ArrayList<>();
		}
		return this.hasAmIElements;
	}

}
