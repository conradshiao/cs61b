/** HW 6, Problem 3.
 *  @author Conrad Shiao
 */
class BigMat {

    /** Returns the number, r (ZERO-INDEXED), of the row of A that contains the most 1's.
     *  When there is more than one such row, the smallest index.
     *  
     *  CODED CORRECTLY. */
    public static int mostOnes(BitMatrix A) {        
        int col = 0;
        int row = 0;
        int most = 0;
        while (row < A.size()) {
            while (A.get(row, col) == 1) {
                col++;
                most = row;
                if (col >= A.size()) {
                    return most;
                }
            }
            row++;
        }
        return most;
    }
}

