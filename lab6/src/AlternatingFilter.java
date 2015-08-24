import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets through every other VALUE element of
 *  its input sequence, starting with the first.
 *  @author Conrad Shiao, cs61b-cz
 */
class AlternatingFilter<Value> extends Filter<Value> {

    /** A filter of values from INPUT that lets through every other
     *  value. */
    AlternatingFilter(Iterator<Value> input) {
        super(input);
        _tracking = false;
    }

    @Override
    protected boolean keep() {
        _tracking = !_tracking;
        return _tracking;
    }

    /** Tracks if I have skipped over an element in this iterator. */
    private boolean _tracking;
}
