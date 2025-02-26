package ares;

import Game.Game;
import action.ActionMaker;

public class Ares extends Game{
    public Ares(int nbOfPlayer){
        super(nbOfPlayer, 30, 10);
        this.ActionMaker = new ActionMaker(this); 
    }

    public Ares(int nbOfPlayer, int SizeX, int SizeY){
        super(nbOfPlayer, SizeX, SizeY);
        this.ActionMaker = new ActionMaker(this);
    }
}
