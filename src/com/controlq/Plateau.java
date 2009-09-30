package com.controlq;

/**
 * A class to simulate the coordinates of a Plateau for
 * Rovers to navigate around in.
 * 
 * @author pjp
 * @version 1
 */
public class Plateau {
    private int minX ;
    private int minY ;
    private int maxX ;
    private int maxY ;

    /**
     * Set the upper right coordinates of the plateau, the lower left will be
     * set to 0,0
     *
     * @param maxX Upper right x coordinate
     * @param maxY Upper right y coordinate
     */
    public Plateau(final int maxX, final int maxY) {
        this(0, 0, maxX, maxY);
    }

    /**
     * Sewt both the upper and lower coordinates of the plateau.
     *
     * @param minX Lower left x coordinate
     * @param minY Lower left x coordinate
     * @param maxX Upper right x coordinate
     * @param maxY Upper right y coordinate
     */
    public Plateau(final int minX, final int minY,
                   final int maxX, final int maxY) {

        // Sanity checks
        if(minX > maxX) {
            throw new IllegalArgumentException("minX cannot be > maxX");
        }

        if(minY > maxY) {
            throw new IllegalArgumentException("minY cannot be > maxY");
        }

        this.minX = minX ;
        this.minY = minY ;
        this.maxX = maxX ;
        this.maxY = maxY ;
    }

    /**
     * The lower left x coordidate of the plateau.
     *
     * @return the minX
     */
    public int getMinX() {
        return minX;
    }

    /**
     * The lower left y coordidate of the plateau.
     *
     * @return the minY
     */
    public int getMinY() {
        return minY;
    }

    /**
     * The upper right x coordidate of the plateau.
     *
     * @return the maxX
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * The upper right y coordidate of the plateau.
     *
     * @return the maxY
     */
    public int getMaxY() {
        return maxY;
    }

}
