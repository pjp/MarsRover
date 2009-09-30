package com.controlq;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * A ENUM to represent a cardinal compass point orientation of the rover
 * <p/>
 * North<br/>
 * South<br/>
 * East<br/>
 * West<br/>
 *
 * @author pjp
 * @version 1
 */
public enum Orientation {
    N { @Override
        public String toString() { return "N" ; }},  // Facing North

    S { @Override
        public String toString() { return "S" ; }},  // Facing South

    E { @Override
        public String toString() { return "E" ; }},  // Facing East

    W { @Override
        public String toString() { return "W" ; }};  // Facing West

    // A reference to a logger
    private static Logger logger = Logger.getLogger("com.controlq");

    // A list of orientations that should be in clockwise sequence.
    private static List<Orientation> compassPointsClockwise =
            new ArrayList<Orientation>();

    static {
        // Build an array of orientations for rotation purposes.
        compassPointsClockwise.add(N);
        compassPointsClockwise.add(E);
        compassPointsClockwise.add(S);
        compassPointsClockwise.add(W);
    }

    /**
     * Given an orientation, rotate it and return the new orientation
     *
     * @param command rotate LEFT or RIGHT
     * @param orientation current orientation
     * @return the next/previous orientation; else null if current orientation
     * not found
     */
    public static Orientation rotate(final Command command,
                                     final Orientation orientation) {
        int currentOrientationIndex = -1 ;  // Mark as not found
        Orientation newOrientation  = null ;
        int compassPointsSize       = compassPointsClockwise.size() ;
        String msg = null ;

        logger.info("Entering with [" + command + "] [" + orientation + "]");
        
        // Try and find the current orientation in the list
        for(int i = 0 ; i < compassPointsSize ; i++) {
            if(orientation == compassPointsClockwise.get(i)) {
                currentOrientationIndex = i ;
                if(logger.getLevel() == Level.DEBUG) {
                    msg="Found current orientation at index [" + i + "]";
                    logger.debug(msg);
                }
                break ;
            }
        }

        if(currentOrientationIndex != -1) {
            // Found current orientation in the list
            if(command == Command.L) {
                if(currentOrientationIndex > 0) {
                    currentOrientationIndex-- ;
                } else {
                    // Wrap backwards
                    currentOrientationIndex = compassPointsSize - 1 ;
                }
                if(logger.getLevel() == Level.DEBUG) {
                    msg="New index is [" + currentOrientationIndex + "]";
                    logger.debug(msg);
                }
            } else if(command == Command.R) {
                if(currentOrientationIndex < (compassPointsSize - 1)) {
                    currentOrientationIndex++ ;
                } else {
                    // Wrap forwards
                    currentOrientationIndex = 0 ;
                }
                if(logger.getLevel() == Level.DEBUG) {
                    msg="New index is [" + currentOrientationIndex + "]";
                    logger.debug(msg);
                }
            } else {
                msg = "Invalid rotate command [" + command + "] - ignored";
                logger.warn(msg);
            }
            newOrientation = compassPointsClockwise.get(currentOrientationIndex);
            if(logger.getLevel() == Level.DEBUG) {
                msg="New orientation is [" + newOrientation + "]";
                logger.debug(msg);
            }
        } else {
            msg = "Could not find orientation to rotate [" + orientation + "]";
            logger.warn(msg);
        }

        return newOrientation;
    }
}
