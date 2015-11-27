package com.psl.cognos.model.crosslaunch.writer;

import java.util.ArrayList;

import com.psl.cognos.model.crosslaunch.component.CrosslaunchDefinition;

public class DmlWriter extends CrossLaunchDefinitionWriter {

  @Override
  public void process(ArrayList<CrosslaunchDefinition> crosslaunchDefinitions) {
    /*
     * ALARM_NAME VARCHAR2(42) KPI_REFERENCE VARCHAR2(137) ENTITY_REFERENCE
     * VARCHAR2(36) HOUR_KEY VARCHAR2(34) COUNTER_REFERENCE VARCHAR2(4000)
     */
    ArrayList<String> CONTENT = new ArrayList<String>();
    for (int i = 0; i < crosslaunchDefinitions.size(); i++) {
      CrosslaunchDefinition crossLaunchDefinition = crosslaunchDefinitions
          .get(i);
      /*
       * Refer to TestWriter String cognosUrl =
       * writeUrl(crosslaunchDefinition.getBuFqnEntityIdentifier(),
       * crosslaunchDefinition.getBuFqnHourKey(),
       * makeKpiReference(crosslaunchDefinition),
       * makeCounterReferences(crosslaunchDefinition));
       */
    }
  }
}
