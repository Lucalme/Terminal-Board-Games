package board.tile;
import board.resource.*;
//import java.util.HashMap;
import java.util.Random;


public class Tile {

    private TileType type;
    private int resourcesPresent;
    private ResourceType resourceType;


    /**
     * Constructeur par défaut de la classe Tile
     * Crée une Tile de type aléatoire.
     */
    public Tile(){
        int alea = new Random().nextInt( TileType.values().length );

        type = TileType.values()[alea];
        this.resourcesPresent = 0;
        MatchResources(type);
    }
    
    /**
     * Crée une Tile avec le type et les resources initiales fournies
     * @param type
     * @param initialResources
     */
    public Tile(TileType type, int initialResources){
        this.type = type;
        this.resourcesPresent = initialResources;
        MatchResources(type);
    }

    /**
     * Crée une Tile du type fourni
     * @param type
     */
    public Tile(TileType type){
        this.type = type;
        MatchResources(type);
    }

    private ResourceType MatchResources(TileType type){
        switch(type){
            case Mountains : 
                resourceType = resourceType.Ore;
                break;
            case Forest :
                resourceType = resourceType.Wood;
                break;
            case Fields :
                resourceType =  resourceType.Wheat;
                break;
            case Pastures:
                resourceType= resourceType.Sheep;
                break;
            default:
                resourceType = null;
                break;
        }
        return resourceType;
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


    @Override
    public String toString(){
        //TODO
        switch (type) {
            default:
                return "_";
        }
        
    }
}