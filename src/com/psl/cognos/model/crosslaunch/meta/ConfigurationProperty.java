package com.psl.cognos.model.crosslaunch.meta;

/**
 * Facilitate information on -D* JVM arguments
 * 
 * @author laiyw
 * 
 */
public enum ConfigurationProperty {
  COGNOS_MODEL_FILE, ALARM_MODEL_FILE, COUNTER_FRIENDLY_NAME_FILE;

  public String getName() {
    switch (this) {
    case COGNOS_MODEL_FILE:
      return "cognosModelFile";
    case ALARM_MODEL_FILE:
      return "alarmModelFile";
    case COUNTER_FRIENDLY_NAME_FILE:
      return "counterFriendlyNameFile";
    default:
      return null;
    }
  }
}
