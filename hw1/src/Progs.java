/** HW #1 solutions.
 *
 *  @author Conrad Shiao cs61b-cz
 */
class Progs {

    /* 1a. */
    /** Returns the sum of all integers, k, such that 1 <= k < N and
     *  N is evenly divisible by k. */
    static int factorSum(int N) {
        int tracking = 1;
        int sum = 0;
        int midPoint = (N / 2) + 1;
        while (tracking < midPoint) {
            if (N % tracking == 0) {
                sum += tracking;
            }
            tracking++;
        }
        return sum;
    }

    /* 1b. */
    /** Print the set of all sociable pairs whose members are all
     *  between 1 and N>=0 (inclusive) on the standard output (one pair per
     *  line, smallest member of each pair first, with no repetitions). */
    static void printSociablePairs(int N) {
        int first = 1;
        while (first <= N) {
            int x = factorSum(first);
            if (x <= N && first == factorSum(x) && first < x) {
                System.out.println(first + " " + x);
            }
            first += 1;
        }
    }

    /* 2a. */
    /** Returns a list consisting of the elements of A followed by the
     *  elements of B.  May modify items of A. Don't use 'new'. */
    static IntList dcatenate(IntList A, IntList B) {
        IntList temp = A;
        if (A == null) {
            return B;
        }
        while (temp.tail != null) {
            temp = temp.tail;
        }
        temp.tail = B;
        return A;
    }

    /* 2b. */
    /** Returns the sublist consisting of LEN items from list L,
     *  beginning with item #START (where the first item is #0).
     *  Does not modify the original list elements.
     *  It is an error if the desired items don't exist. */
    static IntList sublist(IntList L, int start, int len) {
        if (len == 0) {
            return null;
        }
        if (start != 0) {
            return sublist(L.tail, start - 1, len);
        } else {
            return new IntList(L.head, sublist(L.tail, start, len - 1));
        }
    }

    /* 2c. */
    /** Returns the sublist consisting of LEN items from list L,
     *  beginning with item #START (where the first item is #0).
     *  May modify the original list elements. Don't use 'new'.
     *  It is an error if the desired items don't exist. */
    static IntList dsublist(IntList L, int start, int len) {
        if (len == 0) {
            return null;
        }
        if (start != 0) {
            return sublist(L.tail, start - 1, len);
        } else {
            L.tail = dsublist(L.tail, start, len - 1);
            return L;
        }
    }
}
