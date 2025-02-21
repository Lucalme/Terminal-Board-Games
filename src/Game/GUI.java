package Game;
import javax.swing.*;

import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.*;
import java.util.Map;

import Game.GUIUtils.*;

public class GUI extends JFrame{

    public JPanel Body;
    public JPanel GameView;
    public JPanel SidePanel;
    public GUIConsole Console;
    public TilePicker TilePicker;
    public PlayerPanel playerPanel;
    public int[] WindowSize;

    public static final Color transparent = new Color(0,0,0,0);

    public GUI(String name, int[] WindowSize, GUIGame game){
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
        Console = new GUIConsole(game);
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

        TilePicker = new TilePicker(GameView);
        TilePicker.setBackground(Color.lightGray);
        SidePanel.add(TilePicker);

        validate();
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
    private final JPanel GameView;

    public TilePicker(JPanel gameView){
        GameView = gameView;
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

    public Tile getSelectedTile(){
        return tile;
    }

    public boolean hasSelection(){
        return hasSelection;
    }
    
    public void setTileInfo(Tile tile){
        if( tile == null || (hasSelection && tile != this.tile)){return;}
        Content.removeAll();
        TopBar.setText(tile.GetTileType() +" : île n°" + tile.GetIslandID());
        JLabel resources = new JLabel(tile.GetResourceType() +" : "+ tile.GetResourcesPresent());
        resources.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        Content.add(resources);
        JLabel position = new JLabel("Position : (x:"+tile.position.x +";y:"+tile.position.y+")");
        position.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        Content.add(position);

        validate();
        repaint();
    }

    public void setSelection(Tile tile, JPanel fTile){
        //TODO: séparer le code entre la GameView et le TilePicker
        if(hasSelection && tile == this.tile){
            hasSelection = false;
            this.tile = null;
            fTile.removeAll();
            setTileColor(fTile, tile);
            return;
        }else if(hasSelection ){
            return; //TODO: Faire un troisième cas pour remplacer l'ancienne sélection?
        }   
        this.hasSelection = false;
        setTileColor(fTile, tile, true);
        //JLabel innerBorder = new JLabel();
        //innerBorder.setBackground(GUI.transparent);
        //TODO:innerBorder.setIcon();
        //fTile.add(innerBorder);
        this.hasSelection = true;
        this.tile = tile;
        setTileInfo(tile);
        GameView.validate();
        GameView.repaint();
    }

    public void setTileColor(JPanel fTile, Tile tile, boolean hovered){
        if(hovered && !hasSelection){
            setTileColor(fTile, tile);
            fTile.setBackground(fTile.getBackground().darker());
        }
        else if(tile != this.tile){
            setTileColor(fTile, tile);
        }
    }

    public void setTileColor(JPanel fTile, Tile tile){
        if(tile == null ){
            fTile.setBackground(Color.blue);
            return;
        }
        switch(tile.GetTileType()){
            case Fields:
                fTile.setBackground(Color.YELLOW);
                break;
            case Mountains:
                fTile.setBackground(Color.LIGHT_GRAY);
                break;
            case Pastures:
                fTile.setBackground(Color.ORANGE);
                break;
            case Forest:
                fTile.setBackground(Color.green);
        }
        fTile.validate();
    }
}