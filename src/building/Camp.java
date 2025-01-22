package building;


public class Camp extends Building {
    private int warriors;

    public Camp(int warriors) {
        super(warriors);
        this.warriors = warriors;
    }

    @Override
    public String effect() {
        return "The camp improves resource production.";
    }
}