package action;

import Game.Game;
import action.util.IO;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Building;
import building.BuildingEffectType;
import building.Exploitation;
import building.Farm;
import player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionRemplacer extends Action {

    public ActionRemplacer(Player player) {
        super(player, true); // L'action remplace un bâtiment et termine le tour
    }

    @Override
    public boolean CheckInstancePossible(Player player, Game game) {
        // Vérifie si le joueur possède au moins une ferme
        for (Farm farm : getPlayerFarms(player)) {
            return true; // Il y a au moins une ferme disponible pour remplacement
        }
        return false;
    }
    public Building PromptFarm(Player player){
        ArrayList <Building> playerBuilding= player.GetOwnedBuildings();
        ArrayList <Farm> playerFarm=new ArrayList<>();
        String prompt = "Choisissez une ferme à remplacer : \n";
        int count=0;
        for(Building b : playerBuilding){
            if(b instanceof Farm){
                playerFarm.add((Farm)b);
                prompt += count + " ->  numero ile: "+ b.islandId +" position: ("+b.tile.position.x+","+b.tile.position.y+"):" + "\n";
                count++;
            }
        }
        IO.SlowType(prompt);
        boolean done= false;
        int answer=-1;
        while(!done)
        {
            answer =IO.getInt();
           if(answer<playerFarm.size() && answer>=0){
                done=true;
               
           }
        }
        IO.DeleteLines(count+2);
        return playerFarm.get(answer);

    }

    @Override
    public void Effect() {
        // Sélectionner la ferme à remplacer
        Farm selectedFarm = (Farm) PromptFarm(source);

        if (selectedFarm == null) {
            System.out.println("Aucune ferme sélectionnée. Action annulée.");
            return;
        }

        // Récupérer la position de la ferme
        Tile farmTile = selectedFarm.tile;

        // Retirer l'ancienne ferme
        source.RemoveBuilding(selectedFarm);
        farmTile.SetBuilding(null); // Supprime la référence sur la tuile

        // Créer et ajouter la nouvelle exploitation
        Exploitation newExploitation = new Exploitation(source, BuildingEffectType.MultiplyResourceProduction, farmTile);
        source.AddBuilding(newExploitation);
        farmTile.SetBuilding(newExploitation); // Met à jour la tuile avec la nouvelle construction

        System.out.println("Ferme remplacée par une Exploitation à (" + farmTile.position.x + ", " + farmTile.position.y + ").");
    }

    @Override
    public String Description() {
        return "Remplace une ferme par une exploitation, augmentant la production de ressources.";
    }

    

    private ArrayList<Farm> getPlayerFarms(Player player) {
        ArrayList<Farm> farms = new ArrayList<>();
        for (building.Building b : player.GetOwnedBuildings()) {
            if (b instanceof Farm) {
                farms.add((Farm) b);
            }
        }
        return farms;
    }
}
