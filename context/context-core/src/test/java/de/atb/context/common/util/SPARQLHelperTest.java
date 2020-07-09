package de.atb.context.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SPARQLHelperTest {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfType((Class<String>) null);
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
      Class<Object> class0 = Object.class;
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfPropertyQualifier(class0, "UM>3]$UqN|OD(!+\"");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Clazz must be annotated with thewebsemantic.Namespace annotation.
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfNamespace((Class<Object>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      Class<Object> class0 = Object.class;
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfClassQualifierWithoutBraces(class0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Clazz must be annotated with thewebsemantic.Namespace annotation.
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      Class<String> class0 = String.class;
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfClassQualifier(class0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Clazz must be annotated with thewebsemantic.Namespace annotation.
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      Class<Object> class0 = Object.class;
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfNamespace(class0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Clazz must be annotated with thewebsemantic.Namespace annotation.
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      Class<Object> class0 = Object.class;
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfType(class0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Clazz must be annotated with thewebsemantic.RdfType annotation.
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      Class<Object> class0 = Object.class;
      // Undeclared exception!
      try { 
        SPARQLHelper.createProperty(class0, "9");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Clazz must be annotated with thewebsemantic.Namespace annotation.
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfClassQualifier((Class<String>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      // Undeclared exception!
      try { 
        SPARQLHelper.createProperty((Class<Integer>) null, "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nB ,");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfPropertyQualifier((Class<Object>) null, (String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      // Undeclared exception!
      try { 
        SPARQLHelper.getRdfClassQualifierWithoutBraces((Class<Integer>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      String string0 = SPARQLHelper.appendDefaultPrefixes("B ,");
      assertEquals("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nB ,", string0);
  }
}
