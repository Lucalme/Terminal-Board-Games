package ares;


public class Livrable3 extends Ares{

    public Livrable3(){
        super(true, 2);
    }

    public static void main(String[] args){
        Livrable3 game = new Livrable3();
        game.StartGame();
    }

    @Override
    public boolean CheckWinCondition(){
        return false;
    }
}
