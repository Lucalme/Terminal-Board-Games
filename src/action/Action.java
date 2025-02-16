package action;

import java.util.HashMap;
import java.util.Map;

import Game.Game;
import board.resource.ResourceType;
import player.Player;

public abstract class Action {

    /** Le joueur qui a émit l'action */
    public final Player source;
    /** La plupart des action terminent le tour, mais on peut aussi avoir des actions préliminaires comme montrer l'inventaire.. */
    public final boolean finishesTurn;

    public Action(Player player, boolean finishesTurn) {
        source = player;
        this.finishesTurn = finishesTurn;
    }

    public abstract boolean CheckInstancePossible();

    /** L'effet de l'action  */
    public abstract void Effect();
    /** La description de l'action telle qu'elle sera affichée dans la console au Runtime */
    public abstract String Description();

    /** Renvoie le cout d'une action de manière statique, cette méthode doit être Override pour toutes les actions conditionnée par les resources.*/
    public static HashMap<ResourceType, Integer> Cost(){
        return new HashMap<>();
    }


    /**Certaines actions sont conditionnées par des resources mais ne sont pas forcément payantes. Utiliser cette méthode génerique pour les actions payantes. */
    protected void PayCost(){
        for(Map.Entry<ResourceType, Integer> entry : Cost().entrySet()){
            source.removeResource(entry.getKey(), entry.getValue());
        }
    }
}
 
