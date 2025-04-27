package GUI.GUIActions;

import GUI.GUI;
import Game.Game;
import action.Action;
import player.Player;


/**Classe abstraite servant à intéragir avec l'interface graphique avant de lancer une action*/
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
