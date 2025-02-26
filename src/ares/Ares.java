package ares;

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
                tile.SetBuilding(new Army(player, 1, BuildingEffectType.None, tile.GetIslandID()));
                done = true;
            }
        }
    }
}
