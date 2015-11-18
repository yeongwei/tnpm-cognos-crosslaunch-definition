package com.psl.cognos.model.crosslaunch.model;

import java.util.ArrayList;

public class CounterReference {

  private ArrayList<String> x = new ArrayList<String>();

  public boolean add(String counterFqn) {
    if (x.contains(counterFqn)) {
      return false;
    } else {
      x.add(counterFqn);
      return true;
    }
  }
  
  public boolean remove(String counterFqn) {
    return x.remove(counterFqn);
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
