package board.resource;

public enum ResourceType{
    Wheat(true),
    Ore(true),
    Sheep(true), 
    Wood(true),
    Warriors(false);

    public final boolean isTradable;

    private ResourceType(boolean isTradable){
        this.isTradable = isTradable;
    }
}