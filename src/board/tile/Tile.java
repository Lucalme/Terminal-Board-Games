package board.tile;
import board.Position;
import board.resource.*;
import building.Building;
import building.BuildingEffectType;

import java.util.Random;


public class Tile {

    private TileType type;
    private int resourcesPresent;
    private ResourceType resourceType;
    public final Position position;
    private int island = -1;
    private Building building;


    /**
     * Constructeur par défaut de la classe Tile
     * Crée une Tile de type aléatoire.
     */
    public Tile(Position position){
        int alea = new Random().nextInt( TileType.values().length );
        this.position = position;
        type = TileType.values()[alea];
        this.resourcesPresent = 0;
        MatchResources(type);
    }


    /**
     * Crée une Tile avec le type et les resources initiales fournies
     * @param position
     * @param type
     * @param initialResources
     */
    public Tile(Position position, TileType type, int initialResources){
        this.position = position;
        this.type = type;
        this.resourcesPresent = initialResources;
        MatchResources(type);
    }

    public Tile(Position position, TileType type){
        this.type = type;
        this.position = position;
        MatchResources(type);
    }

    private ResourceType MatchResources(TileType type){
        switch(type){
            case Mountains : 
                resourceType = ResourceType.Ore;
                break;
            case Forest :
                resourceType = ResourceType.Wood;
                break;
            case Fields :
                resourceType =  ResourceType.Wheat;
                break;
            case Pastures:
                resourceType= ResourceType.Sheep;
                break;
            default:
                resourceType = null;
                break;
        }
        return resourceType;
    }

    public void UpdateTile(){
        if(building != null && building.effectType == BuildingEffectType.MultiplyResourceProduction){
            resourcesPresent++;
        }
        resourcesPresent++;
    }

    public int GetResourcesPresent(){
        return resourcesPresent;
    }

    public ResourceType GetResourceType(){
        return resourceType;
    }

    public TileType GetTileType(){
        return type;
    }

    public void ClearResources(){
        this.resourcesPresent = 0;
    }



    @Override
    public String toString(){
        //TODO
        switch (type) {
            case Mountains:
                return "M";
            case Forest:
                return "F";
            case Fields:
                return "f";
            case Pastures:
                return "P";
            default:
                return "_";
        }
    }

    public String ToConsoleMode(){
        switch (type) {
            case Mountains:
                return "⬛";
            case Forest:
                return "🟩";
            case Fields:
                return "🟨";
            case Pastures:
                return "🟧";
            default:
                return "🟦";
        }
    }

    public String ToBackground(){

        String res;
        switch(type){
            case Mountains:
                res = "\u001B[47m";
                break;
            case Forest:
                res = "\u001B[42m";
                break;
            case Fields: 
                res = "\u001B[43m";
                break;
            case Pastures:
                res = "\u001B[41m";
                break;
            default:
                res = "\u001B[44m";
        }
        return res ;
    }
    


    public int GetIslandID(){
        return island;
    }

    public void SetIslandID(int id){
        island = id;
    }

    public Building GetBuilding(){
        return building;
    }

    public void SetBuilding(Building building){
        this.building = building;
    }
}