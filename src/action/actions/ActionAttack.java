package action.actions;
import java.util.HashMap;

import action.Action;
import board.resource.ResourceType;
import player.Player;

public class ActionAttack extends Action {
    
    public final Player target;

    public ActionAttack(Player player, Player target){
        super(player, true);
        this.target = target;
    }

    public boolean Effect() {
        return true;
    }

    public HashMap<ResourceType, Integer>  Cost(){
        //return new Cost(ResourceType.Army, 0) TODO: envisager les armées comme un type de resources
        return null;
    }


    public String Description(){
        return source.toString() + " attaque " + target.toString() + "!";
    }
}
