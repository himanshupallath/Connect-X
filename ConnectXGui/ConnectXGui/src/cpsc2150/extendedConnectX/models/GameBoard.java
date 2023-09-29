package cpsc2150.extendedConnectX.models;
/**
 * @author Himanshu Pallath
 * @version 1.0
 * <p>The GameBoard class is the class that represents the game board as a 2D array.</p>
 * <p>This is going to be a MAX_ROWS x MAX_COLUMNS board where the user places tokens into.</p>
 * <p>The empty spaces in the board will be represented with ' ' and will be updated with
 *    player's token as the game goes on</p>
 * <p>This class holds all the primary methods for the default secondary methods in the interface</p>
 * @invariants
 *              MAX_ROWS {@code >=} MIN_SPACES_WIN AND MAX_ROWS {@code <=} MAX_SPACES AND
 *              MAX_COLUMNS {@code >=} MIN_SPACES_WIN AND MAX_COLUMNS {@code <=} MAX_SPACES AND
 *              NUM_TO_WIN {@code >=} MIN_SPACES_WIN AND NUM_TO_WIN {@code <=} MAX_WIN AND
 *              There should be no black spaces between 2 tokens in a column because all tokens must
 *              fall to the next empty row
 * @correspondence
 *              self = board[0..MAX_ROW-1][0..MAX_COLUMN-1]
 */
public class GameBoard implements IGameBoard {
    private char[][] board;
    private final int MAX_ROWS;          // The maximum number of rows in the game board
    private final int MAX_COLUMNS;       // The maximum number of columns in the game board
    private final int NUM_TO_WIN;        // The number of tokens in a row needed to win
    private final int MAX_SPACES = 100;  // The maximum number of spaces in the game board
    private final int MIN_SPACES_WIN = 3;    // The minimum number of spaces in the game board
    private final int MAX_WIN = 25;      // The maximum number of tokens in a row needed to win

    /**
     * Constructor: This is a constructor with no parameters. It will be used to initialize the size of
     * the game board and will all the positions with a space (' ').
     * @param rows the number of rows in the game board
     * @param columns the number of columns in the game board
     * @param numToWin the number of tokens in a row needed to win
     *
     * @pre
     *          rows {@code >=} MIN_SPACES_WIN AND rows {@code <=} MAX_SPACES AND
     *          columns {@code >=} MIN_SPACES_WIN AND columns {@code <=} MAX_SPACES AND
     *          numToWin {@code >=} MIN_SPACES_WIN AND numToWin {@code <=} MAX_WIN
     * @post
     *          board[MAX_ROW][MAX_COLUMN] = ' ';
     *          MAX_ROWS = rows;
     *          MAX_COLUMNS = columns;
     *          NUM_TO_WIN = numToWin;
     */
    public GameBoard(int rows, int columns, int numToWin) {
        MAX_ROWS = rows;
        MAX_COLUMNS = columns;
        NUM_TO_WIN = numToWin;
        board = new char[MAX_ROWS][MAX_COLUMNS];    // Creating the size of the game board
        // Initializing the game board with empty spaces
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * This method places a player's token into column number c
     *
     * @param p The character that is going to be added to the column
     * @param c The column the character is being added to
     * @pre
     *          p must to equal 'X' or 'O' AND
     *          0 {@code <=} c {@code <=} MAX_COLUMNS
     * @post
     *          the new token was placed at the lowest available row and
     *          the rest of the board remained the same
     */
    // MADE A CHANGE HERE
    public void placeToken(char p, int c) {
        int topEmpty = 0;   // The top empty row in the column
        // Finding the top empty row in the column
        while (board[topEmpty][c] != ' ') {
            topEmpty++;
        }
        board[topEmpty][c] = p; // Placing the token in the top empty row
    }

    /**
     * This function returns what is at the position pos on the game board
     *
     * @param pos a position on the game board
     * @return the char that is in the position pos of the game board
     * @pre
     *          0 {@code <=} pos.getRow() {@code <=} MAX_ROWS
     *          0 {@code <=} pos.getColumn() {@code <=} MAX_COLUMNS
     *          pos needs to be a valid position in the 9x7 board
     * @post
     *          whatsAtPos = ' ' iff {no token is at pos} AND
     *          whatsAtPos = token character iff {there is a token at pos
     */
    public char whatsAtPos(BoardPosition pos) {
        int r = pos.getRow();
        int c = pos.getColumn();
        return board[r][c];
    }

    /**
     * This functions returns the number of rows in the game board
     * @return the number of rows on the board
     * @post
     *          getNumRows = the number of rows on the board
     */
    public int getNumRows() {
        return MAX_ROWS;    // Returning the number of rows on the board
    }

    /**
     * This functions returns the number of columns in the game board
     * @return the number of columns on the board
     * @post
     *          getNumColumns = the number of columns on the board
     */
    public int getNumColumns() {
        return MAX_COLUMNS;    // Returning the number of columns on the board
    }

    /**
     * This functions returns the number of tokens in a row needed to win
     * @return the number of tokens in a row needed to win
     *
     * @post
     *          getNumToWin = the number of tokens in a row needed to win
     */
    public int getNumToWin() {
        return NUM_TO_WIN;      // Returning the number of tokens in a row needed to win
    }
}
