package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import Game.Game;
import action.Action;
import action.ActionRequest;
import action.actions.ActionAttack;
import action.actions.ActionTrade;
import action.actions.AresBuildArmy;
import action.actions.AresBuildHarbour;
import board.resource.ResourceType;
import board.tile.Tile;

public class COM extends Player{
    
    private static int comNb = 0;

    public COM(){
        super(comNb);
        comNb++;
    }


    public ActionRequest promptAction(HashMap<String, Class<? extends Action>> possibleActions, Game game){
        try{
            Random r = new Random();
            int a = r.nextInt(possibleActions.entrySet().size());
            while(!(boolean)possibleActions.values().toArray()[a].getClass().getField("finishesTurn").getBoolean(null)){
                a = r.nextInt(possibleActions.entrySet().size());
            }
            @SuppressWarnings("unchecked")
            Class<? extends Action> c = (Class<? extends Action>) possibleActions.values().toArray()[a].getClass();
            c = ActionTrade.class;
            if(c == ActionTrade.class){
                ArrayList<ResourceType> availableResources = (ArrayList<ResourceType>)this.getResources().entrySet().stream().filter((entry) -> entry.getValue() > 3).map(e -> e.getKey()).collect(Collectors.toList());
                ActionTrade trade = new ActionTrade(this, availableResources.get(r.nextInt(availableResources.size())) , ResourceType.values()[r.nextInt(ResourceType.values().length)]);
                ActionRequest res = new ActionRequest(this, trade);
                return res;
            }else if(c == ActionAttack.class){
                Player target = null;
                while(target == null || target == this ){ //TODO : Attention ici si plus que 2 joueurs
                    target = game.players.get(r.nextInt(game.players.size()));
                }
                //Tile baseCamp = null;
                //for(Building b :  this.GetOwnedBuildings()){
//
                //}
                //ActionAttack attack = new ActionAttack(this, , null)
            }else if(c == AresBuildArmy.class){

            }else if(c == AresBuildHarbour.class){

            }
            return null;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public Tile getEmptyTile(Game game){
        HashMap<int[], Tile> tiles = game.board.getTiles();
        for(Tile tile : tiles.values()){
            if (tile.GetBuilding() == null){
                return tile;
            }
        }
        throw new RuntimeException("Pas de tuile vide disponible");
    }
}
