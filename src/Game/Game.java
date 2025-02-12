package Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.Board;
import player.Player;


/**
 * The Game class represents an abstract game with players, a board, and a history of actions.
 * It provides methods to start the game, handle the game loop, and manage player actions and events.
 */
public abstract class Game {
    /**
     * List of players participating in the game.
     */
    protected List<Player> players;

    /**
     * The game board.
     */
    protected Board board;

    /**
     * History of actions taken during the game.
     */
    protected List<String> history;

    /**
     * The current turn number.
     */
    protected int currentTurn;

    /**
     * Scanner for reading player input.
     */
    protected Scanner scanner;

    /**
     * Constructs a new Game with the specified list of players.
     *
     * @param players the list of players participating in the game
     */
    public Game(List<Player> players) {}

    /**
     * Starts the game and initializes the game loop.
     */
    public void StartGame() {}

    /**
     * The main game loop that continues until a win condition is met.
     */
    public void gameLoop() {}

    /**
     * Advances to the next turn.
     */
    public void nextTurn() {}

    /**
     * Checks if the win condition for the game has been met.
     *
     * @return true if a player has won, false otherwise
     */
    public boolean CheckWinCondition() {}

    /**
     * Triggers an event in the game based on the specified event string.
     *
     * @param event the event to be triggered
     */
    public void triggerEvent(String event) {}

    /**
     * Handles a player action based on the specified action string.
     *
     * @param action the action to be handled
     */
    public void handleAction(String action) {}

    /**
     * Prints the history of actions taken during the game.
     */
    public void printHistory() {}
}
