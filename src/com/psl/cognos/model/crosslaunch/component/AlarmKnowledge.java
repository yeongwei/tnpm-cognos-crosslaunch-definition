package com.psl.cognos.model.crosslaunch.component;

public class AlarmKnowledge {
  public final String kpiName;
  public final String kpiNameInModel;
  public final String alarmName;
  
  public AlarmKnowledge(String kpiName, String kpiNameInModel, String alarmName) {
    this.kpiName = kpiName;
    this.kpiNameInModel = kpiNameInModel;
    this.alarmName = alarmName;
  }
}
