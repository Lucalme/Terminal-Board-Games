package board;
import board.tile.Tile;
import board.tile.TileType;


public class Board {
    
    private Tile[][] tiles;

    private int[] defaultDimensions = new int[] {7,7};

    private int size_X;
    private int size_Y;

    public Board(){
        //66% de water 

        size_X = defaultDimensions[0];
        size_Y = defaultDimensions[1];

        InitTiles();

        //for(int k = 0; k <= minWaterTiles; k++ ){
        //    int x = new Random().nextInt(defaultDimensions[0]);
        //    int y = new Random().nextInt(defaultDimensions[1]);
        //    
        //}                                                                   

        //for(int i = 0; i<defaultDimensions[0]; i++){
        //    Tile[] line = new Tile[defaultDimensions[0]];
        //    for(int j = 0; j<defaultDimensions[1]; j++){
        //        line[j] = new Tile();
        //    }
        //    tiles[i] = line;
        //}
    }

    public Board(int[] size) throws Exception{
        if(size.length != 2){
            throw new Exception("La taille de tableau est incorrecte");
        }
    }

    private void InitTiles(){
        int minWaterTiles = (int) .66 * size_X * size_Y; 

        for(int i = 0; i<size_X; i++){
            Tile[] line = new Tile[size_Y];
            for(int j = 0; j<size_Y; j++){
                line[j] = new Tile(TileType.Water);
            }
            tiles[i] = line;
        }

        //TODO: à continuer...
    }

    private void ConsoleDraw(){
        for(int linelen = 0; linelen < tiles[0].length; linelen++  ){
            //for(int l = 0; l< )
        }
    }

}
