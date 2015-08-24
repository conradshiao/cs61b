import java.util.Arrays;


public class Discussion2Practice {
    public static void main(String[] args) {
        /** IntList L = IntList.list(1,2,3,4);
        IntList M = L.tail.tail;
        System.out.println(L);
        System.out.println(M);
        IntList N = IntList.list(5,6,7);
        System.out.println(N);
        L = N.tail;
        System.out.println(L);
        System.out.println("this is M " + M);
        System.out.println("break to (a)"); */
        IntList x = IntList.list(1,2,3,4,5);
        IntList y = skipList(x,2);
        System.out.println(y);
        System.out.println("this one is x " + x);
        IntList A = IntList.list(-1,-2,-3,-4);
        IntList B = A;
        //B = skipList(B, 2);
        System.out.println(B);
        System.out.println("this is A " + A);
        IntList X = reverseList(A);
        System.out.println("this is reverseList test " + X);
        System.out.println(A);
    }
    
    public static IntList skipList(IntList L, int n) {
        IntList M = L;
        while (M != null) {
            for (int i = 0; i < n - 2; i++) {
                M = M.tail;
                if (M.tail == null) {
                    break;
                }
            }
            if (M.tail == null) {
                break;
            }
            M.tail = M.tail.tail;
            M = M.tail;
        }
        return L;
    }
    
    public static IntList reverseList(IntList L) {
        if (L == null) {
            return null;
        }
        IntList M = new IntList(L.head, null);
        L = L.tail;
        while (L != null) {
            M = new IntList(L.head, M);
            L = L.tail;
        }
        return M;
    }
    
    public static IntList destructiveReverseList(IntList L) {
        if (L == null) return null;
        IntList M = L;
        IntList N = L.tail;
        L.tail = null;
        while (N != null) {
            IntList temp = N.tail;
            N.tail = M;
            M = N;
            N = temp;
        }
        return M;
    }
}