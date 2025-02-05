package building;

/**
 * Represents a Farm building in the game.
 * The Farm building produces basic resources for the settlement.
 */
public class Farm extends Building {
    
    /**
     * Constructs a Farm building with a default size of 1.
     */
    public Farm() {
        super(1);
    }

    /**
     * Defines the effect of the Farm building.
     * 
     * @return a description of the farm's effect
     */
    @Override
    public String effect() {
        return "The farm produces basic resources.";
    }
}