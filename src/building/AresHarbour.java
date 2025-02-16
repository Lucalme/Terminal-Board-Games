package building;

import player.Player;

public class AresHarbour extends Building {

    public AresHarbour(Player owner, int size) {
        super(owner, size, BuildingEffectType.MultiplyResourceProduction);
    }
}
