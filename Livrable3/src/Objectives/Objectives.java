package Objectives;

import java.util.HashMap;
import java.util.Map;
import player.Player;

public class Objectives {
    private Map<Player, ObjectiveType> playerObjectives;
    private Map<Player, Boolean> objectivesAchieved;

    public Objectives(){
        playerObjectives = new HashMap<>();
        objectivesAchieved = new HashMap<>();
    }

    /** 
     * Définit l'objectif pour un joueur.
     * @param player le joueur
     * @param objective a atteindre
     */
    public void setObjective(Player player, ObjectiveType objective) {
        playerObjectives.put(player, objective);
        objectivesAchieved.put(player, false);
    }

    /**
     * Marque l'objectif d'un joueur comme atteint.
     * @param player le joueur
     * @return 
     */
    public void achieveObjective(Player player){
        if (playerObjectives.containsKey(player)){
            objectivesAchieved.put(player, true);
        }
    }

    /**
     * verifie si le joueur a atteint son objectif
     * @param player le joueur
     * @return true si l'objectif est atteint, false sinon
     */
    public boolean isObjectiveAchieved(Player player){
        return objectivesAchieved.getOrDefault(player, false);
    }

    /**
     * Détermine les gagnants en fonction des objectifs atteints.
     * @return Une liste des joueurs ayant atteint leurs objectifs
     */
    public Player determineWinner() {
        for (Map.Entry<Player, Boolean> entry : objectivesAchieved.entrySet()) {
            if (entry.getValue()) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("no winner found");
    }

    /**
     * Récupère l'objectif d'un joueur.
     * @param player le joueur
     * @return L'objectif du joueur
     */
    public ObjectiveType getObjective(Player player) {
        return playerObjectives.get(player);
    }
}