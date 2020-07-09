package de.atb.context.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;

public class ModelOutputLanguageTest {

  @Test(timeout = 4000)
  @Ignore
  public void test00()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.TURTLE;
      // Undeclared exception!
      try { 
        modelOutputLanguage0.getModelFromString("DEFAULT");
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // Could not initialize class com.hp.hpl.jena.datatypes.xsd.XSDDatatype
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.RDFXML;
      // Undeclared exception!
      try { 
        modelOutputLanguage0.getModelAsString((Model) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.DEFAULT;
      modelOutputLanguage0.toString();
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.RDFXML_ABBREV;
      // Undeclared exception!
      try { 
        modelOutputLanguage0.writeModelToFile((Model) null, (File) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.DEFAULT;
      modelOutputLanguage0.getLanguage();
  }

  @Test(timeout = 4000)
  @Ignore
  public void test05()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.N_TRIPLE;
      // Undeclared exception!
      try { 
        modelOutputLanguage0.getModelFromString("<p>A syntax violation was detected in an IP V6 (or future) address.</p>");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  @Ignore
  public void test06()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.DEFAULT;
      // Undeclared exception!
      try { 
        modelOutputLanguage0.getModelFromString((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

//  @Test(timeout = 4000)
//  public void test07()  throws Throwable  {
//      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.RDFXML;
//      // Undeclared exception!
//      try { 
//        modelOutputLanguage0.getModelFromString("%h:%m:%s%z");
//        fail("Expecting exception: RuntimeException");
//      
//      } catch(RuntimeException e) {
//         //
//         // org.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 1; Content is not allowed in prolog.
//         //
//         assertThrownBy("com.hp.hpl.jena.rdf.model.impl.RDFDefaultErrorHandler", e);
//      }
//  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      // Undeclared exception!
      try { 
        ModelOutputLanguage.valueOf(".4");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // No enum constant de.atb.context.persistence.ModelOutputLanguage..4
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      // Undeclared exception!
      try { 
        ModelOutputLanguage.valueOf((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // Name is null
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.N3;
      modelOutputLanguage0.getLanguage();
  }

  @Test(timeout = 4000)
  @Ignore
  public void test11()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.N_TRIPLE;
      ModelCom modelCom0 = (ModelCom)modelOutputLanguage0.getModelFromString("");
      modelOutputLanguage0.getModelAsString(modelCom0);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      ModelOutputLanguage modelOutputLanguage0 = ModelOutputLanguage.RDFXML;
      modelOutputLanguage0.toString();
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      ModelOutputLanguage.valueOf("RDFXML");
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      ModelOutputLanguage.valueOf("DEFAULT");
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      ModelOutputLanguage.values();
  }
}
