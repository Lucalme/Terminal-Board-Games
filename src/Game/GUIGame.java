package Game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.text.Style;
import javax.swing.JLabel;

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
        //int tileSizeX = (int)gui.GameView.getPreferredSize().getWidth() / board.SizeX();
        //int tileSizeY = (int)gui.GameView.getPreferredSize().getHeight() / board.SizeY();
        System.out.println("Preferred GameView Size : " + gui.GameView.getPreferredSize());
        System.out.println("GameView Size : " + gui.GameView.getSize());
        for(int i =0 ; i < board.SizeY(); i++){
            for(int j = 0; j < board.SizeX(); j++){
                Tile tile = board.GetTileAtPosition(j,i);
                JPanel fTile = new JPanel();
                fTile.setLayout(new GridLayout(2,2));
                gui.GameView.add(fTile);
                fTile.validate();
                setTileColor(fTile, tile);

                fTile.addMouseListener(new MouseAdapter() {
                   @Override
                   public void mouseEntered(MouseEvent e){
                    gui.TilePicker.setTileInfo(tile);
                   }

                   @Override
                   public void mouseClicked(MouseEvent e){
                    gui.TilePicker.setSelection(tile);
                   }
                
                   //public void mouseExited(MouseEvent e){

                   //}
                    
                }); 
            }
        }
        gui.GameView.validate();//force le rafraichissement de l'élément. 
    }

    private void setTileColor(JPanel c, Tile tile){
        if(tile == null ){
            c.setBackground(Color.blue);
            return;
        }
        switch(tile.GetTileType()){
            case Fields:
                c.setBackground(Color.YELLOW);
                break;
            case Mountains:
                c.setBackground(Color.LIGHT_GRAY);
                break;
            case Pastures:
                c.setBackground(Color.ORANGE);
                break;
            case Forest:
                c.setBackground(Color.green);
        }
        c.validate();
    }
}


class TileAdapter extends MouseAdapter{


    //private setTileInfo(){
//
    //}
}