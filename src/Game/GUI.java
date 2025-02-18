package Game;
import javax.swing.*;

import board.tile.Tile;

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

public class GUI extends JFrame{

    public JPanel Body;
    public JPanel GameView;
    public JPanel SidePanel;
    public GUIConsole Console;
    public TilePicker TilePicker;
    public int[] WindowSize;

    public static final Color transparent = new Color(0,0,0,0);

    public GUI(String name, int[] WindowSize, int boardSizeX, int boardSizeY){
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

        TilePicker = new TilePicker();
        SidePanel.add(TilePicker);
        TilePicker.setBackground(Color.lightGray);

        validate();
    }
    
}
class GUIConsole extends JPanel{

    public GUIConsole(){
        setForeground(Color.white);
        setFont(getFont().deriveFont(50f));
        setLayout(new GridLayout(2,0));
    }

    public void Print(String s){
        removeAll();
        Label l = new Label();
        add(l);
        for(int i = 0; i< s.length(); i++){
            try{Thread.sleep(30);}catch(Exception e){}
            l.setText(l.getText()+s.charAt(i));
            validate();
            repaint();
        }
    }

    public void ShowOptions(HashMap<String, Class<? extends Action>> options){
        //TODO: implémenter l'affichage des actions.
    }

}



class TilePicker extends JPanel{

    private boolean hasSelection = false;
    private Tile tile;
    private GridBagConstraints c;

    public TilePicker(){
        setLayout(new GridBagLayout());
    }
    
    public void setTileInfo(Tile tile){
        if((hasSelection && tile != this.tile) || tile == null){return;}
        removeAll();
        //TOP BAR
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.1;

        JLabel title = new JLabel(tile.GetTileType() +" : île n°" + tile.GetIslandID());
        title.setBackground(Color.darkGray); 
        title.setForeground(Color.white);
        title.setOpaque(true);
        title.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        add(title, c);

        //Content
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        JLabel resources = new JLabel(tile.GetResourceType() +" : "+ tile.GetResourcesPresent());
        resources.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        add(resources,c);
        c.weighty = 0.1;
        c.gridy =2;
        JLabel position = new JLabel("Position : (x:"+tile.position.x +";y:"+tile.position.y+")");
        position.setBorder(BorderFactory.createLineBorder(GUI.transparent, 10));
        add(position,c);


        validate();
        repaint();
    }

    public void setSelection(Tile tile){
        this.hasSelection = true;
        this.tile = tile;
        setTileInfo(tile);
    }
}