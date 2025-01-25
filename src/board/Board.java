package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import board.tile.Tile;
import board.tile.TileType;


public class Board {
    
    private final Map<Position, Tile> tiles;

    private final int sizeX;
    private final int sizeY;

    public Board(int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.tiles = new HashMap<>();
        initTiles();
    }

    public int getSizeX(){
        return  sizeX;
    }

    public int getSizeY(){
        return  sizeY;
    }

    public Map<Position, Tile> getTiles() {
        return tiles;
    }

    private void initTiles() {
        int minWaterTiles = (int) (0.66 * sizeX * sizeY);
        int maxOtherTiles = (sizeX * sizeY) - minWaterTiles;

        Random rand = new Random();
        for (int i = 0; i < maxOtherTiles; i++) {
            Position pos;
            do {
                int randX = rand.nextInt(sizeX);
                int randY = rand.nextInt(sizeY);
                pos = new Position (randX, randY);
            } while (tiles.containsKey(pos));

            Tile tile = new Tile(TileType.FOREST);
            tiles.put(pos, tile);
        }

    // Vérification des tuiles isolées
        Map<Position, Tile> isolatedTiles = new HashMap<>();
        for (Map.Entry<Position, Tile> entry : tiles.entrySet()) {
            if (getTilesNeighborhood(entry.getKey()).isEmpty()) {
                isolatedTiles.put(entry.getKey(), entry.getValue());
            }
        }

        for (Position pos : isolatedTiles.keySet()) {
            tiles.remove(pos);
        }
    }
    public Map<Position, Tile> getTilesNeighborhood(Position pos) {
        Map<Position, Tile> neighborhood = new HashMap<>();
        for (Directions dir : Directions.values()) {
            int newX = pos.getX() + dir.getDx();
            int newY = pos.getY() + dir.getDy();
            Position neighborPos = new Position(newX, newY);
            if (tiles.containsKey(neighborPos)) {
                neighborhood.put(neighborPos, tiles.get(neighborPos));
            }
        }
        return neighborhood;
    }



    public HashMap<int[], Tile> getTiles(){
        HashMap<int[], Tile> res =  new HashMap<int[],Tile>();

        for (int i = 0; i < size_X; i++){
            for (int j = 0; j < size_Y; j++){
                if(tiles[i][j] != null){
                    res.put(new int[] {i,j}, tiles[i][j]);
                }
            }
        }

        return res;
    }

    /**
     * Constructeur de la classe Board avec taille personalisée
     * @param size_X
     * @param size_Y
     */
    
    private void InitTiles(){

        tiles = new Tile[size_X][size_Y];

        int minWaterTiles = (int) (.66 * size_X * size_Y); 
        int maxOtherTiles = (size_X * size_Y) - minWaterTiles; 

        //Par défaut, le tableau est vide : tous ses éléments sont nulls.
        //afin de simplifier le code, on considèrera qu'une tile vide est de type water
        //ainsi, à l'initialisation, tout le board est de type water.
        
        //Attention : On doit ici génerer un nombre inférieur ou égal à maxOtherTiles de tiles non-nulles
        
        HashMap<int[], Tile> currentTiles = new HashMap<>();

        Random rand = new Random();
        for(int i = 0; i < maxOtherTiles; i++){
            int randX;
            int randY;
            //Recherche d'une case vide
            do{
                randX = rand.nextInt(size_X);
                randY = rand.nextInt(size_Y);
            }while(tiles[randX][randY] != null );
            
            // initialisation d'une tile sur la case vide (aléatoire)
            Tile tile = new Tile();
            tiles[randX][randY] = tile;

            currentTiles.put(new int[] {randX, randY}, tile);
        }
        
        HashMap<int[], Tile> isolatedTiles = currentTiles
            .entrySet()
            .stream()
            .filter(
                a -> GetTilesNeighborhood(a.getKey()[0], a.getKey()[1]).length == 0
            )
            .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);


        for (Map.Entry<int[], Tile> kv : isolatedTiles.entrySet()){ 
            //System.out.println("Isolated tile at " + kv.getKey()[0] + " " + kv.getKey()[1]);
            tiles[kv.getKey()[0]][kv.getKey()[1]] = null;
        }
    }

    /**
     * Renvoie les tiles voisines de la tile en position x, y
     * @param x
     * @param y
     * @return
     */
    public Tile[] GetTilesNeighborhood(int x, int y){
        ArrayList<Tile> neighborhood = new ArrayList<Tile>();
        for(Directions dir : Directions.values()){
            if(x + dir.X >= 0 && x + dir.X < size_X && y + dir.Y >= 0 && y + dir.Y < size_Y){
                if(tiles[x + dir.X][y + dir.Y] != null){
                    neighborhood.add(tiles[x + dir.X][y + dir.Y]);
                }
            }
        }
        return neighborhood.toArray( new Tile[neighborhood.size()]);
    }

    public String ToString() throws Exception{

        int squareSize = 3;
        String[] lines = new String[size_Y * squareSize]; 
        for(int i = 0; i < size_Y * squareSize; i++){
            lines[i] = "";
        }

        String forest = "🟩";
        String mountain = "🔳";
        String pasture = "🟧";
        String field = "🟨";
        String water = "🟦";

        

        for(int i = 0; i < size_Y; i++){
            for(int j = 0; j < size_X; j++){
                if(tiles[j][i] == null){
                    for(int k = 0; k < squareSize; k++){
                        lines[i * squareSize + k] += water + water + water;
                    }
                }else{
                    String tileType ;
                    switch(tiles[j][i].GetTileType()){
                        case Forest:
                            tileType = forest;
                            break;
                        case Mountains:
                            tileType = mountain;
                            break;
                        case Pastures:
                            tileType = pasture;
                            break;
                        case Fields:
                            tileType = field;
                            break;
                        default:
                            throw new Exception("Unknown tile type");
                    }
                    for(int k = 0; k < squareSize; k++){
                        lines[i * squareSize + k] += tileType + tileType + tileType;
                    }
                }
            }
        }
        return String.join("\n", lines);
    }


    public void UpdateAllTiles(){
        for (int i = 0; i < size_X; i++){
            for (int j = 0; j < size_Y; j++){
                if(tiles[i][j] != null){
                    tiles[i][j].UpdateTile();
                }
            }
        }
    }

}
