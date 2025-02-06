package action.util;

import java.util.Scanner;

public class Input {
    
    public static void DeleteLines(int lines){
        for(int i = 0; i < lines; i++){
            System.out.print(String.format("\033[%dA", 1)); 
            System.out.print("\033[2K");   
        }
    }

    public static int getInt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer un nombre:");
        while(true){
            try {
                int input = scanner.nextInt();
                scanner.close();
                DeleteLines(1);
                return input;
            }catch(Exception e){
                DeleteLines(2);
                System.out.println("Veuillez entrer un nombre valide.");
                scanner.next();
            }
        }
    }
}
