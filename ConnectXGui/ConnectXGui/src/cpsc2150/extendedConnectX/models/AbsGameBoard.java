package cpsc2150.extendedConnectX.models;

/**
 *  @author Himanshu Pallath
 *  @version 1.0
 *  The AbsGameBoard class is a class that extends the GameBoard class. This class
 *  contains an overridden toString() method that creates the string for the board
 */
public abstract class AbsGameBoard implements IGameBoard {
    /**
     * The toString function returns a string that shows the entire gameboard
     *
     * @return a string that has the row and column in a certain format
     * @post
     *          toString = [ String representation of the game board ] AND
     *          self = #self
     */
    @Override
    public String toString() {
        String gameBoard = "";  // the string that will be returned
        // this for loop adds the top row of the board
        for (int i = 0; i < getNumColumns(); i++) {
            if (i < 10) {
                gameBoard += "| " + i;
            } else {
                gameBoard += "|" + i;
            }
        }
        gameBoard += "|\n";     // adds the last barrier and a new line
        // this for loop adds the rest of the board
        for (int i = getNumRows()-1; i >= 0; i--) {
            for (int j = 0; j < getNumColumns(); j++) {
                BoardPosition pos = new BoardPosition(i, j);    // creates a new position
                gameBoard += "|" + whatsAtPos(pos) + " ";        // adds the character at the position
            }
            gameBoard += "|\n";     // adds the last barrier and a new line
        }
        return gameBoard;   // returns the string
    }
}
