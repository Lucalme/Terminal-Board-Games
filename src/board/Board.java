package board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import board.tile.Tile;
import building.Building;
import player.Player;


/**
 * The Board class represents a game board consisting of tiles.
 * It provides methods to initialize the board, retrieve tiles, and update them.
 */
public class Board {

    private Tile[][] tiles;
    private int size_X = 10;
    private int size_Y = 10;
    private int nbOfIslands;
    
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

    public HashMap<Building , Player> getBuildings(){
        HashMap<Building, Player> res = new HashMap<>();
        for(Map.Entry<int[], Tile> entry : getTiles().entrySet()){
            if(entry.getValue().GetBuilding() != null){
                res.put(entry.getValue().GetBuilding(), entry.getValue().GetBuilding().owner);
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
        Expand();
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
        nbOfIslands = id;
    }

    private void Expand(){
        int maxOtherTiles = (size_X * size_Y) - (int) (.66 * size_X * size_Y);
        if(tiles.length >= maxOtherTiles -2){
            return;
        }
        HashMap<Integer, Integer> islandSizes = new HashMap<>();
        for(Map.Entry<int[], Tile> kv : getTiles().entrySet()){
            int id = kv.getValue().GetIslandID();
            if(islandSizes.containsKey(id)){
                islandSizes.put(id, islandSizes.get(id) + 1);
            }else{
                islandSizes.put(id, 1);
            }
        }
        int minIslandSize = islandSizes.values().stream().min(Integer::compareTo).orElse(2);

        for(Tile t : getTiles().values()){
            if(islandSizes.get(t.GetIslandID()) <= minIslandSize + 2){
                for(Directions dir : Directions.values()){
                    int x = t.position.x + dir.X;
                    int y = t.position.y + dir.Y;
                    if(x < 0 || x >= size_X || y < 0 || y >= size_Y){continue;}
                    if(GetTilesNeighborhood(x,y).length == 1){
                        Tile newTile = new Tile(new Position(x, y));
                        tiles[x][y] = newTile;
                        int id = t.GetIslandID();
                        newTile.SetIslandID(id);
                        islandSizes.put(id, islandSizes.get(id) + 1);
                    }
                    if(islandSizes.get(t.GetIslandID()) >= maxOtherTiles -1){
                        break;
                    }
                }
            }
            if(islandSizes.get(t.GetIslandID()) >= maxOtherTiles -1){
                break;
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
        String space = (isBuilding && tile.GetBuilding() != null)? tile.GetBuilding().toString() : "  ";
        if(tile != null && tile.GetIslandID() == islandCount){
            space = "\033[0;94m"+(tile.GetIslandID() < 10?  tile.GetIslandID()+" " : ""+tile.GetIslandID())+"\033[0m";
            islandCount++;
        }
        if(tile != null && isLast){
            space = tile.GetResourcesPresent() <10 ? " "+tile.GetResourcesPresent() : ""+tile.GetResourcesPresent();
        }
        return space;
    }

    public int GetIslandCount(){
        return nbOfIslands;
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

    public ArrayList<Tile> PrintIsland(int id){
        ArrayList<Tile> island = new ArrayList<>();
        for(int i = 0 ; i < size_X ; i++){
            for(int j = 0 ; j < size_Y ; j++){
                if(tiles[i][j] != null && tiles[i][j].GetIslandID() == id){
                    island.add(tiles[i][j]);
                }
            }
        }
        int minx = island.stream().mapToInt(a -> a.position.x).min().orElse(0);
        int miny = island.stream().mapToInt(a -> a.position.y).min().orElse(0);
        int maxx = island.stream().mapToInt(a -> a.position.x).max().orElse(size_X);
        int maxy = island.stream().mapToInt(a -> a.position.y).max().orElse(size_Y);
        int sizeY = maxy - miny + 1;

        int printSize = 2;
        String reset = "\u001B[0m";
        String[] lines = new String[sizeY * printSize]; 
        for (int f = 0; f < sizeY * printSize; f++) {
            lines[f] = "";
        }

        for(int i = minx ; i <= maxx ; i++){
            for(int j = miny ; j <= maxy ; j++){
                Tile currentTile = tiles[i][j];
                for(int k = 0; k < printSize; k++){
                    String str = "";
                    for(int l = 0; l < printSize; l++){
                        if(currentTile != null && currentTile.GetIslandID() == id){
                            str += currentTile.ToBackground() + TileSpace(currentTile, (k == printSize - 1 && l == printSize - 1), (k == 0 && l == printSize - 1)) + reset;
                        }else{
                            str += "\u001B[44m  \u001B[0m";
                        }   
                    }
                    lines[(j-miny) * printSize + k] += str;
                }
            }
        }
        System.out.println(String.join("\n", lines));

        return island;
    }


}

