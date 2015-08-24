import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class hw8scratch {

    /**
     * @param args
     */
    public static void main(String[] args) {
        List<int[]> x = new ArrayList<int[]>();
        x.add(new int[] {1,2});
        x.add(new int[] {-5,-7});
        System.out.println(Arrays.toString(x.get(1)));
        System.out.println(Arrays.toString(x.get(0)));
        x.remove(0);
        System.out.println(Arrays.toString(x.get(0)));

    }

}
