package com.psl.cognos.model.crosslaunch.model;

import com.psl.cognos.model.crosslaunch.component.CounterReferences;

public class BusinessLayerRow {
  // fqn = fqnPath + [fqnName]
  public final String fqnName;
  public final String fqnPath;

  public final CounterReferences counterReferences;

  public final String fqnEntityIdentifier;
  public final String fqnHourKey;
  public final String uniqueKey;
  
  public BusinessLayerRow(String fqnName, String fqnPath,
      CounterReferences counterReferences, String fqnEntityIdentifier, String fqnHourKey, String uniqueKey) {
    this.fqnName = fqnName;
    this.fqnPath = fqnPath;
    this.counterReferences = counterReferences;
    this.fqnEntityIdentifier = fqnEntityIdentifier;
    this.fqnHourKey = fqnHourKey;
    this.uniqueKey = uniqueKey;
  }

  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append(this.fqnName).append(",");
    s.append(this.fqnPath).append(",");
    s.append(this.counterReferences.toString()).append(",");
    s.append(this.fqnEntityIdentifier).append(",");
    s.append(this.fqnHourKey).append(",");
    s.append(uniqueKey);
    return s.toString();
  }
}
