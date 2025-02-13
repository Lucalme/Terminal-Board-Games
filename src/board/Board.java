package board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import board.tile.Tile;
import board.tile.TileType;


/**
 * The Board class represents a game board consisting of tiles.
 * It provides methods to initialize the board, retrieve tiles, and update them.
 */
public class Board {

    private Tile[][] tiles;
    private int size_X = 7;
    private int size_Y = 7;

    /**
     * Returns the size of the board along the X-axis.
     * 
     * @return the size of the board along the X-axis.
     */
    public int SizeX() {
        return size_X;
    }

    /**
     * Returns the size of the board along the Y-axis.
     * 
     * @return the size of the board along the Y-axis.
     */
    public int SizeY() {
        return size_Y;
    }

    /**
     * Default constructor for the Board class.
     * Initializes the board with default size.
     */
    public Board() {
        InitTiles();
    }


    public Tile GetTileAtPosition(int x, int y){
        if(x < 0 || x > size_X -1 || y < 0 || y > size_Y -1){
            return null;
        }
        return tiles[x][y];
    }

    public Tile GetTileAtPosition(Position pos){
        return GetTileAtPosition(pos.x, pos.y);
    }

    /**
     * Returns a HashMap of the tiles on the board.
     * The keys are int arrays representing the coordinates of the tiles,
     * and the values are the Tile objects.
     * 
     * @return a HashMap of the tiles on the board.
     */
    public HashMap<int[], Tile> getTiles() {
        HashMap<int[], Tile> res = new HashMap<int[], Tile>();
        for (int i = 0; i < size_X; i++) {
            for (int j = 0; j < size_Y; j++) {
                if (tiles[i][j] != null) {
                    res.put(new int[]{i, j}, tiles[i][j]);
                }
            }
        }
        return res;
    }

    /**
     * Constructor for the Board class with custom size.
     * 
     * @param size_X the size of the board along the X-axis.
     * @param size_Y the size of the board along the Y-axis.
     */
    public Board(int size_X, int size_Y) {
        this.size_X = size_X;
        this.size_Y = size_Y;
        InitTiles();
    }

    /**
     * Initializes the tiles of the board.
     * The size of the board must be defined beforehand.
     */
    private void InitTiles() {
        tiles = new Tile[size_X][size_Y];
        int minWaterTiles = (int) (.66 * size_X * size_Y);
        int maxOtherTiles = (size_X * size_Y) - minWaterTiles;

        HashMap<int[], Tile> currentTiles = new HashMap<>();
        Random rand = new Random();
        for (int i = 0; i < maxOtherTiles; i++) {
            int randX;
            int randY;
            do {
                randX = rand.nextInt(size_X);
                randY = rand.nextInt(size_Y);
            } while (tiles[randX][randY] != null);

            Tile tile = new Tile();
            tiles[randX][randY] = tile;
            currentTiles.put(new int[]{randX, randY}, tile);
        }

        HashMap<int[], Tile> isolatedTiles = currentTiles
            .entrySet()
            .stream()
            .filter(a -> GetTilesNeighborhood(a.getKey()[0], a.getKey()[1]).length == 0)
            .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);

        for (Map.Entry<int[], Tile> kv : isolatedTiles.entrySet()) {
            tiles[kv.getKey()[0]][kv.getKey()[1]] = null;
        }
    }

    /**
     * Returns the neighboring tiles of the tile at position (x, y).
     * 
     * @param x the X-coordinate of the tile.
     * @param y the Y-coordinate of the tile.
     * @return an array of neighboring tiles.
     */
    public Tile[] GetTilesNeighborhood(int x, int y) {
        ArrayList<Tile> neighborhood = new ArrayList<Tile>();
        for (Directions dir : Directions.values()) {
            if (x + dir.X >= 0 && x + dir.X < size_X && y + dir.Y >= 0 && y + dir.Y < size_Y) {
                if (tiles[x + dir.X][y + dir.Y] != null) {
                    neighborhood.add(tiles[x + dir.X][y + dir.Y]);
                }
            }
        }
        return neighborhood.toArray(new Tile[neighborhood.size()]);
    }

    /**
     * Returns a string representation of the board.
     * 
     * @return a string representation of the board.
     * @throws Exception if an error occurs during string conversion.
     */
    public String toString(){
        int squareSize = 3;
        String[] lines = new String[size_Y * squareSize];
        for (int f = 0; f < size_Y * squareSize; f++) {
            lines[f] = "";
        }
        String water = "🟦";
        for (int i = 0; i < size_X; i++) {
            for (int j = 0; j < size_Y; j++) {
                String tileType = tiles[i][j] == null ? water : tiles[i][j].ToConsoleMode();
                for (int k = 0; k < squareSize; k++) {
                    String str = "";
                    for (int l = 0; l < squareSize; l++) {
                        str += tileType;
                    }
                    lines[j * squareSize + k] += str;
                }
            }
        }
        return String.join("\n", lines);
    }

    /**
     * Updates all tiles on the board.
     */
    public void UpdateAllTiles() {
        for (int i = 0; i < size_X; i++) {
            for (int j = 0; j < size_Y; j++) {
                if (tiles[i][j] != null) {
                    tiles[i][j].UpdateTile();
                }
            }
        }
    }
}

