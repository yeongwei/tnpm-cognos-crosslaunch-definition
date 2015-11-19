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
  
  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append(this.kpiName).append("|_|");
    s.append(this.kpiNameInModel).append("|_|");
    s.append(this.alarmName);
    
    return s.toString();
  }
}
