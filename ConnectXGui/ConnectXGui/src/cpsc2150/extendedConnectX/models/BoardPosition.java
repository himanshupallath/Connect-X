package cpsc2150.extendedConnectX.models;

/**
 * @author Himanshu Pallath
 * @version 1.0
 * <p>The BoardPosition class will keep track of the individual cell for a board </p>
 *
 * @author Himanshu Pallath
 * @version 1.0
 *
 * @invariants 0 {@code <=} row {@code <=} getNumRows() AND 0 {@code <=} column {@code <=} getNumColumns()
 */
public class BoardPosition {
    private int board_Row;
    private int board_Column;

    /**
     * This is the constructor for the class that initializes the row and column
     * @param row row on the game board
     * @param column column on the game board
     *
     * @pre
     *          0 {@code <=} row {@code <=} getNumRows() AND 0 {@code <=} column {@code <=} getNumColumns()
     *
     * @post
     *          board_Row = row AND board_column = column
     *
     */
    public BoardPosition(int row, int column) {
        board_Row = row;    // row
        board_Column = column; // column
    }

    /**
     * This is a getter function which returns the row
     *
     * @return returns the current row on the game board
     *
     * @post
     *          getRow = board_Row AND board_Row = #board_Row AND board_Column = #board_Column
     */
    public int getRow() {
        return board_Row;
    }

    /**
     * This is a getter function which returns the column
     *
     * @return returns the current column on the game board
     *
     * @post
     *          getColumn = board_Column AND board_Row = #board_Row AND board_Column = #board_Column
     */
    public int getColumn() {
        return board_Column;
    }

    /**
     * The equals function is an overridden function from the Object class which checks the
     * equivalency between the row and column or two objects
     *
     * @param obj the object that is being compared to
     * @return true if the row equals the column and false if they don't equal
     *
     * @pre
     *          obj != null
     *
     * @post
     *          equals = true iff (this->board_Row = obj->board_Row AND this->board_Column = obj->boardColumn) AND
     *          equals = false iff (this->board_Row != obj->board_Row AND this->board_Column != obj->boardColumn)
     */
    public boolean equals(Object obj) {
        // if the object is null then return false
        if (obj == null) {
            return false;
        }
        // if the object is an instance of BoardPosition then return true
        if (obj instanceof BoardPosition) {
            BoardPosition other = (BoardPosition) obj;
            // if the row and column are equal then return true
            return (this.board_Row == other.board_Row && this.board_Column == other.board_Column);
        }
        return false; // return false if the object is not an instance of BoardPosition
    }

    /**
     * The toString function returns a string with the rows and columns written in a certain format
     *
     * @return a string that has the row and column in a certain format
     *
     * @post
     *          toString = "board_Row,board_Column"
     */
    public String toString() {
        return board_Row + "," + board_Column;
    }
}
