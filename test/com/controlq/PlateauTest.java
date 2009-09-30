package com.controlq;

import junit.framework.TestCase;

/**
 *
 * @author pjp
 */
public class PlateauTest extends TestCase {
    
    public PlateauTest(String testName) {
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
     * .
     */
    public void testBadInit() {
        Plateau plateau = null ;

        try {
            // Min > Max
            plateau = new Plateau(3,4,1,2) ;
            fail("Should have thown an exception") ;
        } catch(IllegalArgumentException e) {}

        try {
            // Min > Max
            plateau = new Plateau(3,0,1,2) ;
            fail("Should have thown an exception") ;
        } catch(IllegalArgumentException e) {}
    }

    public void testGoodInit() {
        Plateau plateau = null ;

        plateau = new Plateau(1,2) ;
        plateau = new Plateau(0,0,1,2) ;
        plateau = new Plateau(1,2,3,4) ;
    }
}
