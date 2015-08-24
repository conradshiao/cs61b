
public class scratch {
    public static void main(String[] args) {
        /** int x = 3, y = 4;
        int xor, and, temp;
        and = x & y;
        xor = x | y;
        System.out.println(and);
        System.out.println(xor); */
        int x = 6, y = -3;
        int sum = Adder.add(x, y);
        System.out.println(sum);
        // System.out.println(carry);
       // int N = 9;
        //int[] data = new int[(N + 7) / 8];
        // System.out.println(data.length);
        Nybbles testArray = new Nybbles(9);
       // testArray._data[0] = 303240242;
        testArray.set(5,1);
        System.out.println("here testing now");
        System.out.println(testArray.get(5) == 1);
    }
}
