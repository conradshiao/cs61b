package jump61;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.PriorityQueue;
import static java.lang.System.*;
import java.util.ArrayList;
import java.util.List;

public class proj2scratch {
    
    private final int[] _move = new int[2];

    /**
     * @param args
     */
    public static void main(String[] args) {
        int X = 3, y = 4;
        out.println("x is: " + X + " and y is: " + y);
        List<Integer> L1 = new ArrayList<Integer>();
        L1.add(3);
        System.out.println("L1 looks like " + Arrays.toString(L1.toArray()));
        List<Integer> list1 = new ArrayList<Integer>(L1);
        list1.remove((Object) 3);
        System.out.println("list1 looks like " + Arrays.toString(list1.toArray()));
        System.out.println("L1 looks like " + Arrays.toString(L1.toArray()));

        System.out.println();
        System.out.println("ldskfldsjks");
        String to = "to";
        String t = to.substring(1);
        String p = t.substring(1);
        System.out.println(p);
        System.out.println(p.equals(""));
        System.out.println("######################");
        
        String x = "4 \nis this working \n ha!";
        
        System.out.println("hi victor");
        int[][] victor = new int[][] {{3,4,5}, {6,7,8}};
        System.out.println(victor.length);
        System.out.println(victor[0].length);
        /**Scanner fi = new Scanner(x);
        String l = fi.next();
        //System.out.println(l);
        try {
            throw new IllegalArgumentException("this\nis\nsparta");
        } catch(IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } */
        String test = "    dog past there hi    \nthis \n this is here ! 3";
        Scanner testing = new Scanner(test);
        String line;
        for (line = testing.nextLine(); testing.hasNext() ; line = testing.nextLine()) {
            System.out.println("??");
            String[] temp = line.split("\\s+");
            System.out.println("temp is here: " + Arrays.toString(temp));
            for (String item : temp) {
                System.out.println(item);
            }
        }
    }
}

//class ScannerDemo {

   /** public static void main(String[] args) {
        try {
            String s = "Hello World! 3 + 3.0 = 6 ";
            

            PriorityQueue<Integer> x = new PriorityQueue<Integer>(5, new ScannerDemo.new ruiqi());
            System.out.println("lookee heeeeeeeereee" + x.comparator());
            // create a new scanner with the specified String Object
            Scanner scanner = new Scanner(s);
            boolean test1 = false;

            // check if next token matches the pattern and print it
            System.out.println("" + scanner.next("Hello"));

            System.out.println("" + scanner.next("World!"));
            System.out.printf("this%shere", "");
            System.out.println();
            System.out.println(test1 ? "hi" : 2);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    } /**
    
    static void helper() {
    }
    
    class ruiqi implements Comparator<Integer> {
        
        @Override
        public int compare(Integer x, Integer y) {
            if (x < y) {
                return -1;
            } else {
                return 1;
            }
        }

}
} */
