package Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.Board;
import player.Player;


public abstract class Game {
    protected List<Player> players;
    protected Board board;
    protected List<String> history;
    protected int currentTurn;
    protected Scanner scanner;
    
    public Game(List<Player>players){
        this.players = players;
        this.board = new Board();
        this.history = new ArrayList<>();
        this.currentTurn = 0;
        this.scanner = new Scanner(System.in);
    }

    public void StartGame(){
        System.out.println("THE GAME HAS STARTED!");
        currentTurn = 0;
        gameLoop();
    }

    public void gameLoop(){
        while (!CheckWinCondition()) {
            nextTurn();
            System.out.println(players.get(currentTurn) + ", CHOOSE AN ACTION :");
            System.out.println("1. Perform the action");
            System.out.println("2. Declencher l'evenement");
            System.out.println("3. Check history");
            System.out.println("4. Quit");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice1) {
                case 1:
                    System.out.println("enter the action :");
                    String action = scanner.nextLine();
                    handleAction(action);
                    break;
            
                case 2:
                    System.out.println("entrer l'evenement");
                    String event = scanner.nextLine();
                    triggerEvent(event);
                    break;

                case 3:
                    printHistory();
                    break;

                case 4:
                    System.out.println("GAME OVER !");
                    return;
                
                default :
                    System.out.println("choix invalide, reessayez..");
            }
        }
        System.out.println("a player has won !");
    }

    public void nextTurn() {}

    //donne un exemple de condition de victoire
    public boolean CheckWinCondition() {}

    public void triggerEvent(String event){}
    
    public void handleAction(String action) {}

    public void printHistory(){}
}
