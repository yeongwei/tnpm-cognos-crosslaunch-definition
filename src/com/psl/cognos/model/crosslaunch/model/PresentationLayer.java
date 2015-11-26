package com.psl.cognos.model.crosslaunch.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.psl.cognos.model.crosslaunch.component.Pair;
import com.psl.cognos.model.crosslaunch.meta.ModelNodeValue;
import com.psl.cognos.model.crosslaunch.meta.Vendor;
import com.psl.cognos.model.crosslaunch.writer.Writer;

public class PresentationLayer extends CognosModelLayer {
  private ArrayList<PresentationLayerRow> presentationLayerRows = new ArrayList<PresentationLayerRow>();
  private String fileName = "D:\\development\\_assignment\\TNPM-Cognos-CrossLaunch-Definition\\output\\PresentationLayer-";
  private String headerStr = "FQN,Ref Obj";

  public PresentationLayer(Node node) {
    super(node);
  }

  public ArrayList<PresentationLayerRow> getPresentationLayerRows() {
    return this.presentationLayerRows;
  }

  public HashMap<String, String> asHashMap() {
    final HashMap<String, String> x = new HashMap<String, String>();

    for (int i = 0; i < presentationLayerRows.size(); i++) {
      final PresentationLayerRow r = presentationLayerRows.get(i);
      x.put(r.refObj, r.fqn);
    }

    return x;
  }

  @Override
  public void runImpl() throws Exception {
    Vendor vendors[] = Vendor.values();
    for (int i = 0; i < vendors.length; i++) {
      String xpath = "namespace[name='%s']/";
      xpath = String.format(xpath, vendors[i].getName());

      String folderXpath = xpath + "folder[name='%s']/";
      folderXpath = String.format(folderXpath,
          ModelNodeValue.HOURLY_KPIS.getName());

      // FROM FOLDER
      String nameXpath = folderXpath + "shortcut/name/text()";
      LOGGER.finest(String.format("About to parse %s.", nameXpath));
      ArrayList<Pair> names = asPairs(node, nameXpath);

      String refObjXpath = folderXpath + "shortcut/refobj/text()";
      LOGGER.finest(String.format("About to parse %s.", refObjXpath));
      ArrayList<Pair> refObjs = asPairs(node, refObjXpath);

      presentationLayerRows.addAll(makePresentationLayerRows(
          vendors[i].getName(), names, refObjs));

      // FROM NOT FOLDER
      String shortCutXpath = xpath + "shortcut/name/text()";
      LOGGER.finest(String.format("About to parse %s.", shortCutXpath));
      ArrayList<Pair> shortCutNames = asPairs(node, shortCutXpath);

      String shortCutRefObjXpath = xpath + "shortcut/refobj/text()";
      ArrayList<Pair> shortCutRefObjs = asPairs(node, shortCutRefObjXpath);

      presentationLayerRows.addAll(makePresentationLayerRows(
          vendors[i].getName(), shortCutNames, shortCutRefObjs));
    }

    LOGGER.finest(String.format(
        "Found %d object mapings from Presentation Layer.",
        presentationLayerRows.size()));
    final ArrayList<String> FILE = new ArrayList<String>();
    for (int z = 0; z < presentationLayerRows.size(); z++) {
      FILE.add(presentationLayerRows.get(z).toString());
    }

    Writer writer = new Writer();
    writer.makeFileName(this.fileName);
    writer.setContent(FILE);
    writer.setHeader(headerStr);
    writer.write();
  }

  /**
   * Loop through List of Pair and return as PresentationLayerRow
   * 
   * @param x
   * @param y
   * @return
   * @throws Exception
   */
  private static ArrayList<PresentationLayerRow> makePresentationLayerRows(
      String vendor, ArrayList<Pair> x, ArrayList<Pair> y) throws Exception {
    if (x.size() != y.size()) {
      throw new RuntimeException(
          "The number of node name and refobjs mustbe the same.");
    }

    final ArrayList<PresentationLayerRow> z = new ArrayList<PresentationLayerRow>();
    int totalCount = (x.size() + y.size()) / 2;
    for (int j = 0; j < totalCount; j++) {
      z.add(makePresentationLayerRow(vendor, x.get(j), y.get(j)));
    }
    return z;
  }

  private static PresentationLayerRow makePresentationLayerRow(String vendor,
      Pair x, Pair y) {
    String fqn = "[%s].[%s]";
    fqn = String.format(fqn, vendor, x.value);
    String refObj = y.value;
    return new PresentationLayerRow(fqn, refObj);
  }

  /**
   * Only usuable with xpath that ends with text()
   * 
   * @param node
   * @param xPathStr
   * @return
   * @throws Exception
   */
  private static ArrayList<Pair> asPairs(Object node, String xPathStr)
      throws Exception {
    return asPairs(node, xPathStr, false);
  }

  private static ArrayList<Pair> asPairs(Object node, String xPathStr,
      boolean print) throws Exception {
    ArrayList<Pair> x = new ArrayList<Pair>();

    XPath xpath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xpath.compile(xPathStr);
    Object result = expr.evaluate(node, XPathConstants.NODESET);
    NodeList nodes = (NodeList) result;

    for (int i = 0; i < nodes.getLength(); i++) {
      Node node1 = nodes.item(i);
      if (print)
        LOGGER.info(String.format("%s: %s", node1.getNodeName(),
            node1.getNodeValue()));
      x.add(new Pair(node1.getNodeName(), node1.getNodeValue()));
    }

    return x;
  }
}
