package building;


public abstract class Building {
    protected int size;

    public Building(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public abstract String effect();
}
