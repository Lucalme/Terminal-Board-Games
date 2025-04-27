package demeter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Game.Game;
import Game.GameType;
import action.ActionMaker;
import action.util.IO;
import building.Army;
import building.Building;
import building.BuildingEffectType;
import building.Exploitation;
import building.Farm;
import player.COM;
import player.Player;
import board.resource.ResourceType;
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

    public Demeter(boolean COMGame, int nbOfPlayer){
        super(COMGame, nbOfPlayer);
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
        if(player instanceof COM){
            Tile tile = ((COM)player).getEmptyTile(this); 
            Farm newFarm = new Farm(player, BuildingEffectType.None, tile); 
            tile.SetBuilding(newFarm); 
            player.AddBuilding(newFarm);
            return;
        }
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

    public GameType getGameType() {
        return GameType.DEMETER;
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
        Set<Integer> islandSet = new HashSet<>();
    
        for (Building building : buildings) {
            islandSet.add(building.islandId);
    
            if (building instanceof Farm) {
                points += 1;
            } else if (building instanceof Exploitation) {
                points += 2;
                
            }
        }

    
        if (islandSet.size() == 2) {
            points += 1;
        }
        if (islandSet.size() > 2) {
            points += 2;
        }
    
        player.setResource(ResourceType.VictoryPoints, points);
    
        return points;
    }
    @Override
    protected void gameLoop() {
        super.gameLoop(); // Run the main loop

        // 🎉 Announce winner(s)
        for (Player player : players) {
            int points = calculatePoints(player);
            if (points >= 12) {
                IO.SlowType("🎉 Le joueur " + player.toString() + " a gagné avec " + points + " points !", 40);
            }
        }
}

    
}
