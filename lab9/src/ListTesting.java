import java.util.LinkedList;
import java.util.ListIterator;


/** Class for exploring properties of LinkedLists.
 *  @author Conrad Shiao
 */
class ListTesting {

    /** Carry out a test on a list of size ARGS[0] (converted to an
     *  integer), or 1000 by default.
     *  This prints out the integers 1 to N sequentially, with 1
     *  being printed out again after every 10*k number, for a given
     *  positive-integer k. */
    public static void main(String... args) {
        int N;
        N = 1000;
        if (args.length > 0) {
            N = Integer.parseInt(args[0]);
        }
        LinkedList<Integer> tester = new LinkedList<Integer>();
        for (int i = 1; i <= N; i++) {
            tester.add(i);
        }
        ListIterator<Integer> x = tester.listIterator();
        int counter = 0;
        while (x.hasNext()) {
            System.out.println(x.next());
            counter++;
            if (counter % 10 == 0 && counter != N) {
                backToThePast(x);
            }
        }
    }

    /** This method prints out the first element in X, then moves
     *  the cursor of ListIterator back to where it
     *  was previously. */
    public static void backToThePast(ListIterator<Integer> x) {
        x.previous();
        int startingPoint = x.nextIndex();
        while (x.hasPrevious()) {
            x.previous();
        }
        System.out.println(x.next());
        while (x.nextIndex() <= startingPoint) {
            x.next();
        }
    }
}
