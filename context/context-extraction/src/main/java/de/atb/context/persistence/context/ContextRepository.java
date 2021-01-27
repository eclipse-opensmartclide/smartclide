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


import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import de.atb.context.extraction.ContextContainer;
import de.atb.context.extraction.util.base.BaseDatatypeProperties;
import de.atb.context.extraction.util.base.BaseOntologyClasses;
import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.common.util.TimeFrame;
import de.atb.context.context.util.OntologyNamespace;
import de.atb.context.persistence.common.RepositorySDB;
import lombok.Getter;
import lombok.Setter;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ContextRepository
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
@Setter
@Getter
public final class ContextRepository extends RepositorySDB<ContextContainer> implements IContextRepository {

	private static final Logger logger = LoggerFactory.getLogger(ContextRepository.class);
	private static final String internalBaseUri = "contexts";
	@Getter
	private static final ContextRepository instance;
	private final boolean writeRawContextFiles = true;

	static {
		synchronized (ContextRepository.class) {
			instance = new ContextRepository();
		}
	}

	private ContextRepository() {
		super(ContextRepository.internalBaseUri);
	}

	private synchronized void persistNamedContext(ContextContainer context) {
		Store store = getStore(context.getBusinessCase());
		Model named = SDBFactory.connectNamedModel(store, context.getIdentifier());
		named.removeAll();
		named.add(context);
		named.close();
	}

	private synchronized void persistReasonableContext(ContextContainer context) {
		DataSource ds = getDataSource(context.getBusinessCase());
		Model model = ds.getDefaultModel();
		model.add(context);
	}

	private synchronized void persistRawContext(ContextContainer context) {
		if (writeRawContextFiles) {
			String fileLocation = getLocationForBusinessCase(context.getBusinessCase());
			new File(fileLocation).mkdirs();
			String fileName = String.format("%s%s%s.owl", fileLocation, File.separator, context.getIdentifier());
			File modelFile = new File(fileName);
			try {
				if (modelFile.exists() || modelFile.createNewFile()) {
					ContextRepository.logger.debug(String.format("Writing raw context with id '%s' to '%s'", context.getIdentifier(),
							modelFile.getAbsolutePath()));
					context.writeToFile(modelFile.getAbsolutePath());
				}
			} catch (IOException e) {
				ContextRepository.logger.error(e.getMessage(), e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#getRawContext(de.atb
	 * .context.common.util.ApplicationScenario, java.lang.String)
	 */
	@Override
	public synchronized ContextContainer getRawContext(ApplicationScenario applicationScenario, String contextId) {
		if (applicationScenario == null) {
			throw new NullPointerException("ApplicationScenario may not be null!");
		}
		return getRawContext(applicationScenario.getBusinessCase(), contextId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#getRawContext(de.atb
	 * .context.common.util.BusinessCase, java.lang.String)
	 */
	@Override
	public synchronized ContextContainer getRawContext(BusinessCase businessCase, String contextId) {
		if (contextId == null) {
			throw new NullPointerException("ContextId may not be null!");
		}
		if (contextId.trim().length() == 0) {
			throw new IllegalArgumentException("ContextId may not be empty!");
		}
		if (businessCase == null) {
			throw new NullPointerException("BusinessCase may not be null!");
		}

		String folder = "contexts/";
/*		if (businessCase == BusinessCase.DESMA && ApplicationScenario.CONTEXT_ID_TANK_REFILLING.equals(contextId)) {
			return getStaticContext(BusinessCase.DESMA, folder + contextId + ".owl", contextId);
		} else if (businessCase == BusinessCase.DESMA && ApplicationScenario.CONTEXT_ID_VALVE_SYNCHRONISATION.equals(contextId)) {
			return getStaticContext(BusinessCase.DESMA, folder + contextId + ".owl", contextId);
		}
*/
		String fileLocation = getLocationForBusinessCase(businessCase);
		String fileName = String.format("%s%s%s.owl", fileLocation, File.separator, contextId);
		File modelFile = new File(fileName);
		if (modelFile.canRead()) {
			OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
			String absPath = String.valueOf(modelFile.toURI());
			model.read(absPath);
			ContextContainer context = new ContextContainer(model, false);
			context.setIdentifier(contextId);
			return prepareRawContextContainer(context);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#getContext(de.atb.
	 * context.common.util.BusinessCase, java.lang.String)
	 */
	@Override
	public synchronized ContextContainer getContext(ApplicationScenario applicationScenario, String contextId) {
		if (contextId == null) {
			throw new NullPointerException("ContextId may not be null!");
		}
		if (contextId.trim().length() == 0) {
			throw new IllegalArgumentException("ContextId may not be empty!");
		}
		if (applicationScenario == null) {
			throw new NullPointerException("ApplicationScenario may not be null!");
		}
		if (applicationScenario.getBusinessCase() == null) {
			throw new NullPointerException("BusinessCase may not be null!");
		}
		// return getContext(applicationScenario.getBusinessCase(), contextId);
		return getRawContext(applicationScenario, contextId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#getContext(de.atb.
	 * context.common.util.BusinessCase, java.lang.String)
	 */
	@Override
	public synchronized ContextContainer getContext(BusinessCase businessCase, String contextId) {
		if (contextId == null) {
			throw new NullPointerException("ContextId may not be null!");
		}
		if (contextId.trim().length() == 0) {
			throw new IllegalArgumentException("ContextId may not be empty!");
		}
		if (businessCase == null) {
			throw new NullPointerException("BusinessCase may not be null!");
		}
		Store store = getStore(businessCase);
		Model model = SDBFactory.connectNamedModel(store, contextId);
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		ontModel.add(model);

		ContextContainer context = new ContextContainer(ontModel);
		context.addDefaultNamespaces();
		context.setIdentifier(contextId);
		return prepareRawContextContainer(context);
		// DataSource ds = getDataSource(businessCase);
		// if (ds.containsNamedModel(contextId)) {
		// Model model = ds.getNamedModel(contextId);
		// OntModel m =
		// ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, model);
		// ContextContainer context = new ContextContainer(m);
		// context.setIdentifier(contextId);
		// return prepareRawContextContainer(context);
		// } else {
		// return null;
		// }
	}

	protected ContextContainer getStaticContext(ApplicationScenario appScenario, String identifier) {
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		URL fileUri = Thread.currentThread().getContextClassLoader().getResource(identifier);
		if (fileUri != null) {
			model.read(fileUri.toString());
			ContextContainer cc = new ContextContainer(appScenario, model, false);
			prepareRawContextContainer(cc);
			return cc;
		}
		return null;
	}

	protected ContextContainer getStaticContext(BusinessCase businessCase, String filePath, String identifier) {
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		URL fileUri = Thread.currentThread().getContextClassLoader().getResource(filePath);
		if (fileUri != null) {
			model.read(fileUri.toString());
			ContextContainer cc = new ContextContainer(model, false);
			cc.setIdentifier(identifier);
			prepareRawContextContainer(cc);
			return cc;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#executeSparqlSelectQuery
	 * (de.atb.context.common.util.BusinessCase, java.lang.String)
	 */
	@Override
	public synchronized ResultSet executeSparqlSelectQuery(BusinessCase businessCase, String query) {
		return executeSparqlSelectQuery(businessCase, query, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#executeSparqlDescribeQuery
	 * (de.atb.context.common.util.BusinessCase, java.lang.String)
	 */
	@Override
	public synchronized Model executeSparqlDescribeQuery(BusinessCase businessCase, String query) {
		return executeSparqlDescribeQuery(businessCase, query, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.persistence.IContextRepository#
	 * executeSparqlConstructrQuery
	 * (de.atb.context.common.util.BusinessCase, java.lang.String)
	 */
	@Override
	public synchronized Model executeSparqlConstructQuery(BusinessCase businessCase, String query) {
		return executeSparqlConstructQuery(businessCase, query, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#executeSparqlAskQuery
	 * (de.atb.context.common.util.BusinessCase, java.lang.String)
	 */
	@Override
	public synchronized Boolean executeSparqlAskQuery(BusinessCase businessCase, String query) {
		return executeSparqlAskQuery(businessCase, query, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#executeSparqlAskQuery
	 * (de.atb.context.common.util.BusinessCase, java.lang.String, boolean)
	 */
	@Override
	public synchronized Boolean executeSparqlAskQuery(BusinessCase businessCase, String query, boolean useReasoner) {
		if (businessCase == null) {
			throw new NullPointerException("BusinessCase may not be null!");
		}
		if (query == null) {
			throw new NullPointerException("Query may not be null!");
		}
		if (query.trim().length() == 0) {
			throw new IllegalArgumentException("Query may not be empty!");
		}
		String finalQuery = prepareSparqlQuery(businessCase, query);
		DataSource dataset = getDataSource(businessCase);
		Model model = dataset.getDefaultModel();
		OntModel ontModel = null;
		if (useReasoner) {
			ontModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC, model);
		} else {
			ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, model);
		}
		QueryExecution qexec = QueryExecutionFactory.create(finalQuery, ontModel);
		Boolean result = null;
		try {
			result = qexec.execAsk();
		} catch (QueryException qe) {
			ContextRepository.logger.error(qe.getMessage(), qe);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#executeSparqlSelectQuery
	 * (de.atb.context.common.util.BusinessCase, java.lang.String, boolean)
	 */
	@Override
	public synchronized ResultSet executeSparqlSelectQuery(BusinessCase businessCase, String query, boolean useReasoner) {
		if (businessCase == null) {
			throw new NullPointerException("BusinessCase may not be null!");
		}
		if (query == null) {
			throw new NullPointerException("Query may not be null!");
		}
		if (query.trim().length() == 0) {
			throw new IllegalArgumentException("Query may not be empty!");
		}
		String finalQuery = prepareSparqlQuery(businessCase, query);
		DataSource dataset = getDataSource(businessCase);
		Model model = dataset.getDefaultModel();
		OntModel ontModel = null;
		if (useReasoner) {
			ontModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC, model);
		} else {
			ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, model);
		}
		QueryExecution qexec = QueryExecutionFactory.create(finalQuery, ontModel);
		ResultSet result = null;
		try {
			result = qexec.execSelect();
		} catch (QueryException qe) {
			ContextRepository.logger.error(qe.getMessage(), qe);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#executeSparqlDescribeQuery
	 * (de.atb.context.common.util.BusinessCase, java.lang.String, boolean)
	 */
	@Override
	public synchronized Model executeSparqlDescribeQuery(BusinessCase businessCase, String query, boolean useReasoner) {
		if (businessCase == null) {
			throw new NullPointerException("BusinessCase may not be null!");
		}
		if (query == null) {
			throw new NullPointerException("Query may not be null!");
		}
		if (query.trim().length() == 0) {
			throw new IllegalArgumentException("Query may not be empty!");
		}
		String finalQuery = prepareSparqlQuery(businessCase, query);
		DataSource dataset = getDataSource(businessCase);
		Model model = dataset.getDefaultModel();
		OntModel ontModel = null;
		if (useReasoner) {
			ontModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC, model);
		} else {
			ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, ontModel);
		}
		QueryExecution qexec = QueryExecutionFactory.create(finalQuery, model);
		Model result = null;
		try {
			result = qexec.execDescribe();
		} catch (QueryException qe) {
			ContextRepository.logger.error(qe.getMessage(), qe);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.atb.context.persistence.IContextRepository#
	 * executeSparqlConstructQuery(de.atb.context.common.util.BusinessCase,
	 * java.lang.String, boolean)
	 */
	@Override
	public synchronized Model executeSparqlConstructQuery(BusinessCase businessCase, String query, boolean useReasoner) {
		if (businessCase == null) {
			throw new NullPointerException("BusinessCase may not be null!");
		}
		if (query == null) {
			throw new NullPointerException("Query may not be null!");
		}
		if (query.trim().length() == 0) {
			throw new IllegalArgumentException("Query may not be empty!");
		}
		String finalQuery = prepareSparqlQuery(businessCase, query);
		DataSource dataset = getDataSource(businessCase);
		Model model = dataset.getDefaultModel();
		OntModel ontModel = null;
		if (useReasoner) {
			ontModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC, model);
		} else {
			ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, model);
		}
		QueryExecution qexec = QueryExecutionFactory.create(finalQuery, ontModel);
		Model result = null;
		try {
			result = qexec.execConstruct();
		} catch (QueryException qe) {
			ContextRepository.logger.error(qe.getMessage(), qe);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#initializeRepository
	 * (de.atb.context.common.util.BusinessCase, java.lang.String)
	 */
	@Override
	public synchronized void initializeRepository(BusinessCase bc, String modelUri) {
		ContextRepository.logger.info(String.format("Initializing repository for BusinessCase '%s', loading from url '%s'", bc, modelUri));
		createDefaultModel(OntModel.class, bc, modelUri, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#getDefaultModel(de
	 * .atb.context.common.util.BusinessCase)
	 */
	@Override
	public synchronized Model getDefaultModel(BusinessCase businessCase) {
		DataSource dataset = getDataSource(businessCase);
		return dataset.getDefaultModel();
	}

	protected synchronized ContextContainer prepareRawContextContainer(ContextContainer container) {
		ApplicationScenario applicationScenario = container.inferApplicationScenario();
		container.setApplicationScenario(applicationScenario);

		Date capturedAt = container.inferCapturedAt();
		container.setCapturedAt(capturedAt);

		String monitoringDataId = container.inferMonitoringDataId();
		if (monitoringDataId != null) {
			container.setMonitoringDataId(monitoringDataId);
		}
		return container;
	}

	// protected synchronized String prepareSparqlQuery(BusinessCase
	// businessCase, String query) {
	// String finalQuery = null;
	// Model model = null;
	// OntModel ontModel = null;
	// try {
	// logger.debug("Preparing sparql query '" + query + "' for business case "
	// + businessCase);
	// model = getDataSource(businessCase).getDefaultModel();
	// if (!model.listStatements().hasNext()) {
	// logger.warn("Model is empty!");
	// }
	// ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM,
	// model);
	// String prefixes = OntologyNamespace.getSparqlPrefixes(ontModel);
	// if (!query.contains(prefixes)) {
	// finalQuery = OntologyNamespace.prepareSparqlQuery(query, ontModel);
	// logger.debug("query '" + query + "' prefixed with " + prefixes);
	// } else {
	// finalQuery = query;
	// }
	// } catch (Throwable t) {
	// logger.error(t.getMessage(), t);
	// }
	// return finalQuery;
	// }

	protected synchronized String prepareSparqlQuery(BusinessCase businessCase, String query) {
		ContextRepository.logger.debug("Preparing sparql query '" + query + "' for business case " + businessCase);
		String finalQuery = OntologyNamespace.prepareSparqlQuery(query);
		ContextRepository.logger.debug("Final query is " + finalQuery);
		return finalQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#getLastContextsIds
	 * (de.atb.context.common.util.ApplicationScenario, int)
	 */
	@Override
	public synchronized List<String> getLastContextsIds(ApplicationScenario applicationScenario, int count) {
		String queryString = String
				.format("SELECT ?identifier WHERE {?c rdf:type :%1$s . ?c :%2$s ?identifier . ?c :%3$s \"%4$s\"^^xsd:string . ?c :%5$s ?x} ORDER BY DESC(?x) LIMIT %6$d",
						BaseOntologyClasses.Context, BaseDatatypeProperties.Identifier, BaseDatatypeProperties.ApplicationScenarioIdentifier, applicationScenario.toString(), BaseDatatypeProperties.CapturedAt,
                        count);

		String finalQuery = prepareSparqlQuery(applicationScenario.getBusinessCase(), queryString);
		List<String> ids = new ArrayList<>();
		try {
			ids = getLastIds(finalQuery, applicationScenario.getBusinessCase());
		} catch (QueryException e) {
			ContextRepository.logger.error(e.getMessage(), e);
		}
		return ids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#getLastContextsIds
	 * (de.atb.context.common.util.ApplicationScenario,
	 * de.atb.context.common.util.TimeFrame)
	 */
	@Override
	public synchronized List<String> getLastContextsIds(ApplicationScenario applicationScenario, TimeFrame timeFrame) {
		String queryString = String
				.format("SELECT ?identifier WHERE {?c rdf:type :%1$s . ?c :%2$s ?identifier . ?c :%3$s \"%4$s\"^^xsd:string . ?c :%5$s ?x . ?x <= %6$s . ?x >= %7$s} ORDER BY DESC(?x)",
						BaseOntologyClasses.Context, BaseDatatypeProperties.Identifier, BaseDatatypeProperties.ApplicationScenarioIdentifier, applicationScenario.toString(), BaseDatatypeProperties.CapturedAt,
						timeFrame.getXSDLexicalFormForStartTime(), timeFrame.getXSDLexicalFormForEndTime());
		String finalQuery = prepareSparqlQuery(applicationScenario.getBusinessCase(), queryString);
		List<String> ids = new ArrayList<>();
		try {
			ids = getLastIds(finalQuery, applicationScenario.getBusinessCase());
		} catch (QueryException e) {
			ContextRepository.logger.error(e.getMessage(), e);
		}
		return ids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#getLastContextsIds
	 * (de.atb.context.common.util.BusinessCase, int)
	 */
	@Override
	public synchronized List<String> getLastContextsIds(BusinessCase bc, int count) {
		String queryString = String
				.format("SELECT ?identifier WHERE {?c rdf:type :%1$s . ?c :%2$s ?identifier . ?c :%3$s \"%4$s\"^^xsd:string . ?c :%5$s ?x} ORDER BY DESC(?x) LIMIT %6$d",
						BaseOntologyClasses.Context, BaseDatatypeProperties.Identifier, BaseDatatypeProperties.BusinessCaseIdentifier, bc.toString(), BaseDatatypeProperties.CapturedAt, count);
		String finalQuery = prepareSparqlQuery(bc, queryString);
		List<String> ids = new ArrayList<>();
		try {
			ids = getLastIds(finalQuery, bc);
		} catch (QueryException e) {
			ContextRepository.logger.error(e.getMessage(), e);
		}
		return ids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.IContextRepository#getLastContextsIds
	 * (de.atb.context.common.util.BusinessCase,
	 * de.atb.context.common.util.TimeFrame)
	 */
	@Override
	public synchronized List<String> getLastContextsIds(BusinessCase bc, TimeFrame timeFrame) {
		String queryString = String
				.format("SELECT ?identifier WHERE {?c rdf:type :%1$s . ?c :%2$s ?identifier . ?c :%3$s \"%4$s\"^^xsd:string . ?c :%5$s ?x . ?x <= %6$s . ?x >= %7$s} ORDER BY DESC(?x)",
						BaseOntologyClasses.Context, BaseDatatypeProperties.Identifier, BaseDatatypeProperties.BusinessCaseIdentifier, bc.toString(), BaseDatatypeProperties.CapturedAt, timeFrame.getXSDLexicalFormForStartTime(),
						timeFrame.getXSDLexicalFormForEndTime());
		String finalQuery = prepareSparqlQuery(bc, queryString);
		List<String> ids = new ArrayList<>();
		try {
			ids = getLastIds(finalQuery, bc);
		} catch (QueryException e) {
			ContextRepository.logger.error(e.getMessage(), e);
		}
		return ids;
	}

	protected synchronized List<String> getLastIds(String finalQuery, BusinessCase bc) throws QueryException {
		List<String> ids = new ArrayList<>();
		DataSource ds = getDataSource(bc);
		ResultSet set = executeSelectSparqlQuery(finalQuery, ds.getDefaultModel());
		while (set.hasNext()) {
			QuerySolution solution = set.nextSolution();
			Literal literal = solution.getLiteral("identifier");
			if (literal != null) {
				ids.add(literal.getString());
			}
		}
		return ids;
	}

	protected ResultSet executeSelectSparqlQuery(String sparqlQuery, Model model) {
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ContextRepository.logger.debug("Executing SparQL select query '" + query + "'");
		return qexec.execSelect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.atb.context.persistence.common.IPersistenceUnit#persist(java.
	 * lang.Object)
	 */
	@Override
	public void persist(ContextContainer context) {
		if (context == null) {
			throw new NullPointerException("Context may not be null!");
		}

		if (context.getApplicationScenario() == null) {
			throw new NullPointerException("ApplicationScenario for Context may not be null!");
		}

		if (context.getBusinessCase() == null) {
			throw new NullPointerException("BusinessCase for Context may not be null!");
		}

		if (context.getIdentifier() == null) {
			throw new NullPointerException("Context Identifier may not be null!");
		}

		if (context.getIdentifier().trim().length() < 1) {
			throw new IllegalArgumentException("Context Identifier may not be empty!");
		}
		ContextRepository.logger.debug("Persisting context '" + context.getIdentifier() + " for " + context.getApplicationScenario() + " in BC "
				+ context.getBusinessCase());
		triggerPreProcessors(context.getApplicationScenario(), context);
		persistRawContext(context);
		persistReasonableContext(context);
		persistNamedContext(context);
		triggerPostProcessors(context.getApplicationScenario(), context);
	}
}
