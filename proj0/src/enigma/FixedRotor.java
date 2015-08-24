package enigma;

/**
 * Class that represents a rotor that has no ratchet and does not advance.
 * @author Conrad Shiao, cs61b-cz
 */
class FixedRotor extends Rotor {

    /** Constructor for this class, taking in parameter NAME. */
    public FixedRotor(String name) {
        super(name);
    }

    @Override
    boolean advances() {
        return false;
    }

    @Override
    boolean atNotch() {
        return false;
    }

    /** Fixed rotors do not advance. */
    @Override
    void advance() {
    }

}
