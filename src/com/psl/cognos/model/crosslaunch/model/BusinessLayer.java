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

import com.psl.cognos.model.crosslaunch.component.BusinessLayerGroup;
import com.psl.cognos.model.crosslaunch.component.CounterReference;
import com.psl.cognos.model.crosslaunch.component.CounterReferences;
import com.psl.cognos.model.crosslaunch.component.DomainKnowledge;
import com.psl.cognos.model.crosslaunch.component.DomainKnowledge.Technology;
import com.psl.cognos.model.crosslaunch.parser.Parser;

public class BusinessLayer {
  private Node node;
  private ArrayList<BusinessLayerRow> businessLayerRows = new ArrayList<BusinessLayerRow>();
  private static Logger LOGGER = Logger
      .getLogger(BusinessLayer.class.getName());

  public BusinessLayer(Node node) {
    this.node = node;
  }

  public ArrayList<BusinessLayerRow> getBusinessLayerRows() {
    return this.businessLayerRows;
  }

  public void run() throws Exception {
    /*
     * Parser.parseModel(node,
     * "namespace[name='Huawei KPIs']/folder[name='Hourly KPIs']/querySubject[not(contains(name,'POHC'))]/queryItem/name/text()"
     * , true);
     */
    ArrayList<String> _ROWS = new ArrayList<String>();
    _ROWS.add("FQN NAME,FQN PATH,COUNTER REFERENCE,ENTITY NAME,HOUR KEY");

    String businessKpiGroup = null;
    String businessFolderName = "Hourly KPIs"; // static

    BusinessLayerGroup BUSINESS_GROUPS[] = BusinessLayerGroup.values();
    for (int a = 0; a < BUSINESS_GROUPS.length; a++) {
      BusinessLayerGroup BUSINESS_GROUP = BUSINESS_GROUPS[a];
      businessKpiGroup = BUSINESS_GROUP.getName();

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

          String fqnPath = "[%s].[%s]";
          fqnPath = String.format(fqnPath, businessKpiGroup,
              businessQuerySubjectName);

          // Expression -> refobj
          CounterReferences counterReferences = new CounterReferences();
          expr = xpath.compile("expression/refobj/text()");
          result = expr.evaluate(queryItemNode, XPathConstants.NODESET);
          nodeList1 = (NodeList) result;
          for (int k = 0; k < nodeList1.getLength(); k++) {
            Node node = nodeList1.item(k);
            String counter = node.getNodeValue();
            // LOGGER.info(counter);
            counterReferences.add(new CounterReference(counter));
            numOfCounters += 1;
          }

          // Entity Identifier
          StringBuffer fqnEntityIdentifier = new StringBuffer();
          fqnEntityIdentifier.append("[")
              .append(BUSINESS_GROUP.getVendorName()).append("]");
          fqnEntityIdentifier.append(".");
          Technology _technology = DomainKnowledge.Technology
              .getTechnology(businessQuerySubjectName);
          fqnEntityIdentifier.append("[")
              .append(_technology.getPresentationSegment()).append("]");
          fqnEntityIdentifier.append(".");
          fqnEntityIdentifier.append("[")
              .append(_technology.getEntityIdentifier()).append("]");

          // Hour Key
          StringBuffer fqnHourKey = new StringBuffer();
          fqnHourKey.append("[").append(BUSINESS_GROUP.getVendorName())
              .append("]");
          fqnHourKey.append(".");
          fqnHourKey.append("[Time]");
          fqnHourKey.append(".");
          fqnHourKey.append("[Hour key Start]");

          // Unique Key = Vendor + kpiName
          StringBuffer uniqueKey = new StringBuffer();
          uniqueKey.append(BUSINESS_GROUP.getVendorName());
          uniqueKey.append("-");
          uniqueKey.append(businessQueryItemName);

          BusinessLayerRow ROW = new BusinessLayerRow(businessQueryItemName,
              fqnPath, counterReferences, fqnEntityIdentifier.toString(),
              fqnHourKey.toString(), uniqueKey.toString());
          businessLayerRows.add(ROW);
        }

        // Status
        LOGGER.info(String.format(
            "On querySubject '%s' found %d KPIs and %d Counters.",
            businessQuerySubjectName, queryItemNodeList.getLength(),
            numOfCounters));
      }
    }

    // Write to file
    for (int z = 0; z < businessLayerRows.size(); z++) {
      _ROWS.add(businessLayerRows.get(z).toString());
    }

    Parser.writeToFile(
        "D:/development/_assignment/CognosModel-CrossLaunch/output/"
            + Parser.getFileName("BusinessLayerEnriched-"), _ROWS);
  }
}
