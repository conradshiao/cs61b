/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */

/** List problem.
 *  @author Conrad Shiao, cs61b-cz
 */
class Lists {
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal ascending sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10),
     *  then result is the three-item list ((1, 3, 7), (5), (4, 6, 9, 10)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntList2 naturalRuns(IntList L) {
        IntList run;
        IntList temp;
        run = temp = L;
        if (temp.tail == null) {
            return new IntList2(run, null);
        }
        while (temp.head < temp.tail.head) {
            temp = temp.tail;
            if (temp.tail == null) {
                return new IntList2(run, null);
            }
        }
        L = temp.tail;
        temp.tail = null;
        return new IntList2(run, naturalRuns(L));
    }
}
