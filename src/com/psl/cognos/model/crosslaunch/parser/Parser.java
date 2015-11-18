package com.psl.cognos.model.crosslaunch.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.psl.cognos.model.crosslaunch.component.Cube;

public class Parser {

  private static Logger LOGGER = Logger.getLogger(Parser.class.getName());
      
  public static String getFileName(String prefix) {
    String fileName = prefix;
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
    Date date = new Date();
    fileName += dateFormat.format(date) + ".csv";
    return fileName;
  }

  public static void parseAsFqn(ArrayList<String>... x) {
    // do nothing
  }

  public static ArrayList<Cube> parseModel(Object node, String xPathStr)
      throws Exception {
    return parseModel(node, xPathStr, false);
  }

  public static ArrayList<Cube> parseModel(Object node, String xPathStr,
      boolean print) throws Exception {
    ArrayList<Cube> x = new ArrayList<Cube>();
    // Using XPath to get XML chunks
    XPath xpath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xpath.compile(xPathStr);
    // Object result = expr.evaluate(doc, XPathConstants.NODESET);
    Object result = expr.evaluate(node, XPathConstants.NODESET);
    NodeList nodes = (NodeList) result;

    for (int i = 0; i < nodes.getLength(); i++) {
      Node node1 = nodes.item(i);
      if (print)
        LOGGER.info(String.format("%s: %s", node1.getNodeName(),
            node1.getNodeValue()));
      x.add(new Cube(node1.getNodeName(), node1.getNodeValue()));
    }

    return x;
  }
  
  public static void writeToFile(String fileName, ArrayList<String> content) {
    File file = new File(fileName);
    FileWriter fw;
    BufferedWriter bw;
    try {
      file.createNewFile();
      fw = new FileWriter(file.getAbsoluteFile());
      bw = new BufferedWriter(fw);
      for (int i = 0; i < content.size(); i++) {
        bw.write(content.get(i) + "\n");
      }
      bw.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
