import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/* NOTE: The file ArrayUtil.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #2. */

/** Array utilities.
 *  @author Conrad Shiao
 */
class Arrays {
    /* 2a. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int[] ans = new int[A.length + B.length];
        System.arraycopy(A, 0, ans, 0, A.length);
        System.arraycopy(B, 0, ans, A.length, B.length);
        return ans;
    }

    /* 2b. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        int[] x = new int[A.length - len];
        System.arraycopy(A, 0, x, 0, start);
        System.arraycopy(A, start + len, x, start, A.length - start - len);
        return x;
    }

    /* 2c. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
 /*   static int[][] naturalRuns(int[] A) {
        if (A == null) {
            return null;
        }
        int [][] ans;
        int j = 0;
        int breaks = 1;
        while (j < A.length - 1) {
            if (A[j] > A[j + 1]) {
                breaks += 1;
            }
            j += 1;
        }
        ans = new int[breaks][];
        int previous = 0;
        int len = 1;
        int indexA = 0;
        int i = 1;
        while (i < A.length) {
            if (A[i - 1] >= A[i]) {
                ans[indexA] = new int[len];
                System.arraycopy(A, previous, ans[indexA], 0, len);
                indexA += 1;
                previous = previous + len;
                len = 1;
            } else {
                len += 1;
            }
            i += 1;
        }
        ans[indexA] = new int[len];
        System.arraycopy(A, previous, ans[indexA], 0, len);
        return ans;
    } */

    /* 2c. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}.
     *  
     *  EDITTED: 5/19/14. */
    static int[][] naturalRuns(int[] A) {
        if (A == null) {
            return null;
        }
        int numberOfArrays = numArraysHelper(A);
        int[][] res = new int[numberOfArrays][];
        int subArrayLength = 1;
        int whichArray = 0;
        for (int a = 1; a < A.length; a++) {
            if (A[a] < A[a - 1]) {
                res[whichArray] = new int[subArrayLength];
                System.arraycopy(A, a - subArrayLength, res[whichArray], 0, subArrayLength);
                subArrayLength = 1;
                whichArray++;
                if (a == A.length - 1) { // comparing last entry
                    res[whichArray] = new int[] {A[a]};
                }
            } else {
                subArrayLength++;
            }
        }
    //   System.out.println(java.util.Arrays.deepToString(res));
        return res;
    }

    /** Returns the number of arrays that static function naturalRuns will
     *  break into given input of array A. Helper function. */
    static private int numArraysHelper(int[] A) {
        if (A == null) {
            return 0;
        }
        int breaks = 1;
        for (int i = 1; i < A.length; i++) {
            if (A[i] < A[i - 1]) { // next element is STRICTLY greater
                breaks++;
            }
        }
        return breaks;
    }
}
