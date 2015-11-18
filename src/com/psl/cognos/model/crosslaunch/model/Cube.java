package com.psl.cognos.model.crosslaunch.model;

public class Cube {
  final String name;
  final String value;

  public Cube(String name, String value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("%s|_|$s", name, value);

  }
}