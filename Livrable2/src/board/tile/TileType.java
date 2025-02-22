package board.tile;

public enum TileType {
    Forest("Forêt"),
    Mountains("Montagne"),
    Pastures("Pâturage"), 
    Fields("Champs");

    public final String name;

    private TileType(String s){
        this.name = s;
    }

    public String toString(){
        return name;
    }
}