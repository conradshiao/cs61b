package arith;

/** A rational number.  Members of this class, like the Integer and Double
 *  wrapper classes, are immutable.  Operations create new Rational objects
 *  rather than modifying existing ones.
 *  @author Conrad Shiao, cs61b-cz
 */
public class Rational {

    /** Return the rational number NUM/DEN, where DEN is non-zero. */
    public static Rational frac(long num, long den) {
        if (den == 0) {
            throw new IllegalArgumentException("den must be non-zero");
        }
        if (num == 0) {
            return new Rational(0, 1);
        }
        if (den < 0) {
            return new Rational(-num, -den);
        }
        long divisor = gcd(num, den);
        num = num / divisor;
        den = den / divisor;
        return new Rational(num, den);
    }

    /** Returns the rational number X. */
    public static Rational frac(long x) {
        return frac(x, 1);
    }

    /** Returns the rational number denoted by VAL, which must be of the form
     *  NUM/DEN, +NUM/DEN, -NUM/DEN, +NUM, or -NUM for NUM and DEN
     *  integer numerals and DEN a non-zero integer numeral. */
    public static Rational frac(String val) {
        char first = val.charAt(0);
        int count = 0;
        int count1;
        if (first == '+' || first == '-') {
            count = 1;
        }
        count1 = count;
        while (val.charAt(count1) != '/') {
            count1 += 1;
        }
        int num = Integer.parseInt(val.substring(count, count1 - 1));
        count = count1 + 1;
        count1 = count;
        int denom = Integer.parseInt(val.substring(count, val.length() - 1));
        return new Rational(num, denom);
    }

    /** Returns the value N, where THIS, in lowest terms, is N/D, and D>0. */
    public long numer() {
        return _num;
    }

    /** Returns the value D, where THIS, in lowest terms, is N/D, and D>0. If N
     *  is 0, returns 1. */
    public long denom() {
        return _den;
    }

    /** Returns my representation as a String.  Returns a String of the form
     *  N/D or -N/D, where N/D is a fraction in lowest terms, leaving off /D
     *  when D is 1. */
    public String toString() {
        if (_den == 1) {
            return String.format("%d", _num);
        } else {
            return String.format("%d/%d", _num, _den);
        }
    }

    /** I represent NUM/DEN, which are kept in lowest terms. */
    private final long _num, _den;

    /** A new Rational number whose value is NUM/DEN. */
    private Rational(long num, long den) {
        _num = num;
        _den = den;
    }

    /** Returns the positive greatest common divisor (X,Y) if X!=0 or
     *  Y!=0, or 0 if both X and Y are 0.  (X,Y) is defined as the
     *  largest positive integer that divides both X and Y. */
    private static long gcd(long x, long y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x > y) {
            x %= y;
        }
        while (x != 0) {
            long t = x;
            x = y % x;
            y = t;
        }
        return y;
    }

    /** Returns if Rational X is less than Rational Y. */
    public static boolean less(Rational x, Rational y) {
        return x.numer() * y.denom()
                < y.numer() * x.denom();
    }

    /** Returns Rational representation of integer number X. */
    public static Rational inttorat(int x) {
        return new Rational(x, 1);
    }

    /** Return Rational represented by multiplying X and Y, both Rational. */
    public static Rational multrat(Rational x, Rational y) {
        return new Rational(x.numer() * y.numer(),
                x.denom() * y.denom());
    }

    /** Returns the max of the Rational numbers X and Y. */
    public static Rational maxrat(Rational x, Rational y) {
        if (less(x, y)) {
            return y;
        }
        return x;
    }

    /** Returns the Rational number represented by adding
     * Rational X and Rational Y. */
    public static Rational addrat(Rational x, Rational y) {
        long denominator = x.denom() * y.denom();
        long numer1 = x.numer() * y.denom();
        long numer2 = x.denom() * y.numer();
        return new Rational(numer1 + numer2, denominator);
    }

    /** Returns the Rational number represented by subtracting
     * Y from X, both Rational. */
    public static Rational subrat(Rational x, Rational y) {
        Rational temp = new Rational(-y.numer(), y.denom());
        return addrat(x, temp);
    }

    /** Returns Rational number represented by division of Rational X
     * by Rational Y. */
    public static Rational dividerat(Rational x, Rational y) {
        return new Rational(x.numer() * y.denom(),
                x.denom() * y.numer());
    }

    /** Returns if X and Y, both Rational, are equal. */
    public static boolean equalrat(Rational x, Rational y) {
        long a = gcd(x.numer(), x.denom());
        long b = gcd(y.denom(), y.numer());
        return x.numer() / a == y.numer() / b && x.denom() / a == y.denom() / b;
    }
}
