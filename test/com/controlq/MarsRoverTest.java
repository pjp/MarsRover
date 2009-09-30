package com.controlq;

import junit.framework.TestCase;

/**
 *
 * @author ppe4t
 */
public class MarsRoverTest extends TestCase {
    Plateau plateau = null ;
    String location = "" ;
    String commandLine = "" ;

    public MarsRoverTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        location    = "0 0 N" ;
        commandLine = "LR" ;
        plateau     = new Plateau(3,4);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        plateau       = null ;
    }

    public void testBadInit() {

        try {
            // Bad location
            IRover rover = new MarsRover(plateau, null, commandLine) ;
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}
    }

    public void testGoodInit() {
        IRover rover = new MarsRover(plateau, location, commandLine) ;
        assertEquals(location, rover.toString());
    }

    public void testBadDoSetLocation() {
        MarsRover rover = null ;
        String status = null ;

        rover = new MarsRover(plateau, location, commandLine) ;

        // Bad location
        try {
            rover.doSetLocation(null);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        // Empty location
        location = "";
        try {
            rover.doSetLocation(location);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        // White space only location
        location = "   ";
        try {
            rover.doSetLocation(location);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        // Only one token in the location
        location = "1";
        try {
            rover.doSetLocation(location);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        // Only two tokens in the location
        location = "1 2";
        try {
            rover.doSetLocation(location);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        // Three tokens, but command is invalid
        location = "1 2 3";
        try {
            rover.doSetLocation(location);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        // Max X value is invalid
        location = "L 1 2";
        try {
            rover.doSetLocation(location);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        // Max x and y values are invalid
        location = "L R 1";
        try {
            rover.doSetLocation(location);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        // Invalid command
        location = "1 2 X";
        try {
            rover.doSetLocation(location);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        // coordinate out of range
        location = "3 5 N";
        rover.doSetLocation(location);
        status = rover.toString();
        assertEquals("0 0 N", status);

        // coordinate out of range
        location = "4 4 N";
        rover.doSetLocation(location);
        status = rover.toString();
        assertEquals("0 0 N", status);

        // coordinates out of range
        location = "6 7 N";
        rover.doSetLocation(location);
        status = rover.toString();
        assertEquals("0 0 N", status);

    }

    public void testGoodDoSetLocation() {
        MarsRover rover = null ;
        String status = null ;

        rover = new MarsRover(plateau, location, commandLine) ;

        location = "2 3 S" ;
        rover.doSetLocation(location);
        status = rover.toString();
        assertEquals(location, status);

        location = "3 4 E" ;
        rover.doSetLocation(location);
        status = rover.toString();
        assertEquals(location, status);

        // Very small plateau :-)
        plateau = new Plateau(0,0);
        rover = new MarsRover(plateau, location, commandLine) ;
        location = "0 0 W" ;
        rover.doSetLocation(location);
        status = rover.toString();
        assertEquals(location, status);
    }
    
    public void testBadDoCommandLine() {
        MarsRover rover = null ;
        String status = null ;

        rover = new MarsRover(plateau, location, commandLine) ;

        // Single invalid command
        commandLine = "X";
        rover.doCommandLine(commandLine) ;
        status = rover.toString();
        assertEquals(location, status);

        // Multiple invalid commands
        commandLine = "ABC";
        rover.doCommandLine(commandLine) ;
        status = rover.toString();
        assertEquals(location, status);
    }

    public void testGoodDoCommandLine() {
        MarsRover rover = null ;
        String status = null ;

        rover = new MarsRover(plateau, location, commandLine) ;

        location = "1 2 E" ;
        rover.doSetLocation(location);
        status = rover.toString();
        assertEquals(location, status);

        // Single valid command
        commandLine = "L";
        rover.doCommandLine(commandLine) ;
        status = rover.toString();
        assertEquals("1 2 N", status);

        // Multiple valid command in lowercase
        commandLine = "lm";
        rover.doCommandLine(commandLine) ;
        status = rover.toString();
        assertEquals("0 2 W", status);
    }

    public void testGoodRun() {
        // Test as taken from the problem solutions
        plateau = new Plateau(5,5);
        IRover rover = null ;
        String status ;

        // Multiple valid commands
        location = "1 2 N" ;
        commandLine = "LMLMLMLMM";
        rover = new MarsRover(plateau, location, commandLine) ;
        rover.run();
        status = rover.toString();
        assertEquals("1 3 N", status);

        // Multiple valid commands
        location = "3 3 E" ;
        commandLine = "MMRMMRMRRM";
        rover = new MarsRover(plateau, location, commandLine) ;
        rover.run();
        status = rover.toString();
        assertEquals("5 1 E", status);
    }
}
