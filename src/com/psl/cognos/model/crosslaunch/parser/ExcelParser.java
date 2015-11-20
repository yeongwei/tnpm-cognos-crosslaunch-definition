package com.psl.cognos.model.crosslaunch.parser;

import java.io.File;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {

  final private String excelFile;
  final protected Logger LOGGER = Logger.getLogger(ExcelParser.class.getName());
  final protected XSSFWorkbook WORKBOOK;

  public ExcelParser(String excelFile) throws Exception {
    this.excelFile = excelFile;
    WORKBOOK = new XSSFWorkbook(new File(excelFile));
  }

  public static void main(String args[]) throws Exception {
    ExcelParser excelParser = new ExcelParser(
        "D:/development/_assignment/CognosModel-CrossLaunch/resource/Alarm_Threshold_v0.2_20151102_lau.xlsx");
    excelParser.run();
  }

  public String getExcelFile() {
    return this.excelFile;
  }
  
  public void run() throws Exception {
    XSSFSheet sheet = WORKBOOK.getSheetAt(1);
    XSSFRow row;
    XSSFCell cell;

    int rows; // No of rows
    rows = sheet.getPhysicalNumberOfRows();

    for (int i = 0; i < rows; i++) {
      row = sheet.getRow(i);
      cell = row.getCell(3);
      LOGGER.info(cell.getStringCellValue());
    }
    
    //throw new RuntimeException("Needs implementaion.");
  }
}
