package jump61;

import java.io.Reader;
import java.io.Writer;
import java.io.PrintWriter;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Random;

import static jump61.Color.*;
import static jump61.GameException.error;

/** Main logic for playing (a) game(s) of Jump61.
 *  @author Conrad Shiao
 */
class Game {

    /** Name of resource containing help message. */
    private static final String HELP = "jump61/Help.txt";

    /** A new Game that takes command/move input from INPUT, prints
     *  normal output on OUTPUT, prints prompts for input on PROMPTS,
     *  and prints error messages on ERROROUTPUT. The Game now "owns"
     *  INPUT, PROMPTS, OUTPUT, and ERROROUTPUT, and is responsible for
     *  closing them when its play method returns. */
    Game(Reader input, Writer prompts, Writer output, Writer errorOutput) {
        _board = new MutableBoard(Defaults.BOARD_SIZE);
        _readonlyBoard = new ConstantBoard(_board);
        _prompter = new PrintWriter(prompts, true);
        _inp = new Scanner(input);
        _inp.useDelimiter("(?m)\\p{Blank}*$|^\\p{Blank}*|\\p{Blank}+");
        _out = new PrintWriter(output, true);
        _err = new PrintWriter(errorOutput, true);
        _redplayer = new HumanPlayer(this, RED);
        _blueplayer = new AI(this, BLUE);
        setSeed(0);
    }

    /** Returns the mutable board. This board remains valid
     *  throughout the session. */
    Board getBoard() {
        return _board;
    }

    /** Play a session of Jump61.  This may include multiple games,
     *  and proceeds until the user exits.  Returns an exit code: 0 is
     *  normal; this game always exits with code 0, in following project
     *  specifications.  */
    int play() {
        _out.println("Welcome to " + Defaults.VERSION);
        while (!_quit) {
            if (!_playing) {
                promptForNext();
                readExecuteCommand();
            } else {
                try {
                    if (_board.whoseMove() == RED) {
                        _redplayer.makeMove();
                    } else {
                        _blueplayer.makeMove();
                    }
                    checkForWin();
                } catch (GameException e) {
                    reportError(e.getMessage());
                }
            }
        }
        _out.flush();
        return 0;
    }

    /** Get a move from my input and place its row and column in
     *  MOVE.  Returns true if this is successful, false if game stops
     *  or ends first. */
    boolean getMove(int[] move) {
        while (_playing && _move[0] == 0 && promptForNext()) {
            readExecuteCommand();
        }
        if (_move[0] > 0) {
            move[0] = _move[0];
            move[1] = _move[1];
            _move[0] = 0;
            return true;
        } else {
            return false;
        }
    }

    /** Add a spot to R C, if legal to do so. */
    void makeMove(int r, int c) {
        try {
            _board.addSpot(_board.whoseMove(), r, c, true);
        } catch (GameException e) {
            reportError(e.getMessage());
        }
    }

    /** Add a spot to square #N, if legal to do so. */
    void makeMove(int n) {
        this.makeMove(_board.row(n), _board.col(n));
    }

    /** Return a random integer in the range [0 .. N), uniformly
     *  distributed.  Requires N > 0. */
    int randInt(int n) {
        assert n > 0;
        return _random.nextInt(n);
    }

    /** Send a message to the user as determined by FORMAT and ARGS, which
     *  are interpreted as for String.format or PrintWriter.printf. */
    void message(String format, Object... args) {
        _out.printf(format, args);
    }

    /** Check whether we are playing and if there is an unannounced winner.
     *  If so, announce and stop play. */
    private void checkForWin() {
        if (_playing && _board.getWinner() != null) {
            announceWinner();
            _playing = false;
        }
    }

    /** Send announcement of winner to my user output. */
    private void announceWinner() {
        message("%s wins.%n", _board.getWinner().toCapitalizedString());
    }

    /** Make PLAYER an AI for subsequent moves. */
    private void setAuto(Color player) {
        _playing = false;
        if (player == RED) {
            _redplayer = new AI(this, player);
        } else {
            _blueplayer = new AI(this, player);
        }
    }

    /** Make PLAYER take manual input from the user for subsequent moves. */
    private void setManual(Color player) {
        _playing = false;
        if (player == RED) {
            _redplayer = new HumanPlayer(this, player);
        } else {
            _blueplayer = new HumanPlayer(this, player);
        }
    }

    /** Stop any current game and clear the board to its initial
     *  state. */
    private void clear() {
        _board.clear(_board.size());
        _playing = false;
    }

    /** Print the current board using standard board-dump format. */
    private void dump() {
        _out.println(_board);
    }

    /** Print a help message. */
    private void help() {
        Main.printHelpResource(HELP, _out);
    }

    /** Stop any current game and set the move number to N. */
    private void setMoveNumber(int n) {
        if (n < 0) {
            throw error("move number cannot be set to negative number");
        } else {
            _playing = false;
            _board.setMoves(n);
        }
    }

    /** Seed the random-number generator with SEED. */
    private void setSeed(long seed) {
        _random.setSeed(seed);
    }

    /** Place SPOTS spots on square R:C and color the square red or
     *  blue depending on whether COLOR is "r" or "b". If COLOR
     *  is neither "r" or "b", raise an error. Then, if SPOTS is
     *  0, clears the square, regardless of if COLOR is "r" or "b".
     *  SPOTS must be less than the number of neighbors of square R : C
     *  and cannot be less than 0. */
    private void setSpots(int r, int c, int spots, String color) {
        color = color.toLowerCase();
        if (!(color.equals("r") || color.equals("b"))) {
            throw error("syntax error in set command");
        } else if (!_board.exists(r, c)) {
            throw error("Cannot set spots because square %r %c is"
                    + "out of bounds");
        } else if ((spots > _board.neighbors(r, c)) || spots < 0) {
            throw error("invalid request to set %d spots on square "
                    + "%d %d", spots, r, c);
        } else {
            if (spots == 0) {
                _board.set(r, c, spots, WHITE);
            } else if (color.equals("r")) {
                _board.set(r, c, spots, RED);
            } else if (color.equals("b")) {
                _board.set(r, c, spots, BLUE);
            } else {
                throw error("horrible command");
            }
            _playing = false;
        }
    }

    /** Stop any current game and set the board to an empty N x N board
     *  with numMoves() == 0.  */
    private void setSize(int n) {
        if (n > 1) {
            _board.clear(n);
            _playing = false;
        } else {
            throw error("size of board must be greater than one");
        }
    }

    /** Begin accepting moves for game.  If the game is won,
     *  immediately print a win message and end the game. */
    private void restartGame() {
        _playing = true;
        this.checkForWin();
    }

    /** Save move R C in _move.  Error if R and C do not indicate an
     *  existing square on the current board. */
    private void saveMove(int r, int c) {
        if (!_board.exists(r, c)) {
            throw error("move %d %d out of bounds", r, c);
        } else {
            _move[0] = r;
            _move[1] = c;
        }
    }

    /** Returns a color (player) name from _inp: either RED or BLUE.
     *  Throws an exception if not present. */
    private Color readColor() {
        return Color.parseColor(_inp.next("[rR][eE][dD]|[Bb][Ll][Uu][Ee]"));
    }

    /** Read and execute one command.  Leave the input at the start of
     *  a line, if there is more input. */
    private void readExecuteCommand() {
        try {
            if (_inp.hasNextInt()) {
                if (_playing) {
                    int row = _inp.nextInt();
                    if (_inp.hasNextInt()) {
                        int col = _inp.nextInt();
                        saveMove(row, col);
                    } else {
                        throw error("Syntax error in 'move' command");
                    }
                } else {
                    throw error("No game currently in progress");
                }
            } else if (_inp.hasNext()) {
                executeCommand(_inp.next());
            } else {
                executeCommand("quit");
                return;
            }
        } catch (GameException e) {
            reportError(e.getMessage());
        } catch (NoSuchElementException e) {
            reportError(e.getMessage());
        }
        if (_newLine) {
            _newLine = false;
            return;
        }
        _inp.nextLine();
    }

    /** Gather arguments and execute command CMND.  Throws GameException
     *  on errors. */
    private void executeCommand(String cmnd) {
        try {
            switch (cmnd) {
            case "":
                System.out.println("ooh here");
            case "\n": case "\r\n":
                _newLine = true;
                return;
            case "#":
                break;
            case "clear":
                clear();
                break;
            case "start":
                restartGame();
                break;
            case "quit":
                _quit = true;
                _playing = false;
                break;
            case "auto":
                setAuto(readColor());
                break;
            case "manual":
                setManual(readColor());
                break;
            case "size":
                setSize(_inp.nextInt());
                break;
            case "move":
                setMoveNumber(_inp.nextInt());
                break;
            case "set":
                setSpots(_inp.nextInt(), _inp.nextInt(), _inp.nextInt(),
                        _inp.next());
                break;
            case "dump":
                dump();
                break;
            case "seed":
                setSeed(_inp.nextLong());
                break;
            case "help":
                help();
                break;
            default:
                throw error("bad command: '%s'", cmnd);
            }
        } catch (InputMismatchException e) {
            reportError(e.getMessage());
        } catch (NullPointerException e) {
            reportError("bad parameters");
        }
    }

    /** Print a prompt and wait for input. Returns true iff there is another
     *  token. */
    private boolean promptForNext() {
        _prompter.printf("%s> ", _playing ? _board.whoseMove() : "");
        return _inp.hasNext();
    }

    /** Send an error message to the user formed from arguments FORMAT
     *  and ARGS, whose meanings are as for printf. */
    void reportError(String format, Object... args) {
        _err.print("Error: ");
        _err.printf(format, args);
        _err.println();
    }

    /** Writer on which to print prompts for input. */
    private final PrintWriter _prompter;
    /** Scanner from current game input.  Initialized to return
     *  newlines as tokens. */
    private final Scanner _inp;
    /** Outlet for responses to the user. */
    private final PrintWriter _out;
    /** Outlet for error responses to the user. */
    private final PrintWriter _err;

    /** The board on which I record all moves. */
    private final Board _board;
    /** A readonly view of _board. */
    private final Board _readonlyBoard;

    /** A pseudo-random number generator used by players as needed. */
    private final Random _random = new Random();

    /** True iff a game is currently in progress. */
    private boolean _playing;
    /** True iff the user has called the quit command on the game. */
    private boolean _quit;
    /** True iff user has just inputted a blank line. */
    private boolean _newLine;

    /** The red player for this game. */
    private Player _redplayer;
    /** The blue player for this game. */
    private Player _blueplayer;


   /** Used to return a move entered from the console.  Allocated
    *  here to avoid allocations. */
    private final int[] _move = new int[2];
}
