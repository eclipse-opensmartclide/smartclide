package de.atb.context.services.config;

import org.junit.BeforeClass;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

//import pm2.common.application.SysCallerImpl;
import de.atb.context.services.config.models.SWServiceConfig;

public class ServicesConfigTest {
	protected SWServiceConfig configurationBean;
//	private static SysCallerImpl sci;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		sci = new SysCallerImpl(false);
	}

	@Test
	public void testReadSConfiguration() {
		String handle = null;
//		handle = sci.openDRMobject("services-config.xml", "read");
//		byte[] readConfig = sci.getDRMobject("services-config.xml");
//		sci.closeDRMobject(handle);

//		InputStream is = new ByteArrayInputStream(readConfig);
		Serializer serializer = new Persister();
		try {
//			configurationB = serializer.read(SWServiceConfig.class, is);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		Assert.assertTrue(configurationB.getServices().size() == 3);
//		Assert.assertTrue(configurationB.getService("AmIMonitoring").getName().equals("AmIMonitoringService"));
	}

	@Test
	public void testOpenDRMObject() {
		String handle = null;
//		handle = sci.openDRMobject("services-config.xml", "read");
//		Assert.assertTrue(handle.equals("ObjHandle_1"));
//		handle = sci.openDRMobject("services-config.xml", "read");
//		Assert.assertTrue(handle.equals("ObjHandle_2"));
//		Assert.assertTrue(sci.closeDRMobject("ObjHandle_1"));
//		Assert.assertTrue(sci.closeDRMobject("ObjHandle_2"));
	}

	@Test
	public void testCloseDRMObject() {
//		Assert.assertFalse(sci.closeDRMobject("ObjHandle_1"));
	}
}
