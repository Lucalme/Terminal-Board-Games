package action.actions;

import java.util.HashMap;
import action.ActionBuild;
import board.resource.ResourceType;
import board.tile.Tile;
import building.AresHarbour;
import player.Player;

public class AresBuildHarbour extends ActionBuild {

    public AresBuildHarbour(Player source, Tile tile){
        super(source, new AresHarbour(source, 1, tile.GetIslandID()), tile);
    }

    public static HashMap<ResourceType, Integer> Cost() {
        return new HashMap<>(){{
            //TODO: Vérifier ces valeurs.
            put(ResourceType.Ore, 3);
            put(ResourceType.Sheep, 1);
        }};
    }

    public String Description() {
        return source.toString() + " a construit un Port sur l'île n°"+islandId;
    }

}