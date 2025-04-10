package action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Game.Game;
import ares.Ares;
import ares.GUIAres;
import ares.Livrable3;
import demeter.Demeter;

import action.actions.*;
import action.util.*;
import building.Farm;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Army;
import building.Building;
import player.COM;
import player.Player;

public class ActionMaker {

    protected final HashMap<String, Class<? extends Action>> actionMap;
    protected final Game game;

    public ActionMaker(Game game){
        if(game instanceof Ares || game instanceof Livrable3){
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
            
            if (entry.getKey().equals("Collecter des ressources") ){
                continue;
            }
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
            case "ActionAttack":
                Tile baseCamp = PromptTile(player, "Choissisez votre camp de départ");
                Tile target = PromptTile(player, "Choississez le batiment à attaquer");
                boolean useSecretWeapon = PromptSecretWeapon(player);
                action = new ActionAttack(player, baseCamp, target, useSecretWeapon);
                break;
            case "ShowInventory":
                action = new ShowInventory(player);
                break;
            case "ActionTrade":
                ResourceType base = PromptResource(player, false);
                ResourceType exchange = PromptResource(player, false);
                action = new ActionTrade(player, base, exchange);
                break;
            case "AresBuildHarbour":
                Tile til = PromptTile(player, "Choissisez la position où construire le port");
                action = new AresBuildHarbour(player, til);
                break;
            case "AresBuildArmy":
                Tile ti = PromptTile(player, "Choissisez la position où construire l'armée");
                int nbOfWarriors = PromptWarriors(player);
                action = new AresBuildArmy(player, ti, nbOfWarriors);
                break;
            case "AresBuyWarriors":
                action = new AresBuyWarriors(player);
                break;
            case "AresReplaceArmyWithCamp":
                Tile tile = PromptTile(player, "Choissisez la position de l'armée à remplacer");
                action = new AresReplaceArmyWithCamp(player, tile);
                break;
            case "AresAddWarriorToBuilding":
                Tile tile2 = PromptTile(player, "Choissisez la position de l'armée ou du camp");
                int amnt = PromptWarriors(player);
                action = new AresAddWarriorToBuilding(player, amnt, tile2);
                break;
            case "AresBuySecretWeapon":
                action = new AresBuySecretWeapon(player);
                break;
            case "ActionSkip":
                action = new ActionSkip(player);
                break;
            case "DemeterBuildFarm":
                Tile tii = PromptTile(player, "Choissisez la position où construire la ferme");
                action = new DemeterBuildFarm(player,tii);
                break;
            case "DemeterBuildPort":
                Tile tiii = PromptTile(player, "Choissisez la position où construire le port");
                action = new DemeterBuildPort(player, tiii);
                break;
            case "DemeterReplaceFarmWithExploitation":
                Farm selectedFarm = (Farm)PromptFarm(player);
                action = new DemeterReplaceFarmWithExploitation(player, selectedFarm.tile);
                break;
            case "DemeterBuyThief":
                /*ResourceType chosenResource = chooseResource();
                int amount = stealResourcesFromOthers(chosenResource, player);*/ 
                action = new DemeterBuyThief(player);
                break;
            case "DemeterUseThief":
                ResourceType chosenResource = chooseResource();
                int amount = stealResourcesFromOthers(chosenResource, player); 
                action = new DemeterUseThief(player, chosenResource, amount);
                HashMap<String, Class<? extends Action>> refreshedActions = GetPossibleActions(player);
                break;

            default :
                throw new RuntimeException("ActionMaker!Nom non-reconnu : "+ t.getTypeName());
        }
        return action;
    }


    private boolean PromptSecretWeapon(Player player){
        boolean possible = (player.getResources().get(ResourceType.SecretWeapon) > 0);
        if(!possible){
            return false;
        }
        IO.SlowType("Voulez-vous utiliser votre arme secrète ? (O/N)");
        boolean res = IO.getBool();
        IO.DeleteLines(1);
        return res;
    }

    //public Building PromptBuilding(Player player, Class<? extends Building> clazz){
    //    ArrayList<Building> playerBuildings = player.GetOwnedBuildings();
    //    ArrayList<Building> specificBuildings = new ArrayList<>();
    //    int count = 0;
    //    for(Building b : playerBuildings){
    //        if(b.getClass() == clazz){
    //            specificBuildings.add(b);
    //        }
    //    }
    //}

    public int stealResourcesFromOthers(ResourceType chosenResource, Player currentPlayer) {
        int totalStolen = 0;
    
        for (Player player : game.GetPlayers()) {
            if (player != currentPlayer) {
                int amount = player.getResources().getOrDefault(chosenResource, 0);
                if (amount > 0) {
                    player.removeResource(chosenResource, amount);
                    totalStolen += amount;
                } else {
                    System.out.println(player + " has 0 of " + chosenResource);
                }
            }
        }
        return totalStolen;
    }
    public Building PromptArmy(Player player){
        ArrayList <Building> playerBuilding= player.GetOwnedBuildings();
        ArrayList <Army> playerArmy=new ArrayList<>();
        String prompt = "Choisissez une armée à remplacer : \n";
        int count=0;
        for(Building b : playerBuilding){
            if(b instanceof Army){
                playerArmy.add((Army)b);
                prompt += count + " ->  numero ile: "+ b.islandId +" position: ("+b.tile.position.x+","+b.tile.position.y+"):" + "\n";
                count++;
            }
        }
        IO.SlowType(prompt);
        boolean done= false;
        int answer=-1;
        while(!done)
        {
            answer =IO.getInt();
           if(answer<playerArmy.size() && answer>=0){
                done=true;
               
           }
        }
        IO.DeleteLines(count+2);
        return playerArmy.get(answer);

    }
    public ResourceType chooseResource() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
    
        while (choice < 1 || choice > ResourceType.values().length) {
            System.out.println("Choisissez une ressource à voler :");
            int index = 1;
            for (ResourceType resource : ResourceType.values()) {
                if (resource.isTradable) {
                    System.out.println(index + ". " + resource.name);
                    index++;
                }
            }
    
            System.out.print("Entrez le numéro correspondant à votre choix de ressource : ");
            choice = scanner.nextInt();
    
            if (choice < 1 || choice > ResourceType.values().length) {
                System.out.println("Choix invalide. Veuillez sélectionner un numéro valide.");
            }
        }
    
        return ResourceType.values()[choice - 1];
    }

 
    public Building PromptFarm(Player player){
        ArrayList <Building> playerBuilding= player.GetOwnedBuildings();
        ArrayList <Farm> playerFarm=new ArrayList<>();
        String prompt = "Choisissez une ferme à remplacer : \n";
        int count=0;
        for(Building b : playerBuilding){
            if(b instanceof Farm){
                playerFarm.add((Farm)b);
                prompt += count + " ->  numero ile: "+ b.islandId +" position: ("+b.tile.position.x+","+b.tile.position.y+"):" + "\n";
                count++;
            }
        }
        IO.SlowType(prompt);
        boolean done= false;
        int answer=-1;
        while(!done)
        {
            answer =IO.getInt();
           if(answer<playerFarm.size() && answer>=0){
                done=true;
               
           }
        }
        IO.DeleteLines(count+2);
        return playerFarm.get(answer);

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

    public Tile PromptTile(Player player, String prompt){
        IO.SlowType(prompt);
        Tile res = PromptTile(player);
        IO.DeleteLines(1);
        return res;
    }

    /** @param returnAll pour récupérer tous les types de resources, même celle non-échangeables. */
    private ResourceType PromptResource(Player player, boolean returnAll){
        int i = 1;
        for(ResourceType res : player.getResources().keySet()){
            if(!returnAll && !res.isTradable){continue;}
            IO.SlowType(i + " -> "+ res.toString());
            i++;
        }
        boolean done = false;
        int choice = -1;
        while(!done){
            choice = IO.getInt();
            if(choice -1 < player.getResources().keySet().size() && choice >= 1){
                done  = true;
                break;
            }
            IO.SlowType("Choix invalide, veuillez rééssayer....");
            IO.DeleteLines(1);
        }   
        IO.DeleteLines(player.getResources().keySet().size());
        return player.getResources().keySet().toArray(new ResourceType[0])[choice -1];
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
        if(player instanceof COM){
            boolean fastmode = false;
            String prompt = PromptBuilder(player, GetPossibleActions(player));
            String inventory = player.ResourcesString();
            int lines = prompt.split("\\n").length + inventory.split("\\n").length +1;
            if(!fastmode){
                IO.SlowType(prompt, 10);
                IO.SlowType("Inventaire :",10);
                IO.SlowType(inventory, 10);
            }
            ActionRequest res = ((COM)player).promptAction(GetPossibleActions(player), game);
            while(!res.action.CheckInstancePossible(player, game)){
                IO.SlowType("Action impossible : "+res.action.getClass());
                IO.DeleteLines(1);
                res = ((COM)player).promptAction(GetPossibleActions(player), game);
            }
            IO.DeleteLines(lines);
            return res;
        }
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
