package tile;
import java.util.HashMap;
import java.util.Random;

public class Tile {

    private TileType type;
    private int resourcesPresent;
    private ResourceType resourceType;


    public Tile(){
        int alea = new Random().nextInt( TileType.values().length );

        type = TileType.values()[alea];
        this.resourcesPresent = 0;
        MatchResources(type);
    }
    
    public Tile(TileType type, int initialResources){
        this.type = type;
        this.resourcesPresent = initialResources;
        MatchResources(type);
    }

    public Tile(TileType type){
        this.type = type;
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
            case Water:
                return "O";
            default:
                return "_";
        }
        
    }
}