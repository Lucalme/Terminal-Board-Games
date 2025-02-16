package action;
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
        //TODO: Ajouter le bâtiment à la Tile (ou au board? )
    }


}
