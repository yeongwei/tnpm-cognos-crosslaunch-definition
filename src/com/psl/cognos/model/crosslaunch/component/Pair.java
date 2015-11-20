package com.psl.cognos.model.crosslaunch.component;

public class Pair {
  public final String name;
  public final String value;

  public Pair(String name, String value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("%s|_|$s", name, value);

  }
}