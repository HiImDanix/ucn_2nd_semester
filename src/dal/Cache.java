package dal;

import java.util.Map;

public class Cache {

    private static Map<Class, Map<Integer, Object>> cache = new java.util.HashMap<>();
    private static Cache instance;

    public static void put(Class clazz, int id, Object obj) {
        Map<Integer, Object> map = cache.get(clazz);
        if (map == null) {
            map = new java.util.HashMap<Integer, Object>();
            cache.put(clazz, map);
        }
        map.put(id, obj);
    }

    public static Object get(Class clazz, int id) {
        Map<Integer, Object> map = cache.get(clazz);
        if (map == null) {
            return null;
        }
        return map.get(id);
    }

    public static boolean contains(Class clazz, int id) {
        Map<Integer, Object> map = cache.get(clazz);
        if (map == null) {
            return false;
        }
        return map.containsKey(id);
    }

}
