package de.atb.context.services;

import de.atb.context.services.faults.ContextFault;
import org.apache.cxf.endpoint.Server;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.services.SWServiceContainer;
import de.atb.context.services.manager.ServiceManager;

import java.io.File;

/**
 * TestServiceManager
 * 
 * @author scholze
 * @version $LastChangedRevision: 577 $
 * 
 */
public class TestServiceManager {

	private static final Logger logger = LoggerFactory
			.getLogger(TestServiceManager.class);

	private static Server server;
	private static IContextExtractionService service;
	private static SWServiceContainer serviceContainer;

	@BeforeClass
	public static void beforeClass() {
		String absolutefilePath = new File("").getAbsolutePath();
		File file = new File(
				absolutefilePath.concat(File.separator + "resources" + File.separator + "services-config.xml"));
		String filepath = file.getPath();
        TestServiceManager.serviceContainer = new SWServiceContainer(
				"ContextExtractionService", filepath);
		ServiceManager.getLSWServiceContainer().add(TestServiceManager.serviceContainer);
        TestServiceManager.server = ServiceManager.registerWebservice(TestServiceManager.serviceContainer);
        TestServiceManager.service = ServiceManager.getWebservice(TestServiceManager.serviceContainer);

		Assert.assertTrue(ServiceManager.isPingable(TestServiceManager.service));
	}

	@Test
	public void shouldCheckRegisteredMonitoringServer() {
		Assert.assertTrue("Server could not be registered!", TestServiceManager.server != null);
	}

	@Test
	public void shouldCheckRegisteredServiceInterface() {
		Assert.assertTrue("IContextExtractionService is null!", TestServiceManager.service != null);
	}

	@Test
	public void shouldStartService() {
		Assert.assertTrue(ServiceManager.isPingable(TestServiceManager.service));
		try {
            TestServiceManager.service.start();
		} catch (ContextFault e) {
            TestServiceManager.logger.error(e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
            TestServiceManager.logger.error(e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void shouldStopService() {
		try {
            TestServiceManager.service.stop();
		} catch (ContextFault e) {
            TestServiceManager.logger.error(e.getMessage(), e);
			Assert.fail("Service could not be stopped properly.");
		}
	}

	@Test
	public void shouldStopServer() {
        TestServiceManager.server.stop();
	}

	@AfterClass
	public static void afterClass() {
		ServiceManager.shutdownServiceAndEngine(TestServiceManager.server);
		ServiceManager.getLSWServiceContainer().remove(TestServiceManager.serviceContainer);	}
}
