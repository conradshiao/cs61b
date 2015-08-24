package enigma;


/** Class that represents a rotor in the enigma machine.
 *  @author Conrad Shiao, cs61b-cz
 */
class Rotor {

    /** My current setting (index 0..25, with 0 indicating that 'A'
     *  is showing). */
    private int _setting;

    /** String Array of the info spec on this Rotor. */
    private String[] _infospecs;

    /** Return the Rotor object matched with NAME. */
    public Rotor(String name) {
     //   String[][] temp = PermutationData.ROTOR_SPECS;
        for (String[] infospec : PermutationData.ROTOR_SPECS){
            if (infospec[0].equals(name)) {
                _infospecs = infospec;
                break;
            }
        }
        /*
        for (int i = 0; i < temp.length; i++) {
            if (temp[i][0].equals(name)) {
                _infospecs = temp[i];
                break;
            }
        } */
    }

    /** Size of alphabet used for plaintext and ciphertext. */
    static final int ALPHABET_SIZE = 26;

    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    static char toLetter(int p) {
        return (char) (p + 'A');
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    static int toIndex(char c) {
        return c - 'A';
    }

    /** Returns true iff this rotor has a ratchet and can advance. */
    boolean advances() {
        return true;
    }

    /** Returns true iff this rotor has a left-to-right inverse. */
    boolean hasInverse() {
        return true;
    }

    /** Return my current rotational setting as an integer between 0
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    int getSetting() {
        return _setting;
    }

    /** Set getSetting() to POSN.  */
    void set(int posn) {
        assert 0 <= posn && posn < ALPHABET_SIZE;
        _setting = posn;
    }

    /** Returns int X mod 26, such that X falls in the range of the
     *  ASCII representation of A-Z. */
    private static int modHelper(int x) {
        if (x < 0) {
            return x + ALPHABET_SIZE;
        } else if (x >= ALPHABET_SIZE) {
            return (x - ALPHABET_SIZE);
        } else {
            return x;
        }
    }

    /** Returns abstraction method for convertForward and convertBackward,
     *  where P represents an integer in the range 0..25;
     * J = 1 corresponds to convertForward, while
     * J = 2 corresponds to convertBackward. */
    private int convert(int p, int j) {
        if (p < 0 || p > ALPHABET_SIZE - 1) {
            throw new IllegalArgumentException("Bad input character");
        }
        int x = modHelper(p + _setting);
        String permutation = _infospecs[j];
        char interest = permutation.charAt(x);
        return modHelper(toIndex(interest) - _setting);
    }

    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    int convertForward(int p) {
        return convert(p, 1);
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        return convert(e, 2);
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return _infospecs[3].contains("" + toLetter(_setting));
    }

    /** Advance me one position. */
    void advance() {
        _setting = (_setting + 1) % ALPHABET_SIZE;
    }

}
