package action;
import java.util.HashMap;
import GUI.GUIActions.PrepareBuildArmy;
import GUI.GUIActions.PrepareTrade;
import action.actions.ActionAttack;
import action.actions.ActionSkip;
import action.actions.ActionTrade;
import action.actions.AresAddWarriorToBuilding;
import action.actions.AresBuildArmy;
import action.actions.AresBuildHarbour;
import action.actions.AresBuyWarriors;
import action.actions.AresReplaceArmyWithCamp;
import action.actions.ShowInventory;


public enum ActionMap {
    Ares(new HashMap<String, Class<? extends Action>>() {{
            put("Voir l'inventaire", ShowInventory.class);
            put("Passer votre tour", ActionSkip.class);
            put("Echanger des resources", ActionTrade.class);
            put("Construire une Armée ou un Camp", AresBuildArmy.class);
            put("Remplacer une Armée par un Camp", AresReplaceArmyWithCamp.class);
            put("Construire un Port", AresBuildHarbour.class);
            put("Acheter 5 guerriers", AresBuyWarriors.class);
            put("Attaquer un voisin", ActionAttack.class);
            put("Ajouter des guerriers à un bâtiment", AresAddWarriorToBuilding.class);
        }}),
    Demeter(
        new HashMap<String, Class<? extends Action>>() {{
            put("Voir l'inventaire", ShowInventory.class);
            put("Construire une Farm", action.actions.DemeterBuildFarm.class);
            put("Passer votre tour", ActionSkip.class);
            put("Construire un Port", action.actions.DemeterBuildPort.class);
            put("Remplacer une Farm par une Exploitation", action.actions.DemeterReplaceFarmWithExploitation.class);
            put("Echanger des resources", ActionTrade.class);
            put("Acheter un voleur", action.actions.DemeterBuyThief.class);
            put("Utiliser un voleur", action.actions.DemeterUseThief.class);
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
