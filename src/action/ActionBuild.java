package action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import Game.Game;
import board.Board;
import board.Directions;
import board.resource.ResourceType;
import board.tile.Tile;
import building.AresHarbour;
import building.Building;
import building.BuildingEffectType;
import player.Player;

public abstract class ActionBuild extends Action {
//TODO: hériter de cette classe pour les différents types de bâtiments

    public final Building building;
    public final Tile tile;
    public final int islandId;

    public ActionBuild(Player builder, Building building, Tile tile){
        super(builder, true );
        this.building = building;
        this.tile = tile;
        this.islandId = tile.GetIslandID();
    }
    
    public void Effect(){
        PayCost();
        tile.SetBuilding(building);
        source.AddBuilding(building);
    }

    public static HashMap<ResourceType, Integer> Cost(){
        return new HashMap<>();
    }

    protected static boolean PlayerCanAfford(Player player, HashMap<ResourceType, Integer> cost){
        Map<ResourceType, Integer>playerResource = player.getResources();
        for(Map.Entry<ResourceType, Integer> entry : cost.entrySet()){
            if(playerResource.get(entry.getKey()) < entry.getValue()){
                return false;
            } 
        }
        return true;
    }


    protected boolean TileHasWaterNeighbour(Tile tile, Player player, Game game){
        int boardSizeX = game.board.SizeX();
        int boardSizeY = game.board.SizeY();
        for(Directions dir : Directions.values()){
            if(tile.position.x + dir.X <0 || tile.position.x + dir.X >= boardSizeX || tile.position.y + dir.Y <0 || tile.position.y + dir.Y >= boardSizeY ){
                continue;
            }
            if(game.board.GetTileAtPosition(tile.position.x+dir.X, tile.position.y+dir.Y) == null){
                return true;
            }
        }
        return false;
    }

    protected static boolean atLeastOneTileEmpty(Board board){
        return board.getTiles().values().stream().filter(tile -> tile.GetBuilding() == null).collect(Collectors.toList()).size() > 0;
    }

    protected boolean TileIsEmpty(Tile tile){
        return tile.GetBuilding() == null;
    }

    /** true si la tile à construire est sur une ile déjà occupée par 2 batiments du joueurs, ou que toutes les iles occupées par le joueur
     * (à l'exception de celle désirée) ont au moins 2 batiments dont au moins un port
     */
    public static boolean AresBuildConditions(Player player, Game game, int islandId){
        ArrayList<Building> playerBuildings = player.GetOwnedBuildings();
        if(playerBuildings.stream().filter(b -> b.islandId == islandId).collect(Collectors.toList()).size() > 2)
        {
            return true;
        }
        boolean allIslandOccupiedHaveAtLeast2Buildings = true;
        boolean allIslandsOccupiedHaveAtLeastOneHarbour = true;
        for(Building building : player.GetOwnedBuildings()){
            if(building.islandId != islandId){
                ArrayList<Building> buildingsOnIsland = player.GetOwnedBuildings()
                                                                .stream()
                                                                .filter(b -> b.islandId == building.islandId)
                                                                .collect(Collectors.toCollection(ArrayList::new));
                if(buildingsOnIsland.size() < 2){
                    allIslandOccupiedHaveAtLeast2Buildings = false;
                    break;
                }
                if(buildingsOnIsland.stream().filter(b -> b instanceof AresHarbour).collect(Collectors.toList()).size() == 0){
                    allIslandsOccupiedHaveAtLeastOneHarbour = false;
                    break;
                }
            }
        }
        return allIslandOccupiedHaveAtLeast2Buildings && allIslandsOccupiedHaveAtLeastOneHarbour;
    }

    public static boolean AresAtLeastOneTileSatisfies(Game game, Player player){
        Board board = game.board;
        for(Tile tile : board.getTiles().values()){
            if(tile.GetBuilding() == null && AresBuildConditions(player, game, tile.GetIslandID())){
                return true;
            }
        }
        return false;
    }

}
