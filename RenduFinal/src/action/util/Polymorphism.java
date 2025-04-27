package action.util;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;

import GUI.GUI;
import Game.Game;
import GUI.GUIActions.PreliminaryAction;
import action.Action;
import action.actions.ActionTrade;
import board.resource.ResourceType;
import board.tile.Tile;
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

    public static PreliminaryAction getPreliminaryActionInstance(Class<? extends PreliminaryAction> clazz, Player player, GUI gui){
        try{
            return (PreliminaryAction)clazz.getDeclaredConstructor(Player.class, GUI.class).newInstance(player, gui);
        }catch(Exception e){
            throw new RuntimeException("UNKNOWN METHOD FOR ACTION TYPE :" + clazz.getTypeName());
        }
    }

    public static Action tryGetActionInstance(Class<?> clazz, GUI gui, Object... args){
        //for(Object arg : args){
        //    if(arg == null){
        //        continue;
        //    }
        //    System.out.println("Polymorphism : "+arg.getClass());
        //}
        try{
            Class<?>[] parameterTypes = getParameterTypes(clazz);
            boolean requiresInt = hasParameterOfType(clazz, int.class);
            boolean requiresTwoResourceTypes = clazz == ActionTrade.class;
            HashMap<Class<?>, Integer> objCount = new HashMap<>();
            for (Class<?> c : parameterTypes){
                objCount.put(c, 0);
            }
            Object[] params = new Object[parameterTypes.length];
            for(int i = 0; i < parameterTypes.length; i++){
                params[i] = findNthObjectOfType(parameterTypes[i], objCount.get(parameterTypes[i]), args);
                if(params[i] == null){
                    String typeName = "";
                    if(parameterTypes[i] == int.class){
                        typeName = "un entier";
                    }else if(parameterTypes[i] == ResourceType.class){
                        typeName = "un type de ressource";
                    }else if(parameterTypes[i] == Player.class){
                        typeName = "un joueur";
                    }else if(parameterTypes[i] == Tile.class){
                            typeName = "une case";
                    }else{
                        typeName = parameterTypes[i].getTypeName();
                    }
                    gui.Console.printWarning("Vous devez selectionner " + typeName + " pour effectuer cette action");
                    return null;
                }
                objCount.replace(parameterTypes[i], objCount.get(parameterTypes[i]) + 1);
            }
            return (Action)clazz.getDeclaredConstructor(parameterTypes).newInstance(params);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Object findNthObjectOfType( Class<?> type, int n, Object... args){
        int count = 0;
        System.out.println("Looking for object of type " + type.getTypeName() + " at index " + n);
        for (Object obj : args){
            if(obj == null){
                continue;
            }
            //TODO: check if obj is of primitive type, if so, convert it to its wrapper class
            if(obj.getClass().equals(type.getClass()) || obj.getClass().isAssignableFrom(type) || (obj.getClass() == Integer.class && type ==  int.class)){
                if(count == n){
                    return obj;
                }
                count++;
            }
        }
        return null;
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

    public static boolean hasParameterOfType(Class<?> clazz, Class<?> type){
        for (Class<?> c : getParameterTypes(clazz)){
            if(c.equals(type)){
                return true;
            }
        }
        return false;
    }


    public static boolean isPossible(Class<? extends Action> t , Player player, Game game){
        try{
            return (boolean)t.getDeclaredMethod("isPossible", Player.class, Game.class).invoke(null, player , game);
        }catch(Exception e){
            throw new RuntimeException("UNKNOWN METHOD FOR ACTION TYPE :" + t.getTypeName());
        }
    }
    
}