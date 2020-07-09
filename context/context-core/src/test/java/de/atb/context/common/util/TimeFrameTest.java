package de.atb.context.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TimeFrameTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      TimeFrame timeFrame0 = new TimeFrame((Date) null, (Date) null);
      Calendar calendar0 = timeFrame0.getCalendarForEndTime();
      assertNull(calendar0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      TimeFrame timeFrame0 = new TimeFrame((Date) null, (Date) null);
      boolean boolean0 = timeFrame0.contains((Date) null);
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      TimeFrame timeFrame0 = new TimeFrame();
      String string0 = timeFrame0.getXSDLexicalFormForStartTime();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      TimeFrame timeFrame0 = new TimeFrame((Date) null, (Date) null);
      String string0 = timeFrame0.getXSDLexicalFormForEndTime();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      TimeFrame timeFrame0 = new TimeFrame((Date) null, (Date) null);
      Calendar calendar0 = timeFrame0.getCalendarForStartTime();
      assertNull(calendar0);
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      TimeFrame timeFrame0 = new TimeFrame();
      timeFrame0.setStartTime((Date) null);
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      TimeFrame timeFrame0 = new TimeFrame();
      Date date0 = timeFrame0.getStartTime();
      assertNull(date0);
  }

  @Test(timeout = 4000)
  public void test7()  throws Throwable  {
      TimeFrame timeFrame0 = new TimeFrame((Date) null, (Date) null);
      timeFrame0.setEndTime((Date) null);
  }

  @Test(timeout = 4000)
  public void test8()  throws Throwable  {
      TimeFrame timeFrame0 = new TimeFrame();
      Date date0 = timeFrame0.getEndTime();
      assertNull(date0);
  }
}
