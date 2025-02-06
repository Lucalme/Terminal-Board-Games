package action;

import player.Player;

public abstract class Action {

    public final Player source;
    public final boolean finishesTurn;
    public static final boolean RequiresTile = false;
    public static final boolean RequiresTarget = false;

    public Action(Player player, boolean finishesTurn) {
        source = player;
        this.finishesTurn = finishesTurn;
    }

    public abstract boolean Effect();
    public abstract Cost Cost();

}
 
