import java.util.Arrays;

/** HW #8, Problem 5.
 * @author Conrad Shiao
 */
public class Sum {

    /** Returns true iff A[i]+B[j] = M for some i and j. */
    public static boolean sumsTo(int[] A, int[] B, int m) {
        Arrays.sort(A);
        Arrays.sort(B);
        int a = A.length - 1;
        int b = B.length - 1;
        while (A[a] > m) {
            a--;
        }
        while (B[b] > m) {
            b--;
        }
        int k = 0;
        while (a >= 0 && k <= b) {
            if (A[a] + B[k] == m) {
                return true;
            } else if (A[a] + B[k] > m) {
                a--;
            } else {
                b++;
            }
        }
        return true;
    }
}
