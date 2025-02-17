package Game;
import java.util.ArrayList;
import java.util.List;
import action.ActionMaker;
import action.ActionRequest;
import action.util.IO;
import board.Board;
import player.Player;


/**
 * The Game class represents an abstract game with players, a board, and a history of actions.
 * It provides methods to start the game, handle the game loop, and manage player actions and events.
 */
public abstract class Game {

    public final List<Player> players;
    public final Board board;
    
    protected List<String> history;
    protected List<ActionRequest> pendingActions = new ArrayList<ActionRequest>();
    protected int currentTurn;
    protected ActionMaker ActionMaker;


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

    public Game(int nbOfPlayer, int SizeX, int SizeY){
        players = new ArrayList<Player>();
        for(int i = 1; i <= nbOfPlayer; i++ ){
            players.add(new Player(i));
        }
        board = new Board(SizeX, SizeY);
    }


    /**
     * Starts the game and initializes the game loop.
     */
    public void StartGame() {
        System.out.print("\033\143");
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
            int count = 0;
            for(int i = 0; i<pendingActions.size(); i++){
                ActionRequest req = pendingActions.get(i);
                if(req.ready){
                    IO.SlowType(req.action.Description());
                    req.action.Effect();
                    count++;
                }else{
                    updated.add(req);
                }
            }
            IO.DeleteLines(count);
            pendingActions = updated;
            board.UpdateAllTiles();
            String str = board.toString();
            System.out.println(str);
            linesToErase = str.split("\\n").length +1;
            nextTurn();
        }
    }

    /**
     * Advances to the next turn.
     */
    public void nextTurn() {
        for(Player p : players){
            IO.SlowType("C'est au tour de "+ p.toString());
            ActionRequest r = ActionMaker.Prompt(p);
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

    public List<Player> GetPlayers(){
        return players;
    }

}
