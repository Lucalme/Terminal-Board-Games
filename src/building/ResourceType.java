package building;

public enum ResourceType {
    WOOD, SHEEP, WHEAT, ORE;

    /**
     * Convertit un String en ResourceType.
     * @param resourceName Nom de la ressource (ex: "Wood").
     * @return La valeur de l'énumération correspondante.
     */
    public static ResourceType fromString(String resourceName) {
        try {
            return ResourceType.valueOf(resourceName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ressource inconnue : " + resourceName);
        }
    }
}

