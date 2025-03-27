package ares;

import java.util.ArrayList;
import java.util.List;

import player.COM;
import player.Player;

public class Livrable3 extends Ares{

    static COM player1 = new COM();
    static COM player2 = new COM();
    static List<Player> p = new ArrayList<>(){{
        add(player1);
        add(player2);
    }};
    
    public Livrable3(){
        super(p);
    }

    public static void main(String[] args){
        Livrable3 game = new Livrable3();
        game.StartGame();
    }

    @Override
    public boolean CheckWinCondition(){
        return false;
    }
}
