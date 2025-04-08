package action.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import Game.Game;
import action.Action;
import board.resource.ResourceType;
import player.Player;

public  class DemeterBuyThief extends Action 
{
    private static final int MAX_THIEVES = 3;
    private static int thievesBought = 0;
    private static final HashSet<Player> playersWhoBoughtThief = new HashSet<>();
    private final ResourceType resourceChosen;
    private final int amount;
    public DemeterBuyThief(ResourceType resourceChosen, int amount, Player source) {
        super(source, true); // true = finishes turn
        this.resourceChosen = resourceChosen;
        this.amount = amount;
    }

    @Override
    public String Description() {
        return source.toString() + " bought a thief!";
    }
    private static final HashMap<ResourceType, Integer> COST = new HashMap<>() {{
        put(ResourceType.Ore, 1);
        put(ResourceType.Wood, 1);
        put(ResourceType.Wheat, 1);
    }};

    public static boolean isPossible(Player player, Game game) 
    {
        return thievesBought < MAX_THIEVES &&
               !playersWhoBoughtThief.contains(player) &&
               PlayerCanAfford(player,Cost());
    }
    @Override
    public boolean CheckInstancePossible(Player player, Game game) {
        return true;
    }
    public static void giveResourceAndTrackThief(ResourceType resource, int amount, Player player) {
        player.addResource(resource, amount);
        thievesBought++;
        playersWhoBoughtThief.add(player);
    }

    private void giveResourceAndTrackThief() {
        source.addResource(resourceChosen, amount);
        thievesBought++;
        playersWhoBoughtThief.add(source);
    }

    public void Effect(){
        giveResourceAndTrackThief();
    }

   

}

   
    

