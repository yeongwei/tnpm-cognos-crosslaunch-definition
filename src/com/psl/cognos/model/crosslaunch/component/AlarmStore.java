package com.psl.cognos.model.crosslaunch.component;

import java.util.HashMap;

public class AlarmStore {
  private HashMap<String, AlarmKnowledge> store = new HashMap<String, AlarmKnowledge>();

  public boolean add(AlarmKnowledge alarmKnowledge) {
    if (store.containsKey(alarmKnowledge.kpiNameInModel)
        && store.containsValue(alarmKnowledge)) {
      return false;
    } else {
      store.put(alarmKnowledge.kpiNameInModel, alarmKnowledge);
      return true;
    }
  }
  
  public AlarmKnowledge get(String kpiNameInModel) {
    if (store.containsKey(kpiNameInModel)) {
      return store.get(kpiNameInModel);
    } else {
      return null;
    }
  }
  
  public boolean remove(String kpiNameInModel) {
    if (store.containsKey(kpiNameInModel)) {
      store.remove(kpiNameInModel);
      return true;
    } else {
      return false;
    }
  }
  
  public boolean remove(AlarmKnowledge alarmKnowledge) {
    if (store.containsKey(alarmKnowledge.kpiNameInModel)
        && store.containsValue(alarmKnowledge)) {
      return remove(alarmKnowledge.kpiNameInModel);
    } else {
      return false;
    }
  }
}
