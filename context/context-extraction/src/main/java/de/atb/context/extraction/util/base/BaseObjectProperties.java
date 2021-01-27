/*
 * @(#)ObjectProperties.java
 *
 * $Id: BaseObjectProperties.java 647 2016-10-20 15:13:20Z scholze $
 * 
 * $Rev:: 647                  $ 	last change revision
 * $Date:: 2016-10-20 17:13:20#$	last change date
 * $Author:: scholze             $	last change author
 * 
 * Copyright 2010-15 Sebastian Scholze (ATB). All rights reserved.
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
import com.hp.hpl.jena.ontology.OntModel;
import de.atb.context.extraction.util.IOntPropertyProvider;
import de.atb.context.context.util.IOntologyResource;
import de.atb.context.context.util.OntologyNamespace;
import lombok.Getter;

/**
 * ObjectProperties
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
@Getter
public enum BaseObjectProperties implements IOntologyResource, IOntPropertyProvider<ObjectProperty> {

	/**
	 * Represents the BelongsToContext ObjectProperty from the base namespace.
	 */
	BelongsToContext("belongsToContext"),

	/**
	 * Represents the ConfiguredAt ObjectProperty from the base namespace.
	 */
	ConfiguredAt("configuredAt"),

	/**
	 * Represents the Configures ObjectProperty from the base namespace.
	 */
	Configures("configures"),

	/**
	 * Represents the ConfiguresResource ObjectProperty from the base namespace.
	 */
	ConfiguresResource("configuresResource"),

	/**
	 * Represents the Contains ObjectProperty from the base namespace.
	 */
	Contains("contains"),

	/**
	 * Represents the DeviceSensoredBy ObjectProperty from the base namespace.
	 */
	DeviceSensoredBy("deviceSensoredBy"),

	/**
	 * Represents the HasDevicePart ObjectProperty from the base namespace.
	 */
	HasDevicePart("hasDevicePart"),

	/**
	 * Represents the HasProcessingStep ObjectProperty from the base namespace.
	 */
	HasProcessingStep("hasProcessingStep"),

	/**
	 * Represents the HasSensor ObjectProperty from the base namespace.
	 */
	HasSensor("hasSensor"),

	/**
	 * Represents the IsConfiguresBy ObjectProperty from the base namespace.
	 */
	IsConfiguresBy("isConfiguredBy"),

	/**
	 * Represents the IsContainedBy ObjectProperty from the base namespace.
	 */
	IsContainedBy("isContainedBy"),

	/**
	 * Represents the IsProcessedBy ObjectProperty from the base namespace.
	 */
	IsProcessedBy("isProcessedBy"),

	/**
	 * Represents the IsPumpedBy ObjectProperty from the base namespace.
	 */
	IsPumpedBy("isPumpedBy"),

	/**
	 * Represents the IsRegulatedBy ObjectProperty from the base namespace.
	 */
	IsRegulatedBy("isRegulatedBy"),

	/**
	 * Represents the IsSensoredBy ObjectProperty from the base namespace.
	 */
	IsSensoredBy("isSensoredBy"),

	/**
	 * Represents the IsUsedByProcess ObjectProperty from the base namespace.
	 */
	IsUsedByProcess("isUsedByProcess"),

	/**
	 * Represents the PressureSensoredBy ObjectProperty from the base namespace.
	 */
	PressureSensoredBy("pressureSensoredBy"),

	/**
	 * Represents the Processes ObjectProperty from the base namespace.
	 */
	Processes("processes"),

	/**
	 * Represents the ProvidesSensoricalInformation ObjectProperty from the base
	 * namespace.
	 */
	ProvidesSensoricalInformation("providesSensoricalInformation"),

	/**
	 * Represents the Pumps ObjectProperty from the base namespace.
	 */
	Pumps("pumps"),

	/**
	 * Represents the Regulates ObjectProperty from the base namespace.
	 */
	Regulates("regulates"),

	/**
	 * Represents the RequiredForProcess ObjectProperty from the base namespace.
	 */
	RequiredForProcess("requiredForProcess"),

	/**
	 * Represents the ResourceConfiguresBy ObjectProperty from the base
	 * namespace.
	 */
	ResourceConfiguresBy("resourceConfiguredBy"),

	/**
	 * Represents the SensoredAt ObjectProperty from the base namespace.
	 */
	SensoredAt("sensoredAt"),

	/**
	 * Represents the Sensors ObjectProperty from the base namespace.
	 */
	Sensors("sensors"),

	/**
	 * Represents the SensorsDevice ObjectProperty from the base namespace.
	 */
	SensorsDevice("sensorsDevice"),

	/**
	 * Represents the SensorsPressure ObjectProperty from the base namespace.
	 */
	SensorsPressure("sensorsPressure"),

	/**
	 * Represents the SensorsSpeed ObjectProperty from the base namespace.
	 */
	SensorsSpeed("sensorsSpeed"),

	/**
	 * Represents the SensorsTemperature ObjectProperty from the base namespace.
	 */
	SensorsTemperature("sensorsTemperature"),

	/**
	 * Represents the SensorsTime ObjectProperty from the base namespace.
	 */
	SensorsTime("sensorsTime"),

	/**
	 * Represents the SensorsUnit ObjectProperty from the base namespace.
	 */
	SensorsUnit("sensorsUnit"),

	/**
	 * Represents the SensorsVolume ObjectProperty from the base namespace.
	 */
	SensorsVolume("sensorsVolume"),

	/**
	 * Represents the SpeedSensoredBy ObjectProperty from the base namespace.
	 */
	SpeedSensoredBy("speedSensoredBy"),

	/**
	 * Represents the StepSensoredAt ObjectProperty from the base namespace.
	 */
	StepSensoredAt("stepSensoredAt"),

	/**
	 * Represents the TemperatureSensoredBy ObjectProperty from the base
	 * namespace.
	 */
	TemperatureSensoredBy("temperatureSensoredBy"),

	/**
	 * Represents the TimeSensoredBy ObjectProperty from the base namespace.
	 */
	TimeSensoredBy("timeSensoredBy"),

	/**
	 * Represents the UnitSensoredBy ObjectProperty from the base namespace.
	 */
	UnitSensoredBy("unitSensoredBy"),

	/**
	 * Represents the UsesProcessModel ObjectProperty from the base namespace.
	 */
	UsesProcessModel("usesProcessModel"),

	/**
	 * Represents the UsesResource ObjectProperty from the base namespace.
	 */
	UsesResource("usesResource"),

	/**
	 * Represents the VolumeSensoredBy ObjectProperty from the base namespace.
	 */
	VolumeSensoredBy("volumeSensoredBy"),

	;

	/**
	 * Gets the local name that identifies the Resource within the ontology.
	 */
	private String localName;

	private Class<?>[] domains = new Class<?>[] { Object.class };
	private Class<?>[] ranges = new Class<?>[] { Object.class };

	/**
	 * Gets the Namespace this ObjectProperty belongs to.
	 */
	private final OntologyNamespace namespace = OntologyNamespace.getInstance();

	BaseObjectProperties(final String localName) {
		this.localName = localName;
	}

	BaseObjectProperties(final String localName, final Class<?>[] ranges) {
		this.localName = localName;
		this.ranges = ranges;
	}

	BaseObjectProperties(final String localName, final Class<?> rangeClass) {
		this.localName = localName;
		ranges = new Class<?>[] { rangeClass };
	}

	BaseObjectProperties(final String localName, final Class<?> domainClass, final Class<?> rangeClass) {
		this.localName = localName;
		ranges = new Class<?>[] { rangeClass };
		domains = new Class<?>[] { domainClass };
	}

	BaseObjectProperties(final String localName, final Class<?> domainClass, final Class<?>[] ranges) {
		this.localName = localName;
		this.ranges = ranges;
		domains = new Class<?>[] { domainClass };
	}

	BaseObjectProperties(final String localName, final Class<?>[] domains, final Class<?> rangeClass) {
		this.localName = localName;
		ranges = domains;
		this.domains = new Class<?>[] { rangeClass };
	}

	BaseObjectProperties(final String localName, final Class<?>[] domains, final Class<?>[] ranges) {
		this.localName = localName;
		this.ranges = ranges;
		this.domains = domains;
	}

	// @Override
	// public String getNameSpace(OntModel model) {
	// // return model.getNsPrefixURI(this.namespace.getLocalName());
	// return getNameSpace();
	// }

	// @Override
	// public String getNameSpace() {
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
