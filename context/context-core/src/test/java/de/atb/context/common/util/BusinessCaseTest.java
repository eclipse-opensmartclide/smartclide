package de.atb.context.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class BusinessCaseTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      BusinessCase[] businessCaseArray0 = BusinessCase.values();
      assertNotNull(businessCaseArray0);
  }
}
