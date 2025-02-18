package Game;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.*;

public class GUI extends JFrame{

    public final JPanel Body;
    public final JPanel GameView;
    public final int[] WindowSize;

    public GUI(String name, int[] WindowSize, int boardSizeX, int boardSizeY){
        super(name);
        setLayout(null);
        WindowListener WLS = new WindowAdapter() {
            //@Override
            //public void windowClosing(WindowEvent e ){
            //    System.exit(0);
            //}
        };
        //addWindowListener(WLS);
        setSize(WindowSize[0], WindowSize[1]);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Body = new JPanel();
        Body.setLayout(null);
        setContentPane(Body);
        Body.setBounds(getBounds());
        Body.setPreferredSize(getSize());
        Body.setSize(getSize());
        //System.out.println("Body Size :" + Body.getSize());
        //System.out.println("Body Preferred Size :" + Body.getPreferredSize());
        //Body.setBounds(getBounds());
        Body.setBackground(new Color(0,0,0));
        GameView = new JPanel();
        Body.add(GameView);
        GameView.setLayout(new GridLayout(boardSizeX, boardSizeY, 0, 0));
        GameView.setPreferredSize(new Dimension((int) (Body.getWidth() * .8), (int) (Body.getHeight() * 0.8)));
        GameView.setSize(new Dimension((int) (Body.getWidth() * .8), (int) (Body.getHeight() * 0.8)));
        GameView.setLocation(new Point(0,0));
        this.WindowSize = WindowSize; 
        validate();
    }
    
}
