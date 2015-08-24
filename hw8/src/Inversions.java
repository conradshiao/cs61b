import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/** HW #8, Problem 4.
 *  @author Conrad Shiao
 */
public class Inversions {

    /** A main program for testing purposes.  Prints the number of inversions
     *  in the sequence ARGS. */
    public static void main(String[] args) {
        System.out.println(inversions(Arrays.asList(args)));
    }

    /** Return the number of inversions of T objects in ARGS. */
    public static <T extends Comparable<? super T>> int inversions(List<T> args)
    {
        ArrayList<T> temp = new ArrayList<T>(args);
        int count = 0;
        for (int i = 1; i < temp.size(); i++) {
            T x = temp.get(i);
            for (int j = i - 1; j >= 0; j--) {
                if (temp.get(j).compareTo(x) <= 0) {
                    count++;
                }
            }
        }
        return count;
    }

}
