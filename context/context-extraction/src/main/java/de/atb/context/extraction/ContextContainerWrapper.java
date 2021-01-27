/*
 * @(#)ContextContainerWrapper.java
 *
 * $Id: ContextContainerWrapper.java 647 2016-10-20 15:13:20Z scholze $
 * 
 * $Rev:: 647                  $ 	last change revision
 * $Date:: 2016-10-20 17:13:20#$	last change date
 * $Author:: scholze             $	last change author
 * 
 * Copyright 2011-15 Sebastian Scholze (ATB). All rights reserved.
 *
 */
package de.atb.context.extraction;

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


import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.extraction.util.base.BaseDatatypeProperties;
import de.atb.context.extraction.util.base.BaseOntologyClasses;
import de.atb.context.common.util.ApplicationScenario;
import lombok.Getter;
import lombok.Setter;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import java.util.Date;

/**
 * ContextContainerWrapper
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
@Setter
@Getter
public class ContextContainerWrapper {

	protected ApplicationScenario applicationScenario = ApplicationScenario.getInstance(BusinessCase.getInstance("dummy",BusinessCase.NS_DUMMY_URL));
	protected Boolean useReasoner = Boolean.FALSE;
	protected String identifier;
	protected String monitoringDataId;
	protected String contextModelString;
	protected Date capturedAt = new Date();

	public static ContextContainerWrapper fromContextContainer(final ContextContainer context) {
		if (context == null) {
			throw new NullPointerException("ContextContainer may not be null!");
		}
		if (context.getIdentifier() == null) {
			throw new NullPointerException("Identifier may not be null!");
		}
		if (context.getIdentifier().trim().length() <= 0) {
			throw new NullPointerException("Identifier may not be empty!");
		}
		if (context.getApplicationScenario() == null) {
			throw new NullPointerException("ApplicationScenario may not be null!");
		}
		if (context.getApplicationScenario().getBusinessCase() == null) {
			throw new NullPointerException("BusinessCase may not be null!");
		}
		if (context.getCapturedAt() == null) {
			throw new NullPointerException("CapturedAt Date may not be null!");
		}

		ContextContainerWrapper wrapper = new ContextContainerWrapper();
		wrapper.capturedAt = context.getCapturedAt();
		wrapper.identifier = context.getIdentifier();
		wrapper.useReasoner = (context.getSpecification() != null)
				&& context.getSpecification().equals(PelletReasonerFactory.THE_SPEC);
		wrapper.monitoringDataId = context.getMonitoringDataId();
		wrapper.applicationScenario = context.getApplicationScenario();
		wrapper.contextModelString = context.serializeToString();
		return wrapper;
	}

	public final ContextContainer toContextContainer() {
		return ContextContainerWrapper.toContextContainer(this);
	}

	public static ContextContainer toContextContainer(final ContextContainerWrapper wrapper) {
		if (wrapper == null) {
			throw new NullPointerException("ContextContainerWrapper may not be null!");
		}
		if (wrapper.getIdentifier() == null) {
			throw new NullPointerException("Identifier may not be null!");
		}
		if (wrapper.getIdentifier().trim().length() <= 0) {
			throw new NullPointerException("Identifier may not be empty!");
		}
		if (wrapper.getApplicationScenario() == null) {
			throw new NullPointerException("ApplicationScenario may not be null!");
		}
		if (wrapper.getApplicationScenario().getBusinessCase() == null) {
			throw new NullPointerException("BusinessCase may not be null!");
		}
		if (wrapper.getCapturedAt() == null) {
			throw new NullPointerException("CapturedAt Date may not be null!");
		}

		ContextContainer context = new ContextContainer(wrapper.getApplicationScenario(), (wrapper.getUseReasoner() != null)
				&& (wrapper.getUseReasoner()));
		context.setCapturedAt(wrapper.getCapturedAt());
		context.setIdentifier(wrapper.getIdentifier());
		context.setMonitoringDataId(wrapper.getMonitoringDataId());
		OntModel model = ContextContainer.deserializeToModel(wrapper.getContextModelString());
		context.setNsPrefixes(model.getNsPrefixMap());
		context.add(model);

		Resource contextInstance = ContextContainerWrapper.inferContextInstance(model);
		if (contextInstance != null) {
			context.setContextInstance(contextInstance);
		}
		return context;
	}

	protected static Resource inferContextInstance(final OntModel model) {
		if (model != null) {
			ExtendedIterator<Individual> iter = model.listIndividuals(BaseOntologyClasses.Context.get(model));
			while (iter.hasNext()) {
				Individual res = iter.next();
				boolean hasIdentifier = res.hasProperty(BaseDatatypeProperties.Identifier.getProperty(model));
				boolean hasApplicationScenario = res.hasProperty(BaseDatatypeProperties.ApplicationScenarioIdentifier.getProperty(model));
				if (hasIdentifier && hasApplicationScenario) {
					return res;
				}
			}
		}
		return null;
	}

}
