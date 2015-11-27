package com.psl.cognos.model.crosslaunch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.psl.cognos.model.crosslaunch.component.AlarmKnowledge;
import com.psl.cognos.model.crosslaunch.component.AlarmStore;
import com.psl.cognos.model.crosslaunch.component.CounterReference;
import com.psl.cognos.model.crosslaunch.component.CounterReferences;
import com.psl.cognos.model.crosslaunch.component.CrosslaunchDefinition;
import com.psl.cognos.model.crosslaunch.meta.ConfigurationProperty;
import com.psl.cognos.model.crosslaunch.meta.ModelNodeName;
import com.psl.cognos.model.crosslaunch.meta.ModelNodeValue;
import com.psl.cognos.model.crosslaunch.model.AlarmThreshold;
import com.psl.cognos.model.crosslaunch.model.BusinessLayer;
import com.psl.cognos.model.crosslaunch.model.BusinessLayerRow;
import com.psl.cognos.model.crosslaunch.model.PresentationLayer;
import com.psl.cognos.model.crosslaunch.parser.CognosModelParser;
import com.psl.cognos.model.crosslaunch.writer.CrossLaunchDefinitionWriter;
import com.psl.cognos.model.crosslaunch.writer.ExcelWriter;
import com.psl.cognos.model.crosslaunch.writer.TestWriter;
import com.psl.cognos.model.crosslaunch.writer.Writer;

public class Main {

  private static Logger LOGGER = Logger.getLogger(Main.class.getName());

  public static void main(String args[]) throws Exception {

    // GLOBAL MEMBER
    BusinessLayer businessLayer = null;
    PresentationLayer presentationLayer = null;
    AlarmThreshold alarmThreshold = null;

    // PARSE COGNOS MODEL LAYERS
    String modelFilePath = System
        .getProperty(ConfigurationProperty.COGNOS_MODEL_FILE.getName());
    File modelFile = new File(modelFilePath);

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(modelFile);
    doc.getDocumentElement().normalize();

    // Start from NAMESPACE
    NodeList nameSpaceList = doc.getElementsByTagName(ModelNodeName.NAMESPACE
        .getName());

    // For each NAMESPACE block look out for relevant name
    for (int pointer = 0; pointer < nameSpaceList.getLength(); pointer++) {
      Node node = nameSpaceList.item(pointer);

      String namespaceName = CognosModelParser.getNodeName(node);

      if (namespaceName.equals(ModelNodeValue.BUSINESS_LAYER.getName())) {
        LOGGER.info("About to run Business Layer.");
        businessLayer = new BusinessLayer(node);
        businessLayer.run();
      }

      if (namespaceName.equals(ModelNodeValue.PRESENTATION_LAYER.getName())) {
        LOGGER.info("About to run Presentation Layer.");
        presentationLayer = new PresentationLayer(node);
        presentationLayer.enable();
        presentationLayer.run();
      }
    }

    // PARSE ALARM THRESHOLD
    LOGGER.info("About to parse Alarm Threshold");
    String alarmFilePath = System
        .getProperty(ConfigurationProperty.ALARM_MODEL_FILE.getName());
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
    ArrayList<String> ALARM_KPI_AUDIT = new ArrayList<String>();

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
        // LOGGER.finest(String.format("Skipping for Unique Key '%s'.",
        // BUSINESS_ROW.uniqueKey));
        if (!BUSINESS_ROW.uniqueKey.contains("_Denominator")
            && !BUSINESS_ROW.uniqueKey.contains("_Numerator")
            && !BUSINESS_ROW.uniqueKey.contains("Den")
            && !BUSINESS_ROW.uniqueKey.contains("Num")) {
          String x[] = BUSINESS_ROW.uniqueKey.split("-");

          StringBuffer s = new StringBuffer();
          s.append(x[0]).append(",");
          s.append(BUSINESS_ROW.fqnPath).append(",");

          // TODO: Should this be fixed ???
          StringBuffer kpiName = new StringBuffer();
          for (int v = 1; v < x.length; v++) {
            if (v == x.length - 1) {
              kpiName.append(x[v]);
            } else {
              kpiName.append(x[v]).append("-");
            }
          }
          s.append(kpiName.toString());

          ALARM_KPI_AUDIT.add(s.toString());
        }

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
        .makeFileName("D:\\development\\_assignment\\TNPM-Cognos-CrossLaunch-Definition\\output\\CrossLaunchDefinition-");
    crossLaunchDefinitionWriter.process(CROSSLAUNCH_DEFINITIONS);
    crossLaunchDefinitionWriter.write();

    LOGGER.info("About to write Cross Launch Definition Excel file.");
    ExcelWriter excelWriter = new ExcelWriter(1000, 10);
    excelWriter.setExt("xlsx");
    excelWriter
        .makeFileName("D:\\development\\_assignment\\TNPM-Cognos-CrossLaunch-Definition\\output\\CrossLaunchDefinition-");
    excelWriter.process(CROSSLAUNCH_DEFINITIONS);
    excelWriter.write();

    LOGGER.info("About to Alarm KPI Audit file.");
    Writer writer = new Writer();
    writer
        .makeFileName("D:\\development\\_assignment\\TNPM-Cognos-CrossLaunch-Definition\\output\\AlarmKpiAudit-");
    writer.setContent(ALARM_KPI_AUDIT);
    writer.setHeader("Vendor,FqnPath,KpiName");
    writer.write();

    LOGGER.info("About to write Test file.");
    TestWriter testWriter = new TestWriter();
    testWriter
        .makeFileName("D:\\development\\_assignment\\TNPM-Cognos-CrossLaunch-Definition\\output\\Test-");
    testWriter.process(CROSSLAUNCH_DEFINITIONS);
    testWriter.write();
  }
}
