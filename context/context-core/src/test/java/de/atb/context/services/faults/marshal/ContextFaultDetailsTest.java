package de.atb.context.services.faults.marshal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

import org.junit.Test;

public class ContextFaultDetailsTest {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      contextFaultDetails0.causeName = "%Y-%M%z";
      // Undeclared exception!
      try { 
        contextFaultDetails0.asThrowable();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      String string0 = contextFaultDetails0.getMessage();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails("+$UH/sGz@Z$a");
      String string0 = contextFaultDetails0.getMessage();
      assertEquals("+$UH/sGz@Z$a", string0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      contextFaultDetails0.causeName = "---%D%z";
      String string0 = contextFaultDetails0.getCauseName();
      assertEquals("---%D%z", string0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      contextFaultDetails0.causeName = "---%D%z";
      String string0 = contextFaultDetails0.getCauseName();
      assertEquals("---%D%z", string0);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails("(");
      // Undeclared exception!
      try { 
        contextFaultDetails0.printStackTrace((PrintWriter) null);
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
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails();
      // Undeclared exception!
      try { 
        contextFaultDetails0.printStackTrace((PrintStream) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails();
      contextFaultDetails0.causeName = "de.atb.context.services.faults.marshal.ContextFaultDetails";
      // Undeclared exception!
      try { 
        contextFaultDetails0.asThrowable();
        fail("Expecting exception: ClassCastException");
      
      } catch(ClassCastException e) {
         //
         // de.atb.context.services.faults.marshal.ContextFaultDetails cannot be cast to java.lang.Throwable
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails("eVa>FpZQW", (Throwable) null);
      assertEquals("eVa>FpZQW", contextFaultDetails0.getMessage());
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      String string0 = contextFaultDetails0.getCauseName();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      contextFaultDetails0.causeName = "---%D%z";
      String string0 = contextFaultDetails0.toString();
      assertNotNull(string0);
      assertEquals("---%D%z", string0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      contextFaultDetails0.setMessage(": ");
      String string0 = contextFaultDetails0.toString();
      assertEquals("de.atb.context.services.faults.marshal.ContextFaultDetails: : ", string0);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails();
      contextFaultDetails0.setCauseName("");
      String string0 = contextFaultDetails0.toString();
      assertNotNull(string0);
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      contextFaultDetails0.printStackTrace();
      assertNull(contextFaultDetails0.getCauseName());
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails("");
      String string0 = contextFaultDetails0.getMessage();
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      // Undeclared exception!
      try { 
        contextFaultDetails0.setStackTrace((List<ContextStackTraceElement>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      ContextFaultDetails contextFaultDetails0 = new ContextFaultDetails((Throwable) null);
      List<ContextStackTraceElement> list0 = contextFaultDetails0.getStackTrace();
      assertNull(list0);
  }
}
