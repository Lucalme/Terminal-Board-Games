package building;


public class Port extends Building {
    public Port() {
        super(1);
    }

    @Override
    public String effect() {
        return "The port allows advantageous resource exchanges.";
    }
}