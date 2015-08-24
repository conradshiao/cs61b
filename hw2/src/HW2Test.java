/** java HW2Test should test the methods defined in Lists and Arrays.
 *  @author Conrad Shiao, cs61b-cz
 */
public class HW2Test {

    /** Run all tests. */
    public static void main(String[] args) {
        report("catenate", testCatenate());
        report("remove", testRemove());
        report("naturalRunsarray", testnaturalRunsarray());
        report("naturalRuns", testnaturalRuns());
    }

    /** Print message that test NAME has (if ISOK) or else has not
     *  passed. */
    private static void report(String name, boolean isOK) {
        if (isOK) {
            System.out.printf("%s OK.%n", name);
        } else {
            System.out.printf("%s FAILS.%n", name);
        }
    }

    /** Prints true iff naturalRuns passes all its tests. */
    private static boolean testnaturalRuns() {
        IntList C = IntList.list(1, 3, 7);
        IntList D = IntList.list(5);
        IntList E = IntList.list(4, 6, 9, 10);
        IntList A = IntList.list(1, 3, 7, 5, 4, 6, 9, 10);
        IntList2 B = IntList2.list(C, D, E);
        return Utils.equals(B, Lists.naturalRuns(A));
    }

    /** Prints true iff Catenate passes all its tests. */
    private static boolean testCatenate() {
        int [] A = new int[] {1, 2, 3, 4};
        int [] B = new int[] {5, 6, 7, 8, 9};
        int [] C = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int [] D = {};
        int [] E = {1, 2, 3};
        int [] F = {1, 2, 3};
        int [] G = {-5};
        int [] H = {};
        int [] I = {-5};
        return Utils.equals(Arrays.catenate(A, B), C)
                && Utils.equals(Arrays.catenate(D, E), F)
                && Utils.equals(Arrays.catenate(G, H), I)
                && Utils.equals(Arrays.catenate(D, H), H);
    }

    /** Prints true iff remove passes all its tests. */
    private static boolean testRemove() {
        int [] A = {1, 3, 5, 7, 9, 11, 2, -4, 6, 8};
        int [] C = {1, 3, 11, 2, -4, 6, 8};
        int [] D = {1, 3, 8};
        int [] E = {};
        return Utils.equals(Arrays.remove(A, 2, 3), C)
                && Utils.equals(Arrays.remove(A, 2, 7), D)
                && Utils.equals(Arrays.remove(D, 0, 3), E)
                && Utils.equals(Arrays.remove(E, 0, 0), E);
    }

    /** Prints true iff naturalRuns in Arrays.java passes all its tests. */
    private static boolean testnaturalRunsarray() {
        int [] A = new int[] {1, 3, 5, 2};
        int [][] B = new int[2][];
        B[0] = new int[] {1, 3, 5};
        B[1] = new int[] {2};
        int[] C = new int[] {1, 3, 7, 5, 4, 6, 9, 2};
        int [][] X = new int[4][];
        X[0] = new int[] {1, 3, 7};
        X[1] = new int[] {5};
        X[2] = new int[] {4, 6, 9};
        X[3] = new int[] {2};
        return Utils.equals(B, Arrays.naturalRuns(A))
                && Utils.equals(X, Arrays.naturalRuns(C));
    }
}
