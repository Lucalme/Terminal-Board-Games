package Game.GUIUtils;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.Random;



public class TileAdapter extends MouseAdapter{
    private final Component tile;

    public TileAdapter(Component tile){
        this.tile = tile;
    }

    @Override
    public void mouseEntered(MouseEvent e){
        Random r = new Random();
        tile.setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
    }

    public void mouseExited(MouseEvent e){
        Random r = new Random();
        tile.setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
    }
}
