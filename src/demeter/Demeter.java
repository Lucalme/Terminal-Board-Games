package demeter;

import Game.Game;
import action.ActionMaker;
import player.Player;


public class Demeter extends Game {
    public Demeter(int nbOfPlayer){
        super(nbOfPlayer);
        this.ActionMaker = new ActionMaker(this);
    }

    public Demeter(int nbOfPlayer, int SizeX, int SizeY){
        super(nbOfPlayer, SizeX, SizeY);
        this.ActionMaker = new ActionMaker(this);
    }

    @Override
    protected boolean CheckWinCondition() {
        for (Player player : players) {
            int points = calculatePoints(player);
            if (points >= 12) {
                return true;
            }
        }
        return false;
    }

    private int calculatePoints(Player player) {
        int points = 0;
        points += player.getFarmCount();
        points += player.getExploitationCount() * 2;
        int islands = player.getIslandsCount();
        if(islands >= 2){
            points+=1;
        }
        if (islands > 2) {
            points += 2;
        }
        return points;
    }
}
