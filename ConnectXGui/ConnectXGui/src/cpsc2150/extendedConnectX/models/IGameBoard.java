package cpsc2150.extendedConnectX.models;

/**
 *  @author Himanshu Pallath
 *  @version 1.0
 *  This is an interface for the GameBoard class and GameBoardMem class. The board is a
 *  getNumRows() x getNumColumns board. In these interfaces there are some method that will be
 *  implemented in the GameBoard and GameBoardMeme classes along with some default secondary methods
 *  which are implemented in the interface using the primary methods
 *  Initialization ensures:
 *          Game board starts off filled with black spaces of size: getNumRows() x getNumColumns()
 *          (0,0) is the bottom left of the board
 *  Defines:
 *          rows : Z
 *          columns : Z
 *          numToWin : Z
 *  Constraints:
 *          3 {@code <=} rows {@code <} 100
 *          3 {@code <=} columns {@code <} 100
 *          3 {@code <=} numToWin {@code <} 25
 */
public interface IGameBoard {
    /**
     * This method places a token into column number c
     *
     * @param p The character that is going to be added to the column
     * @param c The column the character is being added to
     * @pre
     *          p must equal a valid player token AND
     *          0 {@code <=} c {@code <=} MAX_COLUMNS
     * @post
     *          the new token was placed at the lowest available row and
     *          the rest of the board remained the same
     */
    public void placeToken(char p, int c);

    /**
     * This function returns what is at the position pos on the game board
     *
     * @param pos a position on the game board
     * @return the char that is in the position pos of the game board
     * @pre
     *          0 {@code <=} pos.getRow() {@code <=} MAX_ROWS
     *          0 {@code <=} pos.getColumn() {@code <=} MAX_COLUMNS
     *          pos needs to be a valid position in the getNumRow() x getNumColumns() board
     * @post
     *          whatsAtPos = ' ' iff {pos has no token} AND
     *          whatsAtPos = token character iff {there is a token at pos}
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * The function returns the number of rows on the board
     * @return the number of rows on the board
     * @post
     *          getNumRows = the number of rows in the board
     */
    public int getNumRows();

    /**
     * The function returns the number of columns on the board
     * @return the number of columns on the board
     * @post
     *          getNumColumns = the number of columns in the board
     */
    public int getNumColumns();

    /**
     * The function returns the number of tokens in a row needed to win
     * @return the number of tokens in a row needed to win
     * @post getNumToWin = the number of tokens in a row needed to win
     */
    public int getNumToWin();

    /**
     * This function will check if the column is full
     * @param c The column being checked
     * @return true if the column is full and false if the column is not full
     * @pre
     *          0 {@code <=} c {@code <=} getNumColumns()
     * @post
     *          checkIfFree = true iff {column is full} AND
     *          checkIfFree = false iff {column is not full}
     */
    default public boolean checkIfFree(int c) {
        BoardPosition pos = new BoardPosition(getNumRows()-1, c);   //get the last row in the column
        return whatsAtPos(pos) == ' ';  //check if the last row in the column is empty
    }

    /**
     * This function checks to see if a player is at position pos
     *
     * @param pos a position on the board where we are checking
     * @param player the player we are checking to see if they are at position pos
     * @return true if player is at pos and false if not
     * @pre
     *          0 {@code <=} pos.getRow() {@code <=} getNumRows()
     *          0 {@code <=} pos.getColumn() {@code <=} getNumColumns()
     *          pos needs to be a valid position in the getNumRows() x getNumColumns() board AND
     *          player need to be either player 1 or player 2
     * @post
     *          isPlayerAtPos = true iff {player is at position pos} AND
     *          isPlayerAtPos = false iff {player is not at position pos} AND
     *          The player is at the board position pos AND
     *          board = #board
     */
    default public boolean isPlayerAtPos(BoardPosition pos, char player) {
        return whatsAtPos(pos) == player;
    }

    /**
     * This function will check to see if the last token placed in
     * column c resulted in the player winning the game. If so it will return
     * true, otherwise false.
     *
     * @param c The column the token was placed in
     * @return true if the player won or false if they didn't win
     * @pre
     *          c is the column where the last token wqs placed
     *          0 {@code <=} c {@code <=} getNumColumns()
     * @post
     *          checkForWin = true iff {the token placed is the last to make up the maximum number of
     *                                  consecutive same markers needed to win either vertically,
     *                                  horizontally, and diagonally} AND
     *          checkForWin = false iff {a player didn't win}
     */
    default public boolean checkForWin(int c) {
        int lastToken = 0;
        BoardPosition pos = new BoardPosition(lastToken, c);
        while (lastToken <= getNumRows()-1 && whatsAtPos(pos) != ' ') {
            lastToken++;
            pos = new BoardPosition(lastToken, c);
        }
        BoardPosition newPos  = new BoardPosition(lastToken-1, c);    //create a new position with the last token placed
        // check if the last token placed resulted in a horizontal win
        if (checkHorizWin(newPos, whatsAtPos(newPos))) {
            return true;
        }
        // check if the last token placed resulted in a vertical win
        else if (checkVertWin(newPos, whatsAtPos(newPos))) {
            return true;
        }
        // check if the last token placed resulted in a diagonal win
        else if (checkDiagWin(newPos, whatsAtPos(newPos))) {
            return true;
        }
        // if none of the above are true then the player didn't win
        else {
            return false;
        }
    }

    /**
     * This function will check to see if the game has resulted in a
     * tie. A game is tied if there are no free board positions remaining.
     *
     * @return true if the game is tied and false if the game is not tied
     * @pre
     *          The game doesn't have a winner yet
     * @post
     *          checkTie = true iff {none of the positions in the game board is empty} AND
     *          checkTie = false iff {there is a position on the game board that is empty}
     */
    default public boolean checkTie() {
        boolean check = true;
        // check if all the columns are full
        for (int i = 0; i < getNumColumns(); i++) {
            if (checkIfFree(i)) {
                check = false;   // if all the columns are full then check is true
                break;
            }
        }
        return check;   // return check
    }

    /**
     * This function checks to see if the last token placed (which was placed in
     * position pos by player p) resulted in getNumToWin() in a row horizontally. Returns
     * true if it does, otherwise false
     *
     * @param pos the position of where the last token was placed
     * @param p the player that placed the token in position pos
     * @return true of player won through a horizontal row of getNumToWin() tokens AND
     *         false is player didn't win
     * @pre
     *          pos needs to be a valid position in the getNumRows() x getNumColumns() board AND
     *          pos is the game board position on the latest play and the given marker is
     *              indeed located at that position AND
     *          p needs to be a valid player character
     * @post
     *          checkHorizWin = true iff {a placed marker is the last to make up the maximum
     *                                    number of consecutive same markers needed to win horizontally} AND
     *          checkHorizWin = false iff {player p didn't win the game}
     */
    default public boolean checkHorizWin(BoardPosition pos, char p) {
        int horizCounter = 0;
        int r = pos.getRow();
        int c = pos.getColumn();
        int tempC = c - 1;
        // check to the left of the last token placed
        if (c <= getNumColumns()-1) {
            while (c < getNumColumns() && whatsAtPos(pos) == p) {
                horizCounter++;     // increment the counter
                c++;
                pos = new BoardPosition(r, c);  // get the next position to the right
            }
        }
        // check to the right of the last token placed
        if (tempC >= 0) {
            BoardPosition newPos = new BoardPosition(r, tempC);
            while (tempC >= 0 && whatsAtPos(newPos) == p) {
                horizCounter++;     // increment the counter
                tempC--;
                newPos = new BoardPosition(r, tempC);   // get the next position to the left
            }
        }
        // if the counter is greater than or equal to the number of tokens needed to win return true else false
        if (horizCounter >= getNumToWin()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function checks to see if the last token placed (which was placed in
     * position pos by player p) resulted in getNumToWin() in a row vertically. Returns
     * true if it does, otherwise false
     *
     * @param pos the position of where the last token was placed
     * @param p the player that placed the token in position pos
     * @return true of player won through a Vertical column of getNumToWin() tokens AND
     *         false is player didn't win
     * @pre
     *          pos needs to be a valid position in the getNumRows() x getNumColumns() board AND
     *          pos is the game board position on the latest play
     *          p needs to be a valid player character
     * @post
     *          checkHorizWin = true iff {last placed marker is the last to make the maximum number of consecutive
     *                                    same markers needed for player p to win} AND
     *          checkHorizWin = false iff {player p didn't win the game}
     */
    default public boolean checkVertWin(BoardPosition pos, char p) {
        int vertCounter = 0;
        int r = pos.getRow();
        int c = pos.getColumn();
        // check below the last token placed
        while (r >= 0 && r < getNumRows() && whatsAtPos(pos) == p) {
            vertCounter++;  // increment the counter
            r--;
            pos = new BoardPosition(r, c);  // get the next position below
        }
        // if vertCounter is equal to the number of tokens needed to win return true else false
        if (vertCounter == getNumToWin()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function checks to see if the last token placed (which was placed in
     * position pos by player p) resulted in getNumToWin() in a row diagonally. Returns
     * true if it does, otherwise false
     *
     * @param pos the position of where the last token was placed
     * @param p the player that placed the token in position pos
     * @return true ff player won through a diagonal of getNumToWin() tokens AND
     *         false is player didn't win
     * @pre
     *          pos needs to be a valid position in the getNumRows() x getNumColumns() board AND
     *          p needs to be a valid player character
     * @post
     *          checkHorizWin = true iff { if a placed marker is the last to make up the maximum number
     *                                     of consecutive same markers needed to win diagonally} AND
     *          checkHorizWin = false iff {player p didn't win the game}
     */
    default public boolean checkDiagWin(BoardPosition pos, char p) {
        int diagCounter = 0;
        int r = pos.getRow();
        int c = pos.getColumn();
        int leftr = pos.getRow();
        int leftc = pos.getColumn();
        int leftTempC = c + 1;
        int leftTempR = r - 1;
        int rightTempC = c - 1;
        int rightTempR = r - 1;
        // checks the right diagonal to the right of the last token placed
        while (r < getNumRows() && c < getNumColumns() && whatsAtPos(pos) == p) {
            diagCounter++;  // increment the counter
            c++;
            r++;
            pos = new BoardPosition(r, c);  // get the next position to the right
        }
        // checks the right diagonal to the left of the last token placed
        if (rightTempC >= 0 && rightTempR >= 0) {
            BoardPosition rightPos = new BoardPosition(rightTempR, rightTempC);
            while (rightTempR >= 0 && rightTempC >= 0 && whatsAtPos(rightPos) == p) {
                diagCounter++;
                rightTempC--;
                rightTempR--;
                rightPos = new BoardPosition(rightTempR, rightTempC);
            }
        }
        // if diagCounter is equal to the number of tokens needed to win return true else false
        if (diagCounter >= getNumToWin()) {
            return true;
        } else {
            diagCounter = 0;
            // checks the left diagonal to the left of the last token placed
            BoardPosition leftPos = new BoardPosition(leftr, leftc);
            while (leftr < getNumRows() && leftc >= 0 && whatsAtPos(leftPos) == p) {
                diagCounter++;
                leftc--;
                leftr++;
                leftPos = new BoardPosition(leftr, leftc);
            }
            // checks the left diagonal to the right of the last token placed
            if (leftTempC < getNumColumns() && leftTempR >= 0) {
                BoardPosition leftTempPos = new BoardPosition(leftTempR, leftTempC);
                while (leftTempC < getNumColumns() && leftTempR >= 0 && whatsAtPos(leftTempPos) == p) {
                    diagCounter++;
                    leftTempC++;
                    leftTempR--;
                    leftTempPos = new BoardPosition(leftTempR, leftTempC);
                }
            }
            // if diagCounter is equal to the number of tokens needed to win return true else false
            return diagCounter >= getNumToWin();
        }
    }
}
