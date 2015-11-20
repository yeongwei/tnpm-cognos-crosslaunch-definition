package com.psl.cognos.model.crosslaunch.meta;

/**
 * Facilitate Cognos Model Node Value
 * Avoid re-typing
 * @author laiyw
 *
 */
public enum ModelNodeValue {
  BUSINESS_LAYER, PRESENTATION_LAYER, HOURLY_KPIS;

  public String getName() {
    switch (this) {
    case BUSINESS_LAYER:
      return "Business Layer";
    case PRESENTATION_LAYER:
      return "Presentation Layer";
    case HOURLY_KPIS:
      return "Hourly KPIs";
    default:
      return null;
    }
  }
}
