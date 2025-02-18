package Game;
import javax.swing.*;

import board.tile.Tile;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
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
        Body.setBackground(Color.gray);


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
        TilePicker.setBackground(Color.CYAN);

        validate();
    }
    
}

class TilePicker extends JPanel{

    private boolean hasSelection = false;
    private Tile tile;

    public TilePicker(){
        setLayout(new GridLayout(5, 0));
    }
    
    public void setTileInfo(Tile tile){
        if((hasSelection && tile != this.tile) || tile == null){removeAll();return;}
        removeAll();
        AddCloseButton(tile);
        JLabel nameLabel = new JLabel(tile.GetTileType() +" : île n°" + tile.GetIslandID());
        JLabel positionLabel = new JLabel("Position : (x:"+tile.position.x +";y:"+tile.position.y+")");
        JLabel resourceLabel = new JLabel(tile.GetResourceType() +":"+ tile.GetResourcesPresent());
        add(nameLabel);
        add(positionLabel);
        add(resourceLabel);
        //resourceLabel.setBounds(getBounds());
        validate();
    }

    public void setSelection(Tile tile){
        this.hasSelection = true;
        this.tile = tile;
        setTileInfo(tile);
    }

    private void AddCloseButton(Tile tile){
        Button closeButton = new Button("X");
        closeButton.setBackground(Color.red);
        add(closeButton);
        closeButton.setBounds(0,0, 100, 100);
        closeButton.validate();
    }
}