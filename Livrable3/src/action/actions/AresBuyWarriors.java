package action.actions;

import java.util.HashMap;
import java.util.Map;

import Game.Game;
import action.Action;
import board.resource.ResourceType;
import player.Player;

public class AresBuyWarriors extends Action{

    public AresBuyWarriors(Player source){
        super(source, true);
    }

    public static HashMap<ResourceType, Integer> Cost(){
        HashMap<ResourceType, Integer> cost = new HashMap<ResourceType, Integer>();
        cost.put(ResourceType.Wheat, 2);
        cost.put(ResourceType.Ore, 1);
        cost.put(ResourceType.Sheep, 2);
        return cost;
    }

    public static boolean isPossible(Player player, Game game){
        return PlayerCanAfford(player, Cost());
    }

    @Override
    public boolean CheckInstancePossible(Player player, Game game) {
        return PlayerCanAfford(player, Cost());
    }

    @Override
    public void Effect() {
        for(Map.Entry<ResourceType, Integer> entry : Cost().entrySet()){
            source.removeResource(entry.getKey(), entry.getValue());
        }
        source.addResource(ResourceType.Warriors, 5);
    }

    @Override
    public String Description() {
        return source.toString() + " a acheté 5 guerriers";
    }
    
    
}
