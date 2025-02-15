package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Game.Game;
import board.Board;
import board.Position;
import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;
import action.util.IO;

/**
 * Une classe qui permet de faire une demande d'action à un joueur.
 */
public class ActionRequest {

    public final Action action;
    public boolean ready;

    private final static HashMap<String, Class<? extends Action>> actionMap = new HashMap<String, Class<? extends Action>>() {{
        put("Voir l'inventaire", ShowInventory.class);
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
                Tile tile = PromptTile(player, game);
                action = new ActionCollect(player, tile);
                break;
            case "ActionAttack":
                Player target = PromptTarget(player, game);
                action = new ActionAttack(player, target);
                break;
                
            case "ShowInventory":
                action = new ShowInventory(player);
        }
        return action;
    }

    private static Tile PromptTile(Player player, Game game){
        Tile tile =null;
        while (tile == null) {
            IO.SlowType("Choissisez la Position");
            IO.SlowType("X:");
            int X = IO.getInt();
            IO.SlowType("Y:");
            int Y = IO.getInt();
            tile = game.board.GetTileAtPosition(X, Y);
            if(tile == null){
                IO.SlowType("Impossible de faire ça ici...");
                IO.DeleteLines(1);
            }
            IO.DeleteLines(3);
        }
        return tile;
    }

    private static Player PromptTarget(Player player, Game game){
        Player target = null;
        List<Player> others = Targetables(player, game);
        IO.SlowType("Choississez votre cible : ");
        for(int i = 0;  i< others.size(); i++){
            IO.SlowType(i+1 +" -> " +others.get(i).toString());
        }
        boolean done = false;
        while (!done) {
            int index = IO.getInt() -1;
            if(index >= 0 && index < others.size()){
                target = others.get(index);
                done = true;
            }else{
                IO.SlowType("Choix Invalide, veuillez rééssayer...");
                IO.DeleteLines(1);
            }
        }
        IO.DeleteLines(others.size()+1);
        return target;
    }


    private static List<Player> Targetables(Player player, Game game){
        List<Player> others = new ArrayList<>();
        for(Player p : game.GetPlayers()){
            if(p != player){
                others.add(p);
            }
        }
        return others;
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
                if(!a.finishesTurn){
                    PreliminaryAction(a);
                    IO.Next();
                    IO.DeleteLines(ResourceType.values().length +1);
                    continue;
                }
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
    

    private static void PreliminaryAction(Action action){
        action.Effect();
    }
}
