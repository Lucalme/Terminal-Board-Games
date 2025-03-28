package action.actions;

import java.util.HashMap;
import java.util.Map;

import Game.Game;
import action.ActionBuild;
import board.resource.ResourceType;
import board.tile.Tile;
import building.AresHarbour;
import player.Player;

public class AresBuildHarbour extends ActionBuild {

    public AresBuildHarbour(Player source, Tile tile){
        super(source, new AresHarbour(source, 1, tile), tile);
    }

    public static HashMap<ResourceType, Integer> Cost() {
        return new HashMap<>(){{
            //TODO: Vérifier ces valeurs.
            put(ResourceType.Ore, 3);
            put(ResourceType.Sheep, 1);
        }};
    }

    /** Vérifie que le player a les resources nécessaires pour l'action */
    public static boolean isPossible(Player player, Game game){
        return atLeastOneTileEmpty(game.board) && PlayerCanAfford(player, Cost());
    }


    public String Description() {
        return source.toString() + " a construit un Port sur l'île n°"+islandId;
    }

    public boolean CheckInstancePossible(Player player, Game game){
        return TileIsEmpty(tile) && TileHasWaterNeighbour(tile, player, game);
    }

}