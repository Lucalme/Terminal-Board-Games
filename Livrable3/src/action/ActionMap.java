package action;
import java.util.HashMap;
import GUI.GUIActions.PrepareBuildArmy;
import GUI.GUIActions.PrepareTrade;
import action.actions.ActionAttack;
import action.actions.ActionSkip;
import action.actions.ActionTrade;
import action.actions.AresBuildArmy;
import action.actions.AresBuildHarbour;
import action.actions.AresBuyWarriors;
import action.actions.ShowInventory;

public enum ActionMap {
    Ares(new HashMap<String, Class<? extends Action>>() {{
            put("Voir l'inventaire", ShowInventory.class);
            put("Passer votre tour", ActionSkip.class);
            put("Echanger des resources", ActionTrade.class);
            put("Construire une Armée ou un Camp", AresBuildArmy.class);
            put("Construire un Port", AresBuildHarbour.class);
            put("Acheter 5 guerriers", AresBuyWarriors.class);
            put("Attaquer un voisin", ActionAttack.class);
        }}),
    Demeter(
        new HashMap<String, Class<? extends Action>>() {{
            put("Voir l'inventaire", ShowInventory.class);
            put("Construire une Farm", action.actions.DemeterBuildFarm.class);
            put("Passer votre tour", ActionSkip.class);
            put("Construire un Port", action.actions.DemeterBuildPort.class);
            put("Remplacer une Farm par une Exploitation", action.actions.DemeterBuildExploitation.class);
        }}),
    GUIAres(new HashMap<String, Class<? extends Action>>() {{
            put("Echanger des resources", PrepareTrade.class);
            put("Construire une Armée ou un Camp", PrepareBuildArmy.class);
            put("Construire un Port", AresBuildHarbour.class);
            put("Attaquer", ActionAttack.class);
        }});
    //Ajouter d'autres types de jeu? 

    public final HashMap<String, Class<? extends Action>> actionMap;

    private ActionMap(HashMap<String, Class<? extends Action>> actionsMap) {
        this.actionMap = actionsMap;
    }
}
