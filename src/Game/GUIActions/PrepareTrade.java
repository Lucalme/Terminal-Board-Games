package Game.GUIActions;

import Game.GUI;
import Game.Game;
import action.actions.ActionTrade;
import player.Player;

public class PrepareTrade extends PreliminaryAction{
    
    public PrepareTrade(Player player, GUI gui){
        super(ActionTrade.class, gui , player);
    }

    public static boolean isPossible(Player player, Game game){
        return ActionTrade.isPossible(player, game);
    }
    
}
