package ares;
import Game.GUIGame;
import Game.Game;

public class GUIAres extends Ares{
    
    public GUIAres(int nbOfPlayer) {
        super(nbOfPlayer);
    }

    @Override
    public void StartGame(){
        
    }
    
    public static void main(String[] args){
        Game game = new GUIGame(2, "Ares", new int[]{800, 600});
    }
}
