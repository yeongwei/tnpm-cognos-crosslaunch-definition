package com.psl.cognos.model.crosslaunch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.psl.cognos.model.crosslaunch.component.AlarmKnowledge;
import com.psl.cognos.model.crosslaunch.component.AlarmStore;
import com.psl.cognos.model.crosslaunch.component.CounterReference;
import com.psl.cognos.model.crosslaunch.component.CounterReferences;
import com.psl.cognos.model.crosslaunch.component.CrosslaunchDefinition;
import com.psl.cognos.model.crosslaunch.meta.ModelNode;
import com.psl.cognos.model.crosslaunch.meta.ModelValue;
import com.psl.cognos.model.crosslaunch.meta.Property;
import com.psl.cognos.model.crosslaunch.model.AlarmThreshold;
import com.psl.cognos.model.crosslaunch.model.BusinessLayer;
import com.psl.cognos.model.crosslaunch.model.BusinessLayerRow;
import com.psl.cognos.model.crosslaunch.model.PresentationLayer;
import com.psl.cognos.model.crosslaunch.writer.CrossLaunchDefinitionWriter;
import com.psl.cognos.model.crosslaunch.writer.TestWriter;

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

    // LOOKUP STORES
    AlarmStore alarmStore = alarmThreshold.getAlarmStore();
    // alarmStore.dump();

    HashMap<String, String> presentationStore = presentationLayer.asHashMap();
    // for (Object key : presentationStore.keySet()) {
    // System.out.println(key);
    // }

    // STATS
    int numOfAlarmsFound = 0;
    int numOfAlarmsNotFound = 0;
    int numOfPrFqnFound = 0;
    int numOfPrFqnNotFound = 0;

    ArrayList<CrosslaunchDefinition> CROSSLAUNCH_DEFINITIONS = new ArrayList<CrosslaunchDefinition>();

    ArrayList<BusinessLayerRow> BUSINESS_ROWS = businessLayer
        .getBusinessLayerRows();
    for (int q = 0; q < BUSINESS_ROWS.size(); q++) {
      CrosslaunchDefinition CROSSLAUNCH_DEFINITION = new CrosslaunchDefinition();
      BusinessLayerRow BUSINESS_ROW = BUSINESS_ROWS.get(q);

      // LOOKUP FOR AlARM NAME
      if (alarmStore.has(BUSINESS_ROW.uniqueKey)) {
        AlarmKnowledge alarmKnowledge = alarmStore.get(BUSINESS_ROW.uniqueKey);
        CROSSLAUNCH_DEFINITION.setAlarmName(alarmKnowledge.alarmName);
        numOfAlarmsFound += 1;
      } else {
        // LOGGER.finest(String.format("Skipping for KPI '%s'.",
        // BUSINESS_ROW.fqnName));
        numOfAlarmsNotFound += 1;
        continue;
      }

      // LOOK UP FOR PRESENTATION FQN
      // LOGGER.finest(String.format(
      // "About to lookup Presentation Path for '%s'.", BUSINESS_ROW.fqnPath));
      if (presentationStore.containsKey(BUSINESS_ROW.fqnPath)) {
        String presentationPath = presentationStore.get(BUSINESS_ROW.fqnPath);
        CROSSLAUNCH_DEFINITION.setBuPrFqnPath(presentationPath);
        numOfPrFqnFound += 1;
      } else {
        CROSSLAUNCH_DEFINITION.setBuPrFqnPath(BUSINESS_ROW.fqnPath);
        numOfPrFqnNotFound += 1;
      }

      // LOOK UP FOR COUNTER
      CounterReferences counterReferences = BUSINESS_ROW.counterReferences;
      ArrayList<CounterReference> counterReferenceStore = counterReferences
          .getStore();
      ArrayList<CounterReference> counterReferenceStoreNew = new ArrayList<CounterReference>();
      for (int r = 0; r < counterReferenceStore.size(); r++) {
        CounterReference counterReference = counterReferenceStore.get(r);
        if (presentationStore.containsKey(counterReference.fqnPath)) {
          counterReference.setPresentationFqnPath(presentationStore
              .get(counterReference.fqnPath));
        }
        counterReferenceStoreNew.add(counterReference);
      }
      CROSSLAUNCH_DEFINITION.setBuCounterReferences(counterReferences);

      // SET REMAINING MEMBERS
      CROSSLAUNCH_DEFINITION.setBuKpiName(BUSINESS_ROW.fqnName);
      CROSSLAUNCH_DEFINITION.setBuFqnPath(BUSINESS_ROW.fqnPath);
      CROSSLAUNCH_DEFINITION.setBuHourKey(BUSINESS_ROW.fqnHourKey);
      CROSSLAUNCH_DEFINITION
          .setBuFqnEntityIdentifier(BUSINESS_ROW.fqnEntityIdentifier);

      CROSSLAUNCH_DEFINITIONS.add(CROSSLAUNCH_DEFINITION);
    }

    for (int d = 0; d < CROSSLAUNCH_DEFINITIONS.size(); d++) {
      // LOGGER.finest(CROSSLAUNCH_DEFINITIONS.get(d).toString());
    }

    LOGGER.info(String.format(
        "There are %d found and %d not found for Alarms.", numOfAlarmsFound,
        numOfAlarmsNotFound));

    LOGGER.info(String.format(
        "There are %d found and %d not found for Presentation FQN Path.",
        numOfPrFqnFound, numOfPrFqnNotFound));

    LOGGER.info("About to write Cross Launch Definition file.");
    CrossLaunchDefinitionWriter crossLaunchDefinitionWriter = new CrossLaunchDefinitionWriter();
    crossLaunchDefinitionWriter
        .setMode(CrossLaunchDefinitionWriter.Mode.PRODUCTION);
    crossLaunchDefinitionWriter.setDelimiter("|");
    crossLaunchDefinitionWriter
        .makeFileName("D:/development/_assignment/CognosModel-CrossLaunch/output/CrossLaunchDefinition-");
    crossLaunchDefinitionWriter.process(CROSSLAUNCH_DEFINITIONS);
    crossLaunchDefinitionWriter.write();

    LOGGER.info("About to write Test file.");
    TestWriter testWriter = new TestWriter();
    testWriter
        .makeFileName("D:/development/_assignment/CognosModel-CrossLaunch/output/Test-");
    testWriter.process(CROSSLAUNCH_DEFINITIONS);
    testWriter.write();
  }
}
