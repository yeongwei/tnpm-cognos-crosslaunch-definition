package com.psl.cognos.model.crosslaunch.component;

/*
 * Consolidate information from both Business Layer and presentation Layer
 */
public class CrosslaunchDefinition {
  /*
   * prefix b for Business Layer prefix p for presentation Layer
   */
  private String buKpiName;
  private String buFqnPath;
  private CounterReferences buCounterReferences;
  private String buFqnEntityIdentifier;
  private String buFqnHourKey;
  private String prFqnPath;
  private String alarmName;

  // public CrosslaunchDefinition(String bKpiName, String bFqnPath,
  // CounterReferences bCounterReferences, String bFqnEntityIdentifier,
  // String bFqnHourKey, String pFqnPath, String alarmName) {
  // this.bKpiName = bKpiName;
  // this.bFqnPath = bFqnPath;
  // this.bCounterReferences = bCounterReferences;
  // this.bFqnEntityIdentifier = bFqnEntityIdentifier;
  // this.bFqnHourKey = bFqnHourKey;
  // this.pFqnPath = pFqnPath;
  // this.alarmName = alarmName;
  // }
  
  public void setBuKpiName(String buKpiName) {
    this.buKpiName = buKpiName;
  }
  
  public String getBuKpiName() {
    return this.buKpiName;
  }
  
  public void setBuFqnPath(String buFqnPath) {
    this.buFqnPath = buFqnPath;
  }
  
  public String getBuFqnPath() {
    return this.buFqnPath;
  }
  
  public void setBuCounterReferences(CounterReferences buCounterReferences) {
    this.buCounterReferences = buCounterReferences;
  }
  
  public CounterReferences getBuCounterReferences() {
    return this.buCounterReferences;
  }
  
  public void setBuHourKey(String buFqnHourKey) {
    this.buFqnHourKey = buFqnHourKey;
  }
  
  public String getbuFqnHourKey() {
    return this.buFqnHourKey;
  } 
  
  public void setBuPrFqnPath(String buFqnPath) {
    this.buFqnPath = buFqnPath;
  }
  
  public String getbuFqnPath() {
    return this.buFqnPath;
  } 
  
  public void setAlarmName(String alarmName) {
    this.alarmName = alarmName;
  }
  
  public String getAlarmName() {
    return this.alarmName;
  }
}
