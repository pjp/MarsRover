package com.controlq;

import junit.framework.TestCase;

/**
 *
 * @author pjp
 */
public class OrientationTest extends TestCase {
    
    public OrientationTest(String testName) {
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
     * Test of rotate method, of class Orientation.
     */
    public void testRotate() {
        Orientation newOrientation = null ;

        newOrientation =
                Orientation.rotate(Command.L, Orientation.N) ;
        assertEquals(Orientation.W,  newOrientation);

        newOrientation =
                Orientation.rotate(Command.R, Orientation.N) ;
        assertEquals(Orientation.E,  newOrientation);
        /************************************************************/

        newOrientation =
                Orientation.rotate(Command.L, Orientation.S) ;
        assertEquals(Orientation.E,  newOrientation);

        newOrientation =
                Orientation.rotate(Command.R, Orientation.S) ;
        assertEquals(Orientation.W,  newOrientation);
        /************************************************************/

        newOrientation =
                Orientation.rotate(Command.L, Orientation.E) ;
        assertEquals(Orientation.N,  newOrientation);

        newOrientation =
                Orientation.rotate(Command.R, Orientation.E) ;
        assertEquals(Orientation.S,  newOrientation);
        /************************************************************/

        newOrientation =
                Orientation.rotate(Command.L, Orientation.W) ;
        assertEquals(Orientation.S,  newOrientation);

        newOrientation =
                Orientation.rotate(Command.R, Orientation.W) ;
        assertEquals(Orientation.N,  newOrientation);
        /************************************************************/

        newOrientation =
                Orientation.rotate(Command.M, Orientation.W) ;
        assertEquals(Orientation.W,  newOrientation);

    }
}
