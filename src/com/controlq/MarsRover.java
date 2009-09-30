package com.controlq;

import java.util.StringTokenizer;

/**
 * Concrete implementation of a Rover to be positioned and moved on a Plateau.
 *
 * It should normally be accessed via the interface IRover.
 *
 * <p>
 * IRover rover = new MarsRover(plateau, location, commandLine);<br/>
 * rover.run();<br/>
 * </p>
 *
 * @author pjp
 * @version 1
 */
public class MarsRover extends ARover {
    public static final int MOVE_INCREMENT = 1 ;

    private Plateau        plateau ;
    private String         commandLine ;
    private int xCoord,    yCoord ;         // Current coordinates
    private Orientation    orientation ;    // Current orientation

    /**
     * Create a new Rover and set it's plateau and parameters for the run
     *
     * @param plateau The plateau to navigate.
     * @param location The initial location of the rover on the plateau
     * @param commandLine The commands to execute
     */
    public MarsRover(final Plateau plateau,
                     final String location,
                     final String commandLine) {
        // Setup the environment for the rover
        this.plateau       = plateau ;
        this.commandLine   = commandLine;   // No need for validation yet

        // Make sure this is called AFTER setting the Plateau
        doSetLocation(location);
    }


    /**
     * Execute the commands for the rover.
     * 
     * This can also be executed in a separate thread if needed.
     */
    public void run() {
        doCommandLine(commandLine);
    }

    /**
     * Set the initial location of the rover on the plateau
     *
     * @param location 3 space separated tokens denoting the initial x y
     * coordinates and an orientation (N S E W)
     */
     void doSetLocation(final String location) {
        String x, y, o ;
        Orientation orient;
        String msg = null ;

        logger.info("Entering with [" + location + "]");

        // Sanity checks, cannot trust the public
        if(location == null || location.trim().length() < 1) {
            msg = "location cannot be null or empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        StringTokenizer tokens = new StringTokenizer(location.trim());
        if(tokens.countTokens() < 3) {
            msg = "location need 3 tokens: x y orientation";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Break out the individual bits
        x = tokens.nextToken();
        y = tokens.nextToken();
        o = tokens.nextToken().toUpperCase();

        try {
            orient = Orientation.valueOf(o);
        } catch(IllegalArgumentException e) {
            msg = "Invalid orientation value [" + o + "]";
            logger.error(msg);
            throw e ;
        }

        // Check each carefully
        if(Nasa.isCoordinateValid(x, plateau.getMinX(), plateau.getMaxX()) &&
           Nasa.isCoordinateValid(y, plateau.getMinX(), plateau.getMaxY())) {

            // Set the values
            orientation = orient;
            xCoord      = Integer.parseInt(x) ;
            yCoord      = Integer.parseInt(y) ;
        } else {
            logger.warn("Invalid new coordinates: + " +
                        "[" + x + " " + y +
                        "] - ignored");
        }
    }

    /**
     * The commands for the rover to execute.
     *
     * @param commandLine Zero or more character commands; L R M
     */
     void doCommandLine(final String commandLine) {
        String commandValue ;
        Command command = null;
        String msg = null;

        logger.info("Entering with [" + commandLine + "]");

        // Sanity checks, cannot trust the public
        if(commandLine == null || commandLine.trim().length() < 1) {
            msg = "command should not be null or empty";
            logger.warn(msg);
            return;
        }

        // Iterate thru. all the characters in the commandLine string
        char[] commands = commandLine.toCharArray();
        for(char cmd : commands) {
            commandValue = Character.toString(cmd).toUpperCase();
            try {
                command = Command.valueOf(commandValue);

                // It's a valid command
                doOneCommand(command);
            } catch(IllegalArgumentException e) {
                // Woops, unknown !
                msg = "Invalid command [" + commandValue + "] - ignored";
                logger.warn(msg);
            }
        }
    }

    /**
     * Execute the command specified
     *
     * @param command The command to execute
     */
    private void doOneCommand(final Command command) {
        Orientation newOrientation = null;

        logger.info("Entering with [" + command + "]");
        
        if(command == Command.L || command == Command.R) {
            // Rotate 
            newOrientation = Orientation.rotate(command, orientation);
            orientation = newOrientation;
        } else {
            // Move the ARover
            moveLocation();
        }
    }

    /**
     * Move the x or y coordinates if they do not break
     * the boundaries of the plateau.
     *
     * Any move that will exceed boundaries will be ignored.
     *
     * It's a cruddy method, but simple and it will work !!!
     */
    private void moveLocation() {
        if(orientation == Orientation.N) {
            if(yCoord < plateau.getMaxY()) {
                yCoord += MOVE_INCREMENT ;
            }
        } else if(orientation == Orientation.S) {
            if(yCoord > plateau.getMinY()) {
                yCoord -= MOVE_INCREMENT ;
            }
        } else if(orientation == Orientation.E) {
            if(xCoord < plateau.getMaxX()) {
                xCoord += MOVE_INCREMENT ;
            }
        } else if(orientation == Orientation.W) {
            if(xCoord > plateau.getMinX()) {
                xCoord -= MOVE_INCREMENT ;
            }
        }
    }

    /**
     *
     * @return The current location and orientation of the rover.
     */
    @Override
    public String toString() {
        StringBuilder status = new StringBuilder() ;

        status.append(xCoord);
        status.append(" ");

        status.append(yCoord);
        status.append(" ");

        status.append(orientation.toString());

        return status.toString();
    }
}
