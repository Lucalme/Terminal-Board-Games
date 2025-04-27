package Objectives;

import Game.GameType;

public enum ObjectiveType {
    CONQUER_TILES("Conquérir un nombre de tuiles", GameType.ARES),
    INVADE_ISLAND("Envahir une île", GameType.ARES),
    REACH_WARRIORS("Atteindre un certain nombre de guerriers", GameType.ARES);

    public final String description;
    public final GameType gameType;

    ObjectiveType(String description, GameType gameType) {
        this.description = description;
        this.gameType = gameType;
    }

    public static ObjectiveType getRandomObjective(GameType gameType) {
        ObjectiveType obj;
        do {
            obj = ObjectiveType.values()[(int) (Math.random() * ObjectiveType.values().length)];
        } while (obj.gameType != gameType);
        return obj;
    }
}