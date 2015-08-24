package trip;

import graph.Weightable;

/** Class to hold the Location.
 *  @author Conrad Shiao */
class Location implements Weightable {

    /** My place's name. */
    private final String _name;
    /** The x-coordinate of my location. */
    private double _x;
    /** The y-coordinate of my location. */
    private double _y;
    /** The distance from me to the starting point. */
    private double _weight;

    /** My constructor, setting my x-coordinate to X, my y-coordinate to
     *  Y, and my name to NAME. */
    public Location(String name, double x, double y) {
        _name = name;
        _x = x;
        _y = y;
        _weight = Double.POSITIVE_INFINITY;
    }

    @Override
    public double weight() {
        return _weight;
    }

    @Override
    public void setWeight(double x) {
        _weight = x;
    }

    @Override
    public String toString() {
        return _name;
    }

    /** Returns my x-coordinate. */
    double getX() {
        return _x;
    }

    /** Returns my y-coordinate. */
    double getY() {
        return _y;
    }
}
