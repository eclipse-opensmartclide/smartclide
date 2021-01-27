/*
 * @(#)ICalDataTypeProperties.java
 *
 * $Id: ICalDataTypeProperties.java 647 2016-10-20 15:13:20Z scholze $
 * 
 * $Rev:: 647                  $ 	last change revision
 * $Date:: 2016-10-20 17:13:20#$	last change date
 * $Author:: scholze             $	last change author
 * 
 * Copyright 2011-15 Sebastian Scholze (ATB). All rights reserved.
 *
 */
package de.atb.context.extraction.util.ical;

/*
 * #%L
 * ATB Context Extraction Core Service
 * %%
 * Copyright (C) 2018 - 2019 ATB
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntModel;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.extraction.util.IOntPropertyProvider;
import de.atb.context.context.util.IOntologyResource;
import de.atb.context.context.util.OntologyNamespace;
import lombok.Getter;

/**
 * ICalDataTypeProperties
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
@Getter
public enum ICalDataTypeProperties implements IOntologyResource, IOntPropertyProvider<DatatypeProperty> {

	Action("action"),

	Calscale("calscace", String.class),

	Categories("categories"),

	Class("class"),

	Comment("comment"),

	Contact("contact"),

	Description("description"),

	HasDateTime("hasDateTime"),

	HasDate("hasDate"),

	Location("location"),

	Method("method"),

	PercentComplete("percentComplete"),

	Priority("priority"),

	ProdId("prodid", String.class),

	RelatedTo("relatedTo"),

	Repeat("repeat"),

	RequestStatus("requestStatus"),

	Resources("resources"),

	Sequence("sequence"),

	Status("status"),

	Summary("summary"),

	Transp("transp"),

	TzId("tzid"),

	TzName("tzname"),

	TzOffsetFrom("tzoffsetfrom"),

	TzOffsetTo("tzoffsetto"),

	Uid("uid"),

	Version("version"),

	X("X-"),

	;

	/**
	 * Gets the local name that identifies the Resource within the ontology.
	 */
	private String localName;

	private Class<?>[] domains = new Class<?>[] { Object.class };
	private Class<?>[] ranges = new Class<?>[] { Object.class };

	/**
	 * Gets the Namespace this DatatypeProperty belongs to.
	 */
	private final OntologyNamespace namespace = OntologyNamespace.getInstance("ical", BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL), "http://www.w3.org/2002/12/cal/ical");

	ICalDataTypeProperties(final String localName) {
		this.localName = localName;
	}

	ICalDataTypeProperties(final String localName, final Class<?>[] ranges) {
		this.localName = localName;
		this.ranges = ranges;
	}

	ICalDataTypeProperties(final String localName, final Class<?> rangeClass) {
		this.localName = localName;
		ranges = new Class<?>[] { rangeClass };
	}

	ICalDataTypeProperties(final String localName, final Class<?> domainClass, final Class<?> rangeClass) {
		this.localName = localName;
		ranges = new Class<?>[] { rangeClass };
		domains = new Class<?>[] { domainClass };
	}

	ICalDataTypeProperties(final String localName, final Class<?> domainClass, final Class<?>[] ranges) {
		this.localName = localName;
		this.ranges = ranges;
		domains = new Class<?>[] { domainClass };
	}

	ICalDataTypeProperties(final String localName, final Class<?>[] domains, final Class<?> rangeClass) {
		this.localName = localName;
		ranges = domains;
		this.domains = new Class<?>[] { rangeClass };
	}

	ICalDataTypeProperties(final String localName, final Class<?>[] domains, final Class<?>[] ranges) {
		this.localName = localName;
		this.ranges = ranges;
		this.domains = domains;
	}

	// @Override
	// public String getURI(OntModel model) {
	// String nsPrefixUri = model.getNsPrefixURI(this.namespace.getLocalName());
	// if (nsPrefixUri != null) {
	// return nsPrefixUri + this.localName;
	// } else {
	// return this.namespace.getAbsoluteUri() + "#" + this.localName;
	// }
	//
	// // return model.getNsPrefixURI(this.namespace.getLocalName()) +
	// // this.localName;
	// }

	@Override
	public String getURI() {
		return namespace.getAbsoluteUri() + "#" + localName;
	}

	@Override
	public String getNameSpacePrefix() {
		return namespace.getLocalName();
	}

	// @Override
	// public String getNameSpace(OntModel model) {
	// // return model.getNsPrefixURI(this.namespace.getLocalName());
	// return getNameSpace();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.context.util.IOntologyResource#getNameSpace()
	 */
	@Override
	public String getNameSpace() {
		return namespace.getAbsoluteUri();
	}

	@Override
	public String toString() {
		return localName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.ce.util.OntPropertyProvider#getProperty(com.hp.hpl
	 * .jena.ontology.OntModel)
	 */
	@Override
	public DatatypeProperty getProperty(final OntModel model) {
		return model.getDatatypeProperty(getURI());
	}
}
