package Game;
import javax.swing.*;

import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame{

    public JPanel Body;
    public JPanel GameView;
    public JPanel SidePanel;
    public GUIConsole Console;
    public TilePicker TilePicker;
    public PlayerPanel playerPanel;
    public int[] WindowSize;

    public static final Color transparent = new Color(0,0,0,0);

    public GUI(String name, int[] WindowSize){
        super(name);
        this.WindowSize = WindowSize; 
        WindowListener WLS = new WindowAdapter() {
            //@Override
            //public void windowClosing(WindowEvent e ){
            //    System.exit(0);
            //}
        };
        //addWindowListener(WLS);
        //setSize(WindowSize[0], WindowSize[1]);
        setSize(WindowSize[0], WindowSize[1]);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Body = new JPanel();
        Body.setLayout(null);
        setContentPane(Body);
        Body.setBounds(getBounds());
        Body.setPreferredSize(getSize());
        Body.setSize(getSize());
        Body.setBackground(Color.BLACK);

        GameView = new JPanel();
        SidePanel = new JPanel();
        Console = new GUIConsole();
        Body.add(GameView);
        Body.add(SidePanel);
        Body.add(Console);

        GameView.setLayout(new GridBagLayout());
        GameView.setSize(new Dimension((int) (Body.getWidth() * .7), (int) (Body.getHeight() * 0.7)));
        GameView.setLocation(new Point(0,0));

        SidePanel.setLayout(new GridLayout(3, 0));
        SidePanel.setBackground(Color.darkGray);
        SidePanel.setSize(new Dimension((int) (Body.getWidth() * .3), (int) (Body.getHeight())));
        SidePanel.setLocation(new Point((int) (Body.getWidth() * .7), 0));

        Console.setBackground(Color.BLACK);
        Console.setSize(new Dimension((int)(Body.getWidth() * .7), (int) (Body.getHeight() * 0.3)));
        Console.setLocation(new Point(0, (int)(Body.getHeight() * .7)));


        playerPanel = new PlayerPanel();
        playerPanel.setBackground(Color.LIGHT_GRAY);
        SidePanel.add(playerPanel);

        TilePicker = new TilePicker();
        TilePicker.setBackground(Color.lightGray);
        SidePanel.add(TilePicker);

        validate();
    }
    
}
class GUIConsole extends JPanel{

    private final JPanel LogPanel;
    private final JPanel OptionsPanel;
    private final JLabel firstLine;
    private final JLabel secondLine;

    public GUIConsole(){
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
        add(OptionsPanel, c);
    }

    public void Print(String s){
        secondLine.setText(firstLine.getText());
        firstLine.setText("");
        for(int i = 0; i< s.length(); i++){
            try{Thread.sleep(30);}catch(Exception e){}
            firstLine.setText(firstLine.getText()+s.charAt(i));
            validate();
            repaint();
        }
    }

    public void ShowOptions(JPanel options){
        OptionsPanel.removeAll();
        OptionsPanel.add(options);
    }

    public void ClearOptions(){
        OptionsPanel.removeAll();
    }

}

class PlayerPanel extends JPanel{

    private final JLabel TopBar;
    private final JPanel Content;

    public PlayerPanel(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;    
        c.gridy = 0;
        TopBar = new JLabel();
        TopBar.setBackground(Color.darkGray);
        TopBar.setForeground(Color.white);
        TopBar.setOpaque(true);
        TopBar.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        add(TopBar, c);

        c.weighty = 0.9;
        c.gridy = 1;
        Content = new JPanel();
        Content.setLayout(new GridLayout(ResourceType.values().length, 1));
        Content.setBackground(Color.lightGray);
        Content.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        add(Content, c);
    }

    public void Update(Player player){
        TopBar.setText(player.toString());
        Content.removeAll();
        for(Map.Entry<ResourceType, Integer> entry : player.getResources().entrySet()){
            Content.add(new JLabel(entry.getKey() + " : " + entry.getValue()));
        }
        validate();
        repaint();
    }
}

class TilePicker extends JPanel{

    private boolean hasSelection = false;
    private Tile tile;
    private final JLabel TopBar;
    private final JPanel Content;

    public TilePicker(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        TopBar = new JLabel();
        TopBar.setBackground(Color.darkGray);
        TopBar.setForeground(Color.white);
        TopBar.setOpaque(true);
        TopBar.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        add(TopBar, c);

        c.weighty = 0.9;
        c.gridy = 1;
        Content = new JPanel();
        Content.setLayout(new GridLayout(0, 1));
        Content.setBackground(Color.lightGray);
        add(Content, c);

        validate(); 
        repaint();

    }
    
    public void setTileInfo(Tile tile){
        if((hasSelection && tile != this.tile) || tile == null){return;}
        TopBar.setText(tile.GetTileType() +" : île n°" + tile.GetIslandID());
        Content.removeAll();
        JLabel resources = new JLabel(tile.GetResourceType() +" : "+ tile.GetResourcesPresent());
        resources.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        Content.add(resources);
        JLabel position = new JLabel("Position : (x:"+tile.position.x +";y:"+tile.position.y+")");
        position.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        Content.add(position);

        validate();
        repaint();
    }

    public void setSelection(Tile tile){
        this.hasSelection = true;
        this.tile = tile;
        setTileInfo(tile);
    }
}