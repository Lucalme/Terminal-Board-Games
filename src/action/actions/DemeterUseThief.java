package action.actions;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import Game.Game;
import action.Action;
import board.resource.ResourceType;
import player.Player;
public class DemeterUseThief extends DemeterBuyThief  {
    private ResourceType resourceChosen;
    private int amount;

    public DemeterUseThief(Player source,ResourceType resourceChosen, int amount) {

        super(source);
        this.resourceChosen = resourceChosen;
        this.amount = amount;
    }

    @Override
    public String Description() {
        return source.toString() + " used a thief and stole "+ amount +" of type "+ resourceChosen;
    }

    public static boolean isPossible(Player player, Game game) 
    {
        Map<ResourceType, Integer> res = player.getResources();
        return res.getOrDefault(ResourceType.Thief, 0) == 1;
    }

    @Override
    public boolean CheckInstancePossible(Player player, Game game) {
        return true;
    }

    private void AddResourceStolen() {
        source.addResource(resourceChosen, amount);
    }

    public void Effect(){
        AddResourceStolen();
        source.removeResource(ResourceType.Thief, 1);
    }
    
}
