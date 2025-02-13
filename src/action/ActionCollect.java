package action;

import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;

public class ActionCollect extends Action {
    
    public final Tile tile;
    public static final boolean RequiresTile = true;
    public static final boolean RequiresTarget = false;

    public ActionCollect(Player player, Tile tile){
        super(player, true);
        this.tile = tile;
    }

    public boolean Effect(){
        ResourceType t  = tile.GetResourceType();
        int r = tile.GetResourcesPresent();
        //TODO: Vider la Tile et ajouter les ressources à l'inventaire.
        return true;
    }

    public Cost Cost(){
        return null;
    }

    public String Description(){
        return source.toString() + " ramasse "+ tile.GetResourcesPresent()+ " "+ tile.GetResourceType() + "!";
    }
}
