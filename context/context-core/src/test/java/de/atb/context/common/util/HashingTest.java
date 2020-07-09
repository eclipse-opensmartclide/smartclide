package de.atb.context.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

public class HashingTest {

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      Hashing.getStringHash("MD5", "MD5");
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      byte[] byteArray0 = new byte[1];
      Hashing.formatBytesArray(byteArray0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      byte[] byteArray0 = new byte[0];
      Hashing.formatBytesArray(byteArray0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      // Undeclared exception!
      try { 
        Hashing.getSHA256Hash((String) null);
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
        Hashing.getSHA1Hash((String) null);
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
        Hashing.getSHA1Checksum((String) null);
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
        Hashing.getMD5Hash((String) null);
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
      // Undeclared exception!
      try { 
        Hashing.getMD5Checksum((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

//  @Test(timeout = 4000)
//  public void test13()  throws Throwable  {
//      try { 
//        Hashing.getFileHash((File) null, "7f138a09169b250e9dcb378140907378");
//        fail("Expecting exception: NoSuchAlgorithmException");
//      
//      } catch(NoSuchAlgorithmException e) {
//         //
//         // 7f138a09169b250e9dcb378140907378 MessageDigest not available
//         //
//         assertThrownBy("sun.security.jca.GetInstance", e);
//      }
//  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      // Undeclared exception!
      try { 
        Hashing.getFileHash((File) null, "SHA-256");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      // Undeclared exception!
      try { 
        Hashing.getChecksum((String) null, "d,l9>In");
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
      // Undeclared exception!
      try { 
        Hashing.formatBytesArray((byte[]) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      // Undeclared exception!
      try { 
        Hashing.getStringHash("$q}=iJU2F5", (String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }


  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      String string0 = Hashing.getSHA1Checksum((File) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test22()  throws Throwable  {
      String string0 = Hashing.getSHA1Checksum("MD5");
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test23()  throws Throwable  {
      String string0 = Hashing.getSHA256Hash("da39a3ee5e6b4b0d3255bfef95601890afd80709");
      assertNotNull(string0);
      assertEquals("10a34637ad661d98ba3344717656fcc76209c2f8", string0);
  }

  @Test(timeout = 4000)
  public void test24()  throws Throwable  {
      String string0 = Hashing.getMD5Checksum((File) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test25()  throws Throwable  {
      String string0 = Hashing.getMD5Hash("f1d3ff8443297732862df21dc4e57262");
      assertEquals("02385956f2ded894a4f0b02c83a8ee3c", string0);
      assertNotNull(string0);
  }

  @Test(timeout = 4000)
  public void test26()  throws Throwable  {
      String string0 = Hashing.getSHA256Checksum((File) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test28()  throws Throwable  {
      String string0 = Hashing.getSHA1Hash("3P<IdhrVNR0?XHldx#?");
      assertEquals("c5ae6ead5800745b2c8b7f2bcacf1c2c2242a02f", string0);
      assertNotNull(string0);
  }

  @Test(timeout = 4000)
  public void test29()  throws Throwable  {
      String string0 = Hashing.getMD5Checksum("MD5");
      assertNull(string0);
  }
}
