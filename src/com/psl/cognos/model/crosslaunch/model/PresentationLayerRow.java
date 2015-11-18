package com.psl.cognos.model.crosslaunch.model;

class PresentationLayerRow {
  public final String fqn;
  public final String refObj;

  public PresentationLayerRow(String fqn, String refObj) {
    this.fqn = fqn;
    this.refObj = refObj;
  }

  @Override
  public String toString() {
    return String.format("%s,%s", this.fqn, this.refObj);
  }
}
