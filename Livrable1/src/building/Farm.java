package building;


public class Farm extends Building {
    public Farm() {
        super(1);
    }

    @Override
    public String effect() {
        return "The farm produces basic resources.";
    }
}