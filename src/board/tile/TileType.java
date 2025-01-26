package board.tile;

public enum TileType {
    FOREST("Forest"), 
    MOUNTAIN("Mountain"),
    PASTURE("Pasture"),
    FIELD("Field"),
    SEA("Sea");

    private final String name;

    TileType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}