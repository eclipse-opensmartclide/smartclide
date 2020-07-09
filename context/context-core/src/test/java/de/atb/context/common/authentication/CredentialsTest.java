package de.atb.context.common.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CredentialsTest {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      Credentials credentials0 = new Credentials();
      credentials0.password = "";
      String string0 = credentials0.toString();
      assertEquals("('null' // '')", string0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      Credentials credentials0 = new Credentials();
      String string0 = credentials0.getUserName();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      Credentials credentials0 = new Credentials("*[/=Qj*g%0w':0{#i", "1696-09-01T00:00:00Z");
      String string0 = credentials0.getUserName();
      assertNotNull(string0);
      assertEquals("1696-09-01T00:00:00Z", credentials0.getPassword());
      assertEquals("*[/=Qj*g%0w':0{#i", string0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      Credentials credentials0 = new Credentials((String) null, "6F 2P*");
      String string0 = credentials0.getPassword();
      assertEquals("6F 2P*", string0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      Credentials credentials0 = new Credentials();
      credentials0.password = "";
      String string0 = credentials0.getPassword();
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      Credentials credentials0 = new Credentials();
      // Undeclared exception!
      try { 
        credentials0.getSHA256HashedPassword();
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
      Credentials credentials0 = new Credentials();
      // Undeclared exception!
      try { 
        credentials0.getSHA1HashedPassword();
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
      Credentials credentials0 = new Credentials();
      // Undeclared exception!
      try { 
        credentials0.getMD5HashedPassword();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      Credentials credentials0 = new Credentials("", "k1Y*]s0");
      String string0 = credentials0.getSHA256HashedPassword();
      assertEquals("e8f17df7d637bfe25d5b3b54a0e8df4f5259892e", string0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      Credentials credentials0 = new Credentials("", "k1Y*]s0");
      String string0 = credentials0.getMD5HashedPassword();
      assertEquals("f0dba2340d507d5aa9414634af33af7e", string0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      Credentials credentials0 = new Credentials("B>", "B>");
      credentials0.setPassword("B>");
      assertEquals("B>", credentials0.getUserName());
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      Credentials credentials0 = new Credentials("", "k1Y*]s0");
      String string0 = credentials0.getUserName();
      assertEquals("", string0);
      assertEquals("k1Y*]s0", credentials0.getPassword());
      assertNotNull(string0);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      Credentials credentials0 = new Credentials();
      credentials0.setUserName("");
      assertEquals("", credentials0.getUserName());
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      Credentials credentials0 = new Credentials();
      String string0 = credentials0.getPassword();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      Credentials credentials0 = new Credentials("", "k1Y*]s0");
      String string0 = credentials0.getSHA1HashedPassword();
      assertEquals("e8f17df7d637bfe25d5b3b54a0e8df4f5259892e", string0);
  }
}
