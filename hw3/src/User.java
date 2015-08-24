/** Functions to sum and increment the elements of a WeirdList.
 *  @author Conrad Shiao */
class User {
    /** Returns the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        Adding x = new Adding(n);
        return L.map(x);
    }

    /** Returns the sum of the elements in L. */
    static int sum(WeirdList L) {
        return L._head + sum(L._tail);
    }

    /** Implements a way to represent an adding function. */
    private static class Adding implements IntUnaryFunction {

        /** Keeps track of what you are summing. */
        private int _sum;

        /** A new Adding which adds N. */
        public Adding(int n) {
            _sum = n;
        }

        @Override
        public int apply(int x) {
            return x + _sum;
        }
    }
}
