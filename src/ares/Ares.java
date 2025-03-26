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

    public Ares(List<Player> players){
        super(players);
        this.ActionMaker = new ActionMaker(this);
    }

    @Override
    public void StartGame(){
        System.out.print("\033\143");
        IO.SlowType("Vous devez construire des armées, Choissisez tour à tout une tuile : ");
        for(Player player : players ){
            BuildNewFreeArmy(player);
        }
        System.out.print("\033\143");
        IO.SlowType("Vous avez tous droit à une armée supplémentaire, choissisez une tuile : ");
        for(int i = players.size() -1; i >=0 ; i--){
            Player player = players.get(i);
            BuildNewFreeArmy(player);
        }
        super.StartGame();  
    }


    private void BuildNewFreeArmy(Player player){
        if(player instanceof COM){
            Tile tile = ((COM)player).getEmptyTile(this); 
            tile.SetBuilding(new Army(player, 1, BuildingEffectType.None, tile));
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
                tile.SetBuilding(new Army(player, 1, BuildingEffectType.None, tile));
                player.AddBuilding(tile.GetBuilding());
                done = true;
            }
        }
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
