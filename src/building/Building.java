package building;

/**
 * Represents an abstract Building in the game.
 * Buildings have a size and can have specific effects that are implemented in subclasses.
 * 
 * Subclasses should provide specific implementations for the effect method.
 */
public abstract class Building {

    /**
     * The size of the building, which can represent its dimensions, capacity, or any other relevant attribute.
     */
    protected int size;

    /**
     * Constructs a Building with the specified size.
     * 
     * @param size the size of the building
     * @param cost the construction cost of the building
     */
    public Building(int size, BuildingEffectType effectType) {
        this.size = size;
    }

    /**
     * Gets the size of the building.
     * 
     * @return the size of the building
     */
    public int getSize() {
        return size;
    }
   
}