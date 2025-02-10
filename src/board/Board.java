package board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import board.tile.Tile;
import board.tile.TileType;


public class Board {
    
    private Tile[][] tiles;

    private int size_X = 7;
    private int size_Y = 7;

    public int SizeX()
    {
        return  size_X;
    }

    public int SizeY()
    {
        return  size_Y;
    }

    /**
     * Constructeur de la classe Board avec taille par défaut
     */
    public Board(){

        InitTiles();
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
    public Board(int size_X, int size_Y){
        this.size_X = size_X;
        this.size_Y = size_Y;
    
        InitTiles();
    }

    /**
     * Initialise les tiles du board
     * la taille du board doit être définie au préalable
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

        int squareSize = 5;
        String[] lines = new String[size_Y * squareSize]; 
        for(int f = 0; f < size_Y * squareSize; f++){
            lines[f] = "";
        }
        String water = "🟦";
        for(int i = 0; i < size_X; i++){
            for(int j = 0; j < size_Y; j++){
                String tileType = tiles[i][j] == null ? water : tiles[i][j].ToConsoleMode();
                for(int k = 0; k < squareSize; k++){
                    String str = "";
                    for(int l = 0; l<squareSize; l++){
                        str += tileType;
                    }
                    lines[j*squareSize+k] += str;
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
