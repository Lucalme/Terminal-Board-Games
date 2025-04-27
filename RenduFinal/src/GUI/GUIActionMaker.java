package GUI;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.GUIActions.PreliminaryAction;

import java.util.Map;
import action.Action;
import action.ActionMaker;
import action.ActionRequest;
import board.tile.Tile;
import action.util.Polymorphism;
import player.Player;

public class GUIActionMaker extends ActionMaker{

    private final GUI gui;
    
    public GUIActionMaker(GUIGame game){
        super(game);
        gui = game.gui;
    }

    public JPanel ShowPossibleActions(Player player){
        JPanel ActionPanel = new JPanel();
        HashMap<String, Class<? extends Action>> possibleActions = GetPossibleActions(player);
        //ActionPanel.setLayout(new GridLayout(possibleActions.size(), 1));
        for(Map.Entry<String, Class<? extends Action>> entry : possibleActions.entrySet()){
            JLabel actionButton = new JLabel(entry.getKey());
            actionButton.setBorder(BorderFactory.createEtchedBorder(Color.red, Color.yellow));
            actionButton.setOpaque(true);
            actionButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    ((GUIGame)game).HandleAction(entry.getValue(), player);
                }
            });
            ActionPanel.add(actionButton);
        }
        return ActionPanel;
    }

    @SuppressWarnings("unchecked")
    public Action getAction(Class<? extends Action> actionClass, Player player, Object... args){
        Action action = null;
        //Le GUI réagit trop lentement, peut-être à cause du contexte (MouseListener) ? -> utiliser gui.Console.PrintNow()
        if(PreliminaryAction.class.isAssignableFrom(actionClass)){ //si l'action hérite de PreliminaryAction
            action = Polymorphism.getPreliminaryActionInstance((Class<? extends PreliminaryAction>)actionClass, player, gui);
            action.Effect();
            return null;
        }
        Object[] finalArgs = new Object[args.length + 2];
        finalArgs[0] = player;
        finalArgs[1] = gui.TilePicker.getSelectedTile();
        for(int i = 0; i < args.length; i++){
            finalArgs[i+2] = args[i];
        }
        action = Polymorphism.tryGetActionInstance(actionClass, gui, finalArgs);
        return action;
    }

    //public void SendActionRequest(Player player, ActionRequest request){
    //    ((GUIGame)game).HandleActionRequest(request);
    //}

}
