package action.util;
import java.util.Scanner;

public class IO {
    
    public static int writeDelay = 40;

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

    public static void Next(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Appuyez sur Entrée pour continuer");
        scanner.nextLine();
        DeleteLines(2);
    }


    public static void FunType(){
        String s = "😅🥳😍😂😁😘😍😚🙂🤗🤩😎😶🤨🫡😲🤑😟😢😬🤯🥵😠😠🥴🤮🥺🥳🤮🤠🥹😇🙂‍↕️🫨🧐😈😈🤖😽";
        int index = 0;
        int max =  s.length();
        while(true){
            System.out.print(s.charAt(index));
            index = (index + 1) % max;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
}
