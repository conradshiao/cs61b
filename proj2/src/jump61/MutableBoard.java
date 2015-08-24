package jump61;


import static jump61.Color.*;
import static jump61.Defaults.*;
import java.util.ArrayList;
import java.util.Arrays;

/** A Jump61 board state.
 *  @author Conrad Shiao
 */
class MutableBoard extends Board {

    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _N = N;
        _gameBoard = new int[N * N];
    }

    /** A board whose initial contents are copied from BOARD0. Clears the
     *  undo history. */
    MutableBoard(Board board0) {
        copy(board0);
        clearUndoHistory();
    }

    @Override
    void clear(int N) {
        clearUndoHistory();
        _gameBoard = new int[N * N];
        _N = N;
        _moves = 1;
    }

    @Override
    void copy(Board board) {
        _N = board.size();
        this._gameBoard = Arrays.copyOf(board._gameBoard,
               board._gameBoard.length);
    }

    @Override
    int size() {
        return _N;
    }

    @Override
    int spots(int r, int c) {
        return spots(sqNum(r, c));
    }

    @Override
    int spots(int n) {
        return Math.abs(_gameBoard[n]);
    }

    @Override
    Color color(int r, int c) {
        return color(sqNum(r, c));
    }

    @Override
    Color color(int n) {
        int temp = _gameBoard[n];
        if (temp == NEUTRAL) {
            return WHITE;
        } else if (temp > NEUTRAL) {
            return RED;
        } else {
            return BLUE;
        }
    }

    @Override
    int numMoves() {
        return _moves;
    }

    @Override
    int numOfColor(Color color) {
        int counter = 0;
        for (int i = 0; i < _gameBoard.length; i++) {
            if (this.color(i) == color) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    void addSpot(Color player, int r, int c, boolean incrementMove) {
        addSpot(player, sqNum(r, c), incrementMove);
    }

    @Override
    void addSpot(Color player, int n, boolean incrementMove) {
        if (this.getWinner() != null) { // already have a winner
            return;
        }
        if (_moves == 1) {
            _lastBoards.add(0, new MutableBoard(this));
        }
        int newSpots = spots(n) + 1;
        _gameBoard[n] = (player == BLUE) ? -newSpots : newSpots;
      /*  _gameBoard[n] = spots(n) + 1;
        if (player == BLUE) {
            _gameBoard[n] = -_gameBoard[n];
        } */
        if (spots(n) > neighbors(n)) {
            jump(n);
        }
        if (incrementMove) {
            _moves++;
            this._lastBoards.add(0, new MutableBoard(this));
        }
    }

    @Override
    void set(int r, int c, int num, Color player) {
        set(sqNum(r, c), num, player);
    }

    @Override
    void set(int n, int num, Color player) {
        int setting;
        switch (num) {
        case 4:
            setting = FOUR_RED_SPOTS;
            break;
        case 3:
            setting = THREE_RED_SPOTS;
            break;
        case 2:
            setting = TWO_RED_SPOTS;
            break;
        case 1:
            setting = ONE_RED_SPOT;
            break;
        default:
            setting = NEUTRAL;
            break;
        }
        if (player == BLUE) {
            setting = -setting;
        }
        _gameBoard[n] = setting;
        clearUndoHistory();
    }

    @Override
    void setMoves(int num) {
        assert num > 0;
        _moves = num;
        clearUndoHistory();
    }

    @Override
    void undo() {
        if (_lastBoards.size() > 0) {
            _lastBoards.remove(0);
            if (_lastBoards.size() > 0) {
                copy(_lastBoards.get(0));
            }
            _moves--;
        }
    }

    /** Clears the undo history. Namely, empties the _lastBoard ArrayList. */
    void clearUndoHistory() {
        _lastBoards.clear();
    }

    /** Do all jumping on this board, assuming that initially, S is the only
     *  square that might be over-full. PLAYER is the color that will
     *  be added to the 4 neighboring spots. */
    private void jump(int S) {
        int r = row(S), c = col(S);
        Color player = this.color(S);
        _gameBoard[S] = (player == BLUE) ? -1 : 1;
       /* int newSpotNumber = (player == BLUE) ? -1 : 1;
        this._gameBoard[S] = newSpotNumber; */
        if (this.exists(r, c - 1)) {
            this.addSpot(player, r, c - 1, false);
        }
        if (this.exists(r, c + 1)) {
            this.addSpot(player, r, c + 1, false);
        }
        if (this.exists(r + 1, c)) {
            this.addSpot(player, r + 1, c, false);
        }
        if (this.exists(r - 1, c)) {
            this.addSpot(player, r - 1, c, false);
        }
    }

    /** Returns my undo history, this._lastBoards. */
    ArrayList<Board> getUndoHistory() {
        return this._lastBoards;
    }

    /** Total combined number of moves by both sides. */
    protected int _moves = 1;
    /** Convenience variable: size of board (squares along one edge). */
    private int _N;
    /** The undo history of boards. Index 0 will have the most recent board. */
    private ArrayList<Board> _lastBoards = new ArrayList<Board>();
}
