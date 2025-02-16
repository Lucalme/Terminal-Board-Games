package action;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import board.resource.ResourceType;
import player.Player;

public abstract class Action {

    public final Player source;
    public final boolean finishesTurn;

    public Action(Player player, boolean finishesTurn) {
        source = player;
        this.finishesTurn = finishesTurn;
    }

    public abstract boolean Effect();
    public abstract HashMap<ResourceType, Integer> Cost();

    public abstract String Description();

    protected void PayCost(){
        for(Map.Entry<ResourceType, Integer> entry : Cost().entrySet()){
            source.removeResource(entry.getKey(), entry.getValue());
        }
    }
}
 
