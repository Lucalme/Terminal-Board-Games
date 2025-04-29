package ares;
import GUI.GUIActionMaker;
import GUI.GUIGame;
import Game.Game;
import Game.GameType;

public class GUIAres extends GUIGame{

    public GUIAres(int nbOfPlayer) {
        super(nbOfPlayer, "Ares", new int[]{800, 600});
        actionMaker = new GUIActionMaker(this);
        StartGame();
    }

    public GameType getGameType(){
        return GameType.ARES;
    }

    public static void main(String[] args){
        Game game = new GUIAres(2);
    }
}
