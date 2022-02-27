package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.data.GameGrid.Player;

/**
 *  * @author Olle Josefsson
 *  **
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer {

    // Game variables
    private final int DEFAULT_SIZE = 15;
    private GameGrid gameGrid;

    // Possible game states
    private GameState currentState;

    private GomokuClient client;

    private String message;

    public static enum GameState {
        NOT_STARTED, MY_TURN, OTHER_TURN, FINISHED
    }

    /**
     * The constructor
     * 
     * @param gc The client used to communicate with the other player
     */
    public GomokuGameState(GomokuClient gc) {
        client = gc;
        client.addObserver(this);
        gc.setGameState(this);
        currentState = GameState.NOT_STARTED;
        gameGrid = new GameGrid(DEFAULT_SIZE);
    }

    /**
     * Returns the message string
     * 
     * @return the message string
     */
    public String getMessageString() {
        return message;
    }

    /**
     * Returns the game grid
     * 
     * @return the game grid
     */
    public GameGrid getGameGrid() {
        return gameGrid;
    }

    /**
     * This player makes a move at a specified location
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void move(int x, int y) {
        if (gameGrid.move(x, y) && currentState == GameState.MY_TURN) {
            gameGrid.grid[x][y] = Player.ME;
            client.sendMoveMessage(x, y);
            currentState = GameState.OTHER_TURN;
            if(gameGrid.isWinner(Player.ME)) {
                currentState = GameState.FINISHED;
                ChangeMessage("Player1 Won");
            }
        }else {
            ChangeMessage("That move can't be made.");
        }
        setChanged();
        notifyObservers();

    }

    /**
     * Starts a new game with the current client
     */
    public void newGame() {
        if(currentState == GameState.NOT_STARTED) {
            return;
        }
        gameGrid.clearGrid();
        ChangeMessage("Player2s turn");
        currentState = GameState.OTHER_TURN;
        client.sendNewGameMessage();
    }

    /**
     * Other player has requested a new game, so the game state is changed
     * accordingly
     */
    public void receivedNewGame() {
        currentState = GameState.MY_TURN;
        gameGrid.clearGrid();
        ChangeMessage("Game has started");
        setChanged();
        notifyObservers();
    }

    /**
     * The connection to the other player is lost, so the game is interrupted
     */
    public void otherGuyLeft() {
        currentState = GameState.FINISHED;
        gameGrid.clearGrid();
        setChanged();
        notifyObservers();
    }

    /**
     * The player disconnects from the client
     */
    public void disconnect() {
        currentState = GameState.FINISHED;
        gameGrid.clearGrid();
        client.disconnect();
        setChanged();
        notifyObservers();
    }

    /**
     * The player receives a move from the other player
     * 
     * @param x The x coordinate of the move
     * @param y The y coordinate of the move
     */
    public void receivedMove(int x, int y) {
        if (gameGrid.move(x, y) && currentState == GameState.MY_TURN) {
            gameGrid.grid[x][y] = Player.OTHER;
            currentState = GameState.MY_TURN;
            if(gameGrid.isWinner(Player.OTHER)) {
                currentState = GameState.FINISHED;
                ChangeMessage("Player2 Won");
            }
        }else {
            ChangeMessage("That move can't be made.");
        }
    }

    public void update(Observable o, Object arg) {
        switch (client.getConnectionStatus()) {
        case GomokuClient.CLIENT:
            message = "Game started, it is your turn!";
            currentState = GameState.MY_TURN;
            break;
        case GomokuClient.SERVER:
            message = "Game started, waiting for other player...";
            currentState = GameState.OTHER_TURN;
            break;
        }
        setChanged();
        notifyObservers();

    }

    private void ChangeMessage(String newMessage) {
        message = newMessage;
        setChanged();
        notifyObservers();
    }

}
