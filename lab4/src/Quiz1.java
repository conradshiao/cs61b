/** An accumulator of ints.  This object is given a sequence of
 *  int values via its accum method.  It performs some processing on
 *  this sequence, and returns a result (or partial result) when
 *  its result() method is called. */
abstract class Accum {

    /** Return the accumulated result. */
    abstract int result();

    /** Process and accumulate the value X. */
    abstract void accum(int x);

    /** Return the result of accumulating all of the values in vals. */
    int reduce(int[] vals) {
        for (int x : vals) 
            accum (x);
        return result();
    }
}

class Quiz1Helper extends Accum {
    
    private int _max;
    
    private int _accumulated;
    
    public void QuizHelper(int max) {
        _max = max;
    }
    
    @Override
    public int result() {
        return _accumulated;
    }
    
    @Override
    public void accum(int x) {
        int y = Math.min(x, _max);
        _accumulated += y;
    }
}