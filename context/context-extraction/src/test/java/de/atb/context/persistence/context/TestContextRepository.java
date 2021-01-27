package de.atb.context.persistence.context;

import de.atb.context.extraction.ContextContainer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;

/**
 * TestContextRepository
 * 
 * @author scholze
 * @version $LastChangedRevision: 495 $
 * 
 */
public class TestContextRepository {

	private static ContextRepository contextRepos;
	private static final String owlFileSubPath = "resources/owl/TankRefilling.owl";
	private static ContextContainer testContext;
	private static final String DUMMY_MONITORINGDATA_MODEL_PATH = "resources/models/dummy_monitoringdata_model.rdf";

	@BeforeClass
	public static void beforeClass() {
        TestContextRepository.contextRepos = ContextRepository.getInstance();
		URI owlFilePath = URI
				.create(Thread.currentThread().getContextClassLoader()
						.getResource(TestContextRepository.owlFileSubPath).toString());
        TestContextRepository.testContext = ContextContainer.readFromFile(owlFilePath);
	}

	@Test
	public void shouldInitContextContainerFromFile() {
		Assert.assertNotNull(TestContextRepository.testContext);
/*		Assert.assertNotNull(testContext.getIdentifier());
		Assert.assertNotNull(testContext.getApplicationScenario());
		Assert.assertNotNull(testContext.getCapturedAt());
		Assert.assertNotNull(testContext.getMonitoringDataId());*/
	}
/*
	@Test
	public void shouldInitContextRepositoryWithDefaultModel() {
		contextRepos.reset(BusinessCase.Base);
		contextRepos.createDefaultModel(OntModel.class, OntologyNamespace.Base,
				true);
	}

	@Test
	public void shouldInitContextRepositoryWithDefaultDesmaModel() {
		contextRepos.reset(BusinessCase.Desma);
		contextRepos.createDefaultModel(OntModel.class, BusinessCase.Desma,
				true);
	}

	@Test
	public void shouldPersistAndRetrieveIdentifiedContextFromRepository() {
		contextRepos.reset(BusinessCase.Desma);
		contextRepos.createDefaultModel(OntModel.class, BusinessCase.Desma,
				true);
		contextRepos.persist(testContext);

		ContextContainer context = contextRepos.getContext(
				ApplicationScenario.TankRefilling, testContext.getIdentifier());
		Assert.assertNotNull(context);
		Assert.assertNotNull(context.getIdentifier());
		Assert.assertNotNull(context.getApplicationScenario());
		Assert.assertNotNull(context.getCapturedAt());
		Assert.assertNotNull(context.getMonitoringDataId());
	}

	@Test
	public void shouldReInitReposWithTestContextAndGetItsIdAsLatestId() {
		URL fileUri = Thread.currentThread().getContextClassLoader()
				.getResource(DUMMY_MONITORINGDATA_MODEL_PATH);
		contextRepos.reset(BusinessCase.Desma);
		contextRepos.initializeRepository(BusinessCase.Desma, fileUri.toString());
		contextRepos.persist(testContext);

		List<String> ids = contextRepos.getLastContextsIds(
				testContext.getApplicationScenario(), 1);
		Assert.assertNotNull(ids);
		Assert.assertTrue(ids.size() > 0);
		String id = ids.get(0);
		Assert.assertNotNull(id);
		Assert.assertTrue(id.equals(testContext.getIdentifier()));
	}

	@Test
	public void shouldExecuteSparqlAskQuery() {
		contextRepos
				.createDefaultModel(OntModel.class, BusinessCase.Base, true);
		Assert.assertTrue(contextRepos.executeSparqlAskQuery(BusinessCase.Base,
				"ASK WHERE {?type rdfs:domain :Resource}").booleanValue());
		Assert.assertFalse(contextRepos.executeSparqlAskQuery(
				BusinessCase.Base,
				"ASK WHERE {?type rdfs:range :NonExistentClass}")
				.booleanValue());
	}
*/
}
