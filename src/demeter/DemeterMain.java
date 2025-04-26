package demeter;

public class DemeterMain {
    public static void main(String[] args){
        int nbOfPlayers = args.length > 0 ? Integer.parseInt(args[0]) : -1;
        int sizeX = args.length > 1 ? Integer.parseInt(args[1]) : -1;
        int sizeY = args.length > 2 ? Integer.parseInt(args[2]) : -1;
        if(nbOfPlayers == -1 || sizeX == -1 || sizeY == -1){
            System.out.println("Usage : java DemeterMain <NombreDeJoueurs> <TailleXDuPlateau> <TailleYDuPlateau>");
            return;
        }
        Demeter game = new Demeter(nbOfPlayers, sizeX, sizeY);
        game.StartGame();
    }
}
