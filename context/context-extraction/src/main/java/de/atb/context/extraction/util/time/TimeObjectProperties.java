/*
 * @(#)TimeObjectProperties.java
 *
 * $Id: TimeObjectProperties.java 647 2016-10-20 15:13:20Z scholze $
 * 
 * $Rev:: 647                  $ 	last change revision
 * $Date:: 2016-10-20 17:13:20#$	last change date
 * $Author:: scholze             $	last change author
 * 
 * Copyright 2011-15 Sebastian Scholze (ATB). All rights reserved.
 *
 */
package de.atb.context.extraction.util.time;

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


import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.extraction.util.IOntPropertyProvider;
import de.atb.context.context.util.IOntologyResource;
import de.atb.context.context.util.OntologyNamespace;
import lombok.Getter;

/**
 * TimeObjectProperties
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
@Getter
public enum TimeObjectProperties implements IOntologyResource, IOntPropertyProvider<ObjectProperty> {

	after("after"),

	before("before"),

	dayOfWeek("dayOfWeek"),

	hasBeginning("hasBeginning"),

	hasDateTimeDescription("hasDateTimeDescription"),

	hasDurationDescription("hasDurationDescription"),

	hasEnd("hasEnd"),

	inDateTime("inDateTime"),

	inside("inside"),

	intervalAfter("intervalAfter"),

	intervalBefore("intervalBefore"),

	intervalContains("intervalContains"),

	intervalDuring("intervalDuring"),

	intervalEquals("intervalEquals"),

	intervalFinishedBy("intervalFinishedBy"),

	intervalFinishes("intervalFinishes"),

	intervalMeets("intervalMeets"),

	intervalMetBy("intervalMetBy"),

	intervalOverlappedBy("intervalOverlappedBy"),

	intervalOverlaps("intervalOverlaps"),

	intervalStartedBy("intervalStartedBy"),

	intervalStarts("intervalStarts"),

	timeZone("timeZone"),

	unitType("unitType"),

	;

	/**
	 * Gets the local name that identifies the Resource within the ontology.
	 */
	private final String localName;

	private Class<?>[] domains = new Class<?>[] { Object.class };
	private Class<?>[] ranges = new Class<?>[] { Object.class };

	/**
	 * Gets the Namespace this ObjectProperty belongs to.
	 */
	private final OntologyNamespace namespace = OntologyNamespace.getInstance("time", BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL), "http://www.w3.org/2006/time");

	TimeObjectProperties(final String localName) {
		this.localName = localName;
	}

	TimeObjectProperties(final String localName, final Class<?>[] ranges) {
		this.localName = localName;
		this.ranges = ranges;
	}

	TimeObjectProperties(final String localName, final Class<?> rangeClass) {
		this.localName = localName;
		ranges = new Class<?>[] { rangeClass };
	}

	TimeObjectProperties(final String localName, final Class<?> domainClass, final Class<?> rangeClass) {
		this.localName = localName;
		ranges = new Class<?>[] { rangeClass };
		domains = new Class<?>[] { domainClass };
	}

	TimeObjectProperties(final String localName, final Class<?> domainClass, final Class<?>[] ranges) {
		this.localName = localName;
		this.ranges = ranges;
		domains = new Class<?>[] { domainClass };
	}

	TimeObjectProperties(final String localName, final Class<?>[] domains, final Class<?> rangeClass) {
		this.localName = localName;
		ranges = domains;
		this.domains = new Class<?>[] { rangeClass };
	}

	TimeObjectProperties(final String localName, final Class<?>[] domains, final Class<?>[] ranges) {
		this.localName = localName;
		this.ranges = ranges;
		this.domains = domains;
	}

	// @Override
	// public String getNameSpace(OntModel model) {
	// return getNameSpace();
	// // return model.getNsPrefixURI(this.namespace.getLocalName());
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
	public String getNameSpacePrefix() {
		return namespace.getLocalName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.ce.util.OntPropertyProvider#getProperty(com.hp.hpl
	 * .jena.ontology.OntModel)
	 */
	@Override
	public ObjectProperty getProperty(final OntModel model) {
		return model.getObjectProperty(getURI());
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
	public String toString() {
		return localName;
	}
}
