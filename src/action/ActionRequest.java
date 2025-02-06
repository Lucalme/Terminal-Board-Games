package action;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Scanner;

import board.Board;
import board.tile.Tile;
import player.Player;

/**
 * Une classe qui permet de faire une demande d'action à un joueur.
 */
public class ActionRequest {

    public final Action action;
    public final boolean ready;

    private final static HashMap<String, Class<? extends Action>> actionMap = new HashMap<String, Class<? extends Action>>() {{
        put("Collecter des ressources", ActionCollect.class);
        put("Attaquer", ActionAttack.class);
    }};
    

    public ActionRequest(Player player, Action action) {
        this.action = action;
        ready = CheckActionPossible();
    }
    
    private Boolean CheckActionPossible(){
        Boolean res = true;
        if (action instanceof ActionBuild){
            if (!((ActionBuild)action).CheckBuildPossible()){
                res = false;
            }
        }
        //TODO: Ajouter la verification des ressources, etc...
        return res;
    }

    private static String PromptBuilder(Player player){
        String res = "Choisissez une action : ";
        //TODO: Vérifier en amont les actions possibles pour le joueur, et renvoyer une paire <String, Action[]> ici.
        int i = 1;
        for (String s : actionMap.keySet()){
            res += "\n"+ i +" -> "+ s;
            i++;
        }
        return res;
    }

    private static Action ActionFromIndex(int i, Player player) {
        Action action = null;
        try{
            Class<? extends Action> c = (Class<? extends Action>)actionMap.get(actionMap.keySet().toArray()[i]);
            System.out.println(c);
            boolean requiresTarget = c.getField("RequiresTarget").getBoolean(null);
            boolean requiresTile = c.getField("RequiresTile").getBoolean(null);

            if(!requiresTarget && !requiresTile){
                action = c.getDeclaredConstructor(Player.class).newInstance(player);
            }else if(requiresTarget && !requiresTile){
                Player target = PromptTarget(null, player);
                action = c.getDeclaredConstructor(Player.class, Player.class).newInstance(player, target);
            }else if(!requiresTarget && requiresTile){
                Tile tile = GetTileFromPlayer(null, player);
                action = c.getDeclaredConstructor(Player.class, Tile.class).newInstance(player, tile);
            }else{
                Player target = PromptTarget(null, player);
                Tile tile = GetTileFromPlayer(null, player);
                action = c.getDeclaredConstructor(Player.class, Player.class, Tile.class).newInstance(player, target, tile);
            }
            return action;
        }catch(Exception e){e.printStackTrace(); return null;}
    }

    private static Tile GetTileFromPlayer(Board board, Player player){
        return new Tile();
    }

    private static Player PromptTarget(Board board, Player player){
        return new Player();
    }
        
    public static ActionRequest Prompt(Player player){
        ActionRequest res = null; // Initialize with a default value
        Boolean done = false;
        Scanner sc = new Scanner(System.in);
        String prompt = PromptBuilder(player);
        while(!done){
            System.out.println(prompt);
            int i = sc.nextInt();
            if (i >= 1 && i <= actionMap.size()){
                Action a = ActionFromIndex(i-1, player);
                res = new ActionRequest(player, a);
                done = true;
            }else{
                System.out.println("Choix invalide, veuillez réessayer...");
            }
        }
        sc.close();
        return res;
    }
}
