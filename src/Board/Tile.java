package Board;
import java.util.Random;

public class Tile {

    private TileType type;
    private int resourcesPresent;
    private ResourceType resourceType;

    
    public Tile(TileType type, int initialResources){
        this.type = type;
        this.resourcesPresent = initialResources;
    }

    public Tile(){
        int alea = new Random().nextInt( TileType.values().length );

        type = TileType.values()[alea];

        System.out.println("Le type de Tuile est : "+type.toString());

        this.resourcesPresent = 0;

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
        if(resourceType != null){
            System.out.println("Le type de resource est :" + resourceType.toString());
        }else{
            System.out.println("Cette tuile n'a pas de resources ! ");
        }
    }

    public int GetResourcesPresent(){
        return resourcesPresent;
    }


}