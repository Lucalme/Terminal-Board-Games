package ares;
import Game.GUIActionMaker;
import Game.GUIGame;
import Game.Game;

public class GUIAres extends GUIGame{

    public GUIAres(int nbOfPlayer) {
        super(nbOfPlayer, "Ares", new int[]{800, 600});
        actionMaker = new GUIActionMaker(this);
        StartGame();
    }


    public static void main(String[] args){
        Game game = new GUIAres(2);
    }
}
