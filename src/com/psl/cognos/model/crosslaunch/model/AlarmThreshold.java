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

  @Override
  public void run() throws Exception {
    int numberOfEntries = 0;
    int numberOfNilEntries = 0;
    alarmStore = new AlarmStore();
    AlarmModel ALARM_MODELS[] = AlarmModel.values();
    ArrayList<String> ROWS = new ArrayList<String>();
    
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
          kpiNameStr = kpiNameStr.trim();
          String kpiNameInModelStr = kpiNameInModel.getStringCellValue();
          kpiNameInModelStr = kpiNameInModelStr.trim();
          String alarmNameStr = alarmName.getStringCellValue();
          alarmNameStr = alarmNameStr.trim();

          if (!kpiNameStr.isEmpty() && !kpiNameInModelStr.isEmpty() && !alarmNameStr.isEmpty()) {
             LOGGER.info(String.format("Found %s|_|%s|_|%s", kpiNameStr,
             kpiNameInModelStr, alarmNameStr));
            String uniqueKey = AlARM_MODEL.getVendorName() + "-"
                + kpiNameInModelStr;

            AlarmKnowledge alarmKnowledge = new AlarmKnowledge(kpiNameStr,
                kpiNameInModelStr, alarmNameStr, uniqueKey);
            alarmStore.add(alarmKnowledge);

            StringBuffer s = new StringBuffer();
            s.append(AlARM_MODEL.getVendorName()).append(",");
            s.append(kpiNameStr).append(",");
            s.append(kpiNameInModelStr).append(",");
            s.append(alarmNameStr).append(",");
            s.append(uniqueKey);
            
            ROWS.add(s.toString());
            
            numberOfEntries += 1;
          } else {
            numberOfNilEntries += 1;
          }

        } else {
          numberOfNilEntries += 1;
        }

      }
    }

    Writer writer = new Writer();
    writer.makeFileName("D:\\development\\_assignment\\TNPM-Cognos-CrossLaunch-Definition\\output\\AlarmThreshold-");
    writer.setContent(ROWS);
    writer.setHeader("Vendor,KpiNameFromAlarmThreshold,KpiNameInModelFromAlarmThreshold,AlarmNameFromAlarmThreshold,UniqueKeyWithinObject");
    writer.write();
    
    LOGGER.info(String.format(
        "Processed %d Alarm Threshold entries and found %d NIL entires.",
        numberOfEntries, numberOfNilEntries));
  }
}
