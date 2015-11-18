package com.psl.cognos.model.crosslaunch.component;

public enum ModelNode {
  NAMESPACE, NAME;

  public String getName() {
    switch (this) {
    case NAMESPACE:
      return "namespace";
    case NAME:
      return "name";
    default:
      return null;
    }
  }
}
