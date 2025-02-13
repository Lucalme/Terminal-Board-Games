package action;

import java.util.ArrayList;

import ares.Ares;
import player.Player;
import Game.Game;

public class ActionMain{
    public static void main(String[] args) {
        Player player = new Player(-1);
        ArrayList<Player> players = new ArrayList<Player>(){
            {add(player);}
        };
        Game game = new Ares(1);
        ActionRequest request = ActionRequest.Prompt(player, game);
    }
}