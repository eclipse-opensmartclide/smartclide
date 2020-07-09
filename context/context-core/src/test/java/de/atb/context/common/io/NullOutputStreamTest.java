package de.atb.context.common.io;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class NullOutputStreamTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      NullOutputStream nullOutputStream0 = new NullOutputStream();
      nullOutputStream0.write((-2572));
      nullOutputStream0.close();
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      NullOutputStream nullOutputStream0 = new NullOutputStream();
      byte[] byteArray0 = new byte[0];
      nullOutputStream0.write(byteArray0, 1, 2098);
      assertArrayEquals(new byte[] {}, byteArray0);
      nullOutputStream0.close();
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      NullOutputStream nullOutputStream0 = new NullOutputStream();
      byte[] byteArray0 = new byte[0];
      nullOutputStream0.write(byteArray0);
      assertArrayEquals(new byte[] {}, byteArray0);
      nullOutputStream0.close();
  }
}
