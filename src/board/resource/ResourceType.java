package board.resource;

public enum ResourceType{
    Wheat(true, "Blé"),
    Ore(true, "Minerai"),
    Sheep(true, "Mouton"), 
    Wood(true,"Bois"),
    Warriors(false,"Guerriers"),
    Thief(false, "Voleur");

    public final boolean isTradable;
    public final String name;

    private ResourceType(boolean isTradable, String s){
        this.isTradable = isTradable;
        this.name = s;
    }

    public String toString(){
        return name;
    }
}