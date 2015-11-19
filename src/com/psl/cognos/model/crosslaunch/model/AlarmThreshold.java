package com.psl.cognos.model.crosslaunch.model;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.psl.cognos.model.crosslaunch.component.AlarmKnowledge;
import com.psl.cognos.model.crosslaunch.component.AlarmModel;
import com.psl.cognos.model.crosslaunch.component.AlarmStore;
import com.psl.cognos.model.crosslaunch.parser.ExcelParser;

public class AlarmThreshold extends ExcelParser {

  private AlarmStore alarmStore;

  public AlarmThreshold(String modelFile) throws Exception {
    super(modelFile);
  }

  public AlarmStore getAlarmStore() {
    return this.alarmStore;
  }

  @Override
  public void run() throws Exception {
    int numberOfEntries = 0;
    int numberOfNilEntries = 0;
    alarmStore = new AlarmStore();
    AlarmModel ALARM_MODELS[] = AlarmModel.values();

    for (int i = 0; i < ALARM_MODELS.length; i++) {
      AlarmModel AlARM_MODEL = ALARM_MODELS[i];
      XSSFSheet sheet = this.WORKBOOK.getSheetAt(AlARM_MODEL.getSheetIndex());

      XSSFRow row;
      XSSFCell kpiName;
      XSSFCell kpiNameInModel;
      XSSFCell alarmName;
      int totalRows = sheet.getPhysicalNumberOfRows();

      // Skip header
      for (int j = 1; j < totalRows; j++) {
        row = sheet.getRow(j);

        kpiName = row.getCell(AlARM_MODEL.getKpiNameColumnIndex());
        kpiNameInModel = row
            .getCell(AlARM_MODEL.getKpiNameInModelColumnIndex());
        alarmName = row.getCell(AlARM_MODEL.getAlarmNameColumnIndex());

        if (kpiName != null && kpiNameInModel != null && alarmName != null) {
          String kpiNameStr = kpiName.getStringCellValue();
          String kpiNameInModelStr = kpiNameInModel.getStringCellValue();
          String alarmNameStr = alarmName.getStringCellValue();

          // LOGGER.info(String.format("Found %s|_|%s|_|%s", kpiNameStr,
          // kpiNameInModelStr, alarmNameStr));

          AlarmKnowledge alarmKnowledge = new AlarmKnowledge(kpiNameStr,
              kpiNameInModelStr, alarmNameStr);
          alarmStore.add(alarmKnowledge);

          numberOfEntries += 1;
        } else {
          numberOfNilEntries += 1;
        }

      }
    }

    LOGGER.info(String.format("Processed %d Alarm Threshold entries and found %d NIL entires.",
        numberOfEntries, numberOfNilEntries));
  }
}
