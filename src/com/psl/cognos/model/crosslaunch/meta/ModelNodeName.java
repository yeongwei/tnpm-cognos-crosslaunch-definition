package com.psl.cognos.model.crosslaunch.meta;

/**
 * Facilitates Cognos Model Node names
 * Minimize re-typing
 * @author laiyw
 *
 */
public enum ModelNodeName {
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
