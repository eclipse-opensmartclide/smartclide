package de.atb.context.services.faults.marshal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class ContextStackTraceElementTest {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      StackTraceElement[] stackTraceElementArray0 = new StackTraceElement[3];
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement(":", "iy1Ln(T15E_G", ":", 157);
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      stackTraceElementArray0[0] = stackTraceElement0;
      StackTraceElement stackTraceElement1 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      stackTraceElementArray0[1] = stackTraceElement1;
      StackTraceElement stackTraceElement2 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      stackTraceElementArray0[2] = stackTraceElement2;
      List<ContextStackTraceElement> list0 = ContextStackTraceElement.convert(stackTraceElementArray0);
      ContextStackTraceElement.toStackTraceElementsArray(list0);
      ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement.toStackTraceElementsArray(list0);
      List<ContextStackTraceElement> list1 = ContextStackTraceElement.convert(stackTraceElementArray0);
      List<StackTraceElement> list2 = ContextStackTraceElement.toStackTraceElementsList(list1);
      ContextStackTraceElement.convert((Collection<StackTraceElement>) list2);
      ContextStackTraceElement.toStackTraceElementsList(list0);
      ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement contextStackTraceElement2 = new ContextStackTraceElement();
      // Undeclared exception!
      try { 
        contextStackTraceElement1.equals(contextStackTraceElement2);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
      }
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("0.", "de.atb.context.services.faults.marshal.ContextStackTraceElement", "0.", 0);
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement1.methodName = "bJ6dLuJ48Z";
      StackTraceElement[] stackTraceElementArray0 = new StackTraceElement[0];
      List<ContextStackTraceElement> list0 = ContextStackTraceElement.convert(stackTraceElementArray0);
      List<StackTraceElement> list1 = ContextStackTraceElement.toStackTraceElementsList(list0);
      List<ContextStackTraceElement> list2 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list1);
      List<StackTraceElement> list3 = ContextStackTraceElement.toStackTraceElementsList(list2);
      List<ContextStackTraceElement> list4 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list3);
      StackTraceElement[] stackTraceElementArray1 = ContextStackTraceElement.toStackTraceElementsArray(list4);
      contextStackTraceElement1.getMethodName();
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[8];
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement1);
      ContextStackTraceElement contextStackTraceElement2 = new ContextStackTraceElement("de.atb.context.services.faults.marshal.ContextStackTraceElement", "0.", "bJ6dLuJ48Z", 0);
      contextStackTraceElementArray0[0] = contextStackTraceElement2;
      contextStackTraceElementArray0[1] = contextStackTraceElement0;
      contextStackTraceElementArray0[2] = contextStackTraceElement0;
      contextStackTraceElementArray0[3] = contextStackTraceElement0;
      contextStackTraceElementArray0[4] = contextStackTraceElement0;
      contextStackTraceElementArray0[5] = contextStackTraceElement1;
      contextStackTraceElementArray0[6] = contextStackTraceElement1;
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement1);
      contextStackTraceElementArray0[7] = contextStackTraceElement1;
      ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
      ContextStackTraceElement.convert(stackTraceElementArray1);
      contextStackTraceElement2.getMethodName();
      contextStackTraceElement1.getMethodName();
      ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
      contextStackTraceElement1.equals(contextStackTraceElement0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("", "", "", 0);
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement contextStackTraceElement2 = new ContextStackTraceElement("/pv", "/pv", "", 0);
      contextStackTraceElement0.getMethodName();
      contextStackTraceElement1.hashCode();
      ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement2.toString();
      contextStackTraceElement0.hashCode();
      ContextStackTraceElement contextStackTraceElement3 = new ContextStackTraceElement("", "", "", 0);
      contextStackTraceElement0.equals(contextStackTraceElement3);
      ContextStackTraceElement contextStackTraceElement4 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement4.declaringClass = "/pv./pv(:0)";
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("", "N?J9w(JE=9EV", "N?J9w(JE=9EV", (-12));
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[6];
      ContextStackTraceElement contextStackTraceElement2 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElementArray0[0] = contextStackTraceElement2;
      contextStackTraceElementArray0[1] = contextStackTraceElement0;
      contextStackTraceElementArray0[2] = contextStackTraceElement1;
      contextStackTraceElementArray0[3] = contextStackTraceElement0;
      ContextStackTraceElement contextStackTraceElement3 = new ContextStackTraceElement("", "", "N?J9w(JE=9EV", (-12));
      contextStackTraceElementArray0[4] = contextStackTraceElement3;
      contextStackTraceElementArray0[5] = contextStackTraceElement0;
      List<StackTraceElement> list0 = ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
      List<ContextStackTraceElement> list1 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list0);
      List<StackTraceElement> list2 = ContextStackTraceElement.toStackTraceElementsList(list1);
      List<ContextStackTraceElement> list3 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list2);
      ContextStackTraceElement.toStackTraceElementsList(list3);
      contextStackTraceElement0.getMethodName();
      contextStackTraceElement1.hashCode();
      ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement3.toString();
      contextStackTraceElement0.hashCode();
      ContextStackTraceElement.convert((Collection<StackTraceElement>) list0);
      contextStackTraceElement3.methodName = "";
      ContextStackTraceElement contextStackTraceElement4 = new ContextStackTraceElement("", "Day", "", 0);
      contextStackTraceElement0.equals(contextStackTraceElement4);
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement3);
      contextStackTraceElement0.fileName = ":";
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("", "N?J9w(JE=9EV", "N?J9w(JE=9EV", 0);
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[6];
      ContextStackTraceElement contextStackTraceElement2 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElementArray0[0] = contextStackTraceElement2;
      contextStackTraceElementArray0[1] = contextStackTraceElement0;
      contextStackTraceElementArray0[2] = contextStackTraceElement1;
      contextStackTraceElementArray0[3] = contextStackTraceElement0;
      ContextStackTraceElement contextStackTraceElement3 = new ContextStackTraceElement("/pv", "/pv", "N?J9w(JE=9EV", 0);
      contextStackTraceElementArray0[4] = contextStackTraceElement3;
      contextStackTraceElementArray0[5] = contextStackTraceElement0;
      List<StackTraceElement> list0 = ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
      List<ContextStackTraceElement> list1 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list0);
      List<StackTraceElement> list2 = ContextStackTraceElement.toStackTraceElementsList(list1);
      List<ContextStackTraceElement> list3 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list2);
      ContextStackTraceElement.toStackTraceElementsList(list3);
      contextStackTraceElement0.getMethodName();
      contextStackTraceElement1.hashCode();
      ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement3.toString();
      contextStackTraceElement0.hashCode();
      ContextStackTraceElement.convert((Collection<StackTraceElement>) list0);
      contextStackTraceElement3.methodName = "/pv";
      ContextStackTraceElement contextStackTraceElement4 = new ContextStackTraceElement("", ".", "", (-3));
      contextStackTraceElement0.equals(contextStackTraceElement4);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement();
      contextStackTraceElement0.getClassName();
      contextStackTraceElement0.declaringClass = "A)yRU&(:[6$1";
      try {
        new ContextStackTraceElement((String) null, "(Native Method)", "(Native Method)", 1192);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // Declaring class is null
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("{>WAFF(@\"uR4F4XRy", "", (String) null, (-1887));
      contextStackTraceElement0.toString();
      StackTraceElement[] stackTraceElementArray0 = new StackTraceElement[0];
      List<ContextStackTraceElement> list0 = ContextStackTraceElement.convert(stackTraceElementArray0);
      ContextStackTraceElement.toStackTraceElementsList(list0);
      contextStackTraceElement0.fileName = null;
      contextStackTraceElement0.hashCode();
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[4];
      contextStackTraceElementArray0[0] = contextStackTraceElement0;
      contextStackTraceElementArray0[1] = contextStackTraceElement0;
      contextStackTraceElementArray0[2] = contextStackTraceElement0;
      ContextStackTraceElement contextStackTraceElement1 = new ContextStackTraceElement();
      contextStackTraceElement1.methodName = "%Y-%M-%DT%h:%m:%s%z";
      contextStackTraceElementArray0[3] = contextStackTraceElement1;
      // Undeclared exception!
      try { 
        ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // Declaring class is null
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      // Undeclared exception!
      try { 
        ContextStackTraceElement.toStackTraceElementsArray((Collection<ContextStackTraceElement>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("DWr(V:V>gUTIRQ~w", "S2", "", (-1967));
      contextStackTraceElement0.toString();
      contextStackTraceElement0.fileName = "tR";
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement2 = ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      contextStackTraceElement2.getLineNumber();
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement.convert(stackTraceElement0);
      // Undeclared exception!
      try { 
        ContextStackTraceElement.convert((StackTraceElement[]) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      // Undeclared exception!
      try { 
        ContextStackTraceElement.toStackTraceElementsList((Collection<ContextStackTraceElement>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement(".", ".", ".", 0);
      contextStackTraceElement0.lineNumber = 0;
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement0.hashCode();
      StackTraceElement[] stackTraceElementArray0 = new StackTraceElement[1];
      stackTraceElementArray0[0] = stackTraceElement0;
      List<ContextStackTraceElement> list0 = ContextStackTraceElement.convert(stackTraceElementArray0);
      ContextStackTraceElement.toStackTraceElementsList(list0);
      contextStackTraceElement0.getFileName();
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      // Undeclared exception!
      try { 
        ContextStackTraceElement.convert((Collection<StackTraceElement>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("$)1x])!JNJYp", "R@.SV!)WMa7^5:9", "$)1x])!JNJYp", 31);
      contextStackTraceElement0.getLineNumber();
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("d*v25lT qR}d", "d*v25lT qR}d", "d*v25lT qR}d", 0);
      contextStackTraceElement0.declaringClass = "k&";
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement1.getClassName();
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("I!lpO)", "I!lpO)", "", 109);
      contextStackTraceElement0.getFileName();
      StackTraceElement[] stackTraceElementArray0 = new StackTraceElement[0];
      List<ContextStackTraceElement> list0 = ContextStackTraceElement.convert(stackTraceElementArray0);
      ContextStackTraceElement.toStackTraceElementsList(list0);
      // Undeclared exception!
      try { 
        ContextStackTraceElement.convert((StackTraceElement) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement();
      contextStackTraceElement0.getFileName();
      StackTraceElement[] stackTraceElementArray0 = new StackTraceElement[0];
      List<ContextStackTraceElement> list0 = ContextStackTraceElement.convert(stackTraceElementArray0);
      ContextStackTraceElement.toStackTraceElementsList(list0);
      contextStackTraceElement0.fileName = "";
      ContextStackTraceElement contextStackTraceElement1 = new ContextStackTraceElement("", "1", "1", (-1393));
      contextStackTraceElement0.declaringClass = "";
      contextStackTraceElement1.methodName = "";
      contextStackTraceElement1.hashCode();
      ContextStackTraceElement contextStackTraceElement2 = new ContextStackTraceElement();
      contextStackTraceElement2.declaringClass = "1";
      contextStackTraceElement0.toString();
      contextStackTraceElement2.getLineNumber();
      contextStackTraceElement2.getMethodName();
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("Z{C2L3`U5G", "", "B\"l", 1338);
      contextStackTraceElement0.toString();
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[4];
      // Undeclared exception!
      try { 
        ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement(" s8)AJ^", " s8)AJ^", " s8)AJ^", 400);
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("", "", (String) null, 999);
      contextStackTraceElement0.getClassName();
      ContextStackTraceElement contextStackTraceElement1 = new ContextStackTraceElement("", "", (String) null, 0);
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[4];
      contextStackTraceElementArray0[0] = contextStackTraceElement1;
      contextStackTraceElementArray0[1] = contextStackTraceElement0;
      contextStackTraceElementArray0[2] = contextStackTraceElement0;
      contextStackTraceElementArray0[3] = contextStackTraceElement0;
      List<StackTraceElement> list0 = ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
      ContextStackTraceElement.convert((Collection<StackTraceElement>) list0);
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      // Undeclared exception!
      try { 
        ContextStackTraceElement.convert((StackTraceElement[]) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement();
      contextStackTraceElement0.fileName = null;
      // Undeclared exception!
      try { 
        contextStackTraceElement0.hashCode();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test22()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("Day", "Day", "", (-2));
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement.convert(stackTraceElement0);
  }

  @Test(timeout = 4000)
  public void test23()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement();
      ContextStackTraceElement contextStackTraceElement1 = new ContextStackTraceElement("%Y-%M%z", "_xHkZ3", (String) null, (-2312));
      contextStackTraceElement1.methodName = "60";
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement1);
      contextStackTraceElement0.getLineNumber();
  }

  @Test(timeout = 4000)
  public void test24()  throws Throwable  {
      try {
        new ContextStackTraceElement("", (String) null, "InvalidFractional", 31);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // Method name is null
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test25()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement();
      contextStackTraceElement0.equals(contextStackTraceElement0);
      // Undeclared exception!
      try { 
        ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // Declaring class is null
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test26()  throws Throwable  {
      try {
        new ContextStackTraceElement((String) null, (String) null, (String) null, 31);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // Declaring class is null
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test27()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement();
      contextStackTraceElement0.toString();
      // Undeclared exception!
      try { 
        ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // Declaring class is null
         //
    	  assertFalse(false);
      }
  }

  @Test(timeout = 4000)
  public void test28()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("0.", "de.atb.context.services.faults.marshal.ContextStackTraceElement", "0.", 0);
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement1.methodName = "bJ6dLuJ48Z";
      StackTraceElement[] stackTraceElementArray0 = new StackTraceElement[0];
      List<ContextStackTraceElement> list0 = ContextStackTraceElement.convert(stackTraceElementArray0);
      List<StackTraceElement> list1 = ContextStackTraceElement.toStackTraceElementsList(list0);
      List<ContextStackTraceElement> list2 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list1);
      List<StackTraceElement> list3 = ContextStackTraceElement.toStackTraceElementsList(list2);
      List<ContextStackTraceElement> list4 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list3);
      ContextStackTraceElement.toStackTraceElementsArray(list4);
      contextStackTraceElement1.getMethodName();
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[8];
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement1);
      ContextStackTraceElement contextStackTraceElement2 = new ContextStackTraceElement("de.atb.context.services.faults.marshal.ContextStackTraceElement", "0.", "bJ6dLuJ48Z", 0);
      contextStackTraceElementArray0[0] = contextStackTraceElement2;
      contextStackTraceElementArray0[1] = contextStackTraceElement0;
      contextStackTraceElementArray0[2] = contextStackTraceElement0;
      contextStackTraceElementArray0[3] = contextStackTraceElement0;
      contextStackTraceElementArray0[4] = contextStackTraceElement0;
      contextStackTraceElementArray0[5] = contextStackTraceElement1;
      contextStackTraceElementArray0[6] = contextStackTraceElement1;
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement1);
      contextStackTraceElementArray0[7] = contextStackTraceElement1;
      ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
      ContextStackTraceElement.convert(stackTraceElementArray0);
      contextStackTraceElement2.getMethodName();
      contextStackTraceElement1.getMethodName();
  }

  @Test(timeout = 4000)
  public void test29()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("", "N?J9w(JE=9EV", "N?J9w(JE=9EV", 0);
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[6];
      ContextStackTraceElement contextStackTraceElement2 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElementArray0[0] = contextStackTraceElement2;
      contextStackTraceElementArray0[1] = contextStackTraceElement0;
      contextStackTraceElementArray0[2] = contextStackTraceElement1;
      contextStackTraceElementArray0[3] = contextStackTraceElement0;
      ContextStackTraceElement contextStackTraceElement3 = new ContextStackTraceElement("/pv", "/pv", "N?J9w(JE=9EV", 0);
      contextStackTraceElementArray0[4] = contextStackTraceElement3;
      contextStackTraceElementArray0[5] = contextStackTraceElement0;
      List<StackTraceElement> list0 = ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
      List<ContextStackTraceElement> list1 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list0);
      List<StackTraceElement> list2 = ContextStackTraceElement.toStackTraceElementsList(list1);
      List<ContextStackTraceElement> list3 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list2);
      ContextStackTraceElement.toStackTraceElementsList(list3);
      contextStackTraceElement0.getMethodName();
      contextStackTraceElement1.hashCode();
      ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement3.toString();
      contextStackTraceElement0.hashCode();
      contextStackTraceElement3.methodName = "/pv";
      ContextStackTraceElement contextStackTraceElement4 = new ContextStackTraceElement("", "Day", "", 0);
      contextStackTraceElement0.equals(contextStackTraceElement4);
      ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
  }

  @Test(timeout = 4000)
  public void test30()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("", "", "", 0);
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[6];
      ContextStackTraceElement contextStackTraceElement2 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElementArray0[0] = contextStackTraceElement2;
      contextStackTraceElementArray0[1] = contextStackTraceElement0;
      contextStackTraceElementArray0[2] = contextStackTraceElement1;
      contextStackTraceElementArray0[3] = contextStackTraceElement0;
      ContextStackTraceElement contextStackTraceElement3 = new ContextStackTraceElement("/pv", "/pv", "", 0);
      contextStackTraceElementArray0[4] = contextStackTraceElement3;
      contextStackTraceElementArray0[5] = contextStackTraceElement0;
      List<StackTraceElement> list0 = ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
      List<ContextStackTraceElement> list1 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list0);
      List<StackTraceElement> list2 = ContextStackTraceElement.toStackTraceElementsList(list1);
      List<ContextStackTraceElement> list3 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list2);
      ContextStackTraceElement.toStackTraceElementsList(list3);
      contextStackTraceElement0.getMethodName();
      contextStackTraceElement1.hashCode();
      ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement3.toString();
      ContextStackTraceElement.convert((Collection<StackTraceElement>) list0);
      contextStackTraceElement3.methodName = "/pv";
      ContextStackTraceElement contextStackTraceElement4 = new ContextStackTraceElement("", "Day", "", 0);
      contextStackTraceElement0.equals(contextStackTraceElement4);
      contextStackTraceElement1.methodName = "";
      contextStackTraceElement1.lineNumber = 0;
      contextStackTraceElementArray0[1].methodName = "/pv";
      contextStackTraceElementArray0[5].fileName = "";
      contextStackTraceElement3.equals(contextStackTraceElement1);
  }

  @Test(timeout = 4000)
  public void test31()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("Y", "Y", "FM%*AHG)uzKz", 0);
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement1.declaringClass = "FM%*AHG)uzKz";
      Integer integer0 = new Integer(1414);
      contextStackTraceElement0.declaringClass = "Declaring class is null";
      contextStackTraceElement1.equals(integer0);
      contextStackTraceElement0.lineNumber = 1414;
      contextStackTraceElement0.methodName = "Y";
      StackTraceElement[] stackTraceElementArray0 = new StackTraceElement[9];
      stackTraceElementArray0[0] = stackTraceElement0;
      stackTraceElementArray0[1] = stackTraceElement0;
      stackTraceElementArray0[2] = stackTraceElement0;
      stackTraceElementArray0[3] = stackTraceElement0;
      stackTraceElementArray0[4] = stackTraceElement0;
      stackTraceElementArray0[5] = stackTraceElement0;
      stackTraceElementArray0[6] = stackTraceElement0;
      stackTraceElementArray0[7] = stackTraceElement0;
      stackTraceElementArray0[8] = stackTraceElement0;
      List<ContextStackTraceElement> list0 = ContextStackTraceElement.convert(stackTraceElementArray0);
      List<StackTraceElement> list1 = ContextStackTraceElement.toStackTraceElementsList(list0);
      List<ContextStackTraceElement> list2 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list1);
      ContextStackTraceElement.toStackTraceElementsArray(list2);
      contextStackTraceElement1.getFileName();
      ContextStackTraceElement.toStackTraceElementsArray(list0);
      contextStackTraceElement0.isNativeMethod();
  }

  @Test(timeout = 4000)
  public void test32()  throws Throwable  {
      ContextStackTraceElement contextStackTraceElement0 = new ContextStackTraceElement("Y[1mSQ+gJE6@_A!'X", "Y[1mSQ+gJE6@_A!'X", "Y[1mSQ+gJE6@_A!'X", (-2410));
      contextStackTraceElement0.fileName = "";
      StackTraceElement stackTraceElement0 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement0);
      contextStackTraceElement0.methodName = ")";
      contextStackTraceElement0.toString();
      ContextStackTraceElement contextStackTraceElement1 = ContextStackTraceElement.convert(stackTraceElement0);
      contextStackTraceElement0.getMethodName();
      ContextStackTraceElement contextStackTraceElement2 = new ContextStackTraceElement("", ",};C\">+9#wPIf+>/`X", ",};C\">+9#wPIf+>/`X", 1479);
      ContextStackTraceElement contextStackTraceElement3 = new ContextStackTraceElement("GMT", "GMT", ",};C\">+9#wPIf+>/`X", 0);
      StackTraceElement[] stackTraceElementArray0 = new StackTraceElement[1];
      stackTraceElementArray0[0] = stackTraceElement0;
      List<ContextStackTraceElement> list0 = ContextStackTraceElement.convert(stackTraceElementArray0);
      List<StackTraceElement> list1 = ContextStackTraceElement.toStackTraceElementsList(list0);
      List<ContextStackTraceElement> list2 = ContextStackTraceElement.convert((Collection<StackTraceElement>) list1);
      ContextStackTraceElement[] contextStackTraceElementArray0 = new ContextStackTraceElement[7];
      contextStackTraceElementArray0[0] = contextStackTraceElement3;
      contextStackTraceElementArray0[1] = contextStackTraceElement3;
      contextStackTraceElementArray0[2] = contextStackTraceElement2;
      contextStackTraceElementArray0[3] = contextStackTraceElement3;
      contextStackTraceElementArray0[4] = contextStackTraceElement3;
      contextStackTraceElementArray0[5] = contextStackTraceElement0;
      contextStackTraceElementArray0[6] = contextStackTraceElement1;
      List<StackTraceElement> list3 = ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray0);
      ContextStackTraceElement.toStackTraceElementsArray(list2);
      ContextStackTraceElement contextStackTraceElement4 = new ContextStackTraceElement();
      contextStackTraceElement4.declaringClass = ",};C\">+9#wPIf+>/`X";
      StackTraceElement stackTraceElement1 = ContextStackTraceElement.toStackTraceElement(contextStackTraceElement3);
      ContextStackTraceElement.toStackTraceElement(contextStackTraceElement3);
      contextStackTraceElement3.equals(contextStackTraceElement0);
      ContextStackTraceElement.convert(stackTraceElement1);
      ContextStackTraceElement.convert((Collection<StackTraceElement>) list1);
      contextStackTraceElement3.getMethodName();
      ContextStackTraceElement[] contextStackTraceElementArray1 = new ContextStackTraceElement[0];
      List<StackTraceElement> list4 = ContextStackTraceElement.toStackTraceElements(contextStackTraceElementArray1);
      assertNotSame(list4, list3);
  }
}
