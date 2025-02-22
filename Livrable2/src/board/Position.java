package board;

public class Position {
    public final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position position) {
        return x == position.x && y == position.y;
    }
        
}
