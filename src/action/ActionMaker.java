package action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Game.Game;
import ares.Ares;
import ares.GUIAres;
import demeter.Demeter;

import action.actions.*;
import action.util.*;

import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;

public class ActionMaker {

    protected final HashMap<String, Class<? extends Action>> actionMap;
    protected final Game game;

    public ActionMaker(Game game){
        if(game instanceof Ares ){
            actionMap = ActionMap.Ares.actionMap;
        }else if(game instanceof Demeter){
            actionMap = ActionMap.Demeter.actionMap;
        }else if(game instanceof GUIAres){
            actionMap = ActionMap.GUIAres.actionMap;
        }else{
            throw new RuntimeException("ActionMaker!Type de jeu non-reconnu : "+ game.getClass().getName());
        }
        this.game = game;
    }

    protected HashMap<String, Class<? extends Action>> GetPossibleActions(Player player){
        HashMap<String, Class<? extends Action>> res = new HashMap<>();
        for(Map.Entry<String, Class<? extends Action>> entry : actionMap.entrySet()){
            if(Polymorphism.isPossible(entry.getValue(), player, game)){
                res.put(entry.getKey(), entry.getValue());
            }
        }
        return res;
    }

    private String PromptBuilder(Player player, HashMap<String, Class<? extends Action>> possibleActions){
        String res = "Choisissez une action : ";
        int i = 1;
        for(Map.Entry<String, Class<? extends Action>> entry : possibleActions.entrySet()){
            res += "\n"+ i +" -> "+ entry.getKey();
            i++;
        }
        return res;
    }

    private Action ActionFromIndex(int i, Player player, HashMap<String, Class<? extends Action>> possibleActions){
        Action action = null;
        Type t = (Type)possibleActions.get(possibleActions.keySet().toArray()[i]);
        String[] array = t.getTypeName().split("\\.");
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
                break;
            case "ActionTrade":
                ResourceType base = PromptResource(false);
                ResourceType exchange = PromptResource(false);
                action = new ActionTrade(player, base, exchange);
                break;
            case "AresBuildHarbour":
                Tile til = PromptTile(player);
                action = new AresBuildHarbour(player, til);
                break;
            case "AresBuildArmy":
                Tile ti = PromptTile(player);
                int nbOfWarriors = PromptWarriors(player);
                action = new AresBuildArmy(player, ti, nbOfWarriors);
                break;
            default :
                throw new RuntimeException("ActionMaker!Nom non-reconnu : "+ t.getTypeName());
        }
        return action;
    }

    public Tile PromptTile(Player player){
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

    /** @param returnAll pour récupérer tous les types de resources, même celle non-échangeables. */
    private ResourceType PromptResource(boolean returnAll){
        int i = 1;
        for(ResourceType res : ResourceType.values()){
            if(!returnAll && !res.isTradable){continue;}
            IO.SlowType(i + " -> "+ res.toString());
            i++;
        }
        boolean done = false;
        int choice = -1;
        while(!done){
            choice = IO.getInt();
            if(choice -1 < ResourceType.values().length && choice >= 1){
                done  = true;
                break;
            }
            IO.SlowType("Choix invalide, veuillez rééssayer....");
            IO.DeleteLines(1);
        }   
        IO.DeleteLines(ResourceType.values().length);
        return ResourceType.values()[choice -1];
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

    private int PromptWarriors(Player player){
        int max = player.getResources().get(ResourceType.Warriors);
        IO.SlowType("Combien de guerriers souhaitez vous placer? (min:1 ; max:"+max+")");
        boolean done = false;
        int res = -1;
        while (!done) {
            res = IO.getInt();
            if(res < 1 || res > max){
                IO.SlowType("Choix invalide, veuillez rééssayer....");
                IO.DeleteLines(1);
            }else{
                done = true;
            }
        }
        return res;
    }

    protected List<Player> Targetables(Player player){
        List<Player> others = new ArrayList<>();
        for(Player p : game.GetPlayers()){
            if(p != player){
                others.add(p);
            }
        }
        return others;
    }
        
    public ActionRequest Prompt(Player player){
        ActionRequest res = null;
        Boolean done = false;
        HashMap<String, Class<? extends Action>> possibleActions = GetPossibleActions(player);
        String prompt = PromptBuilder(player, possibleActions);
        IO.SlowType(prompt);
        while(!done){
            int i = IO.getInt();
            if (i >= 1 && i <= possibleActions.size()){
                Action a = ActionFromIndex(i-1, player, possibleActions);
                if(!a.finishesTurn){
                    a.Effect();
                    continue;
                }
                if(!a.CheckInstancePossible(player, game)){
                    IO.SlowType("Vous ne pouvez pas faire ça pour l'instant....");
                    IO.DeleteLines(1);
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
    
}
