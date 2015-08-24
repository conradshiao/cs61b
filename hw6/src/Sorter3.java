import java.util.Random;
import java.util.LinkedList;
import java.util.ListIterator;

import ucb.util.Stopwatch;

/** Insertion sort with timing.
 *  @author P. N. Hilfinger
 */
public class Sorter3 {

    /** Sort VALUES into non-decreasing order. */
    public static void sort(LinkedList<Double> values) {
        /* Loop invariant:
         * Elements 0 through k-1 are in nondecreasing order:
         *   values[0] <= values[1] <= ... <= values[k-1].
         * Insert element k into its correct position, so that
         *   values[0] <= values[1] <= ... <= values[k-1]. */
        ListIterator<Double> faster = values.listIterator();
        if (faster.hasNext()) {
            faster.next(); // start from index k = 1, as in Sorter.java
        } else {
            return;
        }
        while (faster.hasNext()) {
            int currIndex = faster.nextIndex();
            double temp = faster.next();
            faster.remove();
            double prev = faster.previous();
            while (prev > temp && faster.hasPrevious()) {
                prev = faster.previous();
            }
           if (faster.hasPrevious()) faster.next(); // to insert TEMP variable right after prev
            faster.add(temp);
            /* Now, must reset iterator back to where we started this iteration. */
            while (faster.nextIndex() < currIndex) {
                faster.next();
            }
            // BUGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGYYYYYYYYYYYYYYYYYYY
        }
    }

/*
            int currentIndex = faster.nextIndex();
            faster.next();
            Double temp = faster.previous();
            faster.remove();
            if (!faster.hasPrevious()) {
                faster.next();
            } else {
                Double previous = faster.previous();
                while (faster.hasPrevious() && previous > temp) {
                    previous = faster.previous();
                }
                faster.next();
                faster.add(temp);
                int check = faster.nextIndex();
                while (check < currentIndex) {
                    faster.next();
                    check = faster.nextIndex();
                }
                faster.next();
            }
        } */

    /** Returns an N-element vector, V, filled with random numbers, with
     *  V[k] = C0 * k + C1 * R, where R is uniform over 0.0 .. 1.0.
     */
    static LinkedList<Double> randomVector(int n, double c0, double c1,
                                          Random R) {
        int j;
        LinkedList<Double> values = new LinkedList<Double>();
        for (j = 0; j < n; j += 1) {
            values.add(c0 * j + c1 * R.nextDouble());
        }
        return values;
    }

    /** Print VALUES, one item per line. */
    static void printArr(LinkedList<Double> values) {
        for (double x : values) {
            System.out.println(x);
        }
    }

    /** ARGS is SIZE [ TYPE [SEED]].  Generates random data of
     *  given SIZE using SEED and times a call on sort over this
     *  data.  TYPE is either "A" or "B", selecting one of two
     *  formulas for transforming random numbers into data.*/
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java Sorter SIZE [ TYPE [SEED]]");
            System.exit(1);
        }
        int N = Integer.parseInt(args[0]);
        String type = args.length >= 2 ? args[1] : "A";
        long seed = args.length >= 3 ? Long.parseLong(args[2]) : 42;
        Random R = new Random(seed);

        LinkedList<Double> values;
        if (type.equals("A")) {
            values = randomVector(N, 0.0, 1.0, R);
        } else {
            values = randomVector(N, 1.0, 20.0, R);
        }

        Stopwatch t = new Stopwatch();
        t.start();
        sort(values);
        System.out.printf("%5.3f seconds elapsed.%n",
                           t.stop() * 1.0e-3);
        if (N < 20) {
            printArr(values);
        }
    }
}
