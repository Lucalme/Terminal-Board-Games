package action;

import player.Player;


/**
 * Une classe qui permet de faire une demande d'action à un joueur.
 */
public class ActionRequest {

    public final Action action;
    public boolean ready;

    public ActionRequest(Player player, Action action) {
        this.action = action;
        ready = CheckActionPossible();
    }
    
    private Boolean CheckActionPossible(){
        Boolean res = true;
        if (action instanceof ActionBuild){
            //if (!((ActionBuild)action).CheckBuildPossible()){
            //    res = false;
            //}
        }
        //TODO: Ajouter la verification des ressources, etc...

        return res;
    }


}
