package com.psl.cognos.model.crosslaunch.component;

import java.util.ArrayList;
import java.util.HashMap;

public class CounterReferences {

  private HashMap<String, CounterReference> _x = new HashMap<String, CounterReference>();

  /**
   * Return as [...].[..]
   * 
   * @param counterReference
   * @return
   */
  private static String makeKey(CounterReference counterReference) {
    StringBuffer s = new StringBuffer();
    s.append(counterReference.fqnPath).append(".")
        .append(counterReference.fqnName);
    return s.toString();
  }
  
  /**
   * Create an ArrayList of CounterReference
   * @return
   */
  private ArrayList<CounterReference> flatten() {
    ArrayList<CounterReference> x = new ArrayList<CounterReference>();
    for (Object key : _x.keySet()) {
      x.add(_x.get(key));
    }
    return x;
  }

  public boolean add(CounterReference counterReference) {
    if (_x.containsKey(makeKey(counterReference))) {
      return false;
    } else {
      _x.put(makeKey(counterReference), counterReference);
      return true;
    }

    /*
     * if (x.contains(counterReference)) { return false; } else {
     * x.add(counterReference); return true; }
     */
  }

  public boolean remove(CounterReference counterReference) {
    String key = makeKey(counterReference);
    if (_x.containsKey(key)) {
      _x.remove(key);
      return true;
    } else {
      return false;
    }
    /*
    return x.remove(counterReference);
    */
  }

  /**
   * Defensive Copy of "Store"
   * 
   * @return
   */
  public ArrayList<CounterReference> getStore() {
    return flatten();
  }

  @Override
  public String toString() {   
    ArrayList<CounterReference> x = flatten();
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < x.size(); i++) {
      if (i == x.size() - 1) {
        s.append(x.get(i));
      } else {
        s.append(x.get(i)).append("|");
      }
    }
    return s.toString();
  }
}
