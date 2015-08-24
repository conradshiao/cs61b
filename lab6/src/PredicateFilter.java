import java.util.Iterator;
import utils.Predicate;
import utils.Filter;

/** A kind of Filter that tests the elements of its input sequence of
 *  VALUES by applying a Predicate object to them.
 *  @author Conrad Shiao, cs61b-cz
 */
class PredicateFilter<Value> extends Filter<Value> {

    /** A filter of values from INPUT that tests them with PRED,
     *  delivering only those for which PRED is true. */
    PredicateFilter(Predicate<Value> pred, Iterator<Value> input) {
        super(input);
        _test = pred;
    }

    @Override
    protected boolean keep() {
        return _test.test(candidateNext());
    }

    /** The test that this PredicateFilter performs on its inputs. */
    private Predicate<Value> _test;
}
