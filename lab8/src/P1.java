import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/** Solving problem #1.
 * @author Conrad Shiao
 */

public class P1 {

    /** Converts the input file with ARGS to the desired P1 output. */
    public static void main(String[] args) {
        if (args.length > 2 || args.length == 0) {
            System.err.printf("Input error: wrong number of arguments.%n");
            System.exit(1);
        }
        try {
            Scanner input = new Scanner(new FileReader(new File(args[0])));

            input.useDelimiter("\\n\\n+");
            int i = 0;
            while (input.hasNext()) {
                String group = input.next();
                int result = remainingSpaces(group);
                i += 1;
                System.out.printf("Image %d: %d%n%n", i, result);
            }
            input.close();
        } catch (IOException e) {
            System.err.printf("Input error: %s%n", e.getMessage());
            System.exit(1);
        }
    }

    /** Returns the leftover spaces for one group with TEXT. */
    public static int remainingSpaces(String text) {
        String[] lines = text.split("\\r?\\n");
        int[] spaces = new int[lines.length];
        for (int j = 0; j < lines.length; j++) {
            spaces[j] = numberOfSpaces(lines[j]);
        }
        int min = Integer.MAX_VALUE;
        for (int num: spaces) {
            if (min > num) {
                min = num;
            }
        }
        int sum = 0;
        for (int j = 0; j < lines.length; j++) {
            spaces[j] -= min;
            sum += spaces[j];
        }
        return sum;
    }

    /** Returns the number of spaces with LINE. */
    private static int numberOfSpaces(String line) {
        int first = line.indexOf(" ");
        if (first == -1) {
            return 0;
        } else {
            return line.lastIndexOf(" ") - first + 1;
        }
    }
}
