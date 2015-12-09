package com.psl.cognos.model.crosslaunch.meta;

public class Alarmlet {
  private final String vendorName;
  private final String kpiName;
  private final String kpiNameInModel;
  private final String alarmName;

  public Alarmlet(String vendorName, String kpiName, String kpiNameInModel,
      String alarmName) {
    this.vendorName = vendorName;
    this.kpiName = kpiName;
    this.kpiNameInModel = kpiNameInModel;
    this.alarmName = alarmName;
  }

  public String getVendorName() {
    return this.vendorName;
  }

  public String getKpiName() {
    return this.kpiName;
  }

  public String getKpiNameInModel() {
    return this.kpiNameInModel;
  }

  public String getAlarmName() {
    return this.alarmName;
  }

  public String getUniqueKey() {
    StringBuffer s = new StringBuffer();
    s.append(this.vendorName).append("-").append(this.kpiNameInModel);
    return s.toString();
  }
}
