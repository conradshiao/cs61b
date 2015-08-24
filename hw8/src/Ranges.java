import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/** HW #8, Problem 2.
 *  @author Conrad Shiao
  */
class Ranges {
    /** Assuming that RANGES contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns
     *  a List of the intervals of [0 .. MAX] that are NOT covered by one of
     *  the intervals in RANGES. The resulting list consists of
     *  non-overlapping intervals ordered in increasing order of
     *  their starting points. */
    static List<int[]> uncovered(List<int[]> ranges, int max) {
        Collections.sort(ranges, new sortIntervals());
        combine(ranges);
        ArrayList<int[]> answer = new ArrayList<int[]>();
        ArrayList<int[]> modified = new ArrayList<int[]>();
        for (int[] temp : ranges) {
            modified.add(temp);
        }
        int min = 0;
        boolean maxFlag = false;
        while (!modified.isEmpty()) {
            int[] temp = modified.remove(0);
            if (temp[0] < max) {
                answer.add(new int[] {min, temp[0]});
                // if (temp[0] != 0) answer.add(new int[] {min, temp[0]}); // if don't want 1st interval of (0, 0)
                min = temp[1];
            } else {
                answer.add(new int[] {min, max});
                maxFlag = true;
                break;
            }
        }
        if (!maxFlag) {
            answer.add(new int[] {min, max});
        }
        return answer;
        
      /*  int min = 0;
        int[] remover = new int[2];
        int[] newInterval = new int[2];
        while (!ranges.isEmpty()) {
            remover = ranges.remove(0);
            if (remover[0] != 0) {
                newInterval[0] = min;
                newInterval[1] = remover[0] - 1;
                answer.add(newInterval);
            }
            if (remover[1] < max) {
                newInterval[1] = max;
                newInterval[0] = min;
                answer.add(newInterval);
            }
        }
        return answer; */
    }

    /** Combines overlapping interval ranges in RANGES into non-overlapping
     *  interval ranges.
     *  
     *  It is given that supplied intervals are sorted according
     *  to this.sortIntervals comparator. */
     static void combine(List<int[]> ranges) {        
        int k = 0;
        while (k < ranges.size() - 1) {
            int[] current = ranges.remove(k);
            int[] next = ranges.remove(k);
            if (checkIntervalOverlap(current, next)) {
                int rightEndpoint = Math.max(current[1], next[1]);
                current[1] = rightEndpoint;
                ranges.add(k, current);
            } else { // add back intervals and move on to next element
                ranges.add(k, current);
                ranges.add(k + 1, next);
                k += 1;
            }
        }
    }

    /** Returns true iff INT0 and INT1 are overlapping intervals. 
     *  It is given that the left endpoint of INT0 is less than or
     *  equal to the left endpoint of INT1. */
    private static boolean checkIntervalOverlap(int[] int0, int[] int1) {
        if (int0[1] < int1[0]) {
            return false;
        }
        return true;
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
}
