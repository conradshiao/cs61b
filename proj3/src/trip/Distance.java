package trip;

import graph.Weighted;

/** Class that corresponds to Distance entries.
 *  @author Conrad Shiao
 */
public class Distance implements Weighted {

    /** The place where I start from. */
    private String _startingPoint;
    /** The place that is my destination. */
    private String _destination;
    /** The name of the road. */
    private String _roadName;
    /** The length of the road. */
    private double _length;
    /** The direction that my road will take. */
    private String _direction;
    /** The string that specifies the direction from my starting point to my
     *  destination. */
    private String _fromDirection = null;
    /** The string that specifies the direction going from my destination
     *  to my starting point.  */
    private String _toDirection = null;

    /** The constructor for a Distance object. Sets my starting point to START,
     *  destination to DESTINATION, my road name to ROADNAME, the length of
     *  that particular road to DISTANCE, and the direction of that road to
     *  DIRECTION. */
    public Distance(String start, String destination, String roadName,
            double distance, String direction) {
        _startingPoint = start;
        _destination = destination;
        _roadName = roadName;
        _length = distance;
        _direction = direction;
        String letter = String.valueOf(direction.charAt(0));
        switch (letter) {
        case "N":
            _fromDirection = "south";
            _toDirection = "north";
            break;
        case "S":
            _fromDirection = "north";
            _toDirection = "south";
            break;
        case "E":
            _fromDirection = "west";
            _toDirection = "east";
            break;
        case "W":
            _fromDirection = "east";
            _toDirection = "west";
            break;
        default:
            System.err.printf("%s not a valid parameter", letter);
            System.exit(1);
        }
    }

    /** Return, in the form of a string, the direction that a person
     *  will travel along me based on where FROM starts from. */
    String getDirection(String from) {
        if (from.equals(_startingPoint)) {
            return _fromDirection;
        } else {
            return _toDirection;
        }
    }

    /** Return the directions that my road runs along. */
    String getDirection() {
        return _direction;
    }

    @Override
    public String toString() {
        return _roadName;
    }

    @Override
    public double weight() {
        return _length;
    }
}
