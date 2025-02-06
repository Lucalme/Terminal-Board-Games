package action;
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

    public Cost Cost(){
        //return new Cost(ResourceType.Army, 0) TODO: envisager les armées comme un type de resources
        return new Cost(null, 0);
    }
}
