package building;


public class Army extends Building {
    private int warriors;

    public Army(int warriors) {
        super(warriors);
        this.warriors = warriors;
    }

    @Override
    public String effect() {
        return "The army with " + warriors + " warriors is ready for battle.";
    }
}