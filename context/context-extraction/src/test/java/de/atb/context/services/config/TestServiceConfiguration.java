package de.atb.context.services.config;

import org.junit.Test;
import de.atb.context.services.SWServiceContainer;
import de.atb.context.services.config.models.ISWService;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * TestServiceConfiguration
 * 
 * @author scholze
 * @version $LastChangedRevision: 94 $
 * 
 */
public class TestServiceConfiguration {

	@Test
	public void testGetInstance() {
		String absolutefilePath = new File("").getAbsolutePath();
		File file = new File(
				absolutefilePath.concat(File.separator + "resources" + File.separator + "services-config.xml"));
		String filepath = file.getPath();
		SWServiceContainer serviceContainer = new SWServiceContainer(
				"ContextRepositoryService", filepath);
		String val = "";

		ISWService service = serviceContainer.getService();
		assertTrue(service != null);
		if (service != null) {
			val = String.valueOf(service.getLocation());
			assertTrue(!(val.equals("null") || val.trim().length() == 0));
			val = String.valueOf(service.getServerClass());
			assertTrue(!(val.equals("null") || val.trim().length() == 0));
			val = String.valueOf(service.getProxyClass());
			assertTrue(!(val.equals("null") || val.trim().length() == 0));
		}

		serviceContainer = new SWServiceContainer(
				"ContextExtractionService", filepath);
		val = "";

		service = serviceContainer.getService();
		assertTrue(service != null);
		if (service != null) {
			val = String.valueOf(service.getLocation());
			assertTrue(!(val.equals("null") || val.trim().length() == 0));
			val = String.valueOf(service.getServerClass());
			assertTrue(!(val.equals("null") || val.trim().length() == 0));
			val = String.valueOf(service.getProxyClass());
			assertTrue(!(val.equals("null") || val.trim().length() == 0));
		}
	}

}
