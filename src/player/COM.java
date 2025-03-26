package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import Game.Game;
import action.Action;
import action.ActionRequest;
import action.actions.ActionAttack;
import action.actions.ActionSkip;
import action.actions.ActionTrade;
import action.actions.AresBuildArmy;
import action.actions.AresBuildHarbour;
import action.actions.ShowInventory;
import action.util.IO;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Army;
import building.Building;
import building.Camp;

public class COM extends Player{
    
    private static int comNb = 1;

    public COM(){
        super(comNb);
        comNb++;
    }


    public ActionRequest promptAction(HashMap<String, Class<? extends Action>> possibleActions, Game game){
        try{
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
                    ArrayList<ResourceType> availableResources = (ArrayList<ResourceType>)this.getResources().entrySet().stream().filter((entry) -> entry.getValue() > 3).map(e -> e.getKey()).collect(Collectors.toList());
                    ActionTrade trade = new ActionTrade(this, availableResources.get(r.nextInt(availableResources.size())), ResourceType.values()[r.nextInt(ResourceType.values().length)]);
                    ActionRequest res = new ActionRequest(this, trade);
                    return res;
                case"ActionAttack":
                    ArrayList<Building> selfBuildings = this.GetOwnedBuildings();
                    IO.SlowType("selfBuildings : "+selfBuildings.size(), 100);
                    for(Building b : selfBuildings){
                        if(!(b instanceof Army) && !(b instanceof Camp)){
                            continue;
                        }
                        int islandId = b.islandId;
                        ArrayList<Building> possibleTargets = game.board.getBuildings().entrySet().stream().
                        filter(e -> e.getKey().islandId == islandId && e.getValue().numPlayer != this.numPlayer && (e.getKey() instanceof Army || e.getKey() instanceof Camp) ).map(e -> e.getKey()).
                        collect(Collectors.toCollection(ArrayList::new));
                        IO.SlowType("possibleTargets : "+possibleTargets.size(), 100);
                        if(possibleTargets.size() > 0){
                            ActionAttack attack = new ActionAttack(this, b.tile, possibleTargets.get(r.nextInt(possibleTargets.size())).tile);
                            ActionRequest resz = new ActionRequest(this, attack);
                            return resz;
                        }
                    }
                    throw new RuntimeException("Pas de cible possible");
                case "AresBuildArmy":
                    Tile t = getEmptyTile(game);
                    boolean campPossible = this.getResources().get(ResourceType.Wood) >= 3 && this.getResources().get(ResourceType.Sheep) >= 1 && this.getResources().get(ResourceType.Wheat) >= 1 && this.getResources().get(ResourceType.Ore) >= 3;
                    int n = campPossible ? r.nextInt(this.getResources().get(ResourceType.Warriors)) : r.nextInt(Math.min(5, this.getResources().get(ResourceType.Warriors)));
                    AresBuildArmy buildArmy = new AresBuildArmy(this, t, n);
                    ActionRequest rese = new ActionRequest(this, buildArmy);
                    return rese;
                case "AresBuildHarbour":
                    Tile ti = getEmptyTile(game);
                    AresBuildHarbour buildHarbour = new AresBuildHarbour(this, ti);
                    ActionRequest reso = new ActionRequest(this, buildHarbour);
                    return reso;
                case "ActionSkip":
                    ActionSkip skip = new ActionSkip(this);
                    ActionRequest rest = new ActionRequest(this, skip);
                    return rest;
                case "ShowInventory":
                    ActionSkip skip2 = new ActionSkip(this);
                    ActionRequest rest2 = new ActionRequest(this, skip2);
                    return rest2;
                default:
                    //IO.SlowType("nombre d'actions possibles : "+possibleActions.size(), 100);
                    //IO.SlowType(c.toString(), 200);
                    IO.SlowType("Action non reconnue : "+c.getName(), 100);
                    throw new RuntimeException("Action non reconnue");
            }
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
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
