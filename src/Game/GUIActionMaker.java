package Game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Game.GUIActions.PreliminaryAction;

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
                    HandleGameAction(entry.getValue(), player);
                }
            });
            ActionPanel.add(actionButton);
        }
        return ActionPanel;
    }

    public void HandleGameAction(Class<? extends Action> actionClass, Player player){
        HashMap<String, Class<? extends Action>> possibleActions = GetPossibleActions(player);
        Action action = null;
        String[] names = actionClass.getName().split("\\.");
        String className = names[names.length-1];
        //gui.Console.PrintNow("Action choisie : "+ className); //La console réagit trop lentement, peut-être à cause du contexte (MouseListener)
        Tile tile;
        Integer intParam = null;
        if(PreliminaryAction.class.isAssignableFrom(actionClass)){ //si l'action hérite de PreliminaryAction
            action = Polymorphism.getPreliminaryActionInstance((Class<? extends PreliminaryAction>)actionClass, player, gui);
            action.Effect();
            return;
        }
        action = Polymorphism.tryGetActionInstance(actionClass, gui, player, gui.TilePicker.getSelectedTile(), intParam);
        if(action == null){
            return;
        }
        System.out.println("Action : "+ action.CheckInstancePossible(player, game));
    }

    public void SendActionRequest(Player player, ActionRequest request){
        ((GUIGame)game).HandleActionRequest(request);
    }

}
