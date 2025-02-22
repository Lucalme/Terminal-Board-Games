package action.actions;
import Game.Game;
import action.Action;
import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;

public class ActionCollect extends Action {
    
    public final Tile tile;

    public ActionCollect(Player player, Tile tile){
        super(player, true);
        this.tile = tile;
    }

    public void Effect(){
        ResourceType t  = tile.GetResourceType();
        int r = tile.GetResourcesPresent();
        tile.ClearResources();
        source.addResource(t, r);
    }

    public static boolean isPossible(Player player, Game game){
        return true;
    }

    public boolean CheckInstancePossible(Player player, Game game){
        return tile.GetBuilding() == null || tile.GetBuilding().owner == player;
    }

    public String Description(){
        return source.toString() + " ramasse "+ tile.GetResourcesPresent()+ " "+ tile.GetResourceType() + "!";
    }
}
