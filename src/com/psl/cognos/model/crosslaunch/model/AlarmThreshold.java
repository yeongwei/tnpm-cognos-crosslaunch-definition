package com.psl.cognos.model.crosslaunch.model;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.psl.cognos.model.crosslaunch.component.AlarmModel;
import com.psl.cognos.model.crosslaunch.parser.ExcelParser;

public class AlarmThreshold extends ExcelParser {
  public AlarmThreshold(String modelFile) throws Exception {
    super(modelFile);
  }
  
  @Override
  public void run() throws Exception {
    AlarmModel ALARM_MODELS[] = AlarmModel.values();
    
    for (int i = 0; i < ALARM_MODELS.length; i++) {
      AlarmModel AlARM_MODEL = ALARM_MODELS[i];
      XSSFSheet sheet = this.WORKBOOK.getSheetAt(AlARM_MODEL.getSheetIndex());
      
      XSSFRow row;
      XSSFCell kpiName;
      XSSFCell kpiNameInModel;
      XSSFCell alarmName;
      int totalRows = sheet.getPhysicalNumberOfRows();
      
      for (int j = 0; j < totalRows; j++) {
        row = sheet.getRow(j);
        
        kpiName = row.getCell(AlARM_MODEL.getKpiNameColumnIndex());
        kpiNameInModel = row.getCell(AlARM_MODEL.getKpiNameInModelColumnIndex());
        alarmName = row.getCell(AlARM_MODEL.getAlarmNameColumnIndex());
        
        System.out.println(kpiName + "|_|" + kpiNameInModel + "|_|" + alarmName);
      }
    }
    
  }
}
