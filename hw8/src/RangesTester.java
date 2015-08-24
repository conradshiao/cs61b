import java.util.*;

public class RangesTester {

    /**
     * @param args
     */
    public static void main(String[] args) {
         // ranges = {(1, 3), (1, 6), (1, 8), (12, 17), (22, 38), (25, 30), (19, 41), (47, 49)}
        ArrayList<int[]> ranges = new ArrayList<int[]>();
        ranges.add(new int[] {0, 3});
        ranges.add(new int[] {12, 17});
        ranges.add(new int[] {25, 30});
        ranges.add(new int[] {1, 6});
        ranges.add(new int[] {1, 8});
        ranges.add(new int[] {22, 38});
        ranges.add(new int[] {19, 41});
        ranges.add(new int[] {47, 49});
        boolean isOk = true;
        testcombine(ranges);
        System.out.println("back to main function here");
        List<int[]> res = Ranges.uncovered(ranges, 45);
     //   System.out.println(Arrays.toString(res.get(0)));
        System.out.println(res.size());
        for (int i = 0; i < res.size(); i += 1) {
            System.out.println(Arrays.toString(res.get(i)));
        }
      //  if (!equals(res.get(0), new int[] {0, 1})) isOk = false;
        if (!equals(res.get(0), new int[] {0, 0})) isOk = false;
        if (!equals(res.get(1), new int[] {8, 12})) isOk = false;
        if (!equals(res.get(2), new int[] {17, 19})) isOk = false;
        if (!equals(res.get(3), new int[] {41, 45})) isOk = false;
      //  if (!equals(res.get(4), new int[] {49, 60})) isOk = false;
        if (res.size() != 4) isOk = false;
        report("Ranges.uncovered", isOk);
    }
    
    private static void testcombine(List<int[]> ranges) {
      //  Ranges.combine(ranges);
       // System.out.println(Arrays.toString(ranges.get(0)));
        Collections.sort(ranges, new sortIntervals());
        Ranges.combine(ranges);
        System.out.println("hi");
        for (int[] temp : ranges) {
            System.out.println(Arrays.toString(temp));
        }
    }
    
    /** Helper class for this.uncovered method to sort intervals. */
    private static class sortIntervals implements Comparator<int[]> {

        @Override
        public int compare(int[] x, int[] y) {
            if (x[0] == y[0]) {
                return 0;
            } else if (x[0] > y[0]) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static void report(String name, boolean isOK) {
        String outcome = (isOK) ? "is OK." : "FAILS.";
        System.out.printf("%s %s%n", name, outcome);
    }
    
    private static boolean equals(int[] int0, int[] int1) {
        if (int0[0] != int1[0] || int0[1] != int1[1]) {
            return false;
        } else {
            return true;
        }
    }
}
