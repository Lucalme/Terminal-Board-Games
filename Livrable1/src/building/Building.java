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
     */
    public Building(int size) {
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

    /**
     * Abstract method to define the effect of the building.
     * Subclasses must implement this method to specify the behavior or impact of the building in the game.
     * 
     * @return a description of the building's effect
     */
    public abstract String effect();
}
