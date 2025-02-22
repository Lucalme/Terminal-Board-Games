package ares;

import Game.Game;
import action.ActionMaker;

public class Ares extends Game{
    public Ares(int nbOfPlayer){
        super(nbOfPlayer, 12, 5);
        this.ActionMaker = new ActionMaker(this); 
    }
}
