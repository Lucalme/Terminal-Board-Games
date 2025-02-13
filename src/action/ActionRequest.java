package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Game.Game;
import board.Board;
import board.Position;
import board.tile.Tile;
import player.Player;
import action.util.IO;

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
        Class<?>[] types =  actionMap.get("Attaquer").getDeclaredConstructors()[0].getParameterTypes();
        
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

    private static Action ActionFromIndex(int i, Player player, Game game){
        Action action = null;
        //TODO: simplifier la map et n'utiliser que des string??
        Class<? extends Action> c = (Class<? extends Action>)actionMap.get(actionMap.keySet().toArray()[i]);
        String[] array = c.getName().split("\\.");
        String name = array[array.length-1];
        switch(name){
            case "ActionCollect":
                Tile tile = game.GetBoard().GetTileAtPosition(PromptPosition(player));
                action = new ActionCollect(player, tile);
                break;
            case "ActionAttack":
                Player target = PromptTarget(player, game);
                action = new ActionAttack(player, target);
                break;
        }
        return action;
    }

    private static Position PromptPosition(Player player){
        return new Position(1, 2);
    }

    private static Player PromptTarget(Player player, Game game){
        //List<Player> players = game.getPlayers();
        return new Player(0);
    }
        
    public static ActionRequest Prompt(Player player, Game game){
        ActionRequest res = null; // Initialize with a default value
        Boolean done = false;
        String prompt = PromptBuilder(player);
        IO.SlowType(prompt);
        while(!done){
            int i = IO.getInt();
            if (i >= 1 && i <= actionMap.size()){
                Action a = ActionFromIndex(i-1, player, game);
                res = new ActionRequest(player, a);
                done = true;
                IO.DeleteLines(prompt.split("\\n").length);
            }else{
                IO.SlowType("Choix invalide, veuillez réessayer....");
                IO.DeleteLines(1);
            }
        }
        return res;
    }
}
