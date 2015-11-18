package com.psl.cognos.model.crosslaunch.component;

import java.util.ArrayList;

public class CounterReferences {

  private ArrayList<CounterReference> x = new ArrayList<CounterReference>();

  public boolean add(CounterReference counterReference) {
    if (x.contains(counterReference)) {
      return false;
    } else {
      x.add(counterReference);
      return true;
    }
  }
  
  public boolean remove(CounterReference counterReference) {
    return x.remove(counterReference);
  }
  
  @Override
  public String toString() {
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
