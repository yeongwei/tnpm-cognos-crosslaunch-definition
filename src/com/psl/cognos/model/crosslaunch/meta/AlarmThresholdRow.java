package com.psl.cognos.model.crosslaunch.meta;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class AlarmThresholdRow {

  private static final int kpiNameIndex = 0; // A
  private static final int kpiNameInModelIndex = 1; // B
  private static final int huaweiHourlyCellAlarmNameIndex = 66; // BO
  private static final int aluHourlyCellAlarmNameIndex = 67; // BP
  private static final int ericssonHourlyCellAlarmNameIndex = 68; // BQ

  private static final String huaweiName = "Huawei";
  private static final String aluName = "ALU";
  private static final String ericssonName = "Ericsson";

  private String kpiName;
  private String kpiNameInModel;
  private ArrayList<String> alarmNames = new ArrayList<String>();

  private ArrayList<Alarmlet> alarmlets = new ArrayList<Alarmlet>();

  public AlarmThresholdRow(XSSFRow ROW) {
    XSSFCell CELL = null;

    CELL = ROW.getCell(this.kpiNameIndex);
    if (CELL != null) {
      this.kpiName = CELL.getStringCellValue().trim();
    }

    CELL = ROW.getCell(this.kpiNameInModelIndex);
    if (CELL != null) {
      this.kpiNameInModel = CELL.getStringCellValue().trim();
    }

    // huawei
    CELL = ROW.getCell(this.huaweiHourlyCellAlarmNameIndex);
    if (CELL != null) {
      String alarmName = CELL.getStringCellValue().trim();
      if (!alarmName.isEmpty()) {
        alarmNames.add(alarmName);
        Alarmlet x = new Alarmlet(huaweiName, this.kpiName,
            this.kpiNameInModel, alarmName);
        alarmlets.add(x);
      }
    }

    // alu
    CELL = ROW.getCell(this.aluHourlyCellAlarmNameIndex);
    if (CELL != null) {
      String alarmName = CELL.getStringCellValue().trim();
      if (!alarmName.isEmpty()) {
        alarmNames.add(alarmName);
        Alarmlet x = new Alarmlet(aluName, this.kpiName, this.kpiNameInModel,
            alarmName);
        alarmlets.add(x);
      }
    }

    // ericsson
    CELL = ROW.getCell(this.ericssonHourlyCellAlarmNameIndex);
    if (CELL != null) {
      String alarmName = CELL.getStringCellValue().trim();
      if (!alarmName.isEmpty()) {
        alarmNames.add(alarmName);
        Alarmlet x = new Alarmlet(ericssonName, this.kpiName,
            this.kpiNameInModel, alarmName);
        alarmlets.add(x);
      }
    }
  }

  public String getKpiName() {
    return this.kpiName;
  }

  public String getKpiNameInModel() {
    return this.kpiNameInModel;
  }

  public boolean hasAlarmlets() {
    if (this.alarmlets.size() > 0) {
      return true;
    } else {
      return false;
    }
  }

  public ArrayList<Alarmlet> getAlarmlets() {
    return this.alarmlets;
  }

  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append(this.kpiName).append("|_|");
    s.append(this.kpiNameInModel).append("|_|");
    for (int i = 0; i < this.alarmNames.size(); i++) {
      if (i == this.alarmNames.size() - 1) {
        s.append(this.alarmNames.get(i));
      } else {
        s.append(this.alarmNames.get(i)).append(",");
      }
    }
    return s.toString();
  }
}
