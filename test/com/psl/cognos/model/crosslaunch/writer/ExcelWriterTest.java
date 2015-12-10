package com.psl.cognos.model.crosslaunch.writer;

import java.util.ArrayList;

import org.junit.Test;

import com.psl.cognos.model.crosslaunch.component.CounterReference;
import com.psl.cognos.model.crosslaunch.component.CounterReferences;
import com.psl.cognos.model.crosslaunch.component.CrosslaunchDefinition;

public class ExcelWriterTest {

  @Test
  public void testWriter() throws Exception {
    CounterReference counterReference = new CounterReference("[Path]",
        "[Counter]");
    CounterReferences counterReferences = new CounterReferences();
    counterReferences.add(counterReference);

    CrosslaunchDefinition crosslaunchDefinition = new CrosslaunchDefinition();
    crosslaunchDefinition.setAlarmName("AlarmName");
    crosslaunchDefinition.setBuCounterReferences(counterReferences);
    crosslaunchDefinition.setBuFqnEntityIdentifier("[Path].[EntityIdentifier]");
    crosslaunchDefinition.setBuFqnPath("[Path]");
    crosslaunchDefinition.setBuHourKey("[Path].[Hour key]");
    crosslaunchDefinition.setBuKpiName("KpiName");
    crosslaunchDefinition.setPrFqnPath("[Presentation].[Path]");

    ArrayList<CrosslaunchDefinition> x = new ArrayList<CrosslaunchDefinition>();
    x.add(crosslaunchDefinition);

    ExcelWriter excelWriter = new ExcelWriter(500, 10);
    excelWriter.setExt("xlsx");
    excelWriter
        .makeFileName("D:\\development\\_assignment\\TNPM-Cognos-CrossLaunch-Definition\\output\\Test-CrossLaunchDefinition-");
    excelWriter.process(x);
    excelWriter.write();
  }
}
