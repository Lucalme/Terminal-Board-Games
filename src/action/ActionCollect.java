package action;

import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;

public class ActionCollect extends Action {
    
    public final Tile tile;

    public ActionCollect(Player player, Tile tile){
        super(player);
        this.tile = tile;
    }

    public Boolean Effect(){
        ResourceType t  = tile.GetResourceType();
        int r = tile.GetResourcesPresent();
        return true;
    }
}
