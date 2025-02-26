package demeter;

import Game.Game;
import action.ActionMaker;

public class Demeter extends Game {
    public Demeter(int nbOfPlayer){
        super(nbOfPlayer);
        this.ActionMaker = new ActionMaker(this);
    }

    public Demeter(int nbOfPlayer, int SizeX, int SizeY){
        super(nbOfPlayer, SizeX, SizeY);
        this.ActionMaker = new ActionMaker(this);
    }
}
