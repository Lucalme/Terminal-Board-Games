package ares;

import java.util.Random;

import Game.Game;
import action.ActionMaker;
import action.util.IO;
import board.tile.Tile;
import building.Army;
import building.BuildingEffectType;
import player.Player;

public class Ares extends Game{
    public Ares(int nbOfPlayer){
        super(nbOfPlayer, 30, 10);
        this.ActionMaker = new ActionMaker(this);
        initializeObjectives(); 
    }

    @Override
    public void StartGame(){
        System.out.print("\033\143");
        IO.SlowType("Vous devez construire des armées, Choissisez tour à tout une tuile : ");
        for(Player player : players ){
            BuildNewFreeArmy(player);
        }
        IO.SlowType("Vous avez tous droit à une armée supplémentaire, choissisez une tuile : ");
        for(int i = players.size() -1; i >=0 ; i--){
            Player player = players.get(i);
            BuildNewFreeArmy(player);
        }
        super.StartGame();  
    }


    private void BuildNewFreeArmy(Player player){
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
                done = true;
            }
        }
    }

    protected void initializeObjectives(){
        Random random = new Random();
        for (Player player : players) {
            int objectiveType = random.nextInt(3);
            String objective;
            switch (objectiveType){
                case 0 :
                objective = "Conquérir un nombre de tuiles";
                break;
                case 1 :
                objective = "Envahir une île";
                break;
                case 2 :
                objective = "Atteindre un certain nombre de guerriers";
                break;
                default:
                objective = "Conquérir un nombre de tuiles";
            }
            objectives.setObjective(player, objective);
        }
    }

    @Override
    protected boolean CheckWinCondition() {
        for (Player player : players) {
            String objective = objectives.getObjective(player);
            if (objective.equals("Conquérir un nombre de tuiles") && player.getConqueredTiles() >= 10) {
                return true;
            } else if (objective.equals("Envahir une île") && player.hasInvadedIsland()) {
                return true;
            } else if (objective.equals("Atteindre un certain nombre de guerriers") && player.getWarriors() >= 20) {
                return true;
            }
        }
        return false;
        
    }
}
