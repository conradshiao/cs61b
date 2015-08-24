package enigma;

import static enigma.PermutationData.rotorName;

import java.awt.List;
import java.lang.reflect.Array;
import java.util.*;
import java.util.LinkedList;
import java.util.*;

public class Scratch {

    public static void main(String[] args) {
        /**
         * int p = 0; // char x = (int) (p + 'Z'); // int temp = x;
         * System.out.println(Rotor.toIndex(Rotor.toLetter(3))); //
         * System.out.println(x);
         * 
         * // \\s+ woul be if more than one whitespace is encountered. String[]
         * boop = new String[2]; boop[0] = "hi"; boop[1] = "bye";
         * System.out.println(Arrays.asList(boop).contains("bye"));
         * 
         * /* String[] x = new String[] {"hi","bye","conrad"};
         * System.out.println(Arrays.toString(x));
         * System.out.println(Arrays.asList(x).subList(1,
         */
        String x = "conrad";
        System.out.println(x.hashCode());
        int[] y = new int[] {3,4,5};
        System.out.println(Arrays.toString(y).toString());
        LinkedList<Integer> a = new LinkedList<Integer>();
        System.out.println(y.length);
    }
}

