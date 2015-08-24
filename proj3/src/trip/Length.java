package trip;

import graph.Distancer;

/** The heuristic for the trip package. Is based on the standard
 *  formula for the distance between two objects, using Pythagorean's Theorem.
 *  @author Conrad Shiao
 *
 */
class Length implements Distancer<Location> {

    @Override
    public double dist(Location a, Location b) {
        double xVector = b.getX() - a.getX();
        double yVector = b.getY() - a.getY();
        return Math.sqrt(Math.pow(xVector, 2) + Math.pow(yVector, 2));
    }
}
