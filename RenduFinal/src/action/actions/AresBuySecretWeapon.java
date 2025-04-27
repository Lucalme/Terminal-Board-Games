package action.actions;

import java.util.HashMap;

import Game.Game;
import action.Action;
import board.resource.ResourceType;
import player.Player;

public class AresBuySecretWeapon extends Action {

    public AresBuySecretWeapon(Player player) {
        super(player, true);
    }

    public static HashMap<ResourceType, Integer> Cost(){
        return new HashMap<>(){{
            put(ResourceType.Ore, 1);
            put(ResourceType.Wood, 1);
        }};
    }

    @Override
    public boolean CheckInstancePossible(Player player, Game game) {
        return PlayerCanAfford(player, Cost());
    }

    public static boolean isPossible(Player player, Game game) {
        return PlayerCanAfford(player, Cost());
    }

    @Override
    public void Effect() {
        source.removeResource(ResourceType.Ore, 1);
        source.removeResource(ResourceType.Wood, 1);
        source.addResource(ResourceType.SecretWeapon, 1);
    }

    @Override
    public String Description() {
        return source.toString() + " a acheté une arme secrète";
    }
    
}
