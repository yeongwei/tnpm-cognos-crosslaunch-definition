package com.psl.cognos.model.crosslaunch.component;

public enum ModelValue {
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
