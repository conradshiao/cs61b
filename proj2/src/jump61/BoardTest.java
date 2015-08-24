package jump61;

import static jump61.Color.*;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests of Boards.
 *  @author Conrad Shiao
 */
public class BoardTest {

    private static final String NL = System.getProperty("line.separator");

    @Test
    public void testSize() {
        Board B = new MutableBoard(5);
        assertEquals("bad length", 5, B.size());
        ConstantBoard C = new ConstantBoard(B);
        assertEquals("bad length", 5, C.size());
        assertEquals("bad length", 5, B.size());
        Board X = new MutableBoard(C);
        assertEquals("bad length", 5, C.size());
        Board D = new MutableBoard(B);
        assertEquals("bad length", 5, D.size());
        Board E = new MutableBoard(D);
        assertEquals("bad length", 5, E.size());
        ConstantBoard F = new ConstantBoard(E);
        assertEquals("bad length", 5, F.size());
        Board G = new ConstantBoard(F);
        assertEquals("bad length", 5, G.size());
        Board H = new MutableBoard(F);
        assertEquals("bad length", 5, H.size());
    }

    @Test
    public void testrow() {
        Board B = new MutableBoard(5);
        int test1 = B.row(5);
        assertEquals("square located in wrong row", 2, test1);
        int test2 = B.row(0);
        assertEquals("square located in wrong row", 1, test2);
        int test3 = B.row(10);
        assertEquals("square located in wrong row", 3, test3);
        int test4 = B.row(21);
        assertEquals("square located in wrong row", 5, test4);
    }

    @Test
    public void testcol() {
        Board B = new MutableBoard(6);
        int test1 = B.col(31);
        assertEquals("square is labeled in wrong column", 2, test1);
        int test2 = B.col(0);
        assertEquals("square is labeled in wrong column", 1, test2);
        int test3 = B.col(5);
        assertEquals("square is labeled in wrong column", 6, test3);
        int test4 = B.col(17);
        assertEquals("square is labeled in wrong column", 6, test4);
    }

    @Test
    public void testisLegal() {
        Board B = new MutableBoard(5);
        B.set(2, 2, 1, RED);
        assertTrue("illegal move allowed", B.isLegal(RED, 2, 2));
        assertTrue("illegal move allowed", !B.isLegal(BLUE, 2, 2));
        B.set(1, 1, 1, BLUE);
        assertTrue("illegal move allowed", !B.isLegal(RED, 1, 1));
        assertTrue("illegal move allowed", B.isLegal(RED, 5, 5));
        assertTrue("illegal move allowed", B.isLegal(RED, B.sqNum(2, 2)));
        B.setMoves(1);
        assertTrue("illegal move allowed", B.isLegal(RED, 2, 2));
        B.addSpot(BLUE, 5, 5, true);
        assertTrue("illegal move allowed", !B.isLegal(BLUE, 2, 2));
        assertTrue("illegal move allowed", B.isLegal(BLUE, 5, 5));
        assertTrue("illegal move allowed", B.isLegal(BLUE, 1, 1));
        assertTrue("illegal move allowed", B.isLegal(BLUE, B.sqNum(1, 1)));
    }

    @Test
    public void testsqNum() {
        Board B = new MutableBoard(5);
        int test1 = B.sqNum(1, 1);
        assertEquals("wrong square location", 0, test1);
        int test2 = B.sqNum(3, 4);
        assertEquals("wrong square location", 13, test2);
        int test3 = B.sqNum(4, 1);
        assertEquals("wrong square location", 15, test3);
    }

    @Test
    public void testNeighbors() {
        Board B = new MutableBoard(5);
        int test1 = B.neighbors(1, 1);
        assertEquals("wrong number of neighbors", 2, test1);
        int test2 = B.neighbors(1, 5);
        assertEquals("wrong number of neighbors", 2, test2);
        int test3 = B.neighbors(5, 5);
        assertEquals("wrong number of neighbors", 2, test3);
        int test4 = B.neighbors(5, 3);
        assertEquals("wrong number of neighbors", 3, test4);
        int test5 = B.neighbors(1, 3);
        assertEquals("wrong number of neighbors", 3, test5);
        int test6 = B.neighbors(4, 1);
        assertEquals("wrong number of neighbors", 3, test6);
        int test7 = B.neighbors(3, 3);
        assertEquals("wrong number of neighbors", 4, test7);

    }

    @Test
    public void testSet() {
        Board B = new MutableBoard(5);
        B.set(2, 2, 1, RED);
        B.setMoves(1);
        assertEquals("wrong number of spots", 1, B.spots(2, 2));
        assertEquals("wrong color", RED, B.color(2, 2));
        assertEquals("wrong count", 1, B.numOfColor(RED));
        assertEquals("wrong count", 0, B.numOfColor(BLUE));
        assertEquals("wrong count", 24, B.numOfColor(WHITE));
    }

    @Test
    public void testMove() {
        Board B = new MutableBoard(6);
        B.addSpot(RED, 1, 1, true);
        checkBoard("#1", B, 1, 1, 1, RED);
        B.addSpot(BLUE, 2, 1, true);
        checkBoard("#2", B, 1, 1, 1, RED, 2, 1, 1, BLUE);
        B.addSpot(RED, 1, 1, true);
        checkBoard("#3", B, 1, 1, 2, RED, 2, 1, 1, BLUE);
        B.addSpot(BLUE, 2, 1, true);
        checkBoard("#4", B, 1, 1, 2, RED, 2, 1, 2, BLUE);
        B.addSpot(RED, 1, 1, true);
        checkBoard("#5", B, 1, 1, 1, RED, 2, 1, 3, RED, 1, 2, 1, RED);
        B.undo();
        checkBoard("#4U", B, 1, 1, 2, RED, 2, 1, 2, BLUE);
        B.undo();
        checkBoard("#3U", B, 1, 1, 2, RED, 2, 1, 1, BLUE);
        B.undo();
        checkBoard("#2U", B, 1, 1, 1, RED, 2, 1, 1, BLUE);
        B.undo();
        checkBoard("#1U", B, 1, 1, 1, RED);
        B.undo();
    }

    @Test
    public void testgetWinner() {
        Board B = new MutableBoard(2);
        B.set(2, 2, 2, BLUE);
        B.set(2, 1, 1, BLUE);
        B.set(1, 2, 1, BLUE);
        B.set(1, 1, 1, RED);
        assertEquals("wrong number of moves", 1, B.numMoves());
        B.addSpot(RED, 1, 1, true);
        assertEquals("wrong number of moves", 2, B.numMoves());
        assertEquals("wrong winner", null, B.getWinner());
        B.addSpot(BLUE, 2, 2, true);
        assertEquals("wrong winner", null, B.getWinner());
        B.set(0, 1, BLUE);
        assertEquals("wrong winner", BLUE, B.getWinner());
    }

    private void checkBoard(String msg, Board B, Object... contents) {
        for (int k = 0; k < contents.length; k += 4) {
            String M = String.format("%s at %d %d", msg, contents[k],
                                     contents[k + 1]);
            assertEquals(M, (int) contents[k + 2],
                         B.spots((int) contents[k], (int) contents[k + 1]));
            assertEquals(M, contents[k + 3],
                         B.color((int) contents[k], (int) contents[k + 1]));
        }
        int c;
        c = 0;
        for (int i = B.size() * B.size() - 1; i >= 0; i -= 1) {
            assertTrue("bad white square #" + i,
                       (B.color(i) == WHITE) == (B.spots(i) == 0));
            if (B.color(i) != WHITE) {
                c += 1;
            }
        }
        assertEquals("extra squares filled", contents.length / 4, c);
    }

}
