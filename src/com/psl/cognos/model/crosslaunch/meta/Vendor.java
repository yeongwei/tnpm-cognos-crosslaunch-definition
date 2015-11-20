package com.psl.cognos.model.crosslaunch.meta;

public enum Vendor {
  ALU, ERICSSON, HUAWEI;

  public String getName() {
    switch (this) {
    case ALU:
      return "ALU";
    case ERICSSON:
      return "Ericsson";
    case HUAWEI:
      return "Huawei";
    default:
      return null;
    }
  }
}