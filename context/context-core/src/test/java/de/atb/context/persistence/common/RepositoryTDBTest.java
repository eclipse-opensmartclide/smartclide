package de.atb.context.persistence.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

public class RepositoryTDBTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      // Undeclared exception!
      try { 
        RepositoryTDB.clearDirectory((File) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }
}
