package board;
import java.util.HashMap;
import java.util.Random;

import board.tile.Tile;
import board.tile.TileType;


public class Board {
    
    private Tile[][] tiles;

    private int[] defaultDimensions = new int[] {7,7};

    private int size_X;
    private int size_Y;

    public Board(){
        size_X = defaultDimensions[0];
        size_Y = defaultDimensions[1];

        InitTiles();
    }

    public Board(int size_X, int size_Y) throws Exception{
        this.size_X = size_X;
        this.size_Y = size_Y;
    
        InitTiles();
    }

    private void InitTiles(){


        int minWaterTiles = (int) .66 * size_X * size_Y; 
        int maxOtherTiles = (size_X * size_Y) - minWaterTiles; 

        //Par défaut, le tableau est vide : tous ses éléments sont nulls.
        //afin de simplifier le code, on considèrera qu'une tile vide est de type water
        //ainsi, à l'initialisation, tout le board est de type water.
        
        //Attention : On doit ici génerer un nombre inférieur ou égal à maxOtherTiles de tiles non-nulles
        
        HashMap<int[], Tile> currentTiles = new HashMap<>();

        for(int i = 0; i < (int)(maxOtherTiles * 0.5); i++){
            int randX;
            int randY;
            //Recherche d'une case vide
            do{
                Random rand = new Random();
                randX = rand.nextInt(size_X);
                randY = rand.nextInt(size_Y);
            }while(tiles[randX][randY] != null );
            
            // initialisation d'une tile sur la case vide (aléatoire)
            Tile tile = new Tile();
            tiles[randX][randY] = tile;

            currentTiles.put(new int[] {randX, randY}, tile);
        }
        
        HashMap<int[], Tile> isolatedTiles ; //filtrer ici les Tiles isolées.
        isolatedTiles = currentTiles;
    }

    public String ToString(){
        return "";
    }

}
