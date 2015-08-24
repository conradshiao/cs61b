import java.util.*;

public class SorterTester {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("hi");
        LinkedList<Integer> a = new LinkedList<Integer>();
        for (int i = 1; i < 5; i += 1) {
            a.add(i);
        }
        ListIterator<Integer> test = a.listIterator();
      //  @SuppressWarnings("unchecked")
     //   ListIterator<Integer> clone = (Lis
      /*  for (int k = 1; k <= 6; k++) {
            System.out.println(clone);
        } */
        System.out.println(test.next());
     
        System.out.println(test.previous());
        System.out.println(test.nextIndex());
        test.add(10);
        System.out.println("Printing out elements below");
        while (test.hasPrevious()) {
            test.previous();
        }
        while (test.hasNext()) {
            System.out.println(test.next());
        }
        /* test.previous();
        System.out.println(test.nextIndex());
        test.next(); test.previous();
        while (test.hasNext()) {
            System.out.println(test.next());
        } */
        for (int i = 0; i < 50; i++) {
            System.out.println(); // too lazy LOL
        }
        // real test begins here.
       // double[] collection = new double[] {1.1, -1.1, -6.7, 9.4, 3.0, 3.1, 3.1, 15.7, -30, 500};
        ArrayList<Double> collection = new ArrayList<Double>();
        collection.add(1.1);
        collection.add(-1.1);
        collection.add(-6.7);
        collection.add(9.4);
        collection.add(3.0);
        collection.add(3.1);
        collection.add(3.1);
        collection.add(15.7);
        collection.add(-30.0);
        collection.add(500.0);
        LinkedList<Double> tester = new LinkedList<Double>(collection);//new LinkedList<Double>(collection);
        Sorter3.sort(tester);
        System.out.println(tester.toString());
    }

}
