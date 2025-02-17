package action;
import java.lang.reflect.Type;
import java.util.HashMap;

import action.actions.ActionAttack;
import action.actions.ActionCollect;
import action.actions.ActionTrade;
import action.actions.AresBuildArmy;
import action.actions.AresBuildHarbour;
import action.actions.ShowInventory;

public enum ActionMap {
    Ares(new HashMap<String, Class<? extends Action>>() {{
            put("Voir l'inventaire", ShowInventory.class);
            put("Echanger des resources", ActionTrade.class);
            put("Construire une armée ou un camp", AresBuildArmy.class);
            put("Collecter des ressources", ActionCollect.class);
            put("Construire un Port", AresBuildHarbour.class);
            put("Attaquer", ActionAttack.class);
        }}),
    Demeter(
        new HashMap<String, Class<? extends Action>>() {{
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
