package dal;

import model.modelIF;

import java.util.Map;

/*
 * This is a container for all the model objects.
 * It is needed to build objects that reference each other.
 */
public class Cache {

    private static Map<Class<? extends modelIF>, Map<Integer, Object>> cache = new java.util.HashMap<>();
    private static Cache instance;

    /*
     * This method is used to add a model object to the cache/container.
     */
    public static void put(modelIF obj) {
        Class<? extends modelIF> clazz = obj.getClass();
        Map<Integer, Object> map = cache.get(clazz);
        if (map == null) {
            map = new java.util.HashMap<Integer, Object>();
            cache.put(clazz, map);
        }
        map.put(obj.getID(), obj);
    }

    /*
     * This method is used to get a model object from the cache/container by its ID;
     */
    public static Object get(Class<? extends modelIF> clazz, int id) {
        Map<Integer, Object> map = cache.get(clazz);
        if (map == null) {
            return null;
        }
        return map.get(id);
    }

    /*
     * This method checks if a model object is in the cache/container by its ID;
     */
    public static boolean contains(Class<? extends modelIF> clazz, int id) {
        Map<Integer, Object> map = cache.get(clazz);
        if (map == null) {
            return false;
        }
        return map.containsKey(id);
    }

}