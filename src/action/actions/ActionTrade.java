package action.actions;

import java.util.Map;

import Game.Game;
import action.Action;
import board.resource.ResourceType;
import player.Player;

public class ActionTrade extends Action {

    public final ResourceType playerResourceType;
    public final ResourceType exchangeResourceType;
    private final boolean hasResourceAdvantage = false; 
    
    public ActionTrade(Player source, ResourceType playerResources, ResourceType exchangeResource){
        //TODO: Verifier si le Player a un avantage d'échange
        super(source, true);
        this.exchangeResourceType = exchangeResource;
        this.playerResourceType = playerResources;
    }

    @Override
    public void Effect() {
        //TODO: Verifier si le Player a un avantage d'échange
        source.removeResource(playerResourceType, 3);
        source.addResource(exchangeResourceType, 1);
    }

    @Override
    public String Description() {
        return source.toString() + 
        " échange " + (hasResourceAdvantage ? "2" : "3") +" "
        + playerResourceType.toString() + " contre 1 " + exchangeResourceType.toString(); 
    }


    public static boolean isPossible(Player player, Game game){
        for(Map.Entry<ResourceType, Integer> entry : player.getResources().entrySet()){
            //TODO: Verifier si le Player a un avantage d'échange
            if(entry.getValue() >  3 ){
                return true;
            }
        }
        return false;
    }
}
