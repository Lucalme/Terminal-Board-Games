package demeter;

import java.util.List;

import Game.Game;
import action.ActionMaker;
import building.Building;
import building.Farm;
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
        List<Building> buildings = player.GetOwnedBuildings();
        //Calcul du nombre de fermes
        for (Building building : buildings) {
            if (building instanceof Farm) {
                points++;
            }
            //else if(building instanceof Exploitation){
            //    points += 2;
            //} 
            //TODO: implementer les expoitations
        }
        //if(islands >= 2){
        //    points+=1;
        //}
        //if (islands > 2) {
        //    points += 2;
        //}
        return points;
    }
}
