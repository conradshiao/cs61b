package enigma;

/**
 * Class that represents a reflector in the enigma.
 * @author Conrad Shiao, cs61b-cz
 */
class Reflector extends Rotor {

    /** Constructor for this class which calls upon parent class
     *  using NAME as the passed parameter. */
    public Reflector(String name) {
        super(name);
    }

    @Override
    boolean hasInverse() {
        return false;
    }

    /** Returns a useless value; should never be called. */
    @Override
    int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }

}
