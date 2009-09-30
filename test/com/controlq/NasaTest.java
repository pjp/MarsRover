package com.controlq;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author pjp
 */
public class NasaTest extends TestCase {
    
    public NasaTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of MarsMission method.
     */
    public void testBadMarsMission() {
        List<String> inputs = new ArrayList();
        String line = "" ;

        // No input lines
        try {
            Nasa.MarsMission(inputs);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}


        line = "5 5" ;
        inputs.add(line);

        line = "1 2 N";
        inputs.add(line);

        // Not enough input lines
        try {
            Nasa.MarsMission(inputs);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}

        line = "LMLMLMLMM";
        inputs.add(line);

        line = "3 3 E";
        inputs.add(line);

        // Unbalanced input - missing line
        try {
            Nasa.MarsMission(inputs);
            fail("Should have thrown an exception");
        } catch(IllegalArgumentException e) {}
    }

    public void testSillyMarsMission() {
        List<String> inputs = new ArrayList();
        String line = "" ;
        IRover rover = null;
        List<IRover> rovers = new ArrayList<IRover>();

        line = "5 5" ;
        inputs.add(line);

        line = "0 0 S";
        inputs.add(line);

        // Try and spin 360 deg. and then move outside of the boundaries
        line = "LLLLMMM";
        inputs.add(line);

        rovers = Nasa.MarsMission(inputs);
        assertEquals("0 0 S", rovers.get(0).toString());
    }


    // The solution to the problem is rendered to stdout here
    public void testGoodMarsMission() {
        List<String> inputs = new ArrayList();
        String line = "" ;

        System.out.println("\nInputs:");

        line = "5 5" ;
        System.out.println(line);
        inputs.add(line);

        line = "1 2 N";
        System.out.println(line);
        inputs.add(line);

        line = "LMLMLMLMM";
        System.out.println(line);
        inputs.add(line);

        line = "3 3 E";
        System.out.println(line);
        inputs.add(line);

        line = "MMRMMRMRRM";
        System.out.println(line);
        inputs.add(line);

        System.out.println("\nOutputs:");
        Nasa.MarsMission(inputs);
    }

}
