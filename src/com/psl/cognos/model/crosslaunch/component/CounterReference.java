package com.psl.cognos.model.crosslaunch.component;

public class CounterReference {
  
  public final String fqnPath; // From business layer
  public final String fqnName; // From business layer
  
  public String presentationFqnPath;
  
  public CounterReference(String fqn) {
    this(getFqnPath(fqn), getFqnName(fqn));
  }
  
  public CounterReference(String fqnPath, String fqnName) {
    this.fqnPath = fqnPath;
    this.fqnName = fqnName;
  }
  
  public boolean setPresentationFqnPath(String presentationFqnPath) {
    this.presentationFqnPath = presentationFqnPath;
    return true;
  }
  
  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    
    if (this.presentationFqnPath == null) {
      s.append(this.fqnPath).append(".");
    } else {
      s.append(this.presentationFqnPath).append(".");
    }
    s.append(this.fqnName);
    return s.toString();
  }
  
  protected static String getFqnPath(String val) {
    StringBuilder fqnPath = new StringBuilder();
    String parts[] = val.split("\\.");
    int limit = (parts.length - 1);
    for (int i = 0; i < limit; i++) {
      if (i == (limit - 1)) {
        fqnPath.append(parts[i]);
      } else {
        fqnPath.append(parts[i]).append(".");
      }
    }
    return fqnPath.toString();
  }

  protected static String getFqnName(String val) {
    String parts[] = val.split("\\.");
    return parts[parts.length - 1];
  }
}
