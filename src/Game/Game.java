package Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Objectives.ObjectiveType;
import Objectives.Objectives;
import action.ActionMaker;
import action.ActionRequest;
import action.util.IO;
import board.Board;
import board.resource.ResourceType;
import building.Building;
import building.Farm;
import player.COM;
import player.Player;
import demeter.Demeter;


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
    protected Objectives objectives;


    /**
     * Classe abstraite représentant un jeu de plateau, tour par tour.
     * @param nbOfPlayer le nombre de joueurs
     */
    public Game(int nbOfPlayer) {
        players = new ArrayList<Player>();
        for(int i = 1; i <= nbOfPlayer; i++ ){
            players.add(new Player(i, this));
        }
        board = new Board();
        history = new ArrayList<String>();
        currentTurn = 0;
        objectives = new Objectives();
        initializeObjectives();
    }

    public Game(List<Player> players){
        board = new Board(10, 10);
        this.players = players;
        history = new ArrayList<String>();
        currentTurn = 0;
    }

    public Game(boolean COMGame, int nbOfPlayer){
        players = new ArrayList<Player>();
        for(int i = 1; i <= nbOfPlayer; i++ ){
            players.add(new COM(this));
        }
        board = new Board();
        history = new ArrayList<String>();
        currentTurn = 0;
        objectives = new Objectives();
        initializeObjectives();
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
            players.add(new Player(i, this));
        }
        board = new Board(SizeX, SizeY);
        history = new ArrayList<String>();
        currentTurn = 0;
        objectives = new Objectives();
        initializeObjectives();
    }

    /**
     * Initialise les objectifs des joueurs.
     */
    private void initializeObjectives(){
        for(Player player : players){
            objectives.setObjective(player, ObjectiveType.getRandomObjective());
        }
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
            for(int i = 0; i<pendingActions.size(); i++){ //TODO: executer les actions directement pour éviter les conflits.
                ActionRequest req = pendingActions.get(i);
                if(req.ready){
                    req.action.Effect();
                    IO.SlowType(req.action.Description(), 40);
                    count++;
                }else{
                    updated.add(req);
                }
            }
            IO.DeleteLines(count);
            pendingActions = updated;
            board.UpdateAllTiles(); //mise à jour de toutes les resources de toutes les tiles. 
            for(Map.Entry<Building, Player> entry : board.getBuildings().entrySet()){ //distribution des resources
                Building b = entry.getKey();
                int nbr = b.tile.GetResourcesPresent();
                ResourceType r = b.tile.GetResourceType();
                Player p = entry.getValue();
                p.addResource(r, nbr);
                b.tile.ClearResources();
            }
            String str = board.toString();
            System.out.println(str);
            linesToErase = str.split("\\n").length +1;
            
            nextTurn();
        }
       
        //afficher les gagnants
        /*Player winner = objectives.determineWinner();
        System.out.println("The player " + winner + " has won the game!");*/
    }

    /**
     * Représente un tour complet des joueurs.
     */
    protected void nextTurn() {
        for(Player p : players){
            IO.PrintReset();
            System.out.println(board);
            IO.SlowType("C'est au tour de "+ p.toString());
            ActionRequest r = ActionMaker.Prompt(p);
            pendingActions.add(r);
            IO.DeleteLines(1);
        }
        currentTurn++;
    }


    /**
     * verifie si un joueur a atteint son objectif
     *
     * @return true si la condition de victoire est remplie
     */
    protected boolean CheckWinCondition() {
        for (Player player : players){
            if (objectives.isObjectiveAchieved(player)){
                return true;
            }
        }
        return false;
    }

    public List<Player> GetPlayers(){
        return players;
    }

}
