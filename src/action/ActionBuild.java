package action;
import java.util.HashMap;

import Game.Game;
import board.Directions;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Building;
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
        //TODO: Ajouter le bâtiment à la Tile (ou au board? player? )
    }

    public static HashMap<ResourceType, Integer> Cost(){
        return new HashMap<>();
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
