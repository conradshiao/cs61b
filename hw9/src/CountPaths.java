import java.util.Scanner;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/** HW #9, problem 4.  Path-counting problem (translated and adapted by
 *  M. Dynin).
 *  @author Conrad Shiao */
public class CountPaths {

    /** Read in maze and print out number of paths. */
    public static void main(String[] ignored) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        scanner.useDelimiter("\\s+");
        try {
            setup(scanner);
            String keyMatch = scanner.next();
            ArrayList<String> rectangle = new ArrayList<String>();
            for (int k = _M; k > 0; k--) {
                rectangle.add(scanner.next());
            }
            int number = findNumberOfPaths(_M, _N, _r, _c, keyMatch, rectangle);
            System.out.printf("There are %d paths.", number);
        } catch (InputMismatchException e) {
            System.err.printf("Wrong type of arguments given.");
            System.exit(1);
        } catch (NoSuchElementException e) {
            System.err.printf("Not enough arguments were given.");
            System.exit(1);
        }
    }

    /** Read and initialize the arguments using SCANNER. */
    private static void setup(Scanner scanner) {
        _M = scanner.nextInt();
        _N = scanner.nextInt();
        _r = scanner.nextInt();
        _c = scanner.nextInt();
    }

    /** Returns the number of paths that match KEYMATCH in RECTANGLE,
    /*  which is of size M * N, starting from row R and column C of
    /*  RECTANGLE. */
    private static int findNumberOfPaths(int M, int N, int r, int c,
        String keyMatch, ArrayList<String> rectangle) {
        if (keyMatch.length() == 1) {
            if (keyMatch.equals(letter(rectangle, r, c))) {
                return 1;
            } else {
                return 0;
            }
        }
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = r + 1;
                int newCol = c + j;
                if (newRow >= 0 && newRow < M && newCol >= 0 && newCol < N) {
                    boolean temp;
                    temp = letter(rectangle, newRow, newCol).equals
                        (keyMatch.substring(1, 2));
                    if ((i != 0 || j != 0) && temp) {
                        count += findNumberOfPaths(M, N, newRow, newCol,
                                keyMatch.substring(1, keyMatch.length()),
                                rectangle);
                    }
                }
            }
        }
        return count;
    }

    /** Helper method that returns the letter at row R, column C in
    /*  RECTANGLE. Used for convenience in readability. */
    private static String letter(ArrayList<String> rectangle, int r, int c) {
        return rectangle.get(r).substring(c, c + 1);
    }

    /** The number of rows of the rectangle. */
    private static int _M;
    /** The number of columns of the rectangle. */
    private static int _N;
    /** The row-coordinate of the starting position. */
    private static int _r;
    /** The column-coordinate of the starting position. */
    private static int _c;
}
