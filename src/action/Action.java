package action;

import player.Player;

public abstract class Action {

    public final Player source;

    public Action(Player player) {
        source = player;
    }

    public abstract Boolean Effect();

}
 
