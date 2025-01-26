package board;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import board.tile.Tile;
import board.tile.TileType;

public class Board {
    private final Map<Position, Tile> tiles;
    private final int sizeX;
    private final int sizeY;

    private static final int[] DEFAULT_DIMENSIONS = {7, 7};

    public Board() {
        this(DEFAULT_DIMENSIONS[0], DEFAULT_DIMENSIONS[1]);
    }

    public Board(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.tiles = new HashMap<>();
        initTiles();
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
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
                pos = new Position(randX, randY);
            } while (tiles.containsKey(pos));

            Tile tile = new Tile(TileType.FOREST, pos.getX(), pos.getY()); // Exemple de type de tuile
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
            System.out.println("Isolated tile at " + pos);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Position pos = new Position(x, y);
                if (tiles.containsKey(pos)) {
                    sb.append(tiles.get(pos).getType().toString().charAt(0));
                } else {
                    sb.append("W"); // W pour l'eau
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}