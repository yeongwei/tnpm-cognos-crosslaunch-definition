package com.psl.cognos.model.crosslaunch.component;

public class AlarmKnowledge {
  public final String kpiName;
  public final String kpiNameInModel;
  public final String alarmName;
  public final String uniqueKey;
  
  public AlarmKnowledge(String kpiName, String kpiNameInModel, String alarmName, String uniqueKey) {
    this.kpiName = kpiName;
    this.kpiNameInModel = kpiNameInModel;
    this.alarmName = alarmName;
    this.uniqueKey = uniqueKey;
  }
  
  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append(this.kpiName).append("|_|");
    s.append(this.kpiNameInModel).append("|_|");
    s.append(this.alarmName).append("|_|");
    s.append(this.uniqueKey);
    
    return s.toString();
  }
}
