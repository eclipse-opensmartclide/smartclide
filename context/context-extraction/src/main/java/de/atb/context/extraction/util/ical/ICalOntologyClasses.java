/*
 * @(#)ICalOntologyClasses.java
 *
 * $Id: ICalOntologyClasses.java 647 2016-10-20 15:13:20Z scholze $
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
 * ICalOntologyClasses
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
@Getter
public enum ICalOntologyClasses implements IOntologyResource {

	DomainOfRRule("DomainOf_rrule"),

	ListOfFloat("List_of_Float"),

	VAlarm("Valarm"),

	Value_Cal_Address("Value_CAL-ADDRESS"),

	Value_Date("Value_DATE"),

	Value_DateTime("Value_DATE-TIME"),

	Value_Duration("Value_DURATION"),

	Value_Period("Value_PERIOD"),

	Value_Recur("Value_RECUR"),

	VEvent("Vevent"),

	VFreeBusy("Vfreebusy"),

	VJournal("Vjournal"),

	VTimezone("Vtimezone"),

	VTodo("Vtodo"),

	Value_URI("Value_URI"),

	;

	private int creationCount = 0;

	/**
	 * Gets the local name that identifies the Resource within the ontology.
	 */
	private String localName;

	/**
	 * Gets the Namespace this Class belongs to.
	 */
	private final OntologyNamespace namespace = OntologyNamespace.getInstance("ical", BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL), "http://www.w3.org/2002/12/cal/ical");

	ICalOntologyClasses(final String localName) {
		this.localName = localName;
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

	public Resource createIndividualAndAddToContext(final ContextContainer model, final String id) {
		Resource individual = createIndividual(model, id);
		ObjectProperty belongsToContext = BaseObjectProperties.BelongsToContext.getProperty(model);
		model.add(individual, belongsToContext, model.getContextInstance());
		return individual;
	}

	// @Override
	// public String getNameSpace(OntModel model) {
	// // return model.getNsPrefixURI(this.namespace.getLocalName());
	// return getNameSpace();
	// }

	@Override
	public String getNameSpacePrefix() {
		return namespace.getLocalName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.context.util.IOntologyResource#getNameSpace()
	 */
	@Override
	public String getNameSpace() {
		return namespace.getAbsoluteUri();
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
			String uri = getURI();
			res = model.getOntClass(uri).createIndividual(myId);
		} else {
			res = model.getOntClass(getURI()).createIndividual(getNameSpace() + id);
		}
		return res;
	}
}
