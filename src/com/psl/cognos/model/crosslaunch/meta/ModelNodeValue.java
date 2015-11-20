package com.psl.cognos.model.crosslaunch.meta;

/**
 * Facilitate Cognos Model Node Value
 * Avoid re-typing
 * @author laiyw
 *
 */
public enum ModelNodeValue {
  BUSINESS_LAYER, PRESENTATION_LAYER;

  public String getName() {
    switch (this) {
    case BUSINESS_LAYER:
      return "Business Layer";
    case PRESENTATION_LAYER:
      return "Presentation Layer";
    default:
      return null;
    }
  }
}
