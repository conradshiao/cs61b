import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Scanner;
import java.util.regex.MatchResult;

/** Solving Problem #3.
 * @author Conrad Shiao
 */

public class P3 {

    /** Runs the whole code with files ARGS. */
    public static void main(String[] args) {
        if (args.length == 0) {
            usage();
            return;
        }

        if (args.length > 2) {
            usage();
            throw new IllegalArgumentException("need 1 or 2 files");
        }

        try {
            Reader reader;
            reader = new FileReader(new File(args[0]));

            PrintWriter output;

            if (args.length == 2) {
                output = new PrintWriter(new File(args[1]));
            } else {
                output = new PrintWriter(System.out);
            }

            Scanner input = new Scanner(reader);
            process(input, output);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /** Sets up the INPUT and OUTPUT. */
    static void process(Scanner input, PrintWriter output) {
        input = input.useDelimiter("\\s+");
        while (input.findWithinHorizon("\\d+", 0) != null) {
            MatchResult mat = input.match();
            helper(Integer.parseInt(mat.group(1)));
        }
    }

    /** Setting up the algorithm on length N numbers. */
    static void helper(int n) {
        String parameter = "";
        for (int i = 1; i <= n; i++) {
            parameter += 1;
        }
        int answer = algorithm(parameter);
        System.out.println("The smallest good numeral of length "
                + n + " is " + answer);
    }

    /** Returns and implements the algorithm, starting at PARAMETER. */
    static int algorithm(String parameter) {
        int x = Integer.parseInt(parameter);
        int y = 0;
        for (int j = 1; j <= x * 10; j *= 10) {
            y = x;
            for (int i = 1; i <= 3; i++) {
                if (testGood(String.valueOf(y))) {
                    break;
                }
                y += i * j;
            }
        }
        return y;
    }

    /** Return true iff X is a happy number. */
    static boolean testGood(String x) {
        for (int k = 1; k <= x.length(); k++) {
            for (int i = 0; i + Math.floor(k / 2) <= x.length(); i++) {
                if (x.substring(i, i + k).equals(
                        x.substring(i + k + 1, i + 2 * k))) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Print usage message. */
    private static void usage() {
        System.out.printf("Usage: java format.Main INFILE [OUTFILE]%n"
                          + "   Format INFILE, sending output to OUTFILE "
                          + "(default: standard output).%n");
    }
}
