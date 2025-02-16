package action;

import java.util.HashMap;

import board.resource.ResourceType;
import board.tile.Tile;
import building.AresHarbour;
import player.Player;

public class AresBuildHarbour extends ActionBuild {

    public AresBuildHarbour(Player source, Tile tile){
        super(source, new AresHarbour(source, 1), tile);
    }

    public HashMap<ResourceType, Integer> Cost() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Cost'");
    }

    public String Description() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Description'");
    }

}