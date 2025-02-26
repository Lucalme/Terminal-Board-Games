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
     * Classe abstraite représentant un jeu de plateau, tour par tour.
     * @param nbOfPlayer le nombre de joueurs
     */
    public Game(int nbOfPlayer) {
        players = new ArrayList<Player>();
        for(int i = 1; i <= nbOfPlayer; i++ ){
            players.add(new Player(i));
        }
        board = new Board();
        history = new ArrayList<String>();
        currentTurn = 0;
        ActionMaker = new ActionMaker(this);
    }

    /**
     * Classe abstraite représentant un jeu de plateau, tour par tour.
     * @param nbOfPlayer le nombre de joueurs
     * @param SizeX la taille du plateau en X
     * @param SizeY la taille du plateau en Y
     */
    public Game(int nbOfPlayer, int SizeX, int SizeY){
        players = new ArrayList<Player>();
        for(int i = 1; i <= nbOfPlayer; i++ ){
            players.add(new Player(i));
        }
        board = new Board(SizeX, SizeY);
    }


    /**
     * Démarre le jeu.
     */
    public void StartGame() {
        System.out.print("\033\143");
        gameLoop();
    }

    /**
     * La boucle principale du jeu.
     */
    protected void gameLoop() {
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
     * Représente un tour complet des joueurs.
     */
    protected void nextTurn() {
        for(Player p : players){
            IO.SlowType("C'est au tour de "+ p.toString());
            ActionRequest r = ActionMaker.Prompt(p);
            pendingActions.add(r);
            IO.DeleteLines(1);
        }
    }

    /**
     * TODO: Implémenter la condition de victoire.
     *
     * @return true si la condition de victoire est remplie
     */
    protected boolean CheckWinCondition() {
        return false;
    }

    public List<Player> GetPlayers(){
        return players;
    }

}
