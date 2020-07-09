package de.atb.context.persistence;

import de.atb.context.common.util.ApplicationScenario;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ConfigurationRepositoryTest {

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      ConfigurationRepository configurationRepository0 = new ConfigurationRepository();
      configurationRepository0.basicLocation = null;
      configurationRepository0.getBasicLocation();
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      ConfigurationRepository configurationRepository0 = ConfigurationRepository.getInstance();
      configurationRepository0.basicLocation = "; ";
      configurationRepository0.basicLocation = "";
      configurationRepository0.getBasicLocation();
  }

//  @Test(timeout = 4000)
//  public void test04()  throws Throwable  {
//      ConfigurationRepository configurationRepository0 = new ConfigurationRepository("1697-02-01T00:00:00Z");
//      BusinessCase businessCase0 = BusinessCase.DUMMY;
//      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("C:\\daten\\scholze\\gitlab\\context\\prosecoCoreComponents\\context-core\\1697-02-01T00:00:00Z\\DUMMY");
//      FileSystemHandling.appendStringToFile(evoSuiteFile0, "");
//      configurationRepository0.clearBusinessCaseDirectory(businessCase0);
//  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      ConfigurationRepository configurationRepository0 = new ConfigurationRepository();
      // Undeclared exception!
      try { 
        configurationRepository0.deleteApplicationScenarioConfiguration((ApplicationScenario) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      // Undeclared exception!
      try { 
        ConfigurationRepository.clearDirectory((File) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

//  @Test(timeout = 4000)
//  public void test09()  throws Throwable  {
//      ConfigurationRepository configurationRepository0 = new ConfigurationRepository("1697-02-01T00:00:00Z");
//      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("C:\\daten\\scholze\\gitlab\\context\\prosecoCoreComponents\\context-core\\1697-02-01T00:00:00Z\\DUMMY");
//      byte[] byteArray0 = new byte[0];
//      boolean boolean0 = FileSystemHandling.appendDataToFile(evoSuiteFile0, byteArray0);
//      ApplicationScenario applicationScenario0 = ApplicationScenario.DUMMY_SCENARIO;
//      boolean boolean1 = configurationRepository0.deleteApplicationScenarioConfiguration(applicationScenario0);
//      assertFalse(boolean1 == boolean0);
//  }
//
  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      ConfigurationRepository configurationRepository0 = new ConfigurationRepository();
      String string0 = configurationRepository0.getBasicLocation();
      assertEquals("configurations", string0);
  }
}
