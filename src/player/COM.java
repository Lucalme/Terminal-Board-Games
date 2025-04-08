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
import action.actions.AresBuildArmy;
import action.actions.AresBuildHarbour;
import action.actions.AresBuyWarriors;
import action.actions.AresReplaceArmyWithCamp;
import action.util.IO;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Army;
import building.Building;
import building.Camp;

public class COM extends Player{
    
    private static int comNb = 1;

    public COM(Game game){
        super(comNb, game);
        comNb++;
    }


    public ActionRequest promptAction(HashMap<String, Class<? extends Action>> possibleActions, Game game){
        Random r = new Random();
        int a = r.nextInt(possibleActions.entrySet().size());
        while(possibleActions.values().toArray()[a].getClass().getName().equals("action.actions.ShowInventory")){
            a = r.nextInt(possibleActions.entrySet().size());
        }
        @SuppressWarnings("unchecked")
        Class<? extends Action> c = (Class<? extends Action>)possibleActions.values().toArray()[a];
        String[] array = c.getTypeName().split("\\.");
        String name = array[array.length-1];
        switch(name){
            case "ActionTrade":
                ArrayList<ResourceType> availableResources = (ArrayList<ResourceType>)this.getResources().entrySet().stream().filter((entry) -> entry.getValue() >= 3).map(e -> e.getKey()).collect(Collectors.toList());
                ActionTrade trade = new ActionTrade(this, availableResources.get(r.nextInt(availableResources.size())), ResourceType.values()[r.nextInt(ResourceType.values().length)]);
                ActionRequest res = new ActionRequest(this, trade);
                return res;
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
                    if(possibleTargets.size() > 0){
                        ActionAttack attack = new ActionAttack(this, b.tile, possibleTargets.get(r.nextInt(possibleTargets.size())).tile);
                        ActionRequest resz = new ActionRequest(this, attack);
                        return resz;
                    }
                }
                IO.SlowType("Pas de cible possible", 20);
                IO.DeleteLines(1);
                throw new RuntimeException("Pas de cible possible");
            case "AresBuildArmy":
                Tile t = getEmptyTile(game);
                while(!ActionBuild.AresBuildConditions(this, game, t.GetIslandID())){
                    t = getEmptyTile(game);
                }
                t = getEmptyTile(game);
                boolean campPossible = this.getResources().get(ResourceType.Wood) >= 3 && this.getResources().get(ResourceType.Sheep) >= 1 && this.getResources().get(ResourceType.Wheat) >= 1 && this.getResources().get(ResourceType.Ore) >= 3 && this.getResources().get(ResourceType.Warriors) >= 6;
                int n = campPossible ? Math.max(1, r.nextInt( this.getResources().get(ResourceType.Warriors))) : this.getResources().get(ResourceType.Warriors) > 1 ? Math.max(1, r.nextInt( Math.min(5, this.getResources().get(ResourceType.Warriors))) ): 1;
                AresBuildArmy buildArmy = new AresBuildArmy(this, t, n);
                ActionRequest rese = new ActionRequest(this, buildArmy);
                return rese;
            case "AresBuildHarbour":
                Tile ti = getEmptyTile(game);
                while(!ActionBuild.AresBuildConditions(this, game, ti.GetIslandID())){
                    ti = getEmptyTile(game);
                }
                AresBuildHarbour buildHarbour = new AresBuildHarbour(this, ti);
                ActionRequest reso = new ActionRequest(this, buildHarbour);
                return reso;
            case "ActionSkip":
                ActionSkip skip = new ActionSkip(this);
                ActionRequest rest = new ActionRequest(this, skip);
                return rest;
            case "ShowInventory":
                return promptAction(possibleActions, game);
            case "AresBuyWarriors":
                AresBuyWarriors buyWarriors = new AresBuyWarriors(this);
                ActionRequest resu = new ActionRequest(this, buyWarriors);
                return resu;
            case "AresReplaceArmyWithCamp":
                ArrayList<Building> ownedBuildings = this.GetOwnedBuildings();
                ArrayList<Building> armies = ownedBuildings.stream().filter(b -> b instanceof Army && !(b instanceof Camp)).collect(Collectors.toCollection(ArrayList::new));
                AresReplaceArmyWithCamp replaceArmy = new AresReplaceArmyWithCamp(this, armies.get(r.nextInt(armies.size())).tile);
                ActionRequest resy = new ActionRequest(this, replaceArmy);
                return resy;
            default:
                //IO.SlowType("nombre d'actions possibles : "+possibleActions.size(), 100);
                //IO.SlowType(c.toString(), 200);
                IO.SlowType("Action non reconnue : "+c.getName(), 100);
                throw new RuntimeException("Action non reconnue");
        }
    }


    public Tile getEmptyTile(Game game){
        HashMap<int[], Tile> tiles = game.board.getTiles();
        for(Tile tile : tiles.values()){
            if (tile.GetBuilding() == null){
                return tile;
            }
        }
        throw new RuntimeException("Pas de tuile vide disponible");
    }

}
