/** HW #8, Problem 3.
 *  @author Conrad Shiao
 */
public class SortInts {

    /** Sort A into ascending order.  Assumes that 0 <= A[i] < n*n for all
     *  i, and that the A[i] are distinct. */
    static void sort(long[] A) {
        long[] temp = new long[10];
        int sigfigs = ("" + (A.length * A.length)).length();
        double placeHolder = Math.pow(10, sigfigs);
        for (int k = 1; k < placeHolder; k = k * 10) {
            for (long number: A) {
                int digit = (int) number / k;
                temp[digit] = number;
            }
        }
    }

}
