package action.util;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

import Game.Game;
import action.Action;
import player.Player;

public class Polymorphism{
    
    public static boolean hasConstructor(Class<?> clazz , Class<?>... parameterTypes) {
        try {
            Constructor<?> constructor = clazz.getConstructor(parameterTypes);
            return constructor != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static Class<?>[] getParameterTypes(Class<?> clazz){
        return clazz.getConstructors()[0].getParameterTypes();
    }

    public static boolean hasMultipleParamsOfType(Class<?> clazz, Class<?> type){
        int count = 0;
        for (Class<?> c : getParameterTypes(clazz)){
            if(c.equals(type)){
                count++;
                if(count > 1) {return true;}
            }
        }
        return count > 1;
    }

    public static boolean hasMultipleParamsOfType(Class<?> clazz, Class<?> type, int multiple){
        int count = 0;
        for (Class<?> c : getParameterTypes(clazz)){
            if(c.equals(type)){
                count++;
            }
        }
        return count == multiple;
    }

    public static String[] getParamsNames(Class<?> c){
        String[] res = new String[c.getDeclaredConstructors()[0].getParameterCount()];
        int i = 0;
        for (Parameter param : c.getDeclaredConstructors()[0].getParameters()) {
            res[i] = param.getName();
            i++;
        }
        return res;
    }


    public static boolean isPossible(Class<? extends Action> t , Player player, Game game){
        try{
            return (boolean)t.getDeclaredMethod("isPossible", Player.class, Game.class).invoke(null, player , game);
        }catch(Exception e){
            System.out.println("UNKNOWN METHOD FOR ACTION TYPE :" + t.getTypeName());
            //e.printStackTrace();
            //for(Method method : t.getClass().getDeclaredMethods()){
            //    System.out.println("Name : "+(method.getName()));
            //    for(Class<?> c : method.getParameterTypes()){
            //        System.out.println("Param : "+ c.getName());
            //    }
            //    System.out.println("\n");
            //}
            return false;
        }
    }
    
}