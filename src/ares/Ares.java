package ares;

import java.util.List;

import Game.Game;
import player.Player;
import action.ActionMaker;

public class Ares extends Game{
    public Ares(int nbOfPlayer){
        super(nbOfPlayer, 30, 10);
        this.ActionMaker = new ActionMaker(this); 
    }
}
