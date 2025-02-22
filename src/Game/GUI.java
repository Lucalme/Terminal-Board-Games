package Game;
import javax.imageio.ImageIO;
import javax.swing.*;

import board.resource.ResourceType;
import board.tile.Tile;
import board.tile.TileType;
import player.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.util.HashMap;
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

        TilePicker = new TilePicker(GameView, game.board.SizeX(), game.board.SizeY());
        TilePicker.setBackground(Color.lightGray);
        SidePanel.add(TilePicker);

        validate();
    }

    //TODO: migrer vers une classe GameView
    public void clearSelection(){
        TilePicker.clearSelection();
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
    private final int boardSizeX;
    private final int boardSizeY;

    private final Map<TileType, Image> tileImages = new HashMap<>();
    private final Map<TileType, Image> darkTileImages = new HashMap<>();

    public TilePicker(JPanel gameView, int SizeX, int SizeY) {
        GameView = gameView;
        boardSizeX = SizeX;
        boardSizeY = SizeY;
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
        InitImages();

        validate(); 
        repaint();

    }


    private void InitImages(){
        try{
            tileImages.put(null,ImageIO.read(new File("Resources/Water.png")).getScaledInstance(GameView.getWidth() / boardSizeX, GameView.getHeight() / boardSizeY , Image.SCALE_SMOOTH));
        }catch(Exception e){}
        try{
            tileImages.put(TileType.Fields, ImageIO.read(new File("Resources/Fields.png")).getScaledInstance(GameView.getWidth() / boardSizeX, GameView.getHeight() / boardSizeY, Image.SCALE_SMOOTH));
            
        }catch(Exception e){}
        try{
            tileImages.put(TileType.Forest, ImageIO.read(new File("Resources/Forest.png")).getScaledInstance(GameView.getWidth() / boardSizeX, GameView.getHeight() / boardSizeY, Image.SCALE_SMOOTH));
        }catch(Exception e){}
        try{
            tileImages.put(TileType.Mountains, ImageIO.read(new File("Resources/Mountains.png")).getScaledInstance(GameView.getWidth() / boardSizeX, GameView.getHeight() / boardSizeY, Image.SCALE_SMOOTH));
        }catch(Exception e){}
        try{
            tileImages.put(TileType.Pastures, ImageIO.read(new File("Resources/Pastures.png")).getScaledInstance(GameView.getWidth() / boardSizeX, GameView.getHeight() / boardSizeY, Image.SCALE_SMOOTH));
        }catch(Exception e){}
        for(Map.Entry<TileType, Image> entry : tileImages.entrySet()){
            Image image = entry.getValue();
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            Image darkImage = bufferedImage;
            RescaleOp rescaleOp = new RescaleOp(1.0f, -60.0f, null);
            darkImage = rescaleOp.filter(bufferedImage, null);
            darkTileImages.put(entry.getKey(), darkImage);
        }
    }

    public Tile getSelectedTile(){
        return tile;
    }

    public boolean hasSelection(){
        return hasSelection;
    }

    public void clearSelection(){
        hasSelection = false;
        tile = null;
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
            setTileColor(fTile, tile);
            JLabel label = (JLabel) fTile.getComponent(0);
            ImageIcon icon = (ImageIcon) label.getIcon();
            //TODO: manipuler l'image pour la rendre plus sombre
            //icon.setImage(icon.getImage().getScaledInstance(fTile.getWidth(), fTile.getHeight(), Image.SCALE_SMOOTH));
            ImageIcon darkIcon = new ImageIcon(icon.getImage());
            darkIcon.setImage(darkTileImages.get(tile == null ? darkTileImages.get(null) :  tile.GetTileType()));
            label.setIcon(darkIcon);
            fTile.repaint();
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
            //fTile.setBackground(fTile.getBackground().darker());
            JLabel label = (JLabel) fTile.getComponent(0);
            ImageIcon icon = (ImageIcon) label.getIcon();
            icon.setImage(darkTileImages.get(tile == null ? darkTileImages.get(null) :  tile.GetTileType()));

        }
        else if(tile != this.tile){
            setTileColor(fTile, tile);
        }
    }

    public void setTileColor(JPanel fTile, Tile tile){
        try{
            fTile.removeAll();
            JLabel iconLabel = new JLabel();
            Image icon;

            if(tile == null ){
                //fTile.setBackground(Color.blue);
                iconLabel.setIcon(new ImageIcon(tileImages.get(null)));
                fTile.add(iconLabel);
                fTile.validate();
                return;
            }
            icon = tileImages.get(tile.GetTileType());
            iconLabel.setIcon(new ImageIcon(icon));
            fTile.add(iconLabel);
            fTile.validate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}