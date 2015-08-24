/** A WeirdList holds a sequence of integers.
 *  @author Conrad Shiao
 *  */

public class WeirdList {
    /** The empty sequence of integers. */
    public static final WeirdList EMPTY = new EmptyWeirdList();
    /** First element of a WeirdList. */
    private int _head;
    /** Remaining elements of a WeirdList. */
    private WeirdList _tail;
    /** The length of this WeirdList. */
    private static int _length;

    /** A new WeirdList whose head is HEAD and tail is TAIL. */
    public WeirdList(int head, WeirdList tail) {
        _head = head;
        _tail = tail;
    }


    /** Returns the number of elements in the sequence that
     *  starts with THIS. */
    public int length() {
        return 1 + _tail.length();
    }

    /** Apply FUNC.apply to every element of THIS WeirdList in
     *  sequence, and return a WeirdList of the resulting values. */
    public WeirdList map(IntUnaryFunction func) {
        _head = func.apply(_head);
        _tail.map(func);
        return this;
    }

    /** Print the contents of THIS WeirdList on the standard output
     *  (on one line, each followed by a blank).  Does not print
     *  an end-of-line. */
    public void print() {
        System.out.print(_head + " ");
        _tail.print();
    }

    /** Representation of an empty WeirdList. */
    private static class EmptyWeirdList extends WeirdList {

        /** A new EmptyWeirdList. */
        private EmptyWeirdList() {
            super(0, null);
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public void print() {
            return;
        }

        @Override
        public WeirdList map(IntUnaryFunction func) {
            return this;
        }
    }
}
