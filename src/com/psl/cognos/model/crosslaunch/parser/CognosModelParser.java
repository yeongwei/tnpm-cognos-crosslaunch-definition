package com.psl.cognos.model.crosslaunch.parser;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.psl.cognos.model.crosslaunch.component.Cube;

public class CognosModelParser {
  private static Logger LOGGER = Logger.getLogger(CognosModelParser.class.getName());

  public static ArrayList<Cube> parseModel(Object node, String xPathStr)
      throws Exception {
    return parseModel(node, xPathStr, false);
  }

  public static ArrayList<Cube> parseModel(Object node, String xPathStr,
      boolean print) throws Exception {
    ArrayList<Cube> x = new ArrayList<Cube>();
    // Using XPath to get XML chunks
    XPath xpath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xpath.compile(xPathStr);
    // Object result = expr.evaluate(doc, XPathConstants.NODESET);
    Object result = expr.evaluate(node, XPathConstants.NODESET);
    NodeList nodes = (NodeList) result;

    for (int i = 0; i < nodes.getLength(); i++) {
      Node node1 = nodes.item(i);
      if (print)
        LOGGER.info(String.format("%s: %s", node1.getNodeName(),
            node1.getNodeValue()));
      x.add(new Cube(node1.getNodeName(), node1.getNodeValue()));
    }

    return x;
  }
}
