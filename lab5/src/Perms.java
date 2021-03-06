import java.util.TreeSet;


/** Generating permutations.
 *  @author Conrad Shiao, cs61b-cz
 */
public class Perms {

    /** Assumes that ARGS[0] denotes a positive integer N.  Prints
     *  all permutations of 0 .. N-1, one per line. */
    public static void main(String... args) {
        int N = Integer.parseInt(args[0]);

        int[] A = new int[N];
        for (int i = 0; i < N; i += 1) {
            A[i] = i;
        }

        do {
            printArr(A);
        } while (nextPerm(A));

    }

    /** Print elements of B on one line, separated by spaces. */
    static void printArr(int[] B) {
        for (int i : B) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
    }

    /** Permute A to its next permutation, if possible.  Returns true
     *  if there is such a permutation, and false otherwise. */
    static boolean nextPerm(int[] A) {
        int n = A.length;
        int k = n - 1;
        TreeSet<Integer> s = new TreeSet<Integer>();
        while (k >= 0) {
            if (s.higher(A[k]) != null) {
                Object v = s.higher(A[k]);
                s.remove(v);
                s.add(A[k]);
                A[k] = (int) v;
                System.arraycopy(s.toArray(), 0, A, k + 1, s.toArray().length);
                return true;
            } else {
                s.add(A[k]);
                k -= 1;
            }
        }
        return false;
    }

}
