package board.tile;

public enum TileType {
    FOREST, 
    MOUNTAIN,
    PASTURE,
    FIELD,
    SEA;

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