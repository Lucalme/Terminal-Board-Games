package Game;
import javax.swing.*;

import board.tile.Tile;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.*;

public class GUI extends JFrame{

    public JPanel Body;
    public JPanel GameView;
    public JPanel SidePanel;
    public TilePicker TilePicker;
    public int[] WindowSize;

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
        setSize(WindowSize[0], WindowSize[1]);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Body = new JPanel();
        Body.setLayout(null);
        setContentPane(Body);
        Body.setBounds(getBounds());
        Body.setPreferredSize(getSize());
        Body.setSize(getSize());
        //System.out.println("Body Size :" + Body.getSize());
        //System.out.println("Body Preferred Size :" + Body.getPreferredSize());
        //Body.setBounds(getBounds());
        Body.setBackground(Color.BLACK);


        GameView = new JPanel();
        SidePanel = new JPanel();
        Body.add(GameView);
        Body.add(SidePanel);

        GameView.setLayout(new GridLayout(boardSizeX, boardSizeY, 0, 0));
        //GameView.setPreferredSize(new Dimension((int) (Body.getWidth() * .7), (int) (Body.getHeight() * 0.7)));
        GameView.setSize(new Dimension((int) (Body.getWidth() * .7), (int) (Body.getHeight() * 0.7)));
        GameView.setLocation(new Point(0,0));

        SidePanel.setLayout(new GridLayout(3, 0));
        SidePanel.setBackground(Color.darkGray);
        //SidePanel.setPreferredSize(new Dimension((int) (Body.getWidth() * .3), (int) (Body.getHeight())));
        SidePanel.setSize(new Dimension((int) (Body.getWidth() * .3), (int) (Body.getHeight())));
        SidePanel.setLocation(new Point((int) (Body.getWidth() * .7), 0));

        TilePicker = new TilePicker();
        SidePanel.add(TilePicker);
        TilePicker.setBackground(Color.lightGray);

        validate();
    }
    
}

class TilePicker extends JPanel{

    private boolean hasSelection = false;
    private Tile tile;
    private GridBagConstraints c;

    public TilePicker(){
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
    }
    
    public void setTileInfo(Tile tile){
        if((hasSelection && tile != this.tile) || tile == null){return;}
        removeAll();
        //TOP BAR
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.8;
        c.weighty = 1;
        c.ipady = 10;
        JLabel title = new JLabel(tile.GetTileType() +" : île n°" + tile.GetIslandID());
        title.setBackground(Color.darkGray); 
        title.setOpaque(true);
        title.setForeground(Color.white);
        add(title,c);
        c.gridx = 1;
        c.weightx = 0.2;
        JLabel closeButton = new JLabel("X");
        closeButton.setBackground(Color.red);
        closeButton.setOpaque(true);
        add(closeButton,c);
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                hasSelection = false;
                removeAll();
                repaint();
            }
        });

        //Content
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.VERTICAL;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel("Position : (x:"+tile.position.x +";y:"+tile.position.y+")"),c);
        c.gridy =2;
        add(new JLabel(tile.GetResourceType() +":"+ tile.GetResourcesPresent()),c);
        //resourceLabel.setBounds(getBounds());
        validate();
        repaint();
    }

    public void setSelection(Tile tile){
        this.hasSelection = true;
        this.tile = tile;
        setTileInfo(tile);
    }
}