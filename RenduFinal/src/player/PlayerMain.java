package player;

import java.util.Scanner;

import ares.Ares;
import board.Board;
import board.Position;
import board.resource.ResourceType;
import board.tile.Tile;



public class PlayerMain {
 

public static void main(String[] args) 
{
    Board board = new Board();
    Tile tile = board.GetTileAtPosition(PromptPosition());
    Player player = new Player(1, new Ares(1));  // Make sure you create the player once
    Scanner scanner = new Scanner(System.in);

    boolean running = true;
    while (running) {
        System.out.println("\nChoose an action:");
        System.out.println("1. Show Resources");
        System.out.println("2. Add Resource");
        System.out.println("3. Remove Resource");
        System.out.println("4. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                player.showResources();
                break;
            case 2:
                System.out.println("Enter resource type (e.g., Ore, Wood):");
                String resourceType = scanner.nextLine();
                String formattedResourceType = capitalizeFirstLetter(resourceType); 
                System.out.println(formattedResourceType);

                System.out.println("Enter the amount to add:");
                int amountToAdd = scanner.nextInt();
                scanner.nextLine();
                try {
                    player.addResource(ResourceType.valueOf(formattedResourceType), amountToAdd);
                    System.out.println(amountToAdd + " " + formattedResourceType + " added!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid resource type entered.");
                }
                break;
            case 3:
                System.out.println("Enter resource type (e.g., ORE, WOOD):");
                resourceType = scanner.nextLine();
                String formattedResourceType1 = capitalizeFirstLetter(resourceType); 
                System.out.println(formattedResourceType1);
                System.out.println("Enter the amount to remove:");
                int amountToRemove = scanner.nextInt();
                scanner.nextLine();
                try {
                    player.removeResource(ResourceType.valueOf(resourceType), amountToRemove);
                    System.out.println(amountToRemove + " " + resourceType + " removed!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid resource type entered.");
                }
                break;
            case 4:
                running = false;
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    scanner.close();
    }
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    private static Position PromptPosition(){
        Scanner sc = new Scanner(System.in);
        boolean xdone = false;
        boolean ydone = false;
        int x;
        while(!xdone){
            if (sc.hasNextInt()){
                x = sc.nextInt();
            }else{
                sc.nextInt();
            }
        }
        while(!ydone){

        }
        return new Position(-1, -1);
    }
}
