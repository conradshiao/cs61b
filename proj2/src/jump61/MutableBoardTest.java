package jump61;

import static jump61.Color.*;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests of MutableBoards.
 * @author Conrad Shiao */

public class MutableBoardTest {

    @Test
    public void testspots() {
        MutableBoard B = new MutableBoard(5);
        B.set(1, 1, 2, BLUE);
        B.set(5, 5, 2, RED);
        B.set(3, 4, 1, BLUE);
        assertEquals("wrong number of spots", 2, B.spots(1, 1));
        assertEquals("wrong number of spots", 2, B.spots(5, 5));
        assertEquals("wrong number of spots", 1, B.spots(3, 4));
        assertEquals("wrong number of spots", 2, B.spots(B.sqNum(1, 1)));
        assertEquals("wrong number of spots", 1, B.spots(B.sqNum(3, 4)));
    }

    @Test
    public void testCopy() {
        MutableBoard B = new MutableBoard(6);
        int[] temp1 = new int[36];
        boolean test1 = Arrays.equals(B._gameBoard, temp1);
        assertTrue("copy method has bug", test1);
        B.set(0, 2, BLUE);
        B.set(24, 2, RED);
        temp1[0] = -2; temp1[24] = 2;
        boolean test2 = Arrays.equals(B._gameBoard, temp1);
        assertTrue("copy method has bug", test2);
        MutableBoard C = new MutableBoard(B);
        assertTrue("copy method has bug",
                Arrays.equals(C._gameBoard, B._gameBoard));
        ConstantBoard D = new ConstantBoard(C);
        MutableBoard E = new MutableBoard(D);
        assertTrue("copy method has bug",
                Arrays.equals(E._gameBoard, D._gameBoard));
    }

    @Test
    public void testcolor() {
        MutableBoard B = new MutableBoard(5);
        assertEquals("wrong color at square", WHITE, B.color(0));
        B.set(1, 1, 2, BLUE);
        assertEquals("wrong color at square", BLUE, B.color(1, 1));
        assertEquals("wrong color at square", BLUE, B.color(0));
        B.set(3, 3, 3, RED);
        assertEquals("wrong color at square", RED, B.color(3, 3));
        assertEquals("wrong color at square", RED, B.color(B.sqNum(3, 3)));
    }

    @Test
    public void testnumOfColor() {
        MutableBoard B = new MutableBoard(5);
        assertEquals("wrong number of colors in game board",
                0, B.numOfColor(BLUE));
        for (int i = 0; i < 10; i++) {
            B.set(i, 2, BLUE);
        }
        assertEquals("wrong num of colors in board", 10, B.numOfColor(BLUE));
        B.set(1, 1, RED);
        assertEquals("wrong number of colors in board", 9, B.numOfColor(BLUE));
        assertEquals("wrong number of colors in board", 1, B.numOfColor(RED));
    }

    @Test
    public void testJumpandAddSpot() {
        MutableBoard B = new MutableBoard(5);
        B.set(3, 3, 4, RED);
        B.addSpot(RED, 3, 3, true);
        String testing = "wrong number of colors in game board";
        String moveTesting = "wrong number of moves in current game";
        assertEquals(testing, 1, B._gameBoard[B.sqNum(3, 3)]);
        assertEquals(testing, 1, B._gameBoard[B.sqNum(2, 3)]);
        assertEquals(testing, 1, B._gameBoard[B.sqNum(4, 3)]);
        assertEquals(testing, 1, B._gameBoard[B.sqNum(3, 4)]);
        assertEquals(testing, 1, B._gameBoard[B.sqNum(3, 2)]);
        assertEquals(moveTesting, 2, B.numMoves());
        B.set(1, 1, 2, BLUE);
        B.set(1, 2, 3, RED);
        B.addSpot(BLUE, 1, 1, true);
        assertEquals(testing, -2, B._gameBoard[B.sqNum(1, 1)]);
        assertEquals(testing, -1, B._gameBoard[B.sqNum(1, 2)]);
        assertEquals(testing, -1, B._gameBoard[B.sqNum(1, 3)]);
        assertEquals(testing, -1, B._gameBoard[B.sqNum(2, 1)]);
        assertEquals(testing, -1, B._gameBoard[B.sqNum(2, 2)]);
        assertEquals(moveTesting, 3, B.numMoves());
    }

}
