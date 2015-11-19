package com.psl.cognos.model.crosslaunch.parser;

import java.util.ArrayList;

import com.psl.cognos.model.crosslaunch.component.CrosslaunchDefinition;

public class TestWriter extends CrossLaunchDefinitionWriter {

  @Override
  public void process(ArrayList<CrosslaunchDefinition> crosslaunchDefinitions) {
    ArrayList<String> CONTENT = new ArrayList<String>();
    for (int i = 0; i < crosslaunchDefinitions.size(); i++) {
      StringBuffer s = new StringBuffer();
      CrosslaunchDefinition crosslaunchDefinition = crosslaunchDefinitions
          .get(i);
      s.append(crosslaunchDefinition.getAlarmName()).append("|");
      s.append(crosslaunchDefinition.getBuKpiName()).append("|");
      String cognosUrl = writeUrl(crosslaunchDefinition.getBuFqnEntityIdentifier(), crosslaunchDefinition.getBuFqnHourKey(), 
          makeKpiReference(crosslaunchDefinition), makeCounterReferences(crosslaunchDefinition));
      s.append(cognosUrl);
      CONTENT.add(s.toString());
    }
    this.setContent(CONTENT);
  }

  private String writeUrl(String entityName, String hourKey,
      String kpiReference, String counterReference) {
    StringBuffer url = new StringBuffer();
    url.append("https://dx850.etisalat.corp.ae:17311/p2pd/servlet/dispatch/ext?b_action=cognosViewer&ui.action=run&ui.object=/content/folder[@name='Mobile Access']/folder[@name='AEL']/report[@name='CrossLaunch']&ui.name=CrossLaunch&run.outputFormat=&run.prompt=false");
    url.append("&p_pnodeType=").append(entityName);
    url.append("&p_pHour=").append(hourKey);
    url.append("&p_pkpi=").append(kpiReference);
    url.append("&p_pCounter=").append(counterReference);
    url.append("&p_pnode=?");
    return url.toString();
  }
}
