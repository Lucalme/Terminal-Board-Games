package building;

import player.Player;

public class AresHarbour extends Building {

    public AresHarbour(Player owner, int size, int islandId) {
        super(owner, size, BuildingEffectType.MultiplyResourceProduction, islandId);
    }
}
