package com.psl.cognos.model.crosslaunch.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BusinessLayer {
  private Node node;
  private static Logger LOGGER = Logger
      .getLogger(BusinessLayer.class.getName());

  public BusinessLayer(Node node) {
    this.node = node;
  }

  public void run() throws Exception {
    /*
     * Parser.parseModel(node,
     * "namespace[name='Huawei KPIs']/folder[name='Hourly KPIs']/querySubject[not(contains(name,'POHC'))]/queryItem/name/text()"
     * , true);
     */

    ArrayList<BusinessLayerRow> ROWS = new ArrayList<BusinessLayerRow>();
    String businessKpiGroup = null;
    String businessFolderName = "Hourly KPIs"; // static

    BusinessLayerGroup BUSINESS_GROUP[] = BusinessLayerGroup.values();
    for (int a = 0; a < BUSINESS_GROUP.length; a++) {
      businessKpiGroup = BUSINESS_GROUP[a].getName();

      String querySubjectXpath = "namespace[name='" + businessKpiGroup
          + "']/folder[name='" + businessFolderName
          + "']/querySubject[not(contains(name,'POHC'))]";
      LOGGER.info("About to parse " + querySubjectXpath);
      XPath xpath = XPathFactory.newInstance().newXPath();
      XPathExpression expr = xpath.compile(querySubjectXpath);
      Object result = expr.evaluate(node, XPathConstants.NODESET);

      NodeList nodes = (NodeList) result; // querySubject(s)
      for (int i = 0; i < nodes.getLength(); i++) {
        // Get Query subject name
        expr = xpath.compile("name/text()");
        result = expr.evaluate(nodes.item(i), XPathConstants.NODESET);
        NodeList nodeList1 = (NodeList) result;
        String businessQuerySubjectName = nodeList1.item(0).getNodeValue();
        // LOGGER.info(businessQuerySubjectName);

        Element element = (Element) nodes.item(i);
        NodeList queryItemNodeList = element.getElementsByTagName("queryItem"); // queryItem

        int numOfCounters = 0;
        for (int j = 0; j < queryItemNodeList.getLength(); j++) {
          Node queryItemNode = queryItemNodeList.item(j);

          expr = xpath.compile("name/text()");
          result = expr.evaluate(queryItemNode, XPathConstants.NODESET);
          nodeList1 = (NodeList) result;
          String businessQueryItemName = nodeList1.item(0).getNodeValue();

          String fqn = "[%s].[%s].[%s]";
          fqn = String.format(fqn, businessKpiGroup, businessQuerySubjectName,
              businessQueryItemName);

          // Expression -> refobj
          CounterReference counterReference = new CounterReference();
          expr = xpath.compile("expression/refobj/text()");
          result = expr.evaluate(queryItemNode, XPathConstants.NODESET);
          nodeList1 = (NodeList) result;
          for (int k = 0; k < nodeList1.getLength(); k++) {
            Node node = nodeList1.item(k);
            counterReference.add(node.getNodeValue());
            numOfCounters +=1 ;
          }

          BusinessLayerRow ROW = new BusinessLayerRow(businessQueryItemName,
              fqn, counterReference);
          ROWS.add(ROW);
        }

        LOGGER.info(String.format(
            "On querySubject %s found %d KPIs and %d Counters.",
            businessQuerySubjectName, queryItemNodeList.getLength(), numOfCounters));
      }
    }

    for (int i = 0; i < ROWS.size(); i++) {
      System.out.println(ROWS.get(i).toString());
    }
  }
}
