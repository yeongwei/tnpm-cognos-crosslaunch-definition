package com.psl.cognos.model.crosslaunch.component;

public enum AlarmModel {
  HUAWEI_HOURLY_ALARM, HUAWEI_DASHBOARD_HOURLY_ALARM;

  public int getSheetIndex() {
    switch (this) {
    case HUAWEI_HOURLY_ALARM:
      return 1;
    case HUAWEI_DASHBOARD_HOURLY_ALARM:
      return 2;
    default:
      return -1;
    }
  }

  public int getKpiNameColumnIndex() {
    switch (this) {
    case HUAWEI_HOURLY_ALARM:
    case HUAWEI_DASHBOARD_HOURLY_ALARM:
      return 2;
    default:
      return -1;
    }
  }

  public int getKpiNameInModelColumnIndex() {
    switch (this) {
    case HUAWEI_HOURLY_ALARM:
    case HUAWEI_DASHBOARD_HOURLY_ALARM:
      return 3;
    default:
      return -1;
    }
  }

  public int getAlarmNameColumnIndex() {
    switch (this) {
    case HUAWEI_HOURLY_ALARM:
      return 7;
    case HUAWEI_DASHBOARD_HOURLY_ALARM:
      return 9;
    default:
      return -1;
    }
  }
}
