package action;

import player.Player;

public class ActionMain{
    public static void main(String[] args) {
        Player player = new Player();
        ActionRequest request = ActionRequest.Prompt(player);
    }
}