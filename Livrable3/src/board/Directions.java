package board;

public enum Directions {
    East( 1, 0),
    North(0, 1),
    West(-1, 0),
    South(0, -1);

    public final int X;
    public final int Y;

    private Directions(int X, int Y){
        this.X = X;
        this.Y = Y;
    }
}
