package com.psl.cognos.model.crosslaunch.meta;

public enum Property {
  COGNOS_MODEL_FILE, ALARM_MODEL_FILE;

  public String getName() {
    switch (this) {
    case COGNOS_MODEL_FILE:
      return "cognosModelFile";
    case ALARM_MODEL_FILE:
      return "alarmModelFile";
    default:
      return null;
    }
  }
}
