package Objectives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import player.Player;

public class Objectives {
    private Map<Player, String> playerObjectives;
    private Map<Player, Boolean> objectivesAchieved;

    public Objectives(){
        playerObjectives = new HashMap<>();
        objectivesAchieved = new HashMap<>();
    }

    /** 
     * Définit l'objectif pour un joueur.
     * @param player le joueur
     * @param objectif a atteindre
     */
    public void setObjective(Player player, String objective) {
        playerObjectives.put(player, objective);
        objectivesAchieved.put(player, false);
    }

    /**
     * Marque l'objectif d'un joueur comme atteint.
     * @param player le joueur
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
        return null; 
    }
    }
}