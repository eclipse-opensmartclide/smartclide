package de.atb.context.services.util;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.message.MessageImpl;
import org.junit.Test;

public class Sl4jInInterceptorTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      Sl4jInInterceptor sl4jInInterceptor0 = new Sl4jInInterceptor();
      assertEquals("de.atb.context.services.util.Sl4jInInterceptor", sl4jInInterceptor0.getId());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      Sl4jInInterceptor sl4jInInterceptor0 = new Sl4jInInterceptor("eeive");
      sl4jInInterceptor0.handleMessage(new MessageImpl());
      assertEquals("de.atb.context.services.util.Sl4jInInterceptor", sl4jInInterceptor0.getId());
  }
}
