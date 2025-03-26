package demeter;

import java.util.List;

import Game.Game;
import action.ActionMaker;
import action.util.IO;
import building.Army;
import building.Building;
import building.BuildingEffectType;
import building.Farm;
import player.Player;
import board.tile.Tile;

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
    public void StartGame(){
        System.out.print("\033\143");
        IO.SlowType("Vous devez construire des fermes, Choissisez tour à tout une tuile : ");
        for(Player player : players ){
            BuildNewFreeFarm(player);
        }
        System.out.print("\033\143");
        IO.SlowType("Vous avez tous droit à une ferme supplémentaire, choissisez une tuile : ");
        for(int i = players.size() -1; i >=0 ; i--){
            Player player = players.get(i);
            BuildNewFreeFarm(player);
        }
        super.StartGame();  
    }
    private void BuildNewFreeFarm(Player player){
        String str = board.toString();
        System.out.println(str);
        boolean done = false;
        IO.SlowType("C'est au tour de "+player.toString());
        while(!done){
            Tile tile = ActionMaker.PromptTile(player);
            if(tile.GetBuilding() != null){
                IO.SlowType("Il y a déjà un batiment sur cette tuile...");
            }else{
                Farm newFarm = new Farm(player, BuildingEffectType.None, tile); // Create the new farm
                tile.SetBuilding(newFarm); // Set the farm on the tile
                player.AddBuilding(newFarm);
                done = true;
            }
        }
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
