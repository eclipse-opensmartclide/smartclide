/*
 * @(#)Classes.java
 *
 * $Id: BaseOntologyClasses.java 647 2016-10-20 15:13:20Z scholze $
 * 
 * $Rev:: 647                  $ 	last change revision
 * $Date:: 2016-10-20 17:13:20#$	last change date
 * $Author:: scholze             $	last change author
 * 
 * Copyright 2011-15 Sebastian Scholze (ATB). All rights reserved.
 *
 */
package de.atb.context.extraction.util.base;

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
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import de.atb.context.extraction.ContextContainer;
import de.atb.context.context.util.IOntologyResource;
import de.atb.context.context.util.OntologyNamespace;
import lombok.Getter;

import java.util.UUID;

/**
 * Classes
 * 
 * @author scholze, huesig
 * @version $LastChangedRevision: 647 $
 * 
 */
@Getter
public enum BaseOntologyClasses implements IOntologyResource {

	/**
	 * TODO
	 */
	Shift("Shift"),
	
	/**
	 * TODO
	 */
	AutoTime("AutoTime"),
	
	/**
	 * TODO
	 */
	HandTime("HandTime"),
	
	/**
	 * TODO
	 */
	Cycles("Cycles"),
	
	/**
	 * TODO
	 */
	AverageCycleTime("AverageCycleTime"),
	
	/**
	 * Represents the Bandwith Class from the base namespace.
	 */
	Bandwith("Bandwith"),

	/**
	 * Represents the Bar Class from the base namespace.
	 */
	Bar("Bar"),

	/**
	 * Represents the Celsius Class from the base namespace.
	 */
	Celsius("Celsius"),

	/**
	 * Represents the Component Class from the base namespace.
	 */
	Component("Component"),

	/**
	 * Represents the Configuration Class from the base namespace.
	 */
	Configuration("Configuration"),

	/**
	 * Represents the Container Class from the base namespace.
	 */
	Container("Container"),

	/**
	 * Represents the Context Class from the base namespace.
	 */
	Context("Context"),

	/**
	 * Represents the ContextResource Class from the base namespace.
	 */
	ContextResource("ContextResource"),

	/**
	 * Represents the Degrees Class from the base namespace.
	 */
	Degrees("Degrees"),

	/**
	 * Represents the Fahrenheit Class from the base namespace.
	 */
	Fahrenheit("Fahrenheit"),

	/**
	 * Represents the GenericDevice Class from the base namespace.
	 */
	GenericDevice("GenericDevice"),

	/**
	 * Represents the MeterPerSecond Class from the base namespace.
	 */
	MeterPerSecond("MeterPerSecond"),

	/**
	 * Represents the Milisecond Class from the base namespace.
	 */
	Milisecond("Milisecond"),

	/**
	 * Represents the NetworkDevice Class from the base namespace.
	 */
	NetworkDevice("NetworkDevice"),

	/**
	 * Represents the Operator Class from the base namespace.
	 */
	Operator("Operator"),

	/**
	 * Represents the Person Class from the base namespace.
	 */
	Person("Person"),

	/**
	 * Represents the Pressure Class from the base namespace.
	 */
	Pressure("Pressure"),

	/**
	 * Represents the PressureSensor Class from the base namespace.
	 */
	PressureSensor("PressureSensor"),

	/**
	 * Represents the Process Class from the base namespace.
	 */
	Process("Process"),

	/**
	 * Represents the ProcessingDevice Class from the base namespace.
	 */
	ProcessingDevice("ProcessingDevice"),

	/**
	 * Represents the ProcessModel Class from the base namespace.
	 */
	ProcessModel("ProcessModel"),

	/**
	 * Represents the ProcessStep Class from the base namespace.
	 */
	ProcessStep("ProcessStep"),

	/**
	 * Represents the Product Class from the base namespace.
	 */
	Product("Product"),

	/**
	 * Represents the ProductionUnit Class from the base namespace.
	 */
	ProductionUnit("ProductionUnit"),

	/**
	 * Represents the ProductModel Class from the base namespace.
	 */
	ProductModel("ProductModel"),

	/**
	 * Represents the ProductPart Class from the base namespace.
	 */
	ProductPart("ProductPart"),

	/**
	 * Represents the Protocol Class from the base namespace.
	 */
	Protocol("Protocol"),

	/**
	 * Represents the Pump Class from the base namespace.
	 */
	Pump("Pump"),

	/**
	 * Represents the Resource Class from the base namespace.
	 */
	Resource("Resource"),

	/**
	 * Represents the Second Class from the base namespace.
	 */
	Second("Second"),

	/**
	 * Represents the SelfLearningObject Class from the base namespace.
	 */
	SelfLearningObject("SelfLearningObject"),

	/**
	 * Represents the SensoricalDevice Class from the base namespace.
	 */
	SensoricalDevice("SensoricalDevice"),

	/**
	 * Represents the SensoricalInformation Class from the base namespace.
	 */
	SensoricalInformation("SensoricalInformation"),

	/**
	 * Represents the Speed Class from the base namespace.
	 */
	Speed("Speed"),

	/**
	 * Represents the SpeedSensor Class from the base namespace.
	 */
	SpeedSensor("SpeedSensor"),

	/**
	 * Represents the Temperature Class from the base namespace.
	 */
	Temperature("Temperature"),

	/**
	 * Represents the TemperatureSensor Class from the base namespace.
	 */
	TemperatureSensor("TemperatureSensor"),

	/**
	 * Represents the Time Class from the base namespace.
	 */
	Time("Time"),

	/**
	 * Represents the TimeSensor Class from the base namespace.
	 */
	TimeSensor("TimeSensor"),

	/**
	 * Represents the Timeslot Class from the base namespace.
	 */
	Timeslot("Timeslot"),

	/**
	 * Represents the Unit Class from the base namespace.
	 */
	Unit("Unit"),

	/**
	 * Represents the Valve Class from the base namespace.
	 */
	Valve("Valve"),

	/**
	 * Represents the Volume Class from the base namespace.
	 */
	Volume("Volume"),

	/**
	 * Represents the VolumeSensor Class from the base namespace.
	 */
	VolumeSensor("VolumeSensor"),

	;

	private int creationCount = 0;

	/**
	 * Gets the local name that identifies the Resource within the ontology.
	 */
	private String localName;

	/**
	 * Gets the Namespace this Class belongs to.
	 */
	private final OntologyNamespace namespace = OntologyNamespace.getInstance();

	BaseOntologyClasses(final String localName) {
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

	public Resource get(final OntModel model) {
		return model.getOntClass(getURI());
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
	// }

	@Override
	public String getURI() {
		return namespace.getAbsoluteUri() + "#" + localName;
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
					+ String.format("%s-%s-%d-%s", model.getApplicationScenario().getBusinessCase().toString(), localName,
					creationCount, uid.toString());
			String uri = getURI();
			OntClass clazz = model.getOntClass(uri);
			res = clazz.createIndividual(myId);
		} else {
			res = model.getOntClass(getURI()).createIndividual(getNameSpace() + id);
		}
		return res;
	}

}
