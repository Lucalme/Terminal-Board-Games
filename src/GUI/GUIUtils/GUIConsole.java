package GUI.GUIUtils;
import javax.swing.JPanel;

import GUI.GUI;
import GUI.GUIGame;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

    private int intPromptValue = 0;
    private ResourceType firstResourceType;
    private ResourceType secondResourceType;

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
            Options.add(addIntPrompt(1, player.getResources().get(ResourceType.Warriors), (Integer i) -> intPromptValue = i));
            Options.add(addValidate(action, player, () ->  intPromptValue));
            Print("Combien de soldats voulez-vous ajouter au batiment?");
        }else if( action == ActionTrade.class){
            Options.add(addResourcePrompt(true, (ResourceType type) -> firstResourceType = type));
            Options.add(addResourcePrompt(true, (ResourceType type) -> secondResourceType = type));
            Options.add(addValidate(action, player, () ->  firstResourceType, () -> secondResourceType));
            Print("Choisissez le type de ressource à échanger");
        }else{
            throw new RuntimeException("GUIConsole! Action non supportée : "+ action.getName());
        }
        Options.add(addCancel(player));
        ShowOptions(Options);
    }

    private final JLabel addValidate(Class<? extends Action> action, Player player, Supplier<Object>... fields){
        JLabel button = new JLabel("Valider");
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Object[] args = new Object[fields.length];
                for(int i = 0; i < fields.length; i++){
                    args[i] = fields[i].get();
                    System.out.println(args[i].getClass());
                }
                game.HandleAction(action, player, args);
            }
        });
        return button;
    }

    private JLabel addCancel(Player player){
        JLabel button = new JLabel("Annuler");
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearOptions();
                PrintNow("Action annulée");
                game.ShowPossibleActions(player);
            }
        });
        return button;
    }
        

    private JComboBox<Integer> addIntPrompt(int minValue, int maxValue, Consumer<Integer> fieldReference){
        hasIntPrompt = true;
        JComboBox<Integer> input = new JComboBox<>();
        input.addItem(null);
        for(int i = minValue; i <= maxValue; i++){
            input.addItem(i);
        }
        input.addItemListener(new ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fieldReference.accept((Integer)input.getSelectedItem());
                System.out.println("intPromptValue = "+intPromptValue);
            }            
        });
        input.setBorder(BorderFactory.createLineBorder(GUI.transparent, 5));
        return input;
    }

    public JComboBox<ResourceType> addResourcePrompt(boolean onlyTradables, Consumer<ResourceType> fieldReference ){
        hasIntPrompt = true;
        JComboBox<ResourceType> input = new JComboBox<>( );
        input.addItem(null);
        for(ResourceType type : ResourceType.values()){
            if(onlyTradables && !type.isTradable){continue;}
            input.addItem(type);
        }
        input.addItemListener(new ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fieldReference.accept((ResourceType)input.getSelectedItem());
                //System.out.println("firstResourceType = "+firstResourceType);
            }            
        });
        input.setBorder(BorderFactory.createLineBorder(GUI.transparent, 5));
        return input;
    }

    public void Print(String s){
        secondLine.setText(firstLine.getText());
        firstLine.setText("");
        for(int i = 0; i< s.length(); i++){
            try{Thread.sleep(15);}catch(Exception e){}
            firstLine.setText(firstLine.getText()+s.charAt(i));
            validate();
            repaint();
        }
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
