package com.psl.cognos.model.crosslaunch;

import java.io.File;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.psl.cognos.model.crosslaunch.component.ModelNode;
import com.psl.cognos.model.crosslaunch.component.ModelValue;
import com.psl.cognos.model.crosslaunch.component.Property;
import com.psl.cognos.model.crosslaunch.model.BusinessLayer;
import com.psl.cognos.model.crosslaunch.model.PresentationLayer;

public class Main {

  private static Logger LOGGER = Logger.getLogger(Main.class.getName());

  public static void main(String args[]) throws Exception {
    String modelFilePath = System.getProperty(Property.MODEL_FILE.getName());
    File modelFile = new File(modelFilePath);

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(modelFile);
    doc.getDocumentElement().normalize();

    NodeList nameSpaceList = doc.getElementsByTagName(ModelNode.NAMESPACE
        .getName());

    // LOGGER.info(Integer.toString(nameSpaceList.getLength()));
    BusinessLayer businessLayer = null;
    PresentationLayer presentationLayer = null;
    for (int pointer = 0; pointer < nameSpaceList.getLength(); pointer++) {
      Node node = nameSpaceList.item(pointer);

      Element element = (Element) node;
      NodeList nodeList = element
          .getElementsByTagName(ModelNode.NAME.getName());
      Node node0 = nodeList.item(0);

      if (node0.getTextContent().equals(ModelValue.BUSINESS_LAYER.getName())) {
        LOGGER.info("About to run Business Layer.");
        businessLayer = new BusinessLayer(node);
        businessLayer.run();
      }
      
      if (node0.getTextContent()
          .equals(ModelValue.PRESENTATION_LAYER.getName())) {
        LOGGER.info("About to run Presentation Layer.");
        presentationLayer = new PresentationLayer(node);
        presentationLayer.enable();
        presentationLayer.run();
      }
    }
  }
}
