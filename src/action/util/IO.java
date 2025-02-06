package action.util;

import java.util.Scanner;

public class IO {
    
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
        for (int i = 0; i < s.length(); i++){
            System.out.print(s.charAt(i));
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("\n");
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
