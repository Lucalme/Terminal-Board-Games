package ares;

import java.util.List;
import java.util.Random;

import Game.Game;
import Objectives.ObjectiveType;
import action.ActionMaker;
import action.util.IO;
import board.tile.Tile;
import building.Army;
import building.BuildingEffectType;
import player.COM;
import player.Player;

public class Ares extends Game{
    public Ares(int nbOfPlayer){
        super(nbOfPlayer, 10, 10);
        this.ActionMaker = new ActionMaker(this);
        initializeObjectives(); 
    }

    public Ares(boolean COMGame, int nbOfPlayer){
        super(COMGame, nbOfPlayer);
        this.ActionMaker = new ActionMaker(this);
        initializeObjectives(); 
    }

    public Ares(List<Player> players){
        super(players);
        this.ActionMaker = new ActionMaker(this);
    }

    @Override
    public void StartGame(){
        System.out.print("\033\143");
        IO.SlowType("Vous devez construire des armées, Choissisez tour à tour une tuile : ");
        for(Player player : players ){
            BuildNewFreeArmy(player, 1);
            IO.PrintReset();
        }
        System.out.print("\033\143");
        IO.SlowType("Vous avez tous droit à une armée supplémentaire, choissisez une tuile sur la même ile : ");
        for(int i = players.size() -1; i >=0 ; i--){
            Player player = players.get(i);
            BuildNewFreeArmy(player, 2);
            IO.PrintReset();
        }
        super.StartGame();  
    }


    private void BuildNewFreeArmy(Player player, int round){
        if(player instanceof COM){
            Tile tile = ((COM)player).getEmptyTile(this); 
            if(round == 2){
                int firstBuildingId = player.GetOwnedBuildings().get(0).tile.GetIslandID();
                while(tile.GetIslandID() != firstBuildingId ){
                    tile = ((COM)player).getEmptyTile(this);
                }
            }else {
                while(!islandIsNotOccupied(tile) || !islandHasAtLeast4Tiles(tile)){
                    tile = ((COM)player).getEmptyTile(this);
                }
            }
            tile.SetBuilding(new Army(player, 1, BuildingEffectType.None, tile));
            player.AddBuilding(tile.GetBuilding());
            return;
        }
        String str = board.toString();
        System.out.println(str);
        boolean done = false;
        IO.SlowType("C'est au tour de "+player.toString());
        while(!done){
            Tile tile = ActionMaker.PromptTile(player);
            if(round == 1 && !islandIsNotOccupied(tile)){
                IO.SlowType("Cette île est déjà occupée...");
            }else if(!islandHasAtLeast4Tiles(tile)){
                IO.SlowType("Cette île est trop petite...");
            }else if(round == 2 && tile.GetIslandID() != player.GetOwnedBuildings().get(0).tile.GetIslandID()){
                IO.SlowType("Vous devez construire sur la même île que votre première armée...");
            }else{
                tile.SetBuilding(new Army(player, 1, BuildingEffectType.None, tile));
                player.AddBuilding(tile.GetBuilding());
                done = true;
            }
            System.out.println(board);
        }
    }

    /** un prédicat pour les armées initiales gratuites */
    private boolean islandIsNotOccupied(Tile tile){
        return board.getTiles().entrySet().stream()
                .filter(entry -> entry.getValue().GetIslandID() == tile.GetIslandID())
                .filter(entry -> entry.getValue().GetBuilding() != null)
                .count() == 0;
    }


    /** un prédicat pour les armées initiales gratuites */
    private boolean islandHasAtLeast4Tiles(Tile tile){
        return board.getTiles().entrySet().stream()
                .filter(entry -> entry.getValue().GetIslandID() == tile.GetIslandID())
                .count() >= 4;
    }

    protected void initializeObjectives(){
        Random random = new Random();
        for (Player player : players) {
            int objectiveType = random.nextInt(ObjectiveType.values().length);
            ObjectiveType objective = ObjectiveType.values()[objectiveType];
            objectives.setObjective(player, objective);
        }
    }

    @Override
    protected boolean CheckWinCondition() {
        for (Player player : players) {
            ObjectiveType objective = objectives.getObjective(player);
            switch (objective){
                case CONQUER_TILES:
                    if (player.GetOwnedBuildings().size() > 25){
                        return true;
                    }
                    break;
                case INVADE_ISLAND :
                    return false; //TODO: logique d'envahissement d'île
                case REACH_WARRIORS :
                    if (player.getTotalWarriors() >= 50) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }
}
