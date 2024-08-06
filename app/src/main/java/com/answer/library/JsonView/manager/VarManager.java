package com.answer.library.JsonView.manager;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author AnswerDev
 * @Date 2023/02/26 23:03
 * @Describe 管理Var的类
 */
public class VarManager {

    public static final String TAG = "VarManager";

    // Initialize with a default entry.
    public static final Map<String, Object> varList = new HashMap<String, Object>() {{
            put("a", "主播设置var成功");
        }};

    /**
     * Adds a key-value pair to the map if the key does not already exist.
     *
     * @param key   The key for the value.
     * @param value The value to be added.
     * @return true if the value was added, false if the key already exists.
     */
    public static boolean add(String key, Object value) {
        if (!varList.containsKey(key)) {
            varList.put(key, value);
            return true;
        }
        return false;
    }

    /**
     * Removes the entry associated with the given key.
     *
     * @param key The key for the entry to be removed.
     * @return true if the entry was removed, false if no entry was found for the given key.
     */
    public static boolean remove(String key) {
        return varList.remove(key) != null;
    }

    /**
     * Retrieves the value associated with the given key.
     *
     * @param key The key for the entry to be retrieved.
     * @return The value associated with the key, or null if no entry is found.
     */
    public static Object get(String key) {
        return varList.get(key);
    }

    @Override
    public String toString() {
        return "Name: " + TAG + "\n" +
            "Size: " + varList.size();
    }

    // Default constructor
    public VarManager() { }
}

