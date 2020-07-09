package de.atb.context.services.faults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.atb.context.services.faults.marshal.ContextFaultDetails;

public class ContextFaultTest {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault();
      ContextFault contextFault1 = new ContextFault("de.atb.context.services.faults.ContextFault", (ContextFaultDetails) null, (Throwable) contextFault0);
      String string0 = contextFault1.getMessage();
      assertEquals("de.atb.context.services.faults.ContextFault", string0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault();
      ContextFault contextFault1 = new ContextFault((Throwable) contextFault0);
      ContextFaultDetails contextFaultDetails0 = contextFault1.getFaultInfo();
      assertEquals("de.atb.context.services.faults.ContextFault", contextFaultDetails0.getCauseName());
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault();
      ContextFault contextFault1 = new ContextFault("", (ContextFaultDetails) null, (Throwable) contextFault0);
      String string0 = contextFault1.getMessage();
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault();
      Throwable throwable0 = contextFault0.getCause();
      assertNull(throwable0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault("{yaAn}<ey1NE~0tzi");
      // Undeclared exception!
      try { 
        contextFault0.getMessage();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault();
      String string0 = contextFault0.getMessage();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault();
      ContextFault contextFault1 = new ContextFault((Throwable) contextFault0);
      ContextFault contextFault2 = (ContextFault) contextFault1.getCause();
      assertNotSame(contextFault1, contextFault2);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault();
      ContextFault contextFault1 = new ContextFault("4a..e&(B=U=P", (Throwable) contextFault0);
      assertFalse(contextFault1.equals((Object) contextFault0));
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      new ContextFault("", (ContextFaultDetails) null);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault();
      ContextFaultDetails contextFaultDetails0 = contextFault0.getFaultInfo();
      assertNull(contextFaultDetails0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      ContextFault contextFault0 = new ContextFault("");
      // Undeclared exception!
      try { 
        contextFault0.getCause();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }
}
