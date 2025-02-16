package board.tile;
import board.Position;
import board.resource.*;

public class TileMain {

    public static void main(String[] args){
        for(int i = 0 ; i<6 ; i++){
            Tile tile = new Tile(new Position(i, i));
            System.out.println("Le type de Tuile est : "+tile.GetTileType().toString());
            ResourceType resourceType = tile.GetResourceType();
            if(resourceType != null){
                System.out.println("Le type de resource est :" + resourceType.toString());
            }else{
                System.out.println("Cette tuile n'a pas de resources ! ");
            }
        }
    }

}
