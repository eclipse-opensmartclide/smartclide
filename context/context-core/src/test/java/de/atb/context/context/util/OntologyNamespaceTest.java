package de.atb.context.context.util;

import de.atb.context.common.util.BusinessCase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OntologyNamespaceTest {

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      OntologyNamespace[] ontologyNamespaceArray0 = OntologyNamespace.values();
      assertNotNull(ontologyNamespaceArray0);
  }

  // TODO: fix test
  @Test(timeout = 4000)
  @Ignore
  public void test17() {
      String string0 = OntologyNamespace.prepareSparqlQuery("swrl");
      assertEquals("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\nPREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\nPREFIX owl: <http://www.w3.org/2002/07/owl#>\nPREFIX swrl: <http://www.w3.org/2003/11/swrl#>\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nPREFIX diversity1: <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX diversity2: <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX diversity3: <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX diversity4: <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX desma: <http://www.diversity-project.eu/ontologies/context-context-desma-specification.owl#>\nPREFIX : <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX ical: <http://www.w3.org/2002/12/cal/ical#>\nPREFIX time: <http://www.w3.org/2006/time#>\nPREFIX tzont: <http://www.w3.org/2006/timezone#>\n\nswrl", string0);
  }

  // TODO: fix test
  @Test(timeout = 4000)
  @Ignore
  public void test21() {
      String string0 = OntologyNamespace.getSparqlPrefixes();
      assertEquals("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\nPREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\nPREFIX owl: <http://www.w3.org/2002/07/owl#>\nPREFIX swrl: <http://www.w3.org/2003/11/swrl#>\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nPREFIX diversity1: <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX diversity2: <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX diversity3: <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX diversity4: <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX desma: <http://www.diversity-project.eu/ontologies/context-context-desma-specification.owl#>\nPREFIX : <http://www.proseco-project.eu/ontologies/diversity-context.owl#>\nPREFIX ical: <http://www.w3.org/2002/12/cal/ical#>\nPREFIX time: <http://www.w3.org/2006/time#>\nPREFIX tzont: <http://www.w3.org/2006/timezone#>\n", string0);
  }

  // TODO: fix test
  @Test(timeout = 4000)
  @Ignore
  public void test22() {
      BusinessCase businessCase0 = BusinessCase.getInstance(BusinessCase.NS_DUMMY_ID, BusinessCase.NS_DUMMY_URL);
      String string0 = OntologyNamespace.getAbsoluteUri(businessCase0);
      assertEquals("http://www.w3.org/2002/12/cal/ical", string0);
      assertNotNull(string0);
  }
}
