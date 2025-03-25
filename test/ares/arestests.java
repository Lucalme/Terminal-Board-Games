package ares;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ares.Ares;


public class arestests {
    
    @Test
    public void VarInitialisation(){
        Ares ares = new Ares(2);
        assertEquals(2, ares.players.size(), "player list initialisation failed");
        assertNotEquals(ares.board, null, "board initialisation failed");
    }

    @Test
    public void StartGame(){
    
    }
}
