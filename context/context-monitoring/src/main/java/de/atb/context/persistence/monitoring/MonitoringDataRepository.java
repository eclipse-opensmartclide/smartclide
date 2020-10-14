package de.atb.context.persistence.monitoring;

/*
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import de.atb.context.monitoring.models.IMonitoringDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thewebsemantic.RDF2Bean;
import thewebsemantic.Sparql;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.DataSource;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryException;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateRequest;

import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.common.util.SPARQLHelper;
import de.atb.context.common.util.TimeFrame;
import de.atb.context.context.util.OntologyNamespace;
import de.atb.context.monitoring.rdf.RdfHelper;
import de.atb.context.persistence.common.RepositorySDB;

/**
 * MonitoringDataRepository
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public final class MonitoringDataRepository<Type extends IMonitoringDataModel<?, ?>> extends RepositorySDB<Type> implements
    IMonitoringDataRepository<Type> {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringDataRepository.class);
    private static final String internalBaseUri = "monitoring";
    private static volatile MonitoringDataRepository<? extends IMonitoringDataModel<?, ?>> instance;

    static {
        synchronized (new Object()) {
            instance = new MonitoringDataRepository<>();
        }
    }

    @SuppressWarnings("unchecked")
    public static <Type> MonitoringDataRepository<? extends Type> getInstance() {
        return (MonitoringDataRepository<? extends Type>) instance;
    }

    protected MonitoringDataRepository() {
        super(internalBaseUri);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.proseco.persistence.common.IPersistenceUnit#persist(java.
     * lang.Object)
     */
    @Override
    public synchronized void persist(final Type monitoringData) {
        triggerPreProcessors(monitoringData.getApplicationScenario(), monitoringData);
        persist(monitoringData.toRdfModel(), monitoringData.getApplicationScenario());
        triggerPostProcessors(monitoringData.getApplicationScenario(), monitoringData);
    }

    @SuppressWarnings("unchecked")
    public synchronized <D extends de.atb.context.monitoring.config.models.DataSource> void persist(final String rdfString,
                                                                                                    final Class<Type> clazz, final ApplicationScenario applicationScenario) {
        Type bean = RdfHelper.createMonitoringData(rdfString, (Class<? extends IMonitoringDataModel<Type, D>>) clazz);
        persist(bean);
    }

    protected synchronized void persist(final Model monitoringData, final ApplicationScenario applicationScenario) {
        Model model = getDataSource(applicationScenario.getBusinessCase()).getDefaultModel();
        model.begin();
        model.add(monitoringData);
        model.commit();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringDataRepository#
     * getMonitoringData(de.atb.proseco.common.util.BusinessCase,
     * java.lang.Class, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public synchronized List<Type> getMonitoringData(final BusinessCase businessCase, final Class<Type> clazz, final int count) {
        if (businessCase == null) {
            throw new NullPointerException("BusinessCase may not be null!");
        }
        if (clazz == null) {
            throw new NullPointerException("Clazz may not be null!");
        }
        if (count < 0) {
            throw new IllegalArgumentException("Count has to be > -1!");
        }

        String selectQuery;
        if (count > 0) {
            selectQuery = String.format("SELECT ?s WHERE { ?s a %s ; %s ?mon } ORDER BY DESC (?mon) LIMIT %d",
                SPARQLHelper.getRdfClassQualifier(clazz), SPARQLHelper.getRdfPropertyQualifier(clazz, "monitoredAt"),
                count);
        } else {
            selectQuery = String.format("SELECT ?s WHERE { ?s a %s ; %s ?mon } ORDER BY DESC (?mon)",
                SPARQLHelper.getRdfClassQualifier(clazz), SPARQLHelper.getRdfPropertyQualifier(clazz, "monitoredAt"));
        }
        final String query = SPARQLHelper.appendDefaultPrefixes(selectQuery);
        logger.debug(query);

        List<Type> result = new ArrayList<Type>();
        Dataset set = getDataSource(businessCase);
        Lock lock = set.getLock();
        lock.enterCriticalSection(true);
        Model model = set.getDefaultModel();

        Collection<? extends Type> collection = Sparql.exec(model, clazz, query);
        for (Type type : collection) {
            result.add((Type) initLazyModel(model, type));
        }
        lock.leaveCriticalSection();
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.proseco.persistence.IMonitoringDataRepository#getMonitoringData
     * (de.atb.proseco.common.util.BusinessCase, int)
     */
    @Override
    public synchronized List<Type> getMonitoringData(final ApplicationScenario applicationScenario, final Class<Type> clazz, final int count) {
        if (applicationScenario == null) {
            throw new NullPointerException("ApplicationScenario may not be null!");
        }
        return getMonitoringData(applicationScenario.getBusinessCase(), clazz, count);
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized List<Type> getMonitoringData(final BusinessCase businessCase, final Class<Type> clazz, final TimeFrame timeFrame) {
        if (businessCase == null) {
            throw new NullPointerException("BusinessCase may not be null!");
        }
        if (clazz == null) {
            throw new NullPointerException("Clazz may not be null!");
        }
        if (timeFrame == null) {
            throw new NullPointerException("TimeFrame may not be null!");
        }
        if ((timeFrame.getStartTime() == null) && (timeFrame.getEndTime() == null)) {
            throw new IllegalArgumentException("TimeFrame has to have at least a start date or an and date!");
        }

        String endTime = timeFrame.getXSDLexicalFormForEndTime();
        String startTime = timeFrame.getXSDLexicalFormForStartTime();
        String selectQuery = "";

        if ((endTime != null) && (startTime != null)) {
            selectQuery = String.format("SELECT ?s WHERE { ?s a %s . ?s %s ?mon . FILTER (?mon <= %s && ?mon >= %s)} ORDER BY DESC (?mon)",
                SPARQLHelper.getRdfClassQualifier(clazz), SPARQLHelper.getRdfPropertyQualifier(clazz, "monitoredAt"), endTime,
                startTime);
        }

        String query = SPARQLHelper.appendDefaultPrefixes(selectQuery);
        logger.debug(query);
        Dataset set = getDataSource(businessCase);
        Lock lock = set.getLock();
        lock.enterCriticalSection(true);
        Model model = set.getDefaultModel();

        List<Type> result = new ArrayList<Type>();
        Collection<? extends Type> collection = Sparql.exec(model, clazz, query);
        for (Type type : collection) {
            result.add((Type) initLazyModel(model, type));
        }
        lock.leaveCriticalSection();
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.proseco.persistence.IMonitoringDataRepository#getMonitoringData
     * (de.atb.proseco.common.util.BusinessCase,
     * de.atb.proseco.common.util.TimeFrame)
     */
    @Override
    public synchronized List<Type> getMonitoringData(final ApplicationScenario applicationScenario, final Class<Type> clazz, final TimeFrame timeFrame) {
        if (applicationScenario == null) {
            throw new NullPointerException("ApplicationScenario may not be null!");
        }
        return getMonitoringData(applicationScenario.getBusinessCase(), clazz, timeFrame);
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized Type getMonitoringData(final BusinessCase businessCase, final Class<Type> clazz, final String identifier) {
        if (businessCase == null) {
            throw new NullPointerException("BusinessCase may not be null!");
        }
        if (clazz == null) {
            throw new NullPointerException("Clazz may not be null!");
        }
        if (identifier == null) {
            throw new NullPointerException("Identifier may not be null!");
        }

        if (identifier.trim().length() == 0) {
            throw new IllegalArgumentException("Identifier may not be empty!");
        }
        String selectQuery = String.format("SELECT ?s WHERE { ?s rdf:type %s . ?s %s \"%s\"^^xsd:string}",
            SPARQLHelper.getRdfClassQualifier(clazz), SPARQLHelper.getRdfPropertyQualifier(clazz, "identifier"), identifier);
        String query = SPARQLHelper.appendDefaultPrefixes(selectQuery);
        logger.trace(query);
        Dataset set = getDataSource(businessCase);
        Model model = set.getDefaultModel();

        Collection<? extends Type> result2 = Sparql.exec(model, clazz, query);
        Type result = null;
        if (!result2.isEmpty()) {
            result = (Type) initLazyModel(model, result2.iterator().next());
            if (result != null) {
                result.initialize();
            }
        } else {
            logger.warn("No Monitoring Data with id '" + identifier + "' found!");
        }
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.proseco.persistence.IMonitoringDataRepository#getMonitoringData
     * (de.atb.proseco.common.util.BusinessCase, java.lang.Class,
     * java.lang.String)
     */
    @Override
    public synchronized Type getMonitoringData(final ApplicationScenario applicationScenario, final Class<Type> clazz, final String identifier) {
        if (applicationScenario == null) {
            throw new NullPointerException("ApplicationScenario may not be null!");
        }
        if (clazz == null) {
            throw new NullPointerException("Clazz may not be null!");
        }
        if (identifier == null) {
            throw new NullPointerException("Identifier may not be null!");
        }
        if (identifier.trim().length() == 0) {
            throw new IllegalArgumentException("Identifier may not be empty!");
        }
        final String folder = "monitoring/";

        for (ApplicationScenario scenario : ApplicationScenario.values()) {
            if (identifier.equals(scenario.toString())) {
                return getStaticMonitoringData(applicationScenario, clazz, folder + identifier + ".rdf");
            }
        }

        // if ((applicationScenario == ApplicationScenario.TANK_REFILLING) &&
        // identifier.equals(ApplicationScenario.MONITORING_ID_TANK_REFILLING)) {
        // return getStaticMonitoringData(applicationScenario, clazz, folder +
        // identifier + ".rdf");
        // } else if ((applicationScenario ==
        // ApplicationScenario.VALVE_SYNCHRONISATION)
        // &&
        // identifier.equals(ApplicationScenario.MONITORING_ID_VALVE_SYNCHRONISATION))
        // {
        // return getStaticMonitoringData(applicationScenario, clazz, folder +
        // identifier + ".rdf");
        // } else if ((applicationScenario ==
        // ApplicationScenario.IdleTimeDetection)
        // &&
        // identifier.equals(ApplicationScenario.MONITORING_ID_IDLE_TIME_DETECTION))
        // {
        // return getStaticMonitoringData(applicationScenario, clazz, folder +
        // identifier + ".rdf");
        // } else if ((applicationScenario ==
        // ApplicationScenario.PrioritizationRule)
        // &&
        // identifier.equals(ApplicationScenario.MONITORING_ID_PRIORITIZATION_RULE))
        // {
        // return getStaticMonitoringData(applicationScenario, clazz, folder +
        // identifier + ".rdf");
        // }

        return getMonitoringData(applicationScenario.getBusinessCase(), clazz, identifier);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringDataRepository#
     * getLastIds(de.atb.proseco.common.util.BusinessCase, java.lang.Class,
     * int)
     */
    @Override
    public synchronized List<String> getLastIds(final BusinessCase businessCase, final Class<Type> clazz, final int count) {
        if (clazz == null) {
            throw new NullPointerException("Clazz may not be null!");
        }
        if (businessCase == null) {
            throw new NullPointerException("BusinessCase may not be null!");
        }
        if (count < 0) {
            throw new IllegalArgumentException("Count has to be > -1!");
        }

        String selectQuery;
        if (count > 0) {
            selectQuery = String.format(
                "SELECT ?id WHERE { ?c rdf:type %1$s . ?c %2$s ?id . ?c %3$s ?mon } ORDER BY DESC (?mon) LIMIT %4$d",
                SPARQLHelper.getRdfClassQualifier(clazz), SPARQLHelper.getRdfPropertyQualifier(clazz, "identifier"),
                SPARQLHelper.getRdfPropertyQualifier(clazz, "monitoredAt"), count);
        } else {
            selectQuery = String.format("SELECT ?id WHERE { ?c rdf:type %1$s . ?c %2$s ?id . ?c %3$s ?mon } ORDER BY DESC (?mon)",
                SPARQLHelper.getRdfClassQualifier(clazz), SPARQLHelper.getRdfPropertyQualifier(clazz, "identifier"),
                SPARQLHelper.getRdfPropertyQualifier(clazz, "monitoredAt"));
        }
        final String query = SPARQLHelper.appendDefaultPrefixes(selectQuery);
        logger.debug(query);

        List<String> ids = new ArrayList<String>();
        Dataset set = getDataSource(businessCase);
        Lock lock = set.getLock();
        lock.enterCriticalSection(true);
        Model model = set.getDefaultModel();

        try {
            Query q = QueryFactory.create(query);
            QueryExecution qe = QueryExecutionFactory.create(q, model);
            ResultSet rs = qe.execSelect();
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                RDFNode node = qs.get("id");
                if ((node != null) && node.isLiteral()) {
                    ids.add(node.asLiteral().getString());
                }
            }
        } catch (QueryException e) {
            logger.error(e.getMessage(), e);
        } finally {
            lock.leaveCriticalSection();
        }
        return ids;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.proseco.persistence.IMonitoringDataRepository#getLastIds(
     * de.atb.proseco.common.util.ApplicationScenario, java.lang.Class,
     * int)
     */
    @Override
    public synchronized List<String> getLastIds(final ApplicationScenario applicationScenario, final Class<Type> clazz, final int count) {
        if (applicationScenario == null) {
            throw new NullPointerException("ApplicationScenario may not be null!");
        }
        return getLastIds(applicationScenario.getBusinessCase(), clazz, count);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringDataRepository#
     * getLastIds(de.atb.proseco.common.util.BusinessCase, java.lang.Class,
     * de.atb.proseco.common.util.TimeFrame)
     */
    @Override
    public synchronized List<String> getLastIds(final BusinessCase businessCase, final Class<Type> clazz, final TimeFrame timeFrame) {
        if (businessCase == null) {
            throw new NullPointerException("BusinessCase may not be null!");
        }
        if (clazz == null) {
            throw new NullPointerException("Clazz may not be null!");
        }
        if (timeFrame == null) {
            throw new NullPointerException("TimeFrame may not be null!");
        }
        if ((timeFrame.getStartTime() == null) && (timeFrame.getEndTime() == null)) {
            throw new IllegalArgumentException("TimeFrame has to have at least a start date or an and date!");
        }

        String endTime = timeFrame.getXSDLexicalFormForEndTime();
        String startTime = timeFrame.getXSDLexicalFormForStartTime();
        String selectQuery = "";

        if ((endTime != null) && (startTime != null)) {
            selectQuery = String
                .format("SELECT ?id WHERE { ?c rdf:type %1$s . ?c %2$s ?id . ?c %3$s ?mon . FILTER (?mon <= %4$s && ?mon >= %5$s)} ORDER BY DESC (?mon)",
                    SPARQLHelper.getRdfClassQualifier(clazz), SPARQLHelper.getRdfPropertyQualifier(clazz, "identifier"),
                    SPARQLHelper.getRdfPropertyQualifier(clazz, "monitoredAt"), endTime, startTime);
        } else if (endTime == null) {
            selectQuery = String.format(
                "SELECT ?id WHERE { ?c rdf:type %1$s . ?c %2$s ?id . ?c %3$s ?mon . FILTER (?mon >= %4$s)} ORDER BY DESC (?mon)",
                SPARQLHelper.getRdfClassQualifier(clazz), SPARQLHelper.getRdfPropertyQualifier(clazz, "identifier"),
                SPARQLHelper.getRdfPropertyQualifier(clazz, "monitoredAt"), startTime);
        }
        final String query = SPARQLHelper.appendDefaultPrefixes(selectQuery);
        logger.debug(query);

        List<String> ids = new ArrayList<String>();
        Dataset set = getDataSource(businessCase);
        Lock lock = set.getLock();
        lock.enterCriticalSection(true);
        Model model = set.getDefaultModel();

        try {
            Query q = QueryFactory.create(query);
            QueryExecution qe = QueryExecutionFactory.create(q, model);
            ResultSet rs = qe.execSelect();
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                RDFNode node = qs.get("id");
                if ((node != null) && node.isLiteral()) {
                    ids.add(node.asLiteral().getString());
                }
            }
        } catch (QueryException e) {
            logger.error(e.getMessage(), e);
        } finally {
            lock.leaveCriticalSection();
        }
        return ids;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.proseco.persistence.IMonitoringDataRepository#getLastIds(
     * de.atb.proseco.common.util.ApplicationScenario, java.lang.Class,
     * de.atb.proseco.common.util.TimeFrame)
     */
    @Override
    public synchronized List<String> getLastIds(final ApplicationScenario applicationScenario, final Class<Type> clazz, final TimeFrame timeFrame) {
        if (applicationScenario == null) {
            throw new NullPointerException("ApplicationScenario may not be null!");
        }
        return getLastIds(applicationScenario.getBusinessCase(), clazz, timeFrame);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringDataRepository#
     * executeSparqlUpdateQuery(de.atb.proseco.common.util.BusinessCase,
     * java.lang.String)
     */
    @Override
    public synchronized void executeSparqlUpdateQuery(final BusinessCase businessCase, final String query) {
        executeSparqlUpdateQueries(businessCase, query);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringDataRepository#
     * executeSparqlUpdateQueries(de.atb.proseco.common.util.BusinessCase,
     * java.lang.String[])
     */
    @Override
    public synchronized void executeSparqlUpdateQueries(final BusinessCase businessCase, final String... queries) {
        if (businessCase == null) {
            throw new NullPointerException("BusinessCase may not be null!");
        }
        if (queries == null) {
            throw new NullPointerException("Queries may not be null!");
        }
        if (queries.length <= 0) {
            throw new NullPointerException("Queries may not be empty!");
        }
        UpdateRequest ur = UpdateFactory.create();
        for (String query : queries) {
            ur.add(prepareSparqlQuery(businessCase, query));
        }
        try {
            UpdateAction.execute(ur, getDataSource(businessCase));
        } catch (RuntimeException qe) {
            logger.error(qe.getMessage(), qe);
            throw qe;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IMonitoringDataRepository#
     * executeSparqlSelectQuery(de.atb.proseco.common.util.BusinessCase,
     * java.lang.String)
     */
    @Override
    public synchronized ResultSet executeSparqlSelectQuery(final BusinessCase businessCase, final String query) {
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
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, model);
        QueryExecution qexec = QueryExecutionFactory.create(finalQuery, ontModel);
        ResultSet result = null;
        try {
            result = qexec.execSelect();
        } catch (QueryException qe) {
            logger.error(qe.getMessage(), qe);
        }
        return result;
    }

    public static synchronized String prepareSparqlQuery(final BusinessCase businessCase, final String query) {
        logger.trace("Preparing sparql query '" + query + "' for business case " + businessCase);
        String finalQuery = OntologyNamespace.prepareSparqlQuery(query);
        logger.trace("Final query is " + finalQuery);
        return finalQuery;
    }

    @SuppressWarnings("unchecked")
    protected <T, D extends de.atb.context.monitoring.config.models.DataSource> IMonitoringDataModel<T, D> initLazyModel(final Model model,
                                                                                                                         final Type data) {
        RDF2Bean bb = new RDF2Bean(model);
        Class<T> implClass;
        if ((model != null) && (data != null) && (data.getImplementingClassName() != null)) {
            try {
                implClass = (Class<T>) Class.forName(data.getImplementingClassName());
                return (IMonitoringDataModel<T, D>) bb.loadDeep(implClass, data.getIdentifier());
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            logger.warn("MonitoringDataModel has no implementing class name!");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected synchronized <D extends de.atb.context.monitoring.config.models.DataSource> Type getStaticMonitoringData(
        final ApplicationScenario scenario, final Class<Type> clazz, final String identifier) {
        Type type = null;
        Model model = ModelFactory.createDefaultModel();
        logger.debug("Trying to locate static monitoring data under " + identifier);
        URL fileUri = Thread.currentThread().getContextClassLoader().getResource(identifier);
        if (fileUri != null) {
            logger.debug("Loading static monitoring data for " + scenario + " under '" + identifier + "'");
            logger.debug("\t" + fileUri);
            model.read(fileUri.toString());
            type = RdfHelper.createMonitoringData(model, (Class<? extends IMonitoringDataModel<Type, D>>) clazz);
        } else {
            logger.debug("Unable to locate static monitoring data for " + scenario + " under '" + identifier + "'");
        }
        return type;
    }
}
