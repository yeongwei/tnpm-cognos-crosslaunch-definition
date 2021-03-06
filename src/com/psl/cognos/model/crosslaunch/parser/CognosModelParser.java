package com.psl.cognos.model.crosslaunch.parser;

import java.util.logging.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.psl.cognos.model.crosslaunch.meta.ModelNodeName;

public class CognosModelParser {
  private static Logger LOGGER = Logger.getLogger(CognosModelParser.class
      .getName());
  
  public CognosModelParser() {
    LOGGER.info("This is constructor.");
  }

  /**
   * Get values of name tag within a node E.g. <node><name>getThis</name></node>
   * 
   * @param node
   * @return
   */
  public static String getNodeName(Node node) {
    Element element = (Element) node;
    NodeList nodeList = element.getElementsByTagName(ModelNodeName.NAME
        .getName());
    Node node0 = nodeList.item(0);

    return node0.getTextContent();
  }
}
