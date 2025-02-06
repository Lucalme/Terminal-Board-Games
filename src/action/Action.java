package action;

import player.Player;

public abstract class Action {

    public final Player source;
    public final boolean finishesTurn;

    public Action(Player player, boolean finishesTurn) {
        source = player;
        this.finishesTurn = finishesTurn;
    }

    public abstract boolean Effect();
    public abstract Cost Cost();

}
 
