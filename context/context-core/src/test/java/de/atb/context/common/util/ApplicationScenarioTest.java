package de.atb.context.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationScenarioTest {

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      BusinessCase businessCase0 = BusinessCase.getInstance(BusinessCase.NS_BASE_ID, BusinessCase.NS_BASE_URL);
      ApplicationScenario[] applicationScenarioArray0 = ApplicationScenario.values(businessCase0);
      assertNotNull(applicationScenarioArray0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      ApplicationScenario[] applicationScenarioArray0 = ApplicationScenario.values();
      assertNotNull(applicationScenarioArray0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      ApplicationScenario applicationScenario0 = ApplicationScenario.getInstance();
      // Undeclared exception!
      try { 
        applicationScenario0.initializeModel("");
        fail("Expecting exception: ClassCastException");
      
      } catch(ClassCastException e) {
         //
         // java.lang.Object cannot be cast to de.atb.context.learning.models.IModelInitializer
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      ApplicationScenario applicationScenario0 = ApplicationScenario.getInstance();
      // Undeclared exception!
      try { 
        applicationScenario0.getInitializer();
        fail("Expecting exception: ClassCastException");
      
      } catch(ClassCastException e) {
         //
         // java.lang.Object cannot be cast to de.atb.context.learning.models.IModelInitializer
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      ApplicationScenario applicationScenario0 = ApplicationScenario.getInstance();
      // Undeclared exception!
      try { 
        applicationScenario0.createInitializer();
        fail("Expecting exception: ClassCastException");
      
      } catch(ClassCastException e) {
         //
         // java.lang.Object cannot be cast to de.atb.context.learning.models.IModelInitializer
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      ApplicationScenario applicationScenario0 = ApplicationScenario.getInstance();
      // Undeclared exception!
      try { 
        applicationScenario0.getScenario();
        fail("Expecting exception: ClassCastException");
      
      } catch(ClassCastException e) {
         //
         // java.lang.Object cannot be cast to de.atb.context.learning.models.IModelInitializer
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      ApplicationScenario applicationScenario0 = ApplicationScenario.getInstance();
      BusinessCase businessCase0 = applicationScenario0.getBusinessCase();
      assertEquals("dummy", businessCase0.toString());
  }
}
