import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets all the VALUE elements of its input sequence
 *  that are larger than all the preceding values to go through the
 *  Filter.  So, if its input delivers (1, 2, 3, 3, 2, 1, 5), then it
 *  will produce (1, 2, 3, 5).
 *  @author Conrad Shiao, cs61b-cz
 */
class MonotonicFilter<Value extends Comparable<Value>> extends Filter<Value> {

    /** A filter of values from INPUT that delivers a monotonic
     *  subsequence.  */
    MonotonicFilter(Iterator<Value> input) {
        super(input);
    }

    @Override
    protected boolean keep() {
        if (_firstNotSeen) {
            _firstNotSeen = false;
            _largest = candidateNext();
            return true;
        }
        if (_largest.compareTo(candidateNext()) < 0) {
            _largest = candidateNext();
            return true;
        }
        return false;
    }

    /** Keeps track of current largest element seen. */
    private Value _largest;

    /** Keeps track if the first element has been seen. */
    private boolean _firstNotSeen = true;
}
