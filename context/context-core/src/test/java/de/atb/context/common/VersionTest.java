package de.atb.context.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class VersionTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      Version[] versionArray0 = Version.values();
      assertNotNull(versionArray0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      Version version0 = Version.valueOf("PROSECO");
      assertEquals(0, version0.ordinal());
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      Version version0 = Version.valueOf("INDEXER");
      assertEquals(8, version0.getMinor());
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      // Undeclared exception!
      try { 
        Version.valueOf((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // Name is null
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      // Undeclared exception!
      try { 
        Version.valueOf("InvalidFractional");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // No enum constant de.atb.context.common.Version.InvalidFractional
         //
         assertTrue(true);
      }
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      Version version0 = Version.MONITORING_DATA;
      String string0 = version0.getVersionString();
      assertEquals("0.8.15", string0);
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      Version version0 = Version.PROSECO;
      int int0 = version0.getBuild();
      assertEquals(15, int0);
  }

  @Test(timeout = 4000)
  public void test7()  throws Throwable  {
      Version version0 = Version.MONITORING_DATA;
      int int0 = version0.getMajor();
      assertEquals(0, int0);
  }

  @Test(timeout = 4000)
  public void test8()  throws Throwable  {
      Version version0 = Version.PROSECO;
      int int0 = version0.getMinor();
      assertEquals(8, int0);
  }
}
