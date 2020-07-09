package de.atb.context.services.faults;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ContextNotRunningTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      ContextNotRunning contextNotRunning0 = new ContextNotRunning();
      ContextNotRunning contextNotRunning1 = new ContextNotRunning("9gIr", (Throwable) contextNotRunning0);
      assertFalse(contextNotRunning1.equals((Object) contextNotRunning0));
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      ContextNotRunning contextNotRunning0 = new ContextNotRunning();
      ContextNotRunning contextNotRunning1 = new ContextNotRunning((Throwable) contextNotRunning0);
      assertFalse(contextNotRunning1.equals((Object) contextNotRunning0));
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      new ContextNotRunning("<2O+@");
  }
}
