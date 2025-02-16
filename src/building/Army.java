package building;


public class Army extends Camp {
    
    private int warriors;

    public Army(int warriors) {
        super(warriors, BuildingEffectType.MultiplyResourceProduction);
        this.warriors = warriors;
    }

    public String effect() {
        return "The army with " + warriors + " warriors is ready for battle.";
    }
}