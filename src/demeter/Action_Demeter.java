import java.util.*;

class ActionDemeter {
    public static void buildFarm(Player player, Tile tile) {
        Map<String, Integer> cost = Map.of("Wood", 1, "Ore", 1);
        if (player.hasResources(cost)) {
            player.deductResources(cost);
            player.addBuilding(new Farm(), tile);
            player.displayResources();
            System.out.println(player.name + " déploie une ferme sur " + tile);
        }
    }

    public static void upgradeFarm(Player player) {
        Map<String, Integer> cost = Map.of("Wood", 2, "Wheat", 1, "Sheep", 1);
        if (player.hasResources(cost)) {
            player.deductResources(cost);
            player.buildings.replaceAll(b -> b instanceof Farm ? new BigFarm() : b);
            player.displayResources();
            System.out.println("Une ferme a été transformée en exploitation.");
        }
    }

    public static void buildPort(Player player, Tile tile) {
        Map<String, Integer> cost = Map.of("Wood", 1, "Sheep", 2);
        if (player.hasResources(cost)) {
            player.deductResources(cost);
            player.addBuilding(new Port(), tile);
            player.displayResources();
            System.out.println(player.name + " construit un port sur " + tile);
        }
    }

    public static void exchangeResources(Player player, int amount, String from, String to) {
        if (player.resources.getOrDefault(from, 0) >= amount) {
            player.resources.put(from, player.resources.get(from) - amount);
            player.resources.put(to, player.resources.getOrDefault(to, 0) + 1);
            player.displayResources();
            System.out.println(amount + " " + from + " ont été échangés contre 1 " + to);
        }
    }

    public static void exchangeResourcesWithPort(Player player, int amount, String from, String to) {
        if (player.resources.getOrDefault(from, 0) >= amount && player.buildings.stream().anyMatch(b -> b instanceof Port)) {
            player.resources.put(from, player.resources.get(from) - amount);
            player.resources.put(to, player.resources.getOrDefault(to, 0) + 1);
            player.displayResources();
            System.out.println(amount + " " + from + " ont été échangés contre 1 " + to + " grâce au port");
        }
    }

    public static void buyThief(Player player) {
        Map<String, Integer> cost = Map.of("Ore", 1, "Wood", 1, "Wheat", 1);
        if (player.hasResources(cost)) {
            player.deductResources(cost);
            player.addBuilding(new Thief(), null);
            player.displayResources();
            System.out.println(player.name + " dispose maintenant d'un voleur");
        }
    }
}
