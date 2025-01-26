package board.tile;

import board.resource.ResourceType;
import java.util.Random;

public class Tile {
    private TileType type;
    private int resourcesPresent;
    private ResourceType resourceType;

    public Tile() {
        int alea = new Random().nextInt(TileType.values().length);
        type = TileType.values()[alea];
        this.resourcesPresent = 0;
        MatchResources(type);
    }

    public Tile(TileType type, int initialResources) {
        this.type = type;
        this.resourcesPresent = initialResources;
        MatchResources(type);
    }

    // Initialize to null
    public Tile(TileType type) {
        this.type = type;
        this.resourceType = null; 
        MatchResources(type);
    }

    private ResourceType MatchResources(TileType type) {
        switch (type) {
            case MOUNTAIN:
                resourceType = ResourceType.Ore;
                break;
            case FOREST:
                resourceType = ResourceType.Wood;
                break;
            case FIELD:
                resourceType = ResourceType.Wheat;
                break;
            case PASTURE:
                resourceType = ResourceType.Sheep;
                break;
            default:
                resourceType = null;
                break;
        }
        return resourceType;
    }

    public int GetResourcesPresent() {
        return resourcesPresent;
    }

    public ResourceType GetResourceType() {
        return resourceType;
    }

    public TileType GetTileType() {
        return type;
    }

    @Override
    public String toString() {
        switch (type) {
            case MOUNTAIN:
                return "M";
            case FOREST:
                return "F";
            case FIELD:
                return "f";
            case PASTURE:
                return "P";
            default:
                return "_";
        }
    }
}