package action;
import java.util.HashMap;

public enum ActionMap {
    Ares(new HashMap<>() {{
            put("Voir l'inventaire", ShowInventory.class);
            put("Collecter des ressources", ActionCollect.class);
            put("Construire un Port", AresBuildHarbour.class);
            put("Attaquer", ActionAttack.class);
        }}),
    Demeter(
        new HashMap<>() {{
            put("Voir l'inventaire", ShowInventory.class);
            put("Collecter des ressources", ActionCollect.class);
            put("Attaquer", ActionAttack.class);
        }});
    //Ajouter d'autres types de jeu? 

    public final HashMap<String, Class<? extends Action>> actionMap;

    private ActionMap(HashMap<String, Class<? extends Action>> actionsMap) {
        this.actionMap = actionsMap;
    }
}
