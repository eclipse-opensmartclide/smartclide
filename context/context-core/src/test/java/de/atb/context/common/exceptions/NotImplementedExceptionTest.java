package de.atb.context.common.exceptions;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class NotImplementedExceptionTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      NotImplementedException notImplementedException0 = new NotImplementedException("^o]@");
      NotImplementedException notImplementedException1 = new NotImplementedException("^o]@", (Throwable) notImplementedException0);
      assertFalse(notImplementedException1.equals((Object)notImplementedException0));
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      NotImplementedException notImplementedException0 = new NotImplementedException();
      NotImplementedException notImplementedException1 = new NotImplementedException((Throwable) notImplementedException0);
      assertFalse(notImplementedException1.equals((Object)notImplementedException0));
  }
}
