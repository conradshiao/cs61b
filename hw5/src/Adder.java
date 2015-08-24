/** An alternative addition procedure.
 *  @author Conrad Shiao, cs61b-cz
 */
public class Adder {
    /** Returns X+Y. */
    public static int add(int x, int y) {
        int carry = 0, sum = 0;
        for (int i = 0; i < 32; i += 1) {
            int shifted = 1 << i;
            int tempX = x & shifted;
            int tempY = y & shifted;
            sum = sum | ((tempX ^ tempY) ^ carry);
            carry = ((tempX & tempY) | (carry & (tempX ^ tempY))) << 1;
        }
        return sum;
    }
    
    // Dumber alternate version
  /*  public static int add(int x, int y) {
        int carry = 0, sum = 0;
        for (int i = 0; i < 32; i++) {
            int shift = 1 << i;
            int tempX = (x & shift) >> i, tempY = (y & shift) >> i;
            sum = sum | (((tempX ^ tempY) ^ carry) << i);
            carry = ((tempX & tempY) | (carry & (tempX ^ tempY)));
        }
        return sum;
    } */
}
