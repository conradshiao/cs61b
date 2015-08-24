import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/** Demonstration of sentence reading for Project 1.
 *  @author Conrad Shiao, cs61b-cz
 */
class ReadFacts {

    /** Pattern describing sentences "<Name> is [not] the <Occupation>". */
    static final Pattern NAME_OCC_PATN =
        Pattern.compile("([A-Z][a-z]*)\\s+is\\s+(not)?"
                + "\\s*the\\s+([a-z]+)\\.");

    /** Print out the sentences in the file named ARGS[0]. */
    public static void main(String... args) {
        if (args.length != 1) {
            System.err.println("Usage: java ReadFacts FILENAME");
            System.exit(1);
        }

        Scanner inp;
        try {
            inp = new Scanner(new File(args[0]));
        } catch (IOException e) {
            System.err.printf("Could not read %s.%n", args[0]);
            System.exit(1);
            return;
        }

        while (true) {
            if (inp.findInLine(NAME_OCC_PATN) != null) {
                MatchResult mat = inp.match();
                String name = mat.group(1);
                boolean negated = mat.group(2) != null;
                String occupation = mat.group(3);
                System.out.printf("%s is%s the %s.%n",
                                  name, negated ? " not" : "", occupation);
            } else if (!inp.hasNextLine()) {
                break;
            } else {
                String rest = inp.nextLine();
                if (rest.matches(NAME_OCC_PATN.toString())) {
                    System.out.println("<TRAILING GARBAGE ON LINE>");
                }
            }
        }
    }
}
