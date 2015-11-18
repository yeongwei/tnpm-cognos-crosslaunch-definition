package com.psl.cognos.model.crosslaunch.model;

import com.psl.cognos.model.crosslaunch.component.CounterReferences;

public class BusinessLayerRow {
  final String kpiName;
  final String fqn;
  final CounterReferences counterReference;
  
  public BusinessLayerRow(String kpiName, String fqn, CounterReferences counterReference) {
    this.kpiName = kpiName;
    this.fqn = fqn;
    this.counterReference = counterReference;
  }
  
  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append(this.kpiName).append(",");
    s.append(this.fqn).append(",");
    s.append(this.counterReference.toString());
    return s.toString();
  }
}
