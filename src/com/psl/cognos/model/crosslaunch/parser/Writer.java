package com.psl.cognos.model.crosslaunch.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class Writer {
  private String fileName;
  private String header;
  private String ext = "csv";
  private ArrayList<String> content;
  private String delimiter = ",";
  private final Logger LOGGER = Logger.getLogger(Writer.class.getName());

  public void write() throws Exception {
    File file = new File(this.fileName);
    FileWriter fw;
    BufferedWriter bw;
    try {
      file.createNewFile();
      fw = new FileWriter(file.getAbsoluteFile());
      bw = new BufferedWriter(fw);
      if (header != null && !header.isEmpty()) {
        bw.write(header + "\n");
      }
      for (int i = 0; i < this.content.size(); i++) {
        bw.write(this.content.get(i) + "\n");
      }
      bw.close();
      LOGGER.info(String.format("Finished writting into %d rows into %s.",
          this.content.size(), fileName));
    } catch (Exception ex) {
      throw ex;
    }
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileName() {
    return this.fileName;
  }
  
  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }
  
  public String getDelimiter() {
    return this.delimiter;
  } 
  
  public void makeFileName(String prefix) {
    String fileName = prefix;
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
    Date date = new Date();
    fileName += dateFormat.format(date) + "." + this.ext;
    setFileName(fileName);
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getHeader() {
    return this.header;
  }

  public void setContent(ArrayList<String> content) {
    this.content = content;
  }

  public ArrayList<String> getContent() {
    return this.content;
  }
}
