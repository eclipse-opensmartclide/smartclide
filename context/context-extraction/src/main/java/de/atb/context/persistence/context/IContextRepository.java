package de.atb.context.persistence.context;

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


import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.common.util.TimeFrame;
import de.atb.context.extraction.ContextContainer;
import de.atb.context.persistence.common.IPersistenceUnit;

import java.util.List;

/**
 * IContextRepository
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
public interface IContextRepository extends IPersistenceUnit<ContextContainer> {

	Model getDefaultModel(BusinessCase businessCase);

	ContextContainer getContext(ApplicationScenario applicationScenario, String contextId);

	ContextContainer getContext(BusinessCase businessCase, String contextId);

	ContextContainer getRawContext(BusinessCase businessCase, String contextId);

	ContextContainer getRawContext(ApplicationScenario applicationScenario, String contextId);

	Boolean executeSparqlAskQuery(BusinessCase businessCase, String query);

	ResultSet executeSparqlSelectQuery(BusinessCase businessCase, String query);

	Model executeSparqlDescribeQuery(BusinessCase businessCase, String query);

	Model executeSparqlConstructQuery(BusinessCase businessCase, String query);

	Boolean executeSparqlAskQuery(BusinessCase businessCase, String query, boolean useReasoner);

	ResultSet executeSparqlSelectQuery(BusinessCase businessCase, String query, boolean useReasoner);

	Model executeSparqlDescribeQuery(BusinessCase businessCase, String query, boolean useReasoner);

	Model executeSparqlConstructQuery(BusinessCase businessCase, String query, boolean useReasoner);

	List<String> getLastContextsIds(BusinessCase businessCase, int count);

	List<String> getLastContextsIds(BusinessCase businessCase, TimeFrame timeFrame);

	List<String> getLastContextsIds(ApplicationScenario applicationScenario, int count);

	List<String> getLastContextsIds(ApplicationScenario applicationScenario, TimeFrame timeFrame);

	void initializeRepository(BusinessCase businessCase, String modelUri);

}
