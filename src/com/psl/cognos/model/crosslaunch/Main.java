package com.psl.cognos.model.crosslaunch;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.psl.cognos.model.crosslaunch.component.AlarmStore;
import com.psl.cognos.model.crosslaunch.component.ModelNode;
import com.psl.cognos.model.crosslaunch.component.ModelValue;
import com.psl.cognos.model.crosslaunch.component.Property;
import com.psl.cognos.model.crosslaunch.model.AlarmThreshold;
import com.psl.cognos.model.crosslaunch.model.BusinessLayer;
import com.psl.cognos.model.crosslaunch.model.BusinessLayerRow;
import com.psl.cognos.model.crosslaunch.model.PresentationLayer;

public class Main {

  private static Logger LOGGER = Logger.getLogger(Main.class.getName());

  public static void main(String args[]) throws Exception {

    // GLOBAL MEMBER
    BusinessLayer businessLayer = null;
    PresentationLayer presentationLayer = null;
    AlarmThreshold alarmThreshold = null;

    // PARSE COGNOS MODEL LAYERS
    String modelFilePath = System.getProperty(Property.COGNOS_MODEL_FILE
        .getName());
    File modelFile = new File(modelFilePath);

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(modelFile);
    doc.getDocumentElement().normalize();

    NodeList nameSpaceList = doc.getElementsByTagName(ModelNode.NAMESPACE
        .getName());

    // LOGGER.info(Integer.toString(nameSpaceList.getLength()));

    for (int pointer = 0; pointer < nameSpaceList.getLength(); pointer++) {
      Node node = nameSpaceList.item(pointer);

      Element element = (Element) node;
      NodeList nodeList = element
          .getElementsByTagName(ModelNode.NAME.getName());
      Node node0 = nodeList.item(0);

      if (node0.getTextContent().equals(ModelValue.BUSINESS_LAYER.getName())) {
        LOGGER.info("About to run Business Layer.");
        businessLayer = new BusinessLayer(node);
        businessLayer.run();
      }

      if (node0.getTextContent()
          .equals(ModelValue.PRESENTATION_LAYER.getName())) {
        LOGGER.info("About to run Presentation Layer.");
        presentationLayer = new PresentationLayer(node);
        presentationLayer.enable();
        presentationLayer.run();
      }
    }

    // PARSE ALARM THRESHOLD
    LOGGER.info("About to parse Alarm Threshold");
    String alarmFilePath = System.getProperty(Property.ALARM_MODEL_FILE
        .getName());
    alarmThreshold = new AlarmThreshold(alarmFilePath);
    alarmThreshold.run();

    AlarmStore alarmStore = alarmThreshold.getAlarmStore();
    // alarmStore.dump();

    // LOOKUP FOR AlARM NAME
    int numOfAlarmsFound = 0;
    int numOfAlarmsNotFound = 0;
    ArrayList<BusinessLayerRow> BUSINESS_ROWS = businessLayer
        .getBusinessLayerRows();
    for (int q = 0; q < BUSINESS_ROWS.size(); q++) {
      BusinessLayerRow BUSINESS_ROW = BUSINESS_ROWS.get(q);
      if (alarmStore.has(BUSINESS_ROW.fqnName)) {
        numOfAlarmsFound += 1;
      } else {
        LOGGER.finest(String.format("No Alarm Name found for KPI '%s'.",
            BUSINESS_ROW.fqnName));
        numOfAlarmsNotFound += 1;
      }
    }

    LOGGER.info(String.format(
        "There are %d Alarms found and %d Alarms not found.", numOfAlarmsFound,
        numOfAlarmsNotFound));
  }
}
