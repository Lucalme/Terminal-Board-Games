package Game.GUIUtils;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import Game.GUI;
import Game.GUIGame;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.Button;

import action.Action;
import action.actions.ActionTrade;
import action.actions.AresBuildArmy;
import board.resource.ResourceType;
import player.Player;

public class GUIConsole extends JPanel{

    private final GUIGame game;
    private final JPanel LogPanel;
    private final JPanel OptionsPanel;
    private final JLabel firstLine;
    private final JLabel secondLine;
    private boolean hasIntPrompt = false;

    public GUIConsole(GUIGame game){
        this.game = game;
        setForeground(Color.white);
        setFont(getFont().deriveFont(20f));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        LogPanel = new JPanel();
        LogPanel.setLayout(new GridLayout(2, 0));
        LogPanel.setBackground(Color.BLACK);
        add(LogPanel, c);
        firstLine = new JLabel();
        firstLine.setForeground(Color.white);
        firstLine.setBorder(BorderFactory.createEmptyBorder(0, 10,0,10));
        secondLine = new JLabel();
        secondLine.setForeground(Color.lightGray);
        secondLine.setBorder(BorderFactory.createEmptyBorder(0, 10,0,10));
        LogPanel.add(secondLine);
        LogPanel.add(firstLine);
        c.gridheight = 5;
        c.weighty = 0.9;
        c.gridy = 2;
        OptionsPanel = new JPanel();
        OptionsPanel.setBackground(Color.BLACK);
        OptionsPanel.setLayout(new GridLayout(1, 0));
        add(OptionsPanel, c);
    }

    public boolean hasIntPrompt(){
        return hasIntPrompt;
    }

    public void addPrompt(Class<? extends Action> action, Player player){
        OptionsPanel.removeAll();
        JPanel Options = new JPanel();
        if( action == AresBuildArmy.class){
            Options.add(addIntPrompt());
            Options.add(addValidate(action, player));
            Print("Combien de soldats voulez-vous ajouter au batiment?");
        }else if( action == ActionTrade.class){
            Options.add(addPromptResource(true));
            Options.add(addPromptResource(true));
            Options.add(addValidate(action, player));
            Print("Choisissez le type de ressource à échanger");
        }else {
            throw new RuntimeException("GUIConsole! Action non supportée : "+ action.getName());
        }
        ShowOptions(Options);
    }

    private JLabel addValidate(Class<? extends Action> action, Player player){
        JLabel button = new JLabel("Valider");
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                game.HandleAction(action, player);
            }
        });
        return button;
    }
        

    private JTextField addIntPrompt(){
        hasIntPrompt = true;
        JTextField input = new JTextField();
        input.setBorder(BorderFactory.createLineBorder(GUI.transparent, 5));
        return input;
    }

    public JComboBox<ResourceType> addPromptResource( boolean onlyTradables){
        hasIntPrompt = true;
        JComboBox<ResourceType> input = new JComboBox<>( );
        for(ResourceType type : ResourceType.values()){
            if(onlyTradables && !type.isTradable){continue;}
            input.addItem(type);
        }
        input.setBorder(BorderFactory.createLineBorder(GUI.transparent, 5));
        return input;
    }

    public void Print(String s){
        secondLine.setText(firstLine.getText());
        firstLine.setText("");
        for(int i = 0; i< s.length(); i++){
            try{Thread.sleep(15);}catch(Exception e){}
            firstLine.setText(firstLine.getText()+s.charAt(i));
            firstLine.validate();
            firstLine.repaint();
        }
        validate();
        repaint();
    }

    public void printWarning(String s){
        PrintNow("⚠️ "+s);
    }

    public void PrintNow(String s){
        secondLine.setText(firstLine.getText());
        firstLine.setText(s);
        validate();
        repaint();
    }

    public void ShowOptions(JPanel options){
        OptionsPanel.removeAll();
        options.setBackground(OptionsPanel.getBackground());
        OptionsPanel.add(options);
        validate();
        repaint();
    }

    public void ClearOptions(){
        OptionsPanel.removeAll();
    }

}
