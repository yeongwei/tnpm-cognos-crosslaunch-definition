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
    XSSFCell kpiName;
    XSSFCell kpiNameInModel;
    XSSFCell alarmName;
    int totalRows = sheet.getPhysicalNumberOfRows();

    // Skip header
    for (int j = 1; j < totalRows; j++) {
      row = sheet.getRow(j);
      AlarmThresholdRow alarmThresholdRow = new AlarmThresholdRow(row);

      if (alarmThresholdRow.getStatus()) {
        AlarmKnowledge alarmKnowledge = new AlarmKnowledge(
            alarmThresholdRow.getKpiName(),
            alarmThresholdRow.getKpiNameInModel(),
            alarmThresholdRow.getAlarmName(), alarmThresholdRow.getUniqueKey());
        alarmStore.add(alarmKnowledge);

        StringBuffer s = new StringBuffer();
        s.append(alarmThresholdRow.getVendorName()).append(",");
        s.append(alarmThresholdRow.getKpiName()).append(",");
        s.append(alarmThresholdRow.getKpiNameInModel()).append(",");
        s.append(alarmThresholdRow.getAlarmName()).append(",");
        s.append(alarmThresholdRow.getUniqueKey());

        ROWS.add(s.toString());

        numberOfEntries += 1;
      } else {
        // LOGGER.finest(String.format("Skipping Alarm Threhsold for %s.",
        // alarmThresholdRow.toString()));
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
  private String huaweiHourlyCellAlarmName;
  private String aluHourlyCellAlarmName;
  private String ericssonHourlyCellAlarmName;

  private boolean status;
  private String vendorName;
  private String alarmName;

  AlarmThresholdRow(XSSFRow ROW) {
    this.kpiName = ROW.getCell(this.kpiNameIndex).getStringCellValue();
    if (this.kpiName != null) {
      this.kpiName = this.kpiName.trim();
    }

    this.kpiNameInModel = ROW.getCell(this.kpiNameInModelIndex)
        .getStringCellValue();
    if (this.kpiNameInModel != null) {
      this.kpiNameInModel = this.kpiNameInModel.trim();
    }

    this.huaweiHourlyCellAlarmName = ROW.getCell(
        this.huaweiHourlyCellAlarmNameIndex).getStringCellValue();
    if (this.huaweiHourlyCellAlarmName != null) {
      this.huaweiHourlyCellAlarmName = this.huaweiHourlyCellAlarmName.trim();
    }

    this.aluHourlyCellAlarmName = ROW.getCell(this.aluHourlyCellAlarmNameIndex)
        .getStringCellValue();
    if (this.aluHourlyCellAlarmName != null) {
      this.aluHourlyCellAlarmName.trim();
    }

    this.ericssonHourlyCellAlarmName = ROW.getCell(
        this.ericssonHourlyCellAlarmNameIndex).getStringCellValue();
    if (this.ericssonHourlyCellAlarmName != null) {
      this.ericssonHourlyCellAlarmName.trim();
    }

    init();
  }

  private void init() {
    if (!huaweiHourlyCellAlarmName.isEmpty()
        && aluHourlyCellAlarmName.isEmpty()
        && ericssonHourlyCellAlarmName.isEmpty()) {
      this.vendorName = huaweiName;
      this.alarmName = huaweiHourlyCellAlarmName;
      this.status = true;
    } else if (huaweiHourlyCellAlarmName.isEmpty()
        && !aluHourlyCellAlarmName.isEmpty()
        && ericssonHourlyCellAlarmName.isEmpty()) {
      this.vendorName = aluName;
      this.alarmName = aluHourlyCellAlarmName;
      this.status = true;
    } else if (huaweiHourlyCellAlarmName.isEmpty()
        && aluHourlyCellAlarmName.isEmpty()
        && !ericssonHourlyCellAlarmName.isEmpty()) {
      this.vendorName = ericssonName;
      this.alarmName = ericssonHourlyCellAlarmName;
      this.status = true;
    } else {
      this.status = false;
    }
  }

  boolean getStatus() {
    return this.status;
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
    return this.vendorName + "-" + this.kpiNameInModel;
  }

  String getVendorName() {
    return this.vendorName;
  }

  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append(this.vendorName).append("|_|");
    s.append(this.kpiName).append("|_|");
    s.append(this.kpiNameInModel).append("|_|");
    s.append(this.alarmName).append("|_|");
    return s.toString();
  }
}
