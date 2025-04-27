package action;

import java.util.ArrayList;

import ares.Ares;
import player.Player;
import Game.Game;

public class ActionMain{
    public static void main(String[] args) {
        Game game = new Ares(1);
        Player player = new Player(-1, game);
        ArrayList<Player> players = new ArrayList<Player>(){
            {add(player);}
        };
        ActionMaker actionMaker = new ActionMaker(game);
        ActionRequest request = actionMaker.Prompt(player);
    }
}