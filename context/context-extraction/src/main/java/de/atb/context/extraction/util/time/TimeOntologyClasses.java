/*
 * @(#)TimeOntologyClasses.java
 *
 * $Id: TimeOntologyClasses.java 647 2016-10-20 15:13:20Z scholze $
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
import com.hp.hpl.jena.rdf.model.Resource;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.extraction.ContextContainer;
import de.atb.context.extraction.util.base.BaseObjectProperties;
import de.atb.context.context.util.IOntologyResource;
import de.atb.context.context.util.OntologyNamespace;
import lombok.Getter;

import java.util.UUID;

/**
 * TimeOntologyClasses
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
@Getter
public enum TimeOntologyClasses implements IOntologyResource {

	DateTimeDescription("DateTimeDescription"),

	DateTimeInterval("DateTimeInterval"),

	DayOfWeek("DayOfWeek"),

	DurationDescription("DurationDescription"),

	Instant("Instant"),

	Interval("Interval"),

	January("January"),

	ProperInterval("ProperInterval"),

	TemporalEntity("TemporalEntity"),

	TemporalUnit("TemporalUnit"),

	TimeZone("TimeZone", OntologyNamespace.getInstance("tzont", BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL), "http://www.w3.org/2006/timezone")),

	Year("Year"),

	;

	private int creationCount = 0;

	/**
	 * Gets the local name that identifies the Resource within the ontology.
	 */
	private final String localName;

	/**
	 * Gets the Namespace this Class belongs to.
	 */
	private final OntologyNamespace namespace;

	TimeOntologyClasses(final String localName) {
		this.localName = localName;
		namespace = OntologyNamespace.getInstance("time", BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL), "http://www.w3.org/2006/time");
	}

	TimeOntologyClasses(final String localName, final OntologyNamespace namespace) {
		this.localName = localName;
		this.namespace = namespace;
	}

	/**
	 * Creates an instance of this Class in the given Ontology with a randomly
	 * generated Id.
	 * 
	 * @param model
	 *            The ontology where the created instance should be contained
	 *            in.
	 * @return the newly created Individual (instance).
	 */
	public Resource createIndividual(final ContextContainer model) {
		return createIndividual(model, null);
	}

	public Resource createIndividualAndAddToContext(final ContextContainer model) {
		Resource individual = createIndividual(model);
		ObjectProperty belongsToContext = BaseObjectProperties.BelongsToContext.getProperty(model);
		model.add(individual, belongsToContext, model.getContextInstance());
		return individual;
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

	public Resource get(final ContextContainer model) {
		return model.getOntClass(getURI());
	}

	@Override
	public String toString() {
		return localName;
	}

	/**
	 * Creates an instance of this Class in the given Ontology with the given
	 * Id.
	 * 
	 * @param model
	 *            The ontology where the created instance should be contained
	 *            in.
	 * @param id
	 *            Id (namespace) of the new instance. Must be unique for the
	 *            ontology.
	 * @return the newly created Individual (instance).
	 */
	protected Resource createIndividual(final ContextContainer model, final String id) {
		UUID uid = UUID.randomUUID();
		Resource res = null;
		if (id == null) {
			creationCount++;
			String myId = getNameSpace()
					+ "#"
					+ String.format("%s-%s-%d-%s", model.getBusinessCase().toString(), localName, creationCount,
							uid.toString());

			res = model.getOntClass(getURI()).createIndividual(myId);
		} else {
			res = model.getOntClass(getURI()).createIndividual(getNameSpace() + id);
		}
		return res;
	}

	public Resource createIndividualAndAddToContext(final ContextContainer model, final String id) {
		Resource individual = createIndividual(model, id);
		ObjectProperty belongsToContext = BaseObjectProperties.BelongsToContext.getProperty(model);
		model.add(individual, belongsToContext, model.getContextInstance());
		return individual;
	}

}
