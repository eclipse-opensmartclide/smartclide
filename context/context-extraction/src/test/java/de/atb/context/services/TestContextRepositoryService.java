package de.atb.context.services;

import org.apache.cxf.endpoint.Server;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import de.atb.context.services.SWServiceContainer;
import de.atb.context.services.manager.ServiceManager;

import java.io.File;

/**
 * TestContextRepository
 * 
 * @author scholze
 * @version $LastChangedRevision: 577 $
 * 
 */
public class TestContextRepositoryService {

	private static Server server;
	private static IContextRepositoryService tmpService;
	private static SWServiceContainer serviceContainer;

	@BeforeClass
	public static void beforeClass() {
		String absolutefilePath = new File("").getAbsolutePath();
		File file = new File(
				absolutefilePath.concat(File.separator + "resources" + File.separator + "services-config.xml"));
		String filepath = file.getPath();
        TestContextRepositoryService.serviceContainer = new SWServiceContainer(
				"ContextRepositoryService", filepath);
		ServiceManager.getLSWServiceContainer().add(TestContextRepositoryService.serviceContainer);
        TestContextRepositoryService.server = ServiceManager.registerWebservice(TestContextRepositoryService.serviceContainer);
        TestContextRepositoryService.tmpService = ServiceManager.getWebservice(TestContextRepositoryService.serviceContainer);

		Assert.assertTrue(ServiceManager.isPingable(TestContextRepositoryService.tmpService));
	}

	@AfterClass
	public static void afterClass() {
		ServiceManager.shutdownServiceAndEngine(TestContextRepositoryService.server);
		ServiceManager.getLSWServiceContainer().remove(TestContextRepositoryService.serviceContainer);
	}
}
