package com.psl.cognos.model.crosslaunch.model;

import java.util.logging.Logger;

import org.w3c.dom.Node;

public abstract class CognosModelLayer {
  protected boolean enabled = false;
  protected Node node;
  protected static Logger LOGGER = Logger.getLogger(CognosModelLayer.class
      .getName());
  
  public CognosModelLayer(Node node) {
    this.node = node;
  }
  
  /**
   * Enable for this model to allow execution
   */
  public void enable() {
    this.enabled = true;
  }
  
  /**
   * Hook to runImpl
   * @throws Exception
   */
  public final void run() throws Exception { 
    if (!enabled) {
      LOGGER.finest("Object needs to be enabled.");
      return;
    }
    
    runImpl();
  }
  
  /**
   * Concrete implementation needed here for each layer
   * @throws Exception
   */
  public abstract void runImpl() throws Exception;
}
