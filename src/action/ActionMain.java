package action;

import player.Player;

public class ActionMain{
    public static void main(String[] args) {
        Player player = new Player(-1);
        Game game = new Game();
        ActionRequest request = ActionRequest.Prompt(player);
    }
}