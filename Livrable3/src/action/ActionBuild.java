package action;
import java.util.HashMap;
import java.util.Map;

import Game.Game;
import board.Directions;
import board.resource.ResourceType;
import board.tile.Tile;
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
        if(building.effectType == BuildingEffectType.TradingAdvantage){
            source.setTradingAdvantage(true); //TODO: ecrire plutot une méthode/predicat hasTradingAdvantage dans player
        }
        //TODO: Ajouter le bâtiment à la Tile (ou au board? player? )
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

    protected boolean TileIsEmpty(Tile tile){
        return tile.GetBuilding() == null;
    }
}
