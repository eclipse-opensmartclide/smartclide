package de.atb.context.services.util;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.message.MessageImpl;
import org.junit.Test;

public class Sl4jOutInterceptorTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      Sl4jOutInterceptor sl4jOutInterceptor0 = new Sl4jOutInterceptor("3}p?");
      sl4jOutInterceptor0.handleMessage(new MessageImpl());
      assertEquals("3}p?", sl4jOutInterceptor0.getPhase());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      Sl4jOutInterceptor sl4jOutInterceptor0 = new Sl4jOutInterceptor();
      assertEquals("pre-stream", sl4jOutInterceptor0.getPhase());
  }
}
