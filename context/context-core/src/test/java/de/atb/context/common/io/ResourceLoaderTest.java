package de.atb.context.common.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;

import org.junit.Ignore;
import org.junit.Test;

public class ResourceLoaderTest {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      InputStream inputStream0 = ResourceLoader.getResourceStream("iX");
      assertNull(inputStream0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      // Undeclared exception!
      try { 
        ResourceLoader.resourceExists((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      // Undeclared exception!
      try { 
        ResourceLoader.getResourceURI((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      // Undeclared exception!
      try { 
        ResourceLoader.getResourceStream((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      // Undeclared exception!
      try { 
        ResourceLoader.getResource((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      // Undeclared exception!
      try { 
        ResourceLoader.copyFileResource("", (File) null);
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
      URI uRI0 = ResourceLoader.getResourceURI("");
      assertEquals("file", uRI0.getScheme());
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      URI uRI0 = ResourceLoader.getResourceURI(":6kT:^ h+hm/n");
      assertNull(uRI0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      File file0 = ResourceLoader.getResource("hI>2x7e87>0|");
      assertNull(file0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      boolean boolean0 = ResourceLoader.resourceExists("");
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      boolean boolean0 = ResourceLoader.resourceExists("InvalidXGCValue-fractional");
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      boolean boolean0 = ResourceLoader.copyFileResource("hI>2x7e87>0|", (File) null);
      assertFalse(boolean0);
  }
}
