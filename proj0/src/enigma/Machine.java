package enigma;

import java.util.Arrays;

/**
 * Class that represents a complete enigma machine.
 * @author Conrad Shiao, cs61b-cz
 */
class Machine {

    /** My rotors, for this Machine. */
    private Rotor[] _myRotors = new Rotor[5];

    /**
     * Set my rotors to (from left to right) ROTORS. Initially, the rotor
     * settings are all 'A'.
     */
    void replaceRotors(Rotor[] rotors) {
        _myRotors = Arrays.copyOf(rotors, rotors.length);
    }

    /** Returns true iff SETTING contains all upper-case letters. */
    private boolean allUppercase(String setting) {
        for (int i = 0; i < setting.length(); i++) {
            if (Character.isLowerCase(setting.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Set my rotors according to SETTING, which must be a string of four
     * upper-case letters. The first letter refers to the leftmost rotor
     * setting.
     */
    void setRotors(String setting) {
        if (setting.length() != 4 || (!allUppercase(setting))) {
            throw new IllegalArgumentException(String.format("String %s must "
                    + "be exactly four upper-case letters.", setting));
        }
        for (int i = 0; i <= 3; i++) {
            _myRotors[i + 1].set(Rotor.toIndex(setting.charAt(i)));
        }
    }


    /** Sets my rotors in the convert method to the next configuration. */
    void setRotorsInConvert() {
        for (int i = 3; i < _myRotors.length; i++) {
            if (_myRotors[i].atNotch()) {
                for (int j = i - 1; j < _myRotors.length - 1; j++) {
                    _myRotors[j].advance();
                }
                break;
            }
        }
        _myRotors[4].advance();
    }

    /**
     * Returns character found from right to left signal processing, right AFTER
     * the signal passes through the reflector, with INPUT being an uppercase
     * character.
     */
    char convertHelperForward(char input) {
        int temp = Rotor.toIndex(input);
        for (int i = 4; i >= 0; i -= 1) {
            temp = _myRotors[i].convertForward(temp);
        }
        return Rotor.toLetter(temp);
    }

    /**
     * Returns character found after left to right signal processing, where
     * INPUT has already gone through the reflector and the returned value is
     * the last stage of the method convert.
     */
    char convertHelperBackward(char input) {
        int temp = Rotor.toIndex(input);
        for (int i = 1; i <= 4; i++) {
            temp = _myRotors[i].convertBackward(temp);
        }
        return Rotor.toLetter(temp);
    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of the rotors
     * accordingly.
     */
    String convert(String msg) {
        if (msg.isEmpty()) {
            return "";
        }
        String answer = "";
        for (int k = 0; k < msg.length(); k++) {
            setRotorsInConvert();
            char temp = convertHelperForward(msg.charAt(k));
            temp = convertHelperBackward(temp);
            answer += temp;
        }
        return answer;
    }
}
