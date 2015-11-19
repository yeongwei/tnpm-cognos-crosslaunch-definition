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
  
  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append("ALARM NAME|_|").append(this.buKpiName).append("|_|");
    s.append("KPI NAME|_|").append(this.buKpiName).append("|_|");
    s.append("BUSINESS PATH|_|").append(this.buFqnPath).append("|_|");
    s.append("PRESENTATION PATH|_|").append(this.prFqnPath).append("|_|");
    s.append("ENTITY NAME|_|").append(this.buFqnEntityIdentifier).append("|_|");
    s.append("HOUR KEY|_|").append(this.buFqnHourKey).append("|_|");
    s.append("COUNTERS|_|").append(this.buCounterReferences);
    return s.toString();
  }
  
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
  
  public void setBuFqnEntityIdentifier(String buFqnEntityIdentifier) {
    this.buFqnEntityIdentifier = buFqnEntityIdentifier;
  }
  
  public String getBuFqnEntityIdentifier() {
    return this.buFqnEntityIdentifier;
  } 
  
  public void setBuHourKey(String buFqnHourKey) {
    this.buFqnHourKey = buFqnHourKey;
  }
  
  public String getBuFqnHourKey() {
    return this.buFqnHourKey;
  } 
  
  public void setBuPrFqnPath(String prFqnPath) {
    this.prFqnPath = prFqnPath;
  }
  
  public String getBuPrFqnPath() {
    return this.prFqnPath;
  } 
  
  public void setAlarmName(String alarmName) {
    this.alarmName = alarmName;
  }
  
  public String getAlarmName() {
    return this.alarmName;
  }
}
