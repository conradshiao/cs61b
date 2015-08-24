
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;


public class practice {

    /**
     * @param args
     */
    public static void main(String[] args) {
        HashSet<HashMap<String, String>> b = new HashSet<HashMap<String, String>>();
        HashMap<String, String> a = new HashMap<String, String>();
        b.add(a);
        System.out.println(b.contains(a));
        a.put("hi", "bye");
        b.add(a);
        System.out.println(b.size());
        for (HashMap<String, String> x : b){
            System.out.println(x.toString());
        }
        b.add(a);
        System.out.println(b.size());
       /** // TODO Auto-generated method stub
        HashSet<ArrayList<Integer>> temp = new HashSet<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(3);
        temp.add(a);
        //System.out.print(temp.contains(a));
        a.add(4);
        HashMap<String, String> l = new HashMap<String, String>();
        l.put("hi", "bye");
        TreeSet<HashMap<String, String>> b = new TreeSet<HashMap<String, String>>();
        b.add(l);
        System.out.println(b.contains(l));
        l.put("slkdf", "lskjf");
        System.out.println(b.contains(l));
       // System.out.print("hi: " + temp.contains(a));
    } */
        System.exit(0);
    }
}
