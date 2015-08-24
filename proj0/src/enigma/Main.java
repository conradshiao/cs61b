package enigma;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.Arrays;
import static enigma.PermutationData.rotorName;

/** Enigma simulator.
 *  @author Conrad Shiao, cs61b-cz
 */
public final class Main {

    /** String array containing all possible reflector names from PermutationData.java. */
    private static String[] _reflectors = {rotorName(10), rotorName(11)};

    /** String array containing all possible fixed rotor names from PermutationData.java. */
    private static String[] _fixedRotors = {rotorName(8), rotorName(9)};

    /** String array containing all possible right rotor names from PermutationData.java. */
    private static String[] _rightRotors = {rotorName(0), rotorName(1), rotorName(2),
            rotorName(3), rotorName(4), rotorName(5), rotorName(6), rotorName(7)};

    /** Process a sequence of encryptions and decryptions, as
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    public static void main(String[] unused) {
        Machine M;
        BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));

        M = null;

        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (isConfigurationLine(line)) {
                    M = new Machine();
                    configure(M, line);
                } else {
                    if (M == null) {
                        System.exit(1);
                    }
                    printMessageLine(M.convert(standardize(line)));
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }

    /** Return true iff LINE is an Enigma configuration line. */
    public static boolean isConfigurationLine(String line) {
        String errMsg = "Configuration line '%s' is erroneously formatted";
        String[] temp = line.split("\\s+");
        if (!temp[0].equals("*")) {
            return false;
        }
        if (temp[0].equals("*") && temp.length == 7) {
            if (Arrays.asList(_reflectors).contains(temp[1])) {
                if (Arrays.asList(_fixedRotors).contains(temp[2])) {
                    if (Pattern.matches("[A-Z]*", temp[6]) && temp[6].length() == 4) {
                        String[] used = new String[3];
                        for (int i = 3; i <= 5; i++) {
                            if ((!Arrays.asList(_rightRotors).contains(temp[i])) || Arrays.asList(used).contains(temp[i])) {
                                System.err.printf(errMsg, line);
                                System.exit(1);
                            } else {
                                used[i - 3] = temp[i];
                            }
                        }
                        return true;
                    }
                }
            }
        }
        System.err.printf(errMsg, line);
        System.exit(1);
        return false;
    }

    /** Configure M according to the specification given on CONFIG,
     *  which must have the format specified in the assignment. */
    private static void configure(Machine M, String config) {
        String[] key = config.split("\\s+");
        Rotor[] rotors = new Rotor[5];
        rotors[0] = new Reflector(key[1]);
        rotors[1] = new FixedRotor(key[2]);
        for (int i = 2; i < rotors.length; i += 1) {
            rotors[i] = new Rotor(key[i + 1]);
        }
        M.replaceRotors(rotors);
        M.setRotors(key[6]);
    }

    /** Return the result of converting LINE to all upper case,
     *  removing all blanks and tabs.  It is an error if LINE contains
     *  characters other than letters and blanks. */
    public static String standardize(String line) {
        String answer = line.replaceAll("\\s", "").toUpperCase();
        if (!answer.matches("[A-Z]*")) {
            System.exit(1);
        }
        return answer;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private static void printMessageLine(String msg) {
        int count = 0;
        for (int i = 0; i < msg.length(); i++) {
            System.out.print(msg.charAt(i));
            if ((count + 1) % 5 == 0) {
                System.out.print(" ");
            }
            count += 1;
        }
        System.out.println("");
    }
}

