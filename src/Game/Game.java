package Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import action.Action;
import action.ActionRequest;
import action.util.IO;
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
    public final List<Player> players;

    /**
     * The game board.
     */
    public final Board board;

    /**
     * History of actions taken during the game.
     */
    protected List<String> history;

    protected List<ActionRequest> pendingActions = new ArrayList<ActionRequest>();

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
    public Game(int nbOfPlayer) {
        players = new ArrayList<Player>();
        for(int i = 1; i <= nbOfPlayer; i++ ){
            players.add(new Player(i));
        }
        board = new Board();
    }

    /**
     * Starts the game and initializes the game loop.
     */
    public void StartGame() {
        gameLoop();
    }

    /**
     * The main game loop that continues until a win condition is met.
     */
    private void gameLoop() {
        int linesToErase = 0;
        while(!CheckWinCondition()){
            IO.DeleteLines(linesToErase);
            ArrayList<ActionRequest> updated = new ArrayList<ActionRequest>();
            for(int i = 0; i<pendingActions.size(); i++){
                ActionRequest req = pendingActions.get(i);
                if(req.ready){
                    IO.SlowType(req.action.Description());
                    req.action.Effect();
                }else{
                    updated.add(req);
                }
            }
            pendingActions = updated;
            String str = board.toString();
            System.out.println(str);
            linesToErase = str.split("\\n").length -1;
            nextTurn();
        }
    }

    /**
     * Advances to the next turn.
     */
    public void nextTurn() {
        for(Player p : players){
            IO.SlowType("C'est au tour de "+ p.toString());
            ActionRequest r = ActionRequest.Prompt(p, this);
            pendingActions.add(r);
            IO.DeleteLines(1);
        }
    }

    /**
     * Checks if the win condition for the game has been met.
     *
     * @return true if a player has won, false otherwise
     */
    public boolean CheckWinCondition() {
        return false;
    }

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
    public void handleAction(Action action) {
    }

    /**
     * Prints the history of actions taken during the game.
     */
    public void printHistory() {}

}
