package action;
import java.util.HashMap;

import Game.GUIActions.PrepareBuildArmy;
import Game.GUIActions.PrepareTrade;
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
            put("Construire une Armée ou un Camp", AresBuildArmy.class);
            put("Collecter des ressources", ActionCollect.class);
            put("Construire un Port", AresBuildHarbour.class);
            put("Attaquer", ActionAttack.class);
        }}),
    Demeter(
        new HashMap<String, Class<? extends Action>>() {{
            put("Voir l'inventaire", ShowInventory.class);
            put("Collecter des ressources", ActionCollect.class);
            put("Attaquer", ActionAttack.class);
        }}),
    GUIAres(new HashMap<String, Class<? extends Action>>() {{
            put("Echanger des resources", PrepareTrade.class);
            put("Construire une Armée ou un Camp", PrepareBuildArmy.class);
            put("Collecter des ressources", ActionCollect.class);
            put("Construire un Port", AresBuildHarbour.class);
            put("Attaquer", ActionAttack.class);
        }});
    //Ajouter d'autres types de jeu? 

    public final HashMap<String, Class<? extends Action>> actionMap;

    private ActionMap(HashMap<String, Class<? extends Action>> actionsMap) {
        this.actionMap = actionsMap;
    }
}
