package Objectives;

public enum ObjectiveType {
    CONQUER_TILES("Conquérir un nombre de tuiles"),
    INVADE_ISLAND("Envahir une île"),
    REACH_WARRIORS("Atteindre un certain nombre de guerriers");

    public final String description;

    ObjectiveType(String description) {
        this.description = description;
    }

    public static ObjectiveType getRandomObjective() {
        return ObjectiveType.values()[(int) (Math.random() * ObjectiveType.values().length)];
    }
}