package com.psl.cognos.model.crosslaunch.model;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.psl.cognos.model.crosslaunch.component.AlarmKnowledge;
import com.psl.cognos.model.crosslaunch.component.AlarmStore;
import com.psl.cognos.model.crosslaunch.meta.AlarmModel;
import com.psl.cognos.model.crosslaunch.parser.ExcelParser;
import com.psl.cognos.model.crosslaunch.writer.Writer;

public class AlarmThreshold extends ExcelParser {

  private AlarmStore alarmStore;

  public AlarmThreshold(String modelFile) throws Exception {
    super(modelFile);
  }

  public AlarmStore getAlarmStore() {
    return this.alarmStore;
  }

  /**
   * AlarmKnowledge = KPI + kpiNameInModel + alarmName + uniqueKey Where
   * uniqueKey = vendorName + kpiNameInModel
   */
  @Deprecated
  @Override
  public void run() throws Exception {
    int numberOfEntries = 0;
    int numberOfNilEntries = 0;
    alarmStore = new AlarmStore();
    ArrayList<String> ROWS = new ArrayList<String>();

    XSSFSheet sheet = this.WORKBOOK.getSheetAt(AlarmModel.DASHBOARD_AND_REPORTS
        .getSheetIndex());

    XSSFRow row;
    int totalRows = sheet.getPhysicalNumberOfRows();

    // Skip header
    for (int j = 1; j < totalRows; j++) {
      row = sheet.getRow(j);
      AlarmThresholdRow alarmThresholdRow = new AlarmThresholdRow(row);

      if (alarmThresholdRow.hasAlarmlets()) {
        ArrayList<Alarmlet> x = alarmThresholdRow.getAlarmlets();
        for (int k = 0; k < x.size(); k++) {
          Alarmlet alarmlet = x.get(k);
          AlarmKnowledge alarmKnowledge = new AlarmKnowledge(
              alarmlet.getKpiName(), alarmlet.getKpiNameInModel(),
              alarmlet.getAlarmName(), alarmlet.getUniqueKey());
          alarmStore.add(alarmKnowledge);

          StringBuffer s = new StringBuffer();
          s.append(alarmlet.getVendorName()).append(",");
          s.append(alarmlet.getKpiName()).append(",");
          s.append(alarmlet.getKpiNameInModel()).append(",");
          s.append(alarmlet.getAlarmName()).append(",");
          s.append(alarmlet.getUniqueKey());

          ROWS.add(s.toString());

          numberOfEntries += 1;
        }
      } else {
        if (true)
          LOGGER.finest(String.format("Skipping Alarm Threhsold for %s.",
              alarmThresholdRow.toString()));
        numberOfNilEntries += 1;
      }
    }

    Writer writer = new Writer();
    writer
        .makeFileName("D:\\development\\_assignment\\TNPM-Cognos-CrossLaunch-Definition\\output\\AlarmThreshold-");
    writer.setContent(ROWS);
    writer
        .setHeader("Vendor,KpiNameFromAlarmThreshold,KpiNameInModelFromAlarmThreshold,AlarmNameFromAlarmThreshold,UniqueKeyWithinObject");
    writer.write();

    LOGGER.info(String.format(
        "Processed %d Alarm Threshold entries and %d of NIL entires.",
        numberOfEntries, numberOfNilEntries));
  }
}

/*
 * Represents each INDIVIDUAL SINGLE ALARM
 */
class Alarmlet {
  private final String vendorName;
  private final String kpiName;
  private final String kpiNameInModel;
  private final String alarmName;

  Alarmlet(String vendorName, String kpiName, String kpiNameInModel,
      String alarmName) {
    this.vendorName = vendorName;
    this.kpiName = kpiName;
    this.kpiNameInModel = kpiNameInModel;
    this.alarmName = alarmName;
  }

  String getVendorName() {
    return this.vendorName;
  }

  String getKpiName() {
    return this.kpiName;
  }

  String getKpiNameInModel() {
    return this.kpiNameInModel;
  }

  String getAlarmName() {
    return this.alarmName;
  }

  String getUniqueKey() {
    StringBuffer s = new StringBuffer();
    s.append(this.vendorName).append("-").append(this.kpiNameInModel);
    return s.toString();
  }
}

class AlarmThresholdRow {

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

  AlarmThresholdRow(XSSFRow ROW) {
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

  String getKpiName() {
    return this.kpiName;
  }

  String getKpiNameInModel() {
    return this.kpiNameInModel;
  }

  boolean hasAlarmlets() {
    if (this.alarmlets.size() > 0) {
      return true;
    } else {
      return false;
    }
  }

  ArrayList<Alarmlet> getAlarmlets() {
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
