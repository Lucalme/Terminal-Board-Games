package board;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;


public class BoardTests{

    @Test
    public void AtLeastOneNonWaterTileOnBoard(){
        Board board = new Board();
        assertTrue(board.getTiles().size() > 0);
    }
}