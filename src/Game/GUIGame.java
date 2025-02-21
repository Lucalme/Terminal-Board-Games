package Game;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.JPanel;

import action.Action;
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
        gui = new GUI(name, WindowSize, this);
        gui.Console.Print("Bienvenue dans "+ name);
        gui.Console.Print("Nombre de joueurs : "+ nbOfPlayer);
    }

    /** La méthode StartGame(GUI) doit être appelée explicitement par un joueur */
    public void StartGame(){
        board.UpdateAllTiles();
        DrawBoard();
        gui.playerPanel.Update(players.get(currentPlayerIndex));
        gui.Console.Print("C'est au tour de "+ players.get(currentPlayerIndex).toString());
        gui.Console.ShowOptions(actionMaker.ShowPossibleActions(players.get(currentPlayerIndex)));
    }

    /**inutile? */
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


    public void HandleAction(Class<? extends Action> actionClass, Player player){
        actionMaker.HandleGameAction(actionClass, player);
    }

    protected void DrawBoard(){
        //TODO: Migrer l'essentiel du dessin dans une classe GameView
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
                gui.TilePicker.setTileColor(fTile, tile);
                if(tile == null) {continue;}
                fTile.addMouseListener(new MouseAdapter() {
                   @Override
                   public void mouseEntered(MouseEvent e){
                    gui.TilePicker.setTileInfo(tile);
                    gui.TilePicker.setTileColor(fTile, tile, true);
                   }

                   @Override
                   public void mouseClicked(MouseEvent e){
                    gui.TilePicker.setSelection(tile, fTile);
                   }
                
                   public void mouseExited(MouseEvent e){
                    gui.TilePicker.setTileColor(fTile, tile, false);
                   }
                    
                }); 
            }
            c.gridx = 0;
            c.gridy++;
        }
        gui.GameView.validate();//force le rafraichissement de l'élément. 
        gui.GameView.repaint();
    }

}
