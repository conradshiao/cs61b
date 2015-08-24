package jump61;

import java.util.ArrayList;

/** An automated Player.
 *  @author Conrad Shiao
 */
class AI extends Player {

    /** A new player of GAME initially playing COLOR that chooses
     *  moves automatically.
     */
    AI(Game game, Color color) {
        super(game, color);
    }

    @Override
    void makeMove() {
        Game game = getGame();
        Board board = getBoard();
        Color me = getColor();
        int[] myBestMove = minmax(me, board, 4,
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        game.message("%s moves %d %d.%n", me.toCapitalizedString(),
                board.row(myBestMove[1]), board.col(myBestMove[1]));
        game.makeMove(myBestMove[1]);
    }

    /** Returns the heuristic value and associated move for color P and board
     *  B, to a given DEPTH. ALPHA and BETA is initially set to the -infinity
     *  and infinity, respectively, and adopt the regular definitions from
     *  alpha-beta pruning. The returned int array is of size 2, and will
     *  have the associated heuristic value at index 0 and the move,
     *  represented by the integer spot on the board, at index 1.
     */
    private int[] minmax(Color p, Board b, int depth, int alpha, int beta) {
        ArrayList<Integer> moves = possibleMoves(p, b);
        Color myColor = getColor();
        int value;
        int bestMove = -1;
        if ((b.getWinner() != null) || depth == 0) {
            value = staticEval(myColor, b);
            return new int[] {value, bestMove};
        } else {
            if (p == myColor) {
                for (int move: moves) {
                    b.addSpot(p, move, true);
                    value = minmax(p.opposite(), b, depth - 1, alpha, beta)[0];
                    if (value > alpha) {
                        alpha = value;
                        bestMove = move;
                    }
                    b.undo();
                    if (beta <= alpha) {
                        bestMove = move;
                        break;
                    }
                }
                return new int[] {alpha, bestMove};
            } else {
                for (int move: moves) {
                    b.addSpot(p, move, true);
                    value = minmax(p.opposite(), b, depth - 1, alpha, beta)[0];
                    if (value < beta) {
                        beta = value;
                        bestMove = move;
                    }
                    b.undo();
                    if (beta <= alpha) {
                        bestMove = move;
                        break;
                    }
                }
                return new int[] {beta, bestMove};
            }
        }
    }

    /** Returns heuristic value of board B for player P.
     *  Higher is better for P. */
    private int staticEval(Color p, Board b) {
        return b.numOfColor(p);
    }

    /** Returns valid possible moves, indexed by square number representation,
     *  of this player given board B and color
     *  P and in the form of an ArrayList. */
    private ArrayList<Integer> possibleMoves(Color p, Board b) {
        ArrayList<Integer> answer = new ArrayList<Integer>();
        Board board = getBoard();
        int boardSize = board.size();
        for (int k = 0; k < boardSize * boardSize; k++) {
            if (board.isLegal(p, k)) {
                answer.add(k);
            }
        }
        return answer;
    }
}


