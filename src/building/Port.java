package building;

/**
 * Represents a Port building in the game.
 * The Port building enables advantageous resource exchanges.
 */
public class Port extends Building {
    
    /**
     * Constructs a Port building with a default size of 1.
     */
    public Port() {
        super(1);
    }

    /**
     * Defines the effect of the Port building.
     * 
     * @return a description of the port's effect
     */
    @Override
    public String effect() {
        return "The port allows advantageous resource exchanges.";
    }
}