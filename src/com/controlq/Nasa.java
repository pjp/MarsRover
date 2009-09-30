package com.controlq;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

/**
 * Simulate NASA and a mission to Mars
 *
 * @author pjp
 * @version 1
 */
public class Nasa {
    private static Logger logger = Logger.getLogger("com.controlq");

    /**
     * Check that the coordinate is numeric and is within range.
     *
     * @param coord A coordinate string to be checked for being numeric
     * @param coordMin The min value the coordinate can have
     * @param coordMax The max value the coordinate can have
     *
     * @return true if coordinate is valid; else false
     */
    public static boolean isCoordinateValid(final String coord,
                                            final int coordMin,
                                            final int coordMax) {
        boolean ok = false ;
        int c = 0 ;

        logger.info("coord: [" + coord + "], coordMax: [" + coordMax + "]");

        try {
            // Check it's numeric
            c = Integer.parseInt(coord);

            // Check it's within range
            if(c >= 0 && c <= coordMax) {
                ok = true ;
            }
        } catch(Exception e) {
            logger.warn(e);
            
            // Swallow exception
            ok = false ;        // to be sure, to be sure
        }

        return ok ;
    }

    /**
     * Start a set of rovers on a Mars Mission.
     * 
     * @param inputs An array of input lines defining a plateau and rovers
     * to navigate it.
     *
     * @return The list of rovers on the plateau after they have been
     * driven.
     */
    public static List<IRover> MarsMission(final List<String> inputs) {
        String x, y ;
        IRover rover = null ;
        String msg = null;
        List<IRover> rovers = new ArrayList<IRover>();  // Store all created rovers
        String location, commandLine;
        Thread thread = null ;

        // Sanity checks
        if(inputs.size() < 3) {
            msg = "Need at least 3 input lines";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        int linesOfInput = inputs.size();
        if((linesOfInput % 2) == 0) {
            msg = "Unbalanced input - missing last line ???";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Extract the first line, it defines the plateau's max coordinates
        StringTokenizer tokens = new StringTokenizer(inputs.get(0)) ;
        if(tokens.countTokens() < 2) {
            msg = "First line does not contain plateau's maxX and MaxY";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        x = tokens.nextToken() ;
        y = tokens.nextToken() ;

        if(!Nasa.isCoordinateValid(x, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
            msg = "Plateau's maxX is invalid [" + x + "]";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if(!Nasa.isCoordinateValid(y, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
            msg = "Plateau's maxY is invalid[" + y + "]";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Hurray, create the plateau
        Plateau plateau = new Plateau(Integer.parseInt(x), Integer.parseInt(y));

        // Loop thru the rest of the input lines and build a list of rovers
        linesOfInput-- ;
        for(int i = 1 ; i < linesOfInput ; i+=2) {
            // Extract the location and command line and create run parameters
            location    = inputs.get(i) ;
            commandLine = inputs.get(i+1) ;

            // Create the rover, position it on the plateau, then add it to the list
            rover = new MarsRover(plateau, location, commandLine);
            rovers.add(rover);

        }

        // Now run all the rovers
        for (IRover r : rovers) {
            // Run the rovers sequentially for now
            thread = new Thread(r);
            thread.start();

            try {
                // And wait for it to finish
                thread.join();

                // Dump the current rover's final location and orientation
                System.out.println(r.toString());
            } catch (InterruptedException ex) {
                logger.warn(ex);
            }
        }

        return rovers;
    }
}
