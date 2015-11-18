package com.psl.cognos.model.crosslaunch.component;

public class DomainKnowledge {
  public enum Technology {
    TWO_G, THREE_G, FOUR_G;

    public static Technology getTechnology(String str) {
      if (str.matches(".*2G.*")) {
        return TWO_G;
      } else if (str.matches(".*3G.*")) {
        return THREE_G;
      } else if (str.matches(".*4G.*")) {
        return FOUR_G;
      } else {
        return null;
      }
    }

    // Entity Name aka CellID / EUTRANCELLID
    public String getEntityIdentifier() {
      switch (this) {
      case TWO_G:
        return "CELL ID";
      case THREE_G:
        return "CELL ID";
      case FOUR_G:
        return "EutranCell ID";
      default:
        return null;
      }
    }

    public String getPresentationSegment() {
      switch (this) {
      case TWO_G:
        return "CELL 2G";
      case THREE_G:
        return "CELL 3G";
      case FOUR_G:
        return "CELL 4G";
      default:
        return null;
      }
    }
  }
}
