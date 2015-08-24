import arith.Rational;
import static arith.Rational.*;


/** Square root implementation on rational numbers.
 *  @author Conrad Shiao, cs61b-cz
 */
public class Root2 {

    /** Tolerance of approximation. */
    private static final Rational EPS = frac(1, 1000000);

    /** Return the Kth root of X, where X >= 0, K >= 1. */
    static Rational root(Rational x, int k) {
        /* Strategy: A Newton-Raphson iteration, organized so that all
         * corrections are negative.  We continue until the estimated
         * magnitude of the relative error is less than that of EPS. */

        if (less(x, inttorat(0))) {
            throw new IllegalArgumentException("x must be non-negative");
        }
        if (k < 1.0) {
            throw new IllegalArgumentException("k must be >= 1");
        }
        if (equalrat(x, inttorat(0))) {
            return frac(0);
        }

        Rational y, err;
        Rational threshold = multrat(multrat(inttorat(k), EPS), x);
        y = maxrat(frac(1), x);

        do {
            Rational yk1 = power(y, k - 1);
            err = multrat(subrat(x, yk1), y);
            y = addrat(y, dividerat(err,
                    multrat(inttorat(k), yk1)));
        } while (less(err, threshold));

        return y;
    }

    /** Returns X ** K, for K >= 0, with 0 ** 0 == 1. */
    static Rational power(Rational x, int k) {
        /* Strategy: Exponentiation via the Russian Peasant's Algorithm.
         * Java Note: m&1 is the units bit of m, hence 1 if m is odd and 0
         * if it is even. */

        if (equalrat(x, inttorat(0)) || equalrat(x, inttorat(1))
                || k == 1) {
            return x;
        }
        if (k == 0) {
            return inttorat(1);
        }
        Rational z;
        z = inttorat(1);
        while (k != 0) {
            if ((k & 1) == 1) {
                z = multrat(z, x);
            }
            k /= 2;
            x = multrat(x, x);
        }
        return z;
    }

    /** The command
     *       java Root ARGS
     *  where ARGS has the form x k, prints an approximation of the kth root
     *  of x, assuming x >= 0, k > 0. */
    public static void main(String... args) {
        Rational x = frac(args[0]);
        int k = Integer.parseInt(args[1]);
        System.out.printf("%s ** (1/%d) = %s%n", x, k, root(x, k));
    }

}
