package com.psl.cognos.model.crosslaunch.model;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.psl.cognos.model.crosslaunch.component.AlarmKnowledge;
import com.psl.cognos.model.crosslaunch.component.AlarmStore;
import com.psl.cognos.model.crosslaunch.meta.AlarmModel;
import com.psl.cognos.model.crosslaunch.meta.AlarmThresholdRow;
import com.psl.cognos.model.crosslaunch.meta.Alarmlet;
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