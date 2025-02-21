package Game.GUIActions;

import Game.GUI;
import Game.Game;
import action.Action;
import player.Player;

public abstract class PreliminaryAction extends Action{

    public final Class<? extends Action> actionToPerform;
    public final GUI gui;
    
    public PreliminaryAction(Class<? extends Action> actionToPerform, GUI gui, Player player){
        super(player, false);
        this.actionToPerform = actionToPerform;
        this.gui = gui;
    }

    public void Effect(){
        gui.Console.addPrompt(actionToPerform, source);
    }

    public boolean CheckInstancePossible(Player player, Game game) {
        return true;
    }

    public String Description(){
        return null;
    }
}
