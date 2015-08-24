import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/** HW9, problem #3.
 *  @author Conrad Shiao
 */
public class Shuffle {
    /** Returns the result of non-destructively riffle-shuffling the
     *  two lists of T L1 and L2 together, using the
     *  Gilbert-Shannon-Reeds model, with R supplying pseudo-random
     *  numbers (so that shuffling the same lists twice with a
     *  generator starting in the same state both times results in the
     *  same list).
     */
    static <T> List<T> riffle(List<T> L1, List<T> L2, Random R) {
        List<T> res = new ArrayList<T>();
        R.setSeed(0);
        int list1Index = 0, list2Index = 0;
        while (list1Index < L1.size() && list2Index < L2.size()) {
            int A = L1.size() - list1Index, B = L2.size() - list2Index;
            double probabilityOfL1 = (double) A / (A + B);
            T head;
            if (R.nextDouble() < probabilityOfL1) {
                head = L1.get(list1Index);
                list1Index++;
            } else {
                head = L2.get(list2Index);
                list2Index++;
            }
            res.add(head);
        }
        if (list1Index == L1.size()) {
            for (T temp = L2.get(list2Index);
                    list2Index < L2.size(); list2Index++) {
                res.add(temp);
            }
        } else {
            for (T temp = L1.get(list1Index);
                    list1Index < L1.size(); list1Index++) {
                res.add(temp);
            }
        }
        return res;
    }
}
