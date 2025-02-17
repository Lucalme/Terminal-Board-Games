package board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import board.tile.Tile;


/**
 * The Board class represents a game board consisting of tiles.
 * It provides methods to initialize the board, retrieve tiles, and update them.
 */
public class Board {

    private Tile[][] tiles;
    private int size_X = 7;
    private int size_Y = 7;
    
    public int SizeX() {
        return size_X;
    }
    public int SizeY() {
        return size_Y;
    }

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
        Populate();
        Filter();
        Group();
    }

    private void Populate(){
        tiles = new Tile[size_X][size_Y];
        int minWaterTiles = (int) (.66 * size_X * size_Y);
        int maxOtherTiles = (size_X * size_Y) - minWaterTiles;

        Random rand = new Random();
        for (int i = 0; i < maxOtherTiles; i++) {
            int randX;
            int randY;
            do {
                randX = rand.nextInt(size_X);
                randY = rand.nextInt(size_Y);
            } while (tiles[randX][randY] != null);

            Tile tile = new Tile(new Position(randX, randY));
            tiles[randX][randY] = tile;
        }
    }

    private void Filter(){
        HashMap<int[], Tile> isolatedTiles = getTiles()
        .entrySet()
        .stream()
        .filter(a -> GetTilesNeighborhood(a.getKey()[0], a.getKey()[1]).length == 0)
        .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);

        for (Map.Entry<int[], Tile> kv : isolatedTiles.entrySet()) {
            tiles[kv.getKey()[0]][kv.getKey()[1]] = null;
        }
    }

    private void Group(){
        int id = 0;
        for(int i = 0; i < size_X ; i++){
            for(int j = 0; j < size_Y ; j++){
                Tile tile = tiles[i][j];
                if(tile == null || tile.GetIslandID() != -1){continue;}
                RecursiveAddIslandId(i, j, id);
                id++;
            }
        }
    }

    private void RecursiveAddIslandId(int x, int y, int id){
        tiles[x][y].SetIslandID(id);
        for(Directions dir : Directions.values()){
            if(x+dir.X <0 || x+dir.X >= size_X || y+dir.Y < 0 || y+dir.Y >= size_Y ){continue;}
            Tile neigh = tiles[x+dir.X][y+dir.Y];
            if(neigh != null && neigh.GetIslandID() == -1){
                RecursiveAddIslandId(x+dir.X, y+dir.Y, id);
            }
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
        islandCount = 0;
        int squareSize = 2;
        String[] lines = new String[size_Y * squareSize];
        for (int f = 0; f < size_Y * squareSize; f++) {
            lines[f] = "";
        }
        String reset = "\u001B[0m";
        for (int i = 0; i < size_X; i++) {
            for (int j = 0; j < size_Y; j++) {
                //String tileType = tiles[i][j] == null ? water : "\u001B[41m"+ (tiles[i][j].GetIslandID() %10)+"\uFE0F\u20E3" +" " + "\u001B[0m";
                //String tileType = tiles[i][j] == null ? water :  tiles[i][j].ToConsoleMode() ; 
                Tile tile = tiles[i][j];
                //String tileType = tile == null ? "\u001B[44m  \u001B[0m" : tile.ToBackground()+ space + reset;
                for (int k = 0; k < squareSize; k++) {
                    String str = "";
                    for (int l = 0; l < squareSize; l++) {
                        str += tile == null ? "\u001B[44m  \u001B[0m" : tile.ToBackground()+ TileSpace(tile, (k ==squareSize -1 && l == squareSize -1), (k == 0 && l == squareSize -1)) + reset;
                    }
                    lines[j * squareSize + k] += str;
                }
            }
        }
        return String.join("\n", lines);
    }

    private int islandCount = 0;

    private String TileSpace(Tile tile, boolean isLast, boolean isBuilding){
        String space = (isBuilding && tile.GetBuilding() != null)? "🏦" : "  ";
        if(tile != null && tile.GetIslandID() == islandCount){
            space = "\033[0;94m"+(tile.GetIslandID() < 10?  tile.GetIslandID()+" " : ""+tile.GetIslandID())+"\033[0m";
            islandCount++;
        }
        if(tile != null && isLast){
            space = tile.GetResourcesPresent() <10 ? " "+tile.GetResourcesPresent() : ""+tile.GetResourcesPresent();
        }
        return space;
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

