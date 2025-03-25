import java.util.*;

class Player {
    String name;
    Map<String, Integer> resources;
    List<Building> buildings;
    List<Tile> occupiedTiles;

    public Player(String name) {
        this.name = name;
        this.resources = new HashMap<>(Map.of("Ore", 10, "Sheep", 10, "Wheat", 10, "Wood", 10));
        this.buildings = new ArrayList<>();
        this.occupiedTiles = new ArrayList<>();
    }

    public void displayResources() {
        System.out.println("--> " + name + ": " + resources);
    }

    public boolean hasResources(Map<String, Integer> cost) {
        return cost.entrySet().stream().allMatch(e -> resources.getOrDefault(e.getKey(), 0) >= e.getValue());
    }

    public void deductResources(Map<String, Integer> cost) {
        cost.forEach((key, value) -> resources.put(key, resources.get(key) - value));
    }

    public void addBuilding(Building building, Tile tile) {
        buildings.add(building);
        occupiedTiles.add(tile);
    }

    public void displayBuildings() {
        System.out.println("---> Liste des bâtiments en sa possession: " + buildings);
    }

    public void displayTiles() {
        System.out.println("---> Liste des tuiles occupées: " + occupiedTiles);
    }
}

abstract class Building {
    String name;
    public Building(String name) { this.name = name; }
    @Override public String toString() { return name; }
}

class Farm extends Building {
    public Farm() { super("Farm"); }
}

class BigFarm extends Building {
    public BigFarm() { super("Big Farm"); }
}

class Port extends Building {
    public Port() { super("Harbor"); }
}

class Thief extends Building {
    public Thief() { super("Thief"); }
}

class Tile {
    int x, y;
    public Tile(int x, int y) { this.x = x; this.y = y; }
    @Override public String toString() { return "P(" + x + "," + y + ")"; }
}

class DemeterGame {
    public static void main(String[] args) {
        Player leon = new Player("Leon");
        executeLivrable3Demeter(leon);
    }

    public static void executeLivrable3Demeter(Player player) {
        System.out.println("----- DEMETER -----");

        // 1. Construire une ferme
        Tile farmTile = new Tile(8, 0);
        buildFarm(player, farmTile);

        // 2. Transformer une ferme en exploitation
        upgradeFarm(player);

        // 3. Construire un port
        Tile portTile = new Tile(3, 1);
        buildPort(player, portTile);

        // 4. Échange 3 ressources contre une
        exchangeResources(player, 3, "Wheat", "Ore");

        // 5. Échange 2 ressources grâce au port
        exchangeResourcesWithPort(player, 2, "Wood", "Sheep");

        // 6. Acheter un voleur
        buyThief(player);

        // Affichage final des ressources, bâtiments et tuiles
        player.displayResources();
        player.displayBuildings();
        player.displayTiles();
    }

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
