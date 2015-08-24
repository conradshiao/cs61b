/** java HW1Test should test the methods defined in Progs.
 *  @author Conrad Shiao cs61b-cz
 */
public class HW1Test {

    /** Run all tests. */
    public static void main(String[] args) {
        report("factorSum", testFactorSum());
        report("printSociablePairs", testPrintSociablePairs());
        report("dcatenate", testDcatenate());
        report("sublist", testSublist());
        report("dsublist", testDsublist());
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


    /** Return true iff factorSum passes its tests. */
    private static boolean testFactorSum() {
        int a = Progs.factorSum(220);
        int b = Progs.factorSum(13);
        int c = Progs.factorSum(284);
        int d = Progs.factorSum(27);
        int e = Progs.factorSum(340);
        if (a != 284 || b != 1 || d != 13 || e != 416 || c != 220) {
            return false;
        }
        if (Progs.factorSum(2) != 1) {
            return false;
        }
        return true;
    }

    /** Return true iff printSociablePairs passes its tests. */
    private static boolean testPrintSociablePairs() {
        Progs.printSociablePairs(15000);
        return true;
    }

    /** Return true iff dcantenate passes its tests. */
    private static boolean testDcatenate() {
        IntList A = IntList.list(1, 2, 3, 4, 5);
        IntList B = IntList.list(6, 7, 8, 9, 10);
        IntList combined = IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntList C = IntList.list(1);
        IntList D = IntList.list(3);
        IntList combined1 = IntList.list(1, 3);
        IntList X = null;
        IntList Y = IntList.list(1, 2);
        return (Progs.dcatenate(A, B).equals(combined)
                && Progs.dcatenate(C, D).equals(combined1))
                && Progs.dcatenate(X, Y).equals(Y)
                && Progs.dcatenate(Y, X).equals(Y)
                && Progs.dcatenate(X, X) == null;
    }

    /** Return true iff sublist passes its tests. */
    private static boolean testSublist() {
        IntList A = IntList.list(0, 1, 2, 3, 4, -5, 6, 7, 8, 9, 10);
        IntList B = IntList.list(8, 20, 34, -5);
        IntList A1 = IntList.list(4, -5, 6, 7);
        IntList B1 = IntList.list(34);
        IntList C = IntList.list(5, -3, 2);
        return Progs.sublist(A, 4, 4).equals(A1)
                && Progs.sublist(B, 2, 1).equals(B1)
                && Progs.sublist(C, 2, 0) == null;
    }

    /** Return true iff dsublist passes its tests. */
    private static boolean testDsublist() {
        IntList A = IntList.list(0, 1, 2, 3, 4, -5, 6, 7, 8, 9, 10);
        IntList B = IntList.list(8, 20, 34, -5);
        IntList A1 = IntList.list(4, -5, 6, 7);
        IntList B1 = IntList.list(34);
        IntList C = IntList.list(5, -3, 2);
        return Progs.sublist(A, 4, 4).equals(A1)
                && Progs.sublist(B, 2, 1).equals(B1)
                && Progs.sublist(C, 2, 0) == null;
    }
}
