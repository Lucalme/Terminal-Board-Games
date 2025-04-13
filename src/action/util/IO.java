package action.util;
import java.util.Scanner;

public class IO {
    
    public static int writeDelay = 10;

    public static void DeleteLines(int lines){
        for(int i = 0; i < lines; i++){
            System.out.print(String.format("\033[%dA", 1)); 
            System.out.print("\033[2K");   
        }
    }
    
    public static int getInt() {
        Scanner scanner = new Scanner(System.in);
        while (true) {  // Keep prompting until valid input is received
            System.out.print("Votre choix : ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                DeleteLines(1);
                return input;
            } else {
                scanner.next();
                SlowType("Veuillez entrer un nombre valide...");
                DeleteLines(2);
            }
        }
    }

    public static boolean getBool(){
        Scanner scanner = new Scanner(System.in);
        while (true) {  // Keep prompting until valid input is received
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("O")) {
                DeleteLines(1);
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                DeleteLines(1);
                return false;
            } else {
                SlowType("Veuillez entrer O ou N...");
                DeleteLines(2);
            }
        }
    }

    public static void Next(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Appuyez sur Entrée pour continuer");
        scanner.nextLine();
        DeleteLines(2);
    }

    public static void SlowType(String s){
        SlowType(s, writeDelay);
    }

    public static void SlowType(String s, int delay){
        for (int i = 0; i < s.length(); i++){
            System.out.print(s.charAt(i));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("\n");
    }
    
    public static void PrintReset(){
        System.out.print("\033\143");
        System.out.println("\u001B[0m");
    }

    
}
