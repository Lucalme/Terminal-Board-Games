package Game;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.JPanel;

import action.ActionRequest;
import board.tile.Tile;
import player.Player;


public abstract class GUIGame extends Game {

    public final GUI gui;
    protected GUIActionMaker actionMaker;
    protected int currentPlayerIndex = 0;

    public GUIGame(int nbOfPlayer, String name, int[] WindowSize) {
        super(nbOfPlayer, 29, 16); //TODO: Transmettre la taille du board en paramètre
        //int[] screenSize = {Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height};
        gui = new GUI(name, WindowSize);
        gui.Console.Print("Bienvenue dans "+ name);
        gui.Console.Print("Nombre de joueurs : "+ nbOfPlayer);
    }

    /** La méthode StartGame(GUI) doit être appelée explicitement par un joueur */
    public void StartGame(){
        board.UpdateAllTiles();
        DrawBoard();
        gui.playerPanel.Update(players.get(currentPlayerIndex));
    }

    public void HandleActionRequest(ActionRequest request){
        Player player = players.get(currentPlayerIndex);
        if(request == null || !request.action.CheckInstancePossible(player, this)){
            gui.Console.Print("Action impossible, Veuillez réessayer....");
            return;
        }
        request.action.Effect();
        gui.Console.Print(request.action.Description());
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    protected void DrawBoard(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        for(int i =0 ; i < board.SizeY(); i++){
            for(int j = 0; j < board.SizeX(); j++){
                Tile tile = board.GetTileAtPosition(j,i);
                JPanel fTile = new JPanel();
                gui.GameView.add(fTile, c);
                c.gridx++;
                fTile.validate();
                setTileColor(fTile, tile);

                fTile.addMouseListener(new MouseAdapter() {
                   @Override
                   public void mouseEntered(MouseEvent e){
                    if(tile == null) return;
                    gui.TilePicker.setTileInfo(tile);
                    fTile.setBackground(fTile.getBackground().darker());
                   }

                   @Override
                   public void mouseClicked(MouseEvent e){
                    gui.TilePicker.setSelection(tile);
                   }
                
                   public void mouseExited(MouseEvent e){
                    setTileColor(fTile, tile);
                   }
                    
                }); 
            }
            c.gridx = 0;
            c.gridy++;
        }
        gui.GameView.validate();//force le rafraichissement de l'élément. 
        gui.GameView.repaint();
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
