package Game;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Game.GUIUtils.TileAdapter;
import board.tile.Tile;

public class GUIGame extends Game {

    public final GUI gui;

    public GUIGame(int nbOfPlayer, String name, int[] WindowSize) {
        super(nbOfPlayer, 30, 30);
        gui = new GUI(name, WindowSize, 30, 30);
        DrawBoard();
    }
    

    private void DrawBoard(){
        //gui.Body.add(new Button("OK"));
        int tileSizeX = (int)gui.GameView.getPreferredSize().getWidth() / board.SizeX();
        int tileSizeY = (int)gui.GameView.getPreferredSize().getHeight() / board.SizeY();
        System.out.println("Preferred GameView Size : " + gui.GameView.getPreferredSize());
        System.out.println("GameView Size : " + gui.GameView.getSize());
        Random r = new Random();
        for(int i =0 ; i < board.SizeX(); i++){
            for(int j = 0; j < board.SizeY(); j++){
                Tile tile = board.GetTileAtPosition(i,j);
                JPanel fTile = new JPanel();
                gui.GameView.add(fTile);
                fTile.setVisible(true);
                //fTile.setPreferredSize(new Dimension(tileSizeX, tileSizeY));
                //fTile.setSize(tileSizeX, tileSizeY);
                fTile.setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
                //fTile.setLocation(i * tileSizeX, j * tileSizeY);
                fTile.addMouseListener(new TileAdapter(fTile)); 
            }
        }
        gui.GameView.validate();//force le rafraichissement de l'élément. 
    }
}


