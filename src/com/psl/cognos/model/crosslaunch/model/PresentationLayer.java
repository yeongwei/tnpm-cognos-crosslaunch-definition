package com.psl.cognos.model.crosslaunch.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.w3c.dom.Node;

import com.psl.cognos.model.crosslaunch.component.Cube;
import com.psl.cognos.model.crosslaunch.component.Vendor;
import com.psl.cognos.model.crosslaunch.parser.Parser;

public class PresentationLayer {
  private boolean enabled = false;
  private Node node;
  private static Logger LOGGER = Logger.getLogger(PresentationLayer.class
      .getName());

  public PresentationLayer(Node node) {
    this.node = node;
  }
  
  public PresentationLayer enable() {
    this.enabled = true;
    return this;
  }

  public void run() throws Exception {

    if (!enabled)
      return;
    // Using API to get XML chunks
    /*
     * NodeList nodeList1 =
     * element.getElementsByTagName(ModelNode.NAMESPACE.getName());
     * LOGGER.info(Integer.toString(nodeList1.getLength())); for (int pointer1 =
     * 0; pointer1 < nodeList1.getLength(); pointer1++) { Node node1 =
     * nodeList1.item(pointer1); Element element1 = (Element) node1; NodeList
     * nodeList2 = element1.getElementsByTagName(ModelNode.NAME.getName()); Node
     * node2 = nodeList2.item(0); LOGGER.info(node2.getTextContent()); }
     */

    // parseModel(presentationLayerNode, "name/text()");

    // [Huawei]
    // parseModel(presentationLayerNode,
    // "namespace[name='Huawei']/name/text()");
    // [Huawei].[Hourly KPIs]
    // parseModel(presentationLayerNode,
    // "namespace[name='Huawei']/folder[name='Hourly KPIs']/name/text()");
    // [Huawei].[Hourly KPIs].[Huawei PS 4G EUTRANCELL Hourly POHC]
    // parseModel(presentationLayerNode,
    // "namespace[name='Ericsson']/folder[name='Hourly KPIs']/shortcut/name/text()",
    // true);

    ArrayList<String> FILE = new ArrayList<String>();
    FILE.add("FQN,Ref Obj");
    Vendor vendors[] = Vendor.values();
    for (int i = 0; i < vendors.length; i++) {
      String xpath = "namespace[name='%s']/";
      xpath = String.format(xpath, vendors[i].getName());
      String folderXpath = xpath + "folder[name='%s']/";
      folderXpath = String.format(folderXpath, "Hourly KPIs");

      String nameXpath = folderXpath + "shortcut/name/text()";
      LOGGER.finest(String.format("About to parse %s.", nameXpath));
      ArrayList<Cube> names = Parser.parseModel(node, nameXpath);

      String refObjXpath = folderXpath + "shortcut/refobj/text()";
      LOGGER.finest(String.format("About to parse %s.", refObjXpath));
      ArrayList<Cube> refObjs = Parser.parseModel(node, refObjXpath);

      int totalCount = (names.size() + refObjs.size()) / 2;
      for (int j = 0; j < totalCount; j++) {
        String fqn = "[%s].[%s]";
        fqn = String.format(fqn, vendors[i].getName(), names.get(j).value);
        String refObj = refObjs.get(j).value;
        FILE.add((new PresentationLayerRow(fqn, refObj)).toString());
      }

      String shortCutXpath = xpath + "shortcut/name/text()";
      LOGGER.finest(String.format("About to parse %s.", shortCutXpath));
      ArrayList<Cube> shortCutNames = Parser.parseModel(node, shortCutXpath);
      
      String shortCutRefObjXpath = xpath + "shortcut/refobj/text()";
      ArrayList<Cube> shortCutRefObjs = Parser.parseModel(node, shortCutRefObjXpath);
      
      totalCount = (shortCutNames.size() + shortCutRefObjs.size()) / 2;
      for (int j = 0; j < totalCount; j++) {
        String fqn = "[%s].[%s]";
        fqn = String.format(fqn, vendors[i].getName(), shortCutNames.get(j).value);
        String refObj = shortCutRefObjs.get(j).value;
        FILE.add((new PresentationLayerRow(fqn, refObj)).toString());
      }
    }

    Parser.writeToFile(
        "D:/development/_assignment/CognosModel-CrossLaunch/output/"
            + Parser.getFileName("PresentationLayer-"), FILE);
  }
}
