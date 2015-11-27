package com.psl.cognos.model.crosslaunch.writer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.psl.cognos.model.crosslaunch.component.CrosslaunchDefinition;

/*
 * https://gist.github.com/madan712/3912272
 */
public class ExcelWriter extends CrossLaunchDefinitionWriter {

  private MatrixTable matrixTable;
  private XSSFWorkbook WORKBOOK = new XSSFWorkbook();
  private String sheetName = "Sheet1";

  public ExcelWriter() {
    this(100, 100);
  }

  public ExcelWriter(int rows, int columns) {
    this.matrixTable = new MatrixTable(rows, columns);
  }

  @Override
  public void write() throws Exception {
    XSSFSheet SHEET = WORKBOOK.createSheet(this.sheetName);
    int rows = this.matrixTable.getNumOfRows();
    int columns = this.matrixTable.getNumOfColumns();

    for (int row = 0; row < rows; row++) {
      XSSFRow ROW = SHEET.createRow(row);
      for (int column = 0; column < columns; column++) {
        XSSFCell CELL = ROW.createCell(column);
        CELL.setCellValue(matrixTable.getValue(row, column));
      }
    }

    FileOutputStream excelFileOut = new FileOutputStream(this.getFileName());
    WORKBOOK.write(excelFileOut);
    excelFileOut.flush();
    excelFileOut.close();

    LOGGER
        .info(String.format("Finished writting into %s.", this.getFileName()));
  }

  @Override
  public void process(ArrayList<CrosslaunchDefinition> crosslaunchDefinitions) {
    for (int i = 0; i < crosslaunchDefinitions.size(); i++) {
      CrosslaunchDefinition crosslaunchDefinition = crosslaunchDefinitions
          .get(i);
      /*
       * // ALARM NAME
       * ROW.append(crosslaunchDefinition.getAlarmName()).append(this
       * .getDelimiter()); // KPI REFERENCE - member([Ericsson].[Ericsson CS 2G
       * CELLACUS Hourly].[TCH // Availability],'','TCH Availability')
       * ROW.append
       * (makeKpiReference(crosslaunchDefinition)).append(this.getDelimiter());
       * // ENTITY REFERENCE
       * ROW.append(crosslaunchDefinition.getBuFqnEntityIdentifier
       * ()).append(this.getDelimiter()); // HOUR KEY
       * ROW.append(crosslaunchDefinition
       * .getBuFqnHourKey()).append(this.getDelimiter()); // COUNTER REFERENCE
       * ROW.append(makeCounterReferences(crosslaunchDefinition));
       */

      matrixTable.setValue(i, 0, crosslaunchDefinition.getAlarmName());
      matrixTable.setValue(i, 1,
          sanitize(makeKpiReference(crosslaunchDefinition)));
      matrixTable.setValue(i, 2,
          crosslaunchDefinition.getBuFqnEntityIdentifier());
      matrixTable.setValue(i, 3, crosslaunchDefinition.getBuFqnHourKey());
      matrixTable.setValue(i, 4,
          sanitize(makeCounterReferences(crosslaunchDefinition)));
    }
  }
}

class MatrixTable {

  private Logger LOGGER = Logger.getLogger(MatrixTable.class.getName());
  private int numOfRows;
  private int numOfColumns;
  private String[][] DATA;

  public MatrixTable() {
    this(1000, 1000);
    LOGGER.info("Initialized MatrixTable with default values");
  }

  public MatrixTable(int numOfRows, int numOfColumns) {
    this.numOfRows = numOfRows;
    this.numOfColumns = numOfColumns;

    DATA = new String[numOfRows][numOfColumns];
    // Arrays.fill(DATA, null);
  }

  public void setValue(int row, int column, String value) {
    DATA[row][column] = value;
  }

  public void unsetValue(int row, int column) {
    DATA[row][column] = null;
  }

  public String getValue(int row, int column) {
    return DATA[row][column];
  }

  public int getNumOfRows() {
    return this.numOfRows;
  }

  public int getNumOfColumns() {
    return this.numOfColumns;
  }
}
