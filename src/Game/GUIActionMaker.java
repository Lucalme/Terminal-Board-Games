package Game;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JPanel;
import java.util.Map;
import action.Action;
import action.ActionMaker;
import action.ActionMap;
import action.ActionRequest;
import ares.Ares;
import demeter.Demeter;
import player.Player;

public class GUIActionMaker extends ActionMaker{

    private final GUI gui;
    
    public GUIActionMaker(GUIGame game){
        super(game);
        gui = game.gui;
    }

    public void ShowPossibleActions(Player player){
        HashMap<String, Class<? extends Action>> possibleActions = GetPossibleActions(player);
        JPanel ActionPanel = new JPanel();
        ActionPanel.setLayout(new GridLayout(1, possibleActions.size()));
        for(Map.Entry<String, Class<? extends Action>> entry : possibleActions.entrySet()){
            Button actionButton = new Button(entry.getKey());
            actionButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    Action action = null;
                    String className = entry.getValue().getName();
                    gui.Console.Print("Action choisie : "+ className);
                    //TODO:...
                }
            });
            ActionPanel.add(actionButton);
        }
    }

    public void SendActionRequest(Player player, ActionRequest request){

    }

}
