package jump61;

import static jump61.GameException.error;

/** A Player that gets its moves from manual input.
 *  @author Conrad Shiao
 */
class HumanPlayer extends Player {

    /** A new player initially playing COLOR taking manual input of
     *  moves from GAME's input source. */
    HumanPlayer(Game game, Color color) {
        super(game, color);
    }

    @Override
    void makeMove() {
        Game game = getGame();
        Board board = getBoard();
        if (game.getMove(_position)) {
            int r = _position[0], c = _position[1];
            if (board.isLegal(getColor(), r, c)) {
                game.makeMove(r, c);
            } else {
                throw error("invalid move: %d %d", r, c);
            }
        }
    }

    /** The saved position that I will make a move on.
     *  First index represents row, second index represents column. */
    private final int[] _position = new int[2];
}
