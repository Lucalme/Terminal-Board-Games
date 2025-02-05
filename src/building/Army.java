package building;

/**
 * Represents an Army building in the game.
 * The Army building is responsible for maintaining a number of warriors ready for battle.
 */
public class Army extends Building {
    
    /**
     * The number of warriors in the army.
     */
    private int warriors;

    /**
     * Constructs an Army building with the specified number of warriors.
     * 
     * @param warriors the number of warriors in the army
     */
    public Army(int warriors) {
        super(warriors);
        this.warriors = warriors;
    }

    /**
     * Defines the effect of the Army building.
     * 
     * @return a description of the army's effect
     */
    @Override
    public String effect() {
        return "The army with " + warriors + " warriors is ready for battle.";
    }
}