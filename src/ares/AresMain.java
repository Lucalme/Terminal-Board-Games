package ares;

public class AresMain {
    public static void main(String[] args){
        int nbOfPlayers = args.length > 0 ? Integer.parseInt(args[0]) : -1;
        int sizeX = args.length > 2 ? Integer.parseInt(args[1]) : -1;
        int sizeY = args.length > 2 ? Integer.parseInt(args[2]) : -1;
        if(nbOfPlayers == -1 || sizeX == -1 || sizeY == -1){
            System.out.println("Usage : java AresMain <NombreDeJoueurs> <TailleXDuPlateau> <TailleYDuPlateau>");
            return;
        }
        Ares game = new Ares(nbOfPlayers,  sizeX, sizeY);
        game.StartGame();
    }
}
