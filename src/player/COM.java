package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import Game.Game;
import action.Action;
import action.ActionBuild;
import action.ActionRequest;
import action.actions.ActionAttack;
import action.actions.ActionSkip;
import action.actions.ActionTrade;
import action.actions.AresAddWarriorToBuilding;
import action.actions.AresBuildArmy;
import action.actions.AresBuildHarbour;
import action.actions.AresBuySecretWeapon;
import action.actions.AresBuyWarriors;
import action.actions.AresReplaceArmyWithCamp;
import action.actions.DemeterBuildFarm;
import action.actions.DemeterBuildPort;
import action.actions.DemeterBuyThief;
import action.actions.DemeterReplaceFarmWithExploitation;
import action.actions.DemeterUseThief;
import action.util.IO;
import ares.Ares;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Army;
import building.Building;
import building.Camp;
import building.Exploitation;
import building.Farm;
import demeter.Demeter;

public class COM extends Player{
    
    private static int comNb = 1;

    public COM(Game game){
        super(comNb, game);
        comNb++;
    }

    private int counter = 0;   
    private void count(String ActionName){
        counter++;
        if(counter > 1000){
            throw new RuntimeException("Trop de tentatives pour trouver une action : "+ActionName);
        }
    }

    public ActionRequest promptAction(HashMap<String, Class<? extends Action>> possibleActions, Game game){
        Random r = new Random();
        int a = r.nextInt(possibleActions.entrySet().size());
        counter = 0;
        while(possibleActions.values().toArray()[a].getClass().getName().equals("action.actions.ShowInventory")){
            a = r.nextInt(possibleActions.entrySet().size());
            count("Choix aléatoire d'action");
        }
        //possibleActions.entrySet().stream().map((e) -> {System.out.println(e.getValue().toString()); return 0;}).collect(Collectors.toList());
        //if(true){throw new RuntimeException("Stop");}
        @SuppressWarnings("unchecked")
        Class<? extends Action> c = (Class<? extends Action>)possibleActions.values().toArray()[a];
        String[] array = c.getTypeName().split("\\.");
        String name = array[array.length-1];
        if(game instanceof Ares){
            return HandleAresPrompt(name, possibleActions, game);
        }else if (game instanceof Demeter){
            return HandleDemeterPrompt(name, possibleActions, game);
        }else{
            throw new RuntimeException("Jeu non reconnu");
        }
    }


    private ActionRequest HandleDemeterPrompt(String name, HashMap<String, Class<? extends Action>> possibleActions, Game game){
        Random r = new Random();
        switch(name){
            case "ActionTrade":
                return Trade();
            case "ActionSkip":
                return Skip();
            case "DemeterBuildFarm":
                Tile t = getEmptyTile(game);
                DemeterBuildFarm buildFarm = new DemeterBuildFarm(this, t);
                ActionRequest res = new ActionRequest(this, buildFarm);
                return res;
            case "DemeterBuyThief":
                DemeterBuyThief buyThief = new DemeterBuyThief(this);
                ActionRequest resz = new ActionRequest(this, buyThief);
                return resz;
            case "ShowInventory":
                return promptAction(possibleActions, game);
            case "DemeterReplaceFarmWithExploitation":
                ArrayList<Building> ownedBuildings = this.GetOwnedBuildings();
                ArrayList<Building> farms = ownedBuildings.stream().filter((b) -> {return (b instanceof Farm && !(b instanceof Exploitation));}).collect(Collectors.toCollection(ArrayList::new)); 
                DemeterReplaceFarmWithExploitation replaceFarm = new DemeterReplaceFarmWithExploitation(this, farms.get(r.nextInt(farms.size())).tile);
                ActionRequest resq = new ActionRequest(this, replaceFarm);
                return resq;
            case "DemeterUseThief":
                ResourceType targetResource = this.getResources().entrySet().stream().min((a, b) -> a.getValue() - b.getValue()).get().getKey();
                int amount = game.players.stream().mapToInt(p -> {
                    if(p == this){
                        return 0;
                    }
                    return p.getResources().get(targetResource);
                }).sum();
                //TODO: déplacer le calcul de la somme dans l'action DemeterUseThief
                DemeterUseThief useThief = new DemeterUseThief(this, targetResource, amount);
                ActionRequest resu = new ActionRequest(this, useThief);
                return resu;
            case "DemeterBuildPort":
                Tile ti = getEmptyTile(game);
                DemeterBuildPort buildPort = new DemeterBuildPort(this, ti);
                ActionRequest reso = new ActionRequest(this, buildPort);
                return reso;
            default:
                IO.SlowType("Action non reconnue : "+name, 100);
                throw new RuntimeException("Action non reconnue");
        }
    }


    private ActionRequest HandleAresPrompt(String name, HashMap<String, Class<? extends Action>> possibleActions, Game game)
    {
        Random r = new Random();
        switch(name){
            case "ActionTrade":
                return Trade();
            case "ActionSkip":
                return Skip();
            case"ActionAttack":
                ArrayList<Building> selfBuildings = this.GetOwnedBuildings();
                for(Building b : selfBuildings){
                    if(!(b instanceof Army) && !(b instanceof Camp)){
                        continue;
                    }
                    int islandId = b.islandId;
                    ArrayList<Building> possibleTargets = game.board.getBuildings().entrySet().stream().
                                                            filter( e -> e.getKey().islandId == islandId &&
                                                                    e.getValue().numPlayer != this.numPlayer &&
                                                                    (e.getKey() instanceof Army || e.getKey() instanceof Camp) 
                                                            ).map(e -> e.getKey()).collect(Collectors.toCollection(ArrayList::new));
                    boolean secretWeapon = this.getResources().get(ResourceType.SecretWeapon) > 0;
                    if(possibleTargets.size() > 0){
                        ActionAttack attack = new ActionAttack(this, b.tile, possibleTargets.get(r.nextInt(possibleTargets.size())).tile, secretWeapon);
                        ActionRequest resz = new ActionRequest(this, attack);
                        return resz;
                    }
                }
                IO.SlowType("Pas de cible possible", 20);
                IO.DeleteLines(1);
                throw new RuntimeException("Pas de cible possible");
            case "AresBuildArmy":
                Tile t = getEmptyTile(game);
                counter = 0;
                while(!ActionBuild.AresBuildConditions(this, game, t.GetIslandID())){
                    t = getEmptyTile(game);
                    count("AresBuildArmy");
                }
                boolean campPossible = this.getResources().get(ResourceType.Wood) >= 3 && this.getResources().get(ResourceType.Sheep) >= 1 && this.getResources().get(ResourceType.Wheat) >= 1 && this.getResources().get(ResourceType.Ore) >= 3 && this.getResources().get(ResourceType.Warriors) >= 6;
                int n = campPossible ? Math.max(1, r.nextInt( this.getResources().get(ResourceType.Warriors))) : this.getResources().get(ResourceType.Warriors) > 1 ? Math.max(1, r.nextInt( Math.min(5, this.getResources().get(ResourceType.Warriors))) ): 1;
                AresBuildArmy buildArmy = new AresBuildArmy(this, t, n);
                ActionRequest rese = new ActionRequest(this, buildArmy);
                return rese;
            case "AresBuildHarbour":
                Tile ti = getEmptyTile(game);
                counter = 0;
                while(!ActionBuild.AresBuildConditions(this, game, ti.GetIslandID())){
                    ti = getEmptyTile(game);
                    count("AresBuildHarbour");
                }
                AresBuildHarbour buildHarbour = new AresBuildHarbour(this, ti);
                ActionRequest reso = new ActionRequest(this, buildHarbour);
                return reso;
            case "AresAddWarriorToBuilding":
                ArrayList<Building> militaryBuildings = this.GetOwnedBuildings()
                                                                        .stream()
                                                                        .filter(b -> b instanceof Army || b instanceof Camp)
                                                                        .collect(Collectors.toCollection(ArrayList::new));
                Building selected = militaryBuildings.get(r.nextInt(militaryBuildings.size()));
                int warriorsToAdd = Math.max(1, r.nextInt(this.getResources().get(ResourceType.Warriors)));
                AresAddWarriorToBuilding addWarrior = new AresAddWarriorToBuilding(this, warriorsToAdd, selected.tile);
                ActionRequest resq = new ActionRequest(this, addWarrior);
                return resq;
            case "ShowInventory":
                return promptAction(possibleActions, game);
            case "AresBuyWarriors":
                AresBuyWarriors buyWarriors = new AresBuyWarriors(this);
                ActionRequest resu = new ActionRequest(this, buyWarriors);
                return resu;
            case "AresReplaceArmyWithCamp":
                ArrayList<Building> ownedBuildings = this.GetOwnedBuildings();
                ArrayList<Building> armies = ownedBuildings.stream().filter((b) -> {return (b instanceof Army && !(b instanceof Camp));}).collect(Collectors.toCollection(ArrayList::new)); 
                AresReplaceArmyWithCamp replaceArmy = new AresReplaceArmyWithCamp(this, armies.get(r.nextInt(armies.size())).tile);
                ActionRequest resy = new ActionRequest(this, replaceArmy);
                return resy;
            case "AresBuySecretWeapon":
                AresBuySecretWeapon buySecretWeapon = new AresBuySecretWeapon(this);
                ActionRequest resx = new ActionRequest(this, buySecretWeapon);
                return resx;
            default:
                IO.SlowType("Action non reconnue : "+name, 100);
                throw new RuntimeException("Action non reconnue");
        }
    }

    private ActionRequest Trade(){
        Random r = new Random();
        ArrayList<ResourceType> availableResources =
                 (ArrayList<ResourceType>)this.getResources()
                                                .entrySet()
                                                .stream()
                                                .filter((entry) -> (entry.getValue() >= (hasTradingAdvantage() ? 2 : 3) && entry.getKey().isTradable))
                                                .map(e -> e.getKey())
                                                .collect(Collectors.toList());
                ResourceType baseResource = availableResources.get(r.nextInt(availableResources.size()));
                availableResources.remove(baseResource);
                ResourceType targetResource = availableResources.get(r.nextInt(availableResources.size()));
                ActionTrade trade = new ActionTrade(this, baseResource, targetResource);
                ActionRequest res = new ActionRequest(this, trade);
                return res;
    }


    private ActionRequest Skip(){
        ActionSkip skip = new ActionSkip(this);
        ActionRequest rest = new ActionRequest(this, skip);
        return rest;
    }

    public Tile getEmptyTile(Game game){
        HashMap<int[], Tile> tiles = game.board.getTiles()
                                                .entrySet()
                                                .stream()
                                                .filter(e -> e.getValue().GetBuilding() == null)
                                                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (a, b) -> b, HashMap::new));

        if(tiles.size() > 0){
            ArrayList<Tile> emptyTiles = new ArrayList<>(tiles.values());
            Random r = new Random();
            int i = r.nextInt(emptyTiles.size());
            return emptyTiles.get(i);
        }
        throw new RuntimeException("Pas de tuile vide disponible");
    }

}
