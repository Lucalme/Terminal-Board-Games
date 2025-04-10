package demeter;

public class DemeterBotGame extends Demeter
{
    public DemeterBotGame(int nbOfPlayer) {
        super(true, 2);
    }

    public static void main(String[] args){
        DemeterBotGame game = new DemeterBotGame(2);
        game.StartGame();
    }    
}
