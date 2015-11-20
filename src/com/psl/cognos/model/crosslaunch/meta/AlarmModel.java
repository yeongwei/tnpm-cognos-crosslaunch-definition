package com.psl.cognos.model.crosslaunch.meta;


public enum AlarmModel {
  HUAWEI_HOURLY_ALARM, HUAWEI_DASHBOARD_HOURLY_ALARM, ALU_HOURLY_ALARM, ALU_DASHBOARD_HOURLY_ALARM, ERICSSON_HOURLY_ALARM, ERICSSON_DASHBOARD_HOURLY_ALARM;

  public int getSheetIndex() {
    switch (this) {
    case HUAWEI_HOURLY_ALARM:
      return 1;
    case HUAWEI_DASHBOARD_HOURLY_ALARM:
      return 2;
    case ALU_HOURLY_ALARM:
      return 3;
    case ALU_DASHBOARD_HOURLY_ALARM:
      return 4;
    case ERICSSON_HOURLY_ALARM:
      return 5;
    case ERICSSON_DASHBOARD_HOURLY_ALARM:
      return 6;
    default:
      return -1;
    }
  }

  public int getKpiNameColumnIndex() {
    switch (this) {
    case HUAWEI_HOURLY_ALARM:
    case HUAWEI_DASHBOARD_HOURLY_ALARM:
    case ALU_HOURLY_ALARM:
    case ALU_DASHBOARD_HOURLY_ALARM:
    case ERICSSON_HOURLY_ALARM:
    case ERICSSON_DASHBOARD_HOURLY_ALARM:
      return 2;
    default:
      return -1;
    }
  }

  public int getKpiNameInModelColumnIndex() {
    switch (this) {
    case HUAWEI_HOURLY_ALARM:
    case HUAWEI_DASHBOARD_HOURLY_ALARM:
    case ALU_HOURLY_ALARM:
    case ALU_DASHBOARD_HOURLY_ALARM:
    case ERICSSON_HOURLY_ALARM:
    case ERICSSON_DASHBOARD_HOURLY_ALARM:
      return 3;
    default:
      return -1;
    }
  }

  public int getAlarmNameColumnIndex() {
    switch (this) {
    case HUAWEI_HOURLY_ALARM:
    case ALU_HOURLY_ALARM:
    case ERICSSON_HOURLY_ALARM:
      return 7;
    case HUAWEI_DASHBOARD_HOURLY_ALARM:
    case ALU_DASHBOARD_HOURLY_ALARM:
    case ERICSSON_DASHBOARD_HOURLY_ALARM:
      return 9;
    default:
      return -1;
    }
  }

  public String getVendorName() {
    switch (this) {
    case HUAWEI_HOURLY_ALARM:
    case HUAWEI_DASHBOARD_HOURLY_ALARM:
      return BusinessLayerGroup.HUAWEI_KPIS.getVendorName();
    case ALU_HOURLY_ALARM:
    case ALU_DASHBOARD_HOURLY_ALARM:
      return BusinessLayerGroup.ALU_KPIS.getVendorName();
    case ERICSSON_HOURLY_ALARM:
    case ERICSSON_DASHBOARD_HOURLY_ALARM:
      return BusinessLayerGroup.ERICSSON_KPIS.getVendorName();
    default:
      return null;
    }
  }
}
