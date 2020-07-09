package de.atb.context.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilsTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      boolean boolean0 = StringUtils.isEmpty("");
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      boolean boolean0 = StringUtils.isEmpty("Cj H8(ouO");
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      boolean boolean0 = StringUtils.isEmpty((String) null);
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      String string0 = StringUtils.capitalize("Cj H8(ouO");
      assertEquals("Cj H8(OuO", string0);
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      String string0 = StringUtils.capitalize((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      String string0 = StringUtils.capitalize("");
      assertEquals("", string0);
  }
}
