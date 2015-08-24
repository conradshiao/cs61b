import static java.lang.System.*;

import java.util.HashSet;

public class lab9scratch {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String x = "coNRad";
        FoldedString s = new FoldedString(x);
        FoldedString t = new FoldedString(x.toUpperCase());
        HashSet<FoldedString> set1 =
                new HashSet<FoldedString>(7, 2.0f);
        set1.add(s);
        set1.add(null);
        System.out.println(t.equals(s));
        System.out.println(s.equals(t));
        System.out.println(set1.contains(s));
        System.out.println(set1.contains(t));
        System.out.println(set1.contains(null));
        System.out.println(set1.size());
    }

}