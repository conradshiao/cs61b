import java.util.Arrays;


public class BigMatTester {

    /** Class to test the functionality and correct-ness of BitMat.java.
     *  Note: All rows and columns of a BitMatrix are ZERO-INDEXED.
     * @param args
     */
    public static void main(String[] args) {
        String function_name = "BigMat.mostOnes";
        BitMatrix test = new BitMatrix(20);
        for (int i = 0; i < test.size(); i++) {
            test.set(i, i, 1); // lower triangular BitMatrix of all 1's
        }
        report(function_name, BigMat.mostOnes(test) == 19);
        test = new BitMatrix(100);
        for (int i = 0; i < test.size(); i++) {
            int col = i + 37;
            if (col > test.size()) {
                col = col % test.size();
            }
            test.set(i, col, 1); // (0, 37), (1, 38), (x, 99) -> x = row 62;
        }
        report(function_name, BigMat.mostOnes(test) == 62);
        test = new BitMatrix(6);
        test.set(0, 3, 1);
        test.set(1, 2, 1);
        test.set(2, 1, 1);
        test.set(3, 3, 1);
        test.set(4, 4, 1);
        test.set(5, 4, 1);
        report(function_name, BigMat.mostOnes(test) == 4);
        test = new BitMatrix(10000);
        report(function_name, BigMat.mostOnes(test) == 0);
    }
        
    
    
    /** Helper function to check correctness of BigMat.MostOnes function. */
    private static void report(String function, boolean flag) {
        String isCorrect = (flag) ? "is OK" : "FAILS";
        System.out.printf("Function %s %s.%n", function, isCorrect);
    }
}
