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

    
   /*  public DemeterBuyThief(ResourceType resourceChosen, int amount, Player source) {
        super(source, true); // true = finishes turn
        this.resourceChosen = resourceChosen;
        this.amount = amount;
    }*/
    public DemeterBuyThief(Player source) {
        super(source, true); // true = finishes turn
    }

    @Override
    public String Description() {
        return source.toString() + " bought a thief!" ;
    }
    private static final HashMap<ResourceType, Integer> COST = new HashMap<>() {{
        put(ResourceType.Ore, 1);
        put(ResourceType.Wood, 1);
        put(ResourceType.Wheat, 1);
    }};

    public static boolean isPossible(Player player, Game game) 
    {
        return thievesBought < MAX_THIEVES && !playersWhoBoughtThief.contains(player) && PlayerCanAfford(player,COST);
    }
   @Override
    public boolean CheckInstancePossible(Player player, Game game) 
    {
       return isPossible(player, game);
    }

   /*  private void giveResourceAndTrackThief() {
        source.addResource(resourceChosen, amount);
        thievesBought++;
        playersWhoBoughtThief.add(source);
    }*/

    public void Effect(){
        source.addResource(ResourceType.Thief, 1);
        for (HashMap.Entry<ResourceType, Integer> entry : COST.entrySet()) {
            source.removeResource(entry.getKey(), entry.getValue());
        }
        playersWhoBoughtThief.add(source);
        thievesBought++;
    }
}

   
    

