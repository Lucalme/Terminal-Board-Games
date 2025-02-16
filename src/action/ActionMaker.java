package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Game.Game;
import action.util.IO;
import ares.Ares;
import board.tile.Tile;
import demeter.Demeter;
import player.Player;

public class ActionMaker {

    private final HashMap<String, Class<? extends Action>> actionMap;
    private Game game;

    public ActionMaker(Game game){
        if(game instanceof Ares){
            actionMap = ActionMap.Ares.actionMap;
        }else if(game instanceof Demeter){
            actionMap = ActionMap.Demeter.actionMap;
        }else{
            actionMap = null;
        }
        this.game = game;
    }

    private String PromptBuilder(Player player){
        String res = "Choisissez une action : ";
        //TODO: Vérifier en amont les actions possibles pour le joueur, et renvoyer une paire <String, Action[]> ici.
        int i = 1;
        for (String s : actionMap.keySet()){
            res += "\n"+ i +" -> "+ s;
            i++;
        }
        return res;
    }

    private Action ActionFromIndex(int i, Player player){
        Action action = null;
        //TODO: simplifier la map et n'utiliser que des string??
        Class<? extends Action> c = (Class<? extends Action>)actionMap.get(actionMap.keySet().toArray()[i]);
        String[] array = c.getName().split("\\.");
        String name = array[array.length-1];
        switch(name){
            case "ActionCollect":
                Tile tile = PromptTile(player);
                action = new ActionCollect(player, tile);
                break;
            case "ActionAttack":
                Player target = PromptTarget(player);
                action = new ActionAttack(player, target);
                break;
                
            case "ShowInventory":
                action = new ShowInventory(player);
        }
        return action;
    }

    private Tile PromptTile(Player player){
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

    private Player PromptTarget(Player player){
        Player target = null;
        List<Player> others = Targetables(player);
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


    private List<Player> Targetables(Player player){
        List<Player> others = new ArrayList<>();
        for(Player p : game.GetPlayers()){
            if(p != player){
                others.add(p);
            }
        }
        return others;
    }
        
    public ActionRequest Prompt(Player player){
        ActionRequest res = null; // Initialize with a default value
        Boolean done = false;
        String prompt = PromptBuilder(player);
        IO.SlowType(prompt);
        while(!done){
            int i = IO.getInt();
            if (i >= 1 && i <= actionMap.size()){
                Action a = ActionFromIndex(i-1, player);
                if(!a.finishesTurn){
                    PreliminaryAction(a);
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
