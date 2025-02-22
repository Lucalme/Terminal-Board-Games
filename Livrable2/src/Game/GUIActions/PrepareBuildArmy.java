package Game.GUIActions;

import Game.GUI;
import Game.Game;
import action.actions.AresBuildArmy;
import player.Player;

public class PrepareBuildArmy extends PreliminaryAction{
    
    public PrepareBuildArmy(Player player, GUI gui){
        super(AresBuildArmy.class, gui , player);
    }

    public static boolean isPossible(Player player, Game game){
        return AresBuildArmy.isPossible(player, game);
    }
}
