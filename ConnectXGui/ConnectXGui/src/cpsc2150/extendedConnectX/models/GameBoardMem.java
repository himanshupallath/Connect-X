package cpsc2150.extendedConnectX.models;
import java.util.*;

/**
 * @author Himanshu Pallath
 * @version 1.0
 * <p>The GameBoardMem class is the class that represents the game board as a map.</p>
 * <p>This is going to be a MAX_ROWS x MAX_COLUMNS board where the user places tokens into.</p>
 * <p>The map will have keys for each of the player's token along with a list of all the
 *    BoardPositions they occupy.
 * <p>This class holds all the primary methods for the default secondary methods in the interface</p>
 * @invariants
 *              MAX_ROWS {@code >=} 3 AND MAX_ROWS {@code <=} 100 AND
 *              MAX_COLUMNS {@code >=} 3 AND MAX_COLUMNS {@code <=} 100 AND
 *              NUM_TO_WIN {@code >=} 3 AND NUM_TO_WIN {@code <=} 25 AND
 *              There should be no black spaces between 2 tokens in a column because all tokens must
 *              fall to the next empty row
 * @correspondence
 *              self = board<Character, List<BoardPosition>>
 */
public class GameBoardMem implements IGameBoard{
    Map<Character, List<BoardPosition>> board = new HashMap<Character, List<BoardPosition>>();
    private final int MAX_ROWS;          // The maximum number of rows in the game board
    private final int MAX_COLUMNS;       // The maximum number of columns in the game board
    private final int NUM_TO_WIN;        // The number of tokens in a row needed to win
    private final int MAX_SPACES = 100;  // The maximum number of spaces in the game board
    private final int MIN_SPACES_WIN = 3;    // The minimum number of spaces in the game board
    private final int MAX_WIN = 25;      // The maximum number of tokens in a row needed to win


    /**
     * This is the constructor for the game board which will initialize the number of rows, columns,
     * and the number of tokens in a row needed to win
     * @param rows the number of rows in the game board
     * @param columns the number of columns in the game board
     * @param numToWin the number of tokens in a row needed to win
     *
     * @pre
     *          rows {@code >=} MIN_SPACES_WIN AND rows {@code <=} MAX_SPACES AND
     *          columns {@code >=} MIN_SPACES_WIN AND columns {@code <=} MAX_SPACES AND
     *          numToWin {@code >=} MIN_SPACES_WIN AND numToWin {@code <=} MAX_WIN
     * @post
     *          MAX_ROWS = rows AND
     *          MAX_COLUMNS = columns AND
     *          NUM_TO_WIN = numToWin AND
     *          board is initialized to an empty map
     */
    public GameBoardMem(int rows, int columns, int numToWin){
        MAX_ROWS = rows;
        MAX_COLUMNS = columns;
        NUM_TO_WIN = numToWin;
    }

    /**
     * This method places a token into the next available row in column number c
     *
     * @param p The character that is going to be added to the column
     * @param c The column the character is being added to
     *
     * @pre
     *          p must equal one of the characters for a player AND
     *          0 {@code <=} c {@code <=} MAX_COLUMNS
     *
     * @post
     *          the new token was placed at the lowest available row and
     *          the rest of the board remained the same
     */
    public void placeToken(char p, int c) {
        // Finding the top empty row in the column
        for (int i = 0; i < MAX_ROWS; i++) {
            BoardPosition pos = new BoardPosition(i, c);
            // Checks to see if the position is empty
            if (whatsAtPos(pos) == ' ') {
                // If key is not in the map, add it then add the position to the list
                if (!board.containsKey(p)) {
                    board.put(p, new ArrayList<BoardPosition>());
                    board.get(p).add(pos);
                } else {
                    // If key is in the map, add the position to the list
                    board.get(p).add(pos);
                }
                break;
            }
        }
    }

    /**
     * This method checks to see what character is at a given position
     *
     * @param pos the position that is being checked
     * @return the character at the given position
     *
     * @pre
     *          0 {@code <=} pos.getRow() {@code <=} MAX_ROWS
     *          0 {@code <=} pos.getColumn() {@code <=} MAX_COLUMNS
     *          pos needs to be a valid position in the MAX_ROWS x MAX_COLUMNS board
     * @post
     *          whatsAtPos = ' ' iff {no token is at pos} AND
     *          whatsAtPos = token character iff {there is a token at pos
     */
    public char whatsAtPos(BoardPosition pos) {
        // loops through each key in board
        for (Map.Entry<Character, List<BoardPosition>> entry : board.entrySet()) {
            List<BoardPosition> posList = entry.getValue();
            // loops through each BoardPosition in the list of BoardPositions
            for (BoardPosition position : posList) {
                // checks if the position is equal to pos
                if (position.equals(pos)) {
                    return entry.getKey();  // returns the key
                }
            }
        }
        return ' ';   // returns a space if there is no token at pos
    }

    /**
     * This is an overriden method which checks to see if 'player' is at position 'pos'
     * @param pos a position on the board where we are checking
     * @param player the player we are checking to see if they are at position pos
     * @return true if player is at position pos, false otherwise
     *
     * @pre
     *          0 {@code <=} pos.getRow() {@code <=} MAX_ROWS
     *          0 {@code <=} pos.getColumn() {@code <=} MAX_COLUMNS
     *          pos needs to be a valid position in the MAX_ROWS x MAX_COLUMNS board
     *          player must be one of the characters for a player
     * @post
     *          isPlayerAtPos = true iff {player is at position pos} AND
     *          isPlayerAtPos = false iff {player is not at position pos}
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        // loops through each key in board
        for (Map.Entry<Character, List<BoardPosition>> entry : board.entrySet()) {
            List<BoardPosition> posList = entry.getValue();
            // loops through each BoardPosition in the list of BoardPositions
            for (BoardPosition position : posList) {
                // checks if the position is equal to pos and if the key is equal to player
                if (position.equals(pos) && entry.getKey() == player) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method returns the number of rows in the game board
     * @return the number of rows in the game board
     *
     * @post
     *          getNumRows = number of rows in the game board
     */
    public int getNumRows() {
        return MAX_ROWS;    // returns the number of rows
    }

    /**
     * This method returns the number of columns in the game board
     * @return the number of columns in the game board
     *
     * @post
     *          getNumColumns = number of columns in the game board
     */
    public int getNumColumns() {
        return MAX_COLUMNS;    // returns the number of columns
    }

    /**
     * This method returns the number of tokens in a row needed to win
     * @return the number of tokens in a row needed to win
     *
     * @post
     *          getNumToWin = number of tokens in a row needed to win
     */
    public int getNumToWin() {
        return NUM_TO_WIN;    // returns the number of tokens in a row needed to win
    }
}
