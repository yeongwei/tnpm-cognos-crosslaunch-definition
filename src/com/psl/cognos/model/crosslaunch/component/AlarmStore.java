package com.psl.cognos.model.crosslaunch.component;

import java.util.HashMap;
import java.util.logging.Logger;

public class AlarmStore {
  private final Logger LOGGER = Logger.getLogger(AlarmStore.class.getName());
  private HashMap<String, AlarmKnowledge> store = new HashMap<String, AlarmKnowledge>();

  public boolean add(AlarmKnowledge alarmKnowledge) {
    if (store.containsKey(alarmKnowledge.uniqueKey)
        && store.containsValue(alarmKnowledge)) {
      return false;
    } else {
      store.put(alarmKnowledge.uniqueKey, alarmKnowledge);
      return true;
    }
  }

  public AlarmKnowledge get(String uniqueKey) {
    if (store.containsKey(uniqueKey)) {
      return store.get(uniqueKey);
    } else {
      // LOGGER.finest("KPI Name In Model is required here.");
      return null;
    }
  }

  public boolean has(String uniqueKey) {
    if (store.containsKey(uniqueKey))  {
      return true; 
    } else {
      // LOGGER.finest("KPI Name In Model is required here.");
      return false;
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

  public void dump() {
    LOGGER.fine("About to perform store dump.");
    for (Object key : store.keySet()) {
      System.out.println(store.get(key).toString());
    }
  }
}
