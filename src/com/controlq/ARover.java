package com.controlq;

import org.apache.log4j.Logger;

/**
 * Always like a base class to put shared methods/objects in
 * 
 * @author pjp
 * @version 1
 *
 */
public abstract class ARover implements IRover {
    /**
     * Shared logger
     */
    protected static Logger logger = Logger.getLogger("com.controlq");
}
