package com.psl.cognos.model.crosslaunch.model;

public enum BusinessLayerGroup {
  HUAWEI_KPIS, ERICSSON_KPIS, ALU_KPIS;

  public String getName() {
    switch (this) {
    case HUAWEI_KPIS:
      return "Huawei KPIs";
    case ERICSSON_KPIS:
      return "Ericsson KPIs";
    case ALU_KPIS:
      return "ALU KPIs";
    default:
      return null;
    }
  }
}
