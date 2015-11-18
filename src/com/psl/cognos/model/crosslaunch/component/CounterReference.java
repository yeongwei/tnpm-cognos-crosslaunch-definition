package com.psl.cognos.model.crosslaunch.component;

import com.psl.cognos.model.crosslaunch.parser.Parser;

public class CounterReference {
  
  public final String fqnPath; // From business layer
  public final String fqnName; // From business layer
  
  public String presentationFqnPath;
  
  public CounterReference(String fqn) {
    this(Parser.getFqnPath(fqn), Parser.getFqnName(fqn));
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
}
