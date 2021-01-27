package de.atb.context.services;

import de.atb.context.services.faults.ContextFault;
import de.atb.context.services.wrapper.ContextExtractionServiceWrapper;
import org.apache.cxf.endpoint.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import de.atb.context.services.SWServiceContainer;
import de.atb.context.services.manager.ServiceManager;

import java.io.File;

/**
 * TestContextExtractionService
 * 
 * @author scholze
 * @version $LastChangedRevision: 577 $
 * 
 */
public class TestContextExtractionService {

	private static Server server;
	private static IContextExtractionService extractionService;
	private static SWServiceContainer serviceContainer;

	@BeforeClass
	public static void beforeClass() {
		String absolutefilePath = new File("").getAbsolutePath();
		File file = new File(
				absolutefilePath.concat(File.separator + "resources" + File.separator + "services-config.xml"));
		String filepath = file.getPath();
        TestContextExtractionService.serviceContainer = new SWServiceContainer(
				"ContextExtractionService", filepath);
		ServiceManager.getLSWServiceContainer().add(TestContextExtractionService.serviceContainer);
        TestContextExtractionService.server = ServiceManager.registerWebservice(TestContextExtractionService.serviceContainer);
        TestContextExtractionService.extractionService = ServiceManager.getWebservice(TestContextExtractionService.serviceContainer);
	}

	@Test
	public void testNothing() throws ContextFault {
		ContextExtractionServiceWrapper wrapper = new ContextExtractionServiceWrapper(TestContextExtractionService.extractionService);
		wrapper.ping();
	}

	@AfterClass
	public static void afterClass() {
		ServiceManager.shutdownServiceAndEngine(TestContextExtractionService.server);
		ServiceManager.getLSWServiceContainer().remove(TestContextExtractionService.serviceContainer);
	}

}
