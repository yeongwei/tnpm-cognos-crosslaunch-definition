package com.psl.cognos.model.crosslaunch.component;

public enum Property {
  MODEL_FILE;

  public String getName() {
    switch (this) {
    case MODEL_FILE:
      return "modelFile";
    default:
      return null;
    }
  }
}
