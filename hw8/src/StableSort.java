import java.util.Random;

/** HW #8, Problem 1.
 *  @author Conrad Shiao
 */
public class StableSort {

    /** A type of functional object for comparing ints (analogous to the
     *  Comparator<Integer> interface, but operates on the primitive type
     *  int.). */
    interface IntComparator {
        /** Defines an ordering on ints by returning an integer that is <, ==,
         *  or > 0 depending on whether, X comes before, at the same place, or
         *  after Y in the ordering.  */
        int compare(int x, int y);
    }

    /** Sort the array A according to the given ORDERING.  The sort is not
     *  stable. */
    static void sort(int[] A, IntComparator ordering) {
        sort(A, 0, A.length - 1, ordering);
    }

    /** Generator for creating random pivots. */
    private static Random gen = new Random();

    /** Swap elements A[I] and A[J]. */
    static void swap(int[] A, int i, int j) {
        int t = A[i]; A[i] = A[j]; A[j] = t;
    }

    /** Sort elements A[L .. U], according to ORDERING. */
    static void sort(int[] A, int L, int U, IntComparator ordering) {
        if (L >= U) {
            return;
        } else {
            int m;
            swap(A, L, L + gen.nextInt(U - L + 1));
            int T = A[L];
            m = L;
            for (int i = L + 1; i <= U; i += 1) {
                if (ordering.compare(A[i], T) < 0) {
                    m += 1;
                    swap(A, i, m);
                }
            }
            swap(A, L, m);
            sort(A, L, m - 1, ordering);
            sort(A, m + 1, U, ordering);
        }
    }

    /** Given that D is an Nx2 array of Strings (that is, each D[i] is
     *  a String[] with length 2), returns an array p such that
     *  D[p[0]], D[p[1]], D[p[2]], ... D[p[D.length-1]] is a stable sorting
     *  of D by the first strings.  D is not modified.   That is,
     *  produce a p that sorts D[0], D[1], etc., by D[0][0], D[1][0], D[2][0],
     *  etc., and where two D[i] have the same first element, leaves them
     *  in the same order they have in D.  */
    static int[] sortStably(String[][] D) {
        int[] p = new int[D.length];
        for (int i = 0; i < p.length; i += 1) {
            p[i] = i;
        }
        sort(p, new SortStablyClass(D));
        return p;
    }

    /** Helper class for sortStably method. */
    static class SortStablyClass implements IntComparator {

        /** Array of String arrays used as basis of sortStably. */
        private String[][] _sorting;

        /** Constructor for this class, taking in D. */
        SortStablyClass(String[][] D) {
            _sorting = D;
        }

        @Override
        public int compare(int x, int y) {
            if (_sorting[x][0].charAt(0) > _sorting[y][0].charAt(0)) {
                return 1;
            } else if (_sorting[x][0].charAt(0) == _sorting[y][0].charAt(0)) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}


