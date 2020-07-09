package de.atb.context.common.exceptions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.IllegalFormatConversionException;
import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;

import org.junit.Test;

public class ConfigurationExceptionTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      try {
        new ConfigurationException("pfsD8b'%R", (Object[]) null);
        fail("Expecting exception: UnknownFormatConversionException");
      
      } catch(UnknownFormatConversionException e) {
         //
         // Conversion = 'R'
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      Object[] objectArray0 = new Object[0];
      try {
        new ConfigurationException(">%xdd$2abPS&Ie", objectArray0);
        fail("Expecting exception: MissingFormatArgumentException");
      
      } catch(MissingFormatArgumentException e) {
         //
         // Format specifier '%x'
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      Object[] objectArray0 = new Object[9];
      objectArray0[0] = (Object) objectArray0;
      try {
        new ConfigurationException("%Er&5", objectArray0);
        fail("Expecting exception: IllegalFormatConversionException");
      
      } catch(IllegalFormatConversionException e) {
         //
         // e != [Ljava.lang.Object;
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      try {
        new ConfigurationException((String) null, (Object[]) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      ConfigurationException configurationException0 = new ConfigurationException((String) null, (Throwable) null);
      Object[] objectArray0 = new Object[7];
      try {
        new ConfigurationException((String) null, (Throwable) configurationException0, objectArray0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      ConfigurationException configurationException0 = new ConfigurationException("de.atb.context.common.exceptions.ConfigurationException");
      StackTraceElement[] stackTraceElementArray0 = configurationException0.getStackTrace();
      ConfigurationException configurationException1 = new ConfigurationException("", (Throwable) configurationException0, (Object[]) stackTraceElementArray0);
      assertFalse(configurationException1.equals((Object)configurationException0));
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      ConfigurationException configurationException0 = new ConfigurationException("de.atb.context.common.exceptions.ConfigurationException");
      StackTraceElement[] stackTraceElementArray0 = configurationException0.getStackTrace();
      ConfigurationException configurationException1 = new ConfigurationException("", (Object[]) stackTraceElementArray0);
      assertFalse(configurationException1.equals((Object)configurationException0));
  }

  @Test(timeout = 4000)
  public void test7()  throws Throwable  {
      ConfigurationException configurationException0 = new ConfigurationException("%1!qwrbXpS*`hWP");
      Object[] objectArray0 = new Object[2];
      try {
        new ConfigurationException("%1!qwrbXpS*`hWP", (Throwable) configurationException0, objectArray0);
        fail("Expecting exception: UnknownFormatConversionException");
      
      } catch(UnknownFormatConversionException e) {
         //
         // Conversion = '1'
         //
    	  assertFalse(false);
      }
  }
}
