package com.psl.cognos.model.crosslaunch.writer;

import java.util.ArrayList;

import com.psl.cognos.model.crosslaunch.component.CounterReference;
import com.psl.cognos.model.crosslaunch.component.CounterReferences;
import com.psl.cognos.model.crosslaunch.component.CrosslaunchDefinition;

public class CrossLaunchDefinitionWriter extends Writer {

  private Mode mode = null;

  public void setMode(Mode mode) {
    this.mode = mode;
  }

  public void process(ArrayList<CrosslaunchDefinition> crosslaunchDefinitions) {
    if (mode == null) {
      throw new RuntimeException("Mode needs to be defined.");
    }

    ArrayList<String> CONTENT = new ArrayList<String>();
    for (int i = 0; i < crosslaunchDefinitions.size(); i++) {
      StringBuffer ROW = new StringBuffer();
      CrosslaunchDefinition crosslaunchDefinition = crosslaunchDefinitions
          .get(i);
      /*
       * private String buKpiName; private String buFqnPath; private
       * CounterReferences buCounterReferences; private String
       * buFqnEntityIdentifier; private String buFqnHourKey; private String
       * prFqnPath; private String alarmName;
       */

      // ALARM NAME
      ROW.append(crosslaunchDefinition.getAlarmName()).append(this.getDelimiter());
      // KPI REFERENCE - member([Ericsson].[Ericsson CS 2G CELLACUS Hourly].[TCH
      // Availability],'','TCH Availability')
      ROW.append(makeKpiReference(crosslaunchDefinition)).append(this.getDelimiter());
      // ENTITY REFERENCE
      ROW.append(crosslaunchDefinition.getBuFqnEntityIdentifier()).append(this.getDelimiter());
      // HOUR KEY
      ROW.append(crosslaunchDefinition.getBuFqnHourKey()).append(this.getDelimiter());
      // COUNTER REFERENCE
      ROW.append(makeCounterReferences(crosslaunchDefinition));
      
      CONTENT.add(sanitize(ROW.toString()));
    }
    
    this.setContent(CONTENT);
    this.setHeader("ALARM NAME|KPI REFERENCE|ENTITY REFERENCE|HOUR KEY|COUNTER REFERENCE");
  }

  protected String makeKpiReference(CrosslaunchDefinition crosslaunchDefinition) {
    return makeMember(crosslaunchDefinition.getBuPrFqnPath(),
        crosslaunchDefinition.getBuKpiName());
  }
  
  protected String makeCounterReferences(CrosslaunchDefinition crosslaunchDefinition) {
    StringBuffer s = new StringBuffer();
    s.append("<selectChoices>");
    
    CounterReferences counterReferences = crosslaunchDefinition
        .getBuCounterReferences();
    ArrayList<CounterReference> counterStore = counterReferences.getStore();
    for (int j = 0; j < counterStore.size(); j++) {
      CounterReference counterReference = counterStore.get(j);
      if (counterReference.presentationFqnPath == null) {
        s.append(makeSelectOption(counterReference.fqnPath,
            counterReference.fqnName));
      } else {
        s.append(makeSelectOption(counterReference.presentationFqnPath,
            counterReference.fqnName));
      }
    }
    
    s.append("</selectChoices>");
    return s.toString();
  }
  
  protected String makeMember(String fqnPath, String name) {
    StringBuffer s = new StringBuffer();
    s.append("member(").append(fqnPath).append(".[").append(name).append("]")
        .append(",'',").append("'").append(name).append("'").append(")");

    return s.toString();
  }

  /*
   * E.g. <selectOption useValue=
   * "member([Ericsson].[Ericsson CS 2G CELLACUS Hourly].[TCH Attempts],'','TCH Attempts')"
   * />
   */
  protected String makeSelectOption(String fqnPath, String name) {
    name = name.replace("[", "");
    name = name.replace("]", "");
    
    StringBuffer s = new StringBuffer();
    s.append("<selectOption useValue=\"");
    s.append(makeMember(fqnPath, name));
    s.append("\"/>");

    return s.toString();

  }
  
  protected static String sanitize(String val) {
    return val.replace("\"", "%22");
  }

  public enum Mode {
    PRODUCTION, DEVELOPMENT;
  }
}
