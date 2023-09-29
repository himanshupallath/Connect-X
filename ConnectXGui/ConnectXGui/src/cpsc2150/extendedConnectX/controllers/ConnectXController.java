package cpsc2150.extendedConnectX.controllers;

import cpsc2150.extendedConnectX.models.*;
import cpsc2150.extendedConnectX.views.*;
import java.util.*;

/**
 * The controller class will handle communication between our View and our Model ({@link IGameBoard})
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your your {@link BoardPosition} class, {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4.
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class.
 *
 * @version 2.0
 */
public class ConnectXController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private ConnectXView screen;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;
    
    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */
    private int numPlayers;

    /**
     * <p>
     * The private int for which player is currently playing.
     */
    private int curPlayer;
    /**
     * <p>
     * char array for the players.
     */
    private char[] players;

    /**
     * <p>
     * List to keep track of the rows for each column
     */
    ArrayList<Integer> colList = new ArrayList<Integer>();

    /**
     * <p>
     * boolean to keep track of if a player has won
     */
    private boolean win;
    /**
     * <p>
     * This creates a controller for running the Extended ConnectX game
     * </p>
     * 
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     * @param np
     *      The number of players for this game.
     * 
     * @post [ the controller will respond to actions on the view using the model. ]
     */
    public ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        this.numPlayers = np;
        // Some code is needed here.
        players = new char[]{'X', 'O', 'H', 'D', 'A', 'B', 'K', 'G', 'F', 'S'};
        curPlayer = 0;
        win = false;
        for (int i = 0; i < curGame.getNumColumns(); i++) {
            colList.add(0);
        }
    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     * 
     * @param col 
     *      The column of the activated button
     * 
     * @post [ will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int col) {
        // Your code goes here.
        String playerTurn = "";
        if (win) {
            win = false;
            newGame();
        }
        //check if the column is full
        if (curGame.checkIfFree(col)) {
            //place the token
            if (curPlayer + 1 == numPlayers) {
                playerTurn = "It is " + players[0] + "'s turn.";
            } else {
                playerTurn = "It is " + players[curPlayer+1] + "'s turn.";
            }
            screen.setMessage(playerTurn);
            curGame.placeToken(players[curPlayer], col);
            screen.setMarker(colList.get(col), col, players[curPlayer]);
            colList.set(col, colList.get(col) + 1);
            //check for a win
            if (curGame.checkForWin(col)) {
                screen.setMessage("Player " + players[curPlayer] + " wins! Press any button to play again.");
                win = true;
            } else if (curGame.checkTie()) {
                screen.setMessage("It's a tie! Press any button to play again.");
                win = true;
            }
        } else {
            screen.setMessage("Column is full");
        }
        curPlayer = (curPlayer + 1) % numPlayers;
    }

    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     * 
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}