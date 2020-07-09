package de.atb.context.common.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class ClasspathHelperTest {

//  @Test
//  public void test0()  throws Throwable  {
//      List<File> list0 = ClasspathHelper.getClassLocationsForCurrentClasspath();
//      //assertEquals(148, list0.size());
//  }
//
//  @Test
//  public void test1()  throws Throwable  {
//      List<Class<?>> list0 = ClasspathHelper.getAllClasses(false);
//      //assertEquals(385, list0.size());
//  }

//  @Test
//  public void test2()  throws Throwable  {
//      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("C:\\daten\\scholze\\gitlab\\context\\prosecoCoreComponents\\context-core\\c:\\temp\\EvoSuite_pathingJar3289621560767690942.jar");
//      FileSystemHandling.createFolder(evoSuiteFile0);
//      List<Class<?>> list0 = ClasspathHelper.getMatchingClasses("");
//      assertTrue(list0.isEmpty());
//  }
//
//  @Test
//  public void test3()  throws Throwable  {
//      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("C:\\daten\\scholze\\gitlab\\context\\prosecoCoreComponents\\context-core\\c:\\temp\\EvoSuite_pathingJar3289621560767690942.jar");
//      byte[] byteArray0 = new byte[6];
//      FileSystemHandling.appendDataToFile(evoSuiteFile0, byteArray0);
//      Class<File> class0 = File.class;
//      List<Class<?>> list0 = ClasspathHelper.getMatchingClasses("Failed to read classes from jar file: ", class0, true);
//      assertTrue(list0.isEmpty());
//  }
//
//  @Test
//  public void test4()  throws Throwable  {
//      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("C:\\daten\\scholze\\gitlab\\context\\prosecoCoreComponents\\context-core\\c:\\temp\\EvoSuite_pathingJar3289621560767690942.jar");
//      FileSystemHandling.createFolder(evoSuiteFile0);
//      List<Class<?>> list0 = ClasspathHelper.getMatchingClasses("", true);
//      assertEquals(0, list0.size());
//  }

  @Test
  public void test5()  throws Throwable  {
      Class<Integer> class0 = Integer.class;
      List<Class<?>> list0 = ClasspathHelper.getMatchingClasses(class0, false);
      assertEquals(0, list0.size());
  }

  @Test
  public void test6()  throws Throwable  {
      Class<String> class0 = String.class;
      List<Class<?>> list0 = ClasspathHelper.getMatchingClasses("", class0);
      assertEquals(0, list0.size());
  }

  @Test
  public void test7()  throws Throwable  {
      Class<String> class0 = String.class;
      List<Class<?>> list0 = ClasspathHelper.getMatchingClasses(class0);
      assertEquals(0, list0.size());
  }

//  @Test
//  public void test8()  throws Throwable  {
//      List<Class<?>> list0 = ClasspathHelper.getMatchingClasses("", true);
//      assertEquals(0, list0.size());
//  }

//  @Test
//  public void test9()  throws Throwable  {
//      List<Class<?>> list0 = ClasspathHelper.getAllClasses();
//      //assertEquals(385, list0.size());
//  }
}
