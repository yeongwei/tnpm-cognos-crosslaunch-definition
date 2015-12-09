package com.psl.cognos.model.crosslaunch.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Logger;

import com.psl.cognos.model.crosslaunch.component.CounterReference;
import com.psl.cognos.model.crosslaunch.meta.ConfigurationProperty;

public class CounterFriendlyName {
  private Logger LOGGER = Logger.getLogger(CounterFriendlyName.class.getName());
  private HashMap<String, String> store = new HashMap<String, String>();

  public static void main(String[] args) throws Exception {
    CounterFriendlyName counterFriendlyName = new CounterFriendlyName(
        System.getProperty(ConfigurationProperty.COUNTER_FRIENDLY_NAME_FILE
            .getName()));
    counterFriendlyName.LOGGER.finer(counterFriendlyName
        .getFriendlyName(new CounterReference(
            "[Huawei].[Huawei CS 2G CELL Hourly]",
            "[Call Setup Success Rate - 2G_Numerator2]")));
  }

  public CounterFriendlyName(String modelFile) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(modelFile));
    String currentLine;
    int count = 0;
    while ((currentLine = br.readLine()) != null) {
      count += 1;
      if (count == 1) {
        continue;
      }
      String[] currentLineParts = currentLine.split(",");
      if (currentLineParts.length == 2) {
        String counterFqn = currentLineParts[0].trim();
        String friendlyName = currentLineParts[1].trim();
        LOGGER.finest(String.format("Counter '%s' has friendly name as '%s'.",
            counterFqn, friendlyName));
        store.put(counterFqn, friendlyName);
      } else {
        LOGGER.finest(String.format("Line %d is invalid.", count));
      }
    }
  }

  public String getFriendlyName(CounterReference counterReference) {
    LOGGER.finest(counterReference.toString());
    if (this.store.containsKey(counterReference.toString())) {
      return store.get(counterReference.toString());
    } else {
      String fqnName = counterReference.fqnName;
      fqnName = fqnName.replace("[", "");
      fqnName = fqnName.replace("]", "");
      return fqnName;
    }
  }
}
