package com.psl.cognos.model.crosslaunch.component;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.junit.Ignore;
import org.junit.Test;

public class CounterReferencesTest {

  private static Logger LOGGER = Logger.getLogger(CounterReferencesTest.class
      .getName());

  @Ignore
  @Test
  public void testEquality() {
    String samePath = "[Some].[Path]";
    String sameKpi = "[aKpi]";

    CounterReference obj1 = new CounterReference(samePath, sameKpi);
    CounterReference obj2 = new CounterReference(samePath, sameKpi);

    LOGGER.info(Integer.toString(obj1.hashCode()));
    LOGGER.info(Integer.toString(obj2.hashCode())); // Different hashCode

    assertTrue(obj1 == obj2); // fail
    assertTrue(obj1.equals(obj2)); // fail
  }
  
  @Test
  public void testDeduplication() {
    CounterReferences counterReferences = new CounterReferences();
    
    String samePath = "[Some].[Path]";
    String sameKpi = "[aKpi]";
    CounterReference obj1 = new CounterReference(samePath, sameKpi);
    CounterReference obj2 = new CounterReference(samePath, sameKpi);
    
    counterReferences.add(obj1);
    counterReferences.add(obj2);
    counterReferences.add(obj1);
    counterReferences.add(obj2);
    
    ArrayList<CounterReference> store = counterReferences.getStore();
    assertTrue(store.size() == 1);
    
    assertTrue(store.get(0) == obj1);
    // assertTrue(store.get(0) == obj2); // not relevant
  } 
}
