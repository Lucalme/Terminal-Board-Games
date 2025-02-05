package building;

/**
 * Represents a Camp building in the game.
 * The Camp building enhances resource production.
 */
public class Camp extends Building {
    
    /**
     * The number of warriors stationed in the camp.
     */
    private int warriors;

    /**
     * Constructs a Camp building with the specified number of warriors.
     * 
     * @param warriors the number of warriors in the camp
     */
    public Camp(int warriors) {
        super(warriors);
        this.warriors = warriors;
    }

    /**
     * Defines the effect of the Camp building.
     * 
     * @return a description of the camp's effect
     */
    @Override
    public String effect() {
        return "The camp improves resource production.";
    }
}
