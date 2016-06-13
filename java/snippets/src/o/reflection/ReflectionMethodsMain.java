package o.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import o.beans.Man;

public class ReflectionMethodsMain {

    /**
     * @param args
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Man man = new Man("Anon Imo");
        Method[] allMethods = man.getClass().getDeclaredMethods();
        for (Method m : allMethods) {
            if ( ! m.getName().startsWith("is")) {
                continue;
            }

            System.out.format("%s: %s", m.getName().substring(2), ((Boolean) m.invoke(man, null)).booleanValue());
        }
    }

}
