package action;
import board.tile.Tile;
import building.Building;
import player.Player;

public abstract class ActionBuild extends Action{
//TODO: hériter de cette classe pour les différents types de bâtiments

    public final Building building;
    public final Tile tile;


    public ActionBuild(Player builder, Building building, Tile tile){
        super(builder, true );
        this.building = building;
        this.tile = tile;
    }
    
    public boolean Effect(){
       //TODO: Faire payer les resources au joueur
       //TODO: Ajouter le bâtiment à la Tile
       return true;
    }

    public abstract boolean CheckBuildPossible();
}
