package com.answer.library.JsonView.manager;

import android.view.View.OnClickListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author AnswerDev
 * @Date 2023/04/01 20:20
 * @Describe OnClick管理
 */

public class OnClickIdManager {

    public static final String TAG = "OnClickIdManager";

    private static final Map<String, OnClickListener> viewClickMap = new HashMap<>();

    /**
     * Adds a click listener for a given id if it doesn't already exist.
     * @param id The identifier for the click listener.
     * @param click The click listener to add.
     * @return true if the click listener was added, false if it already exists.
     */
    public static boolean add(String id, OnClickListener click) {
        if (!viewClickMap.containsKey(id)) {
            viewClickMap.put(id, click);
            return true;
        }
        return false;
    }

    /**
     * Removes the click listener associated with the given id.
     * @param id The identifier of the click listener to remove.
     * @return true if the click listener was removed, false if it didn't exist.
     */
    public static boolean remove(String id) {
        return viewClickMap.remove(id) != null;
    }

    /**
     * Retrieves the click listener associated with the given id.
     * @param id The identifier of the click listener to retrieve.
     * @return The click listener associated with the id, or null if it doesn't exist.
     */
    public static OnClickListener get(String id) {
        return viewClickMap.get(id);
    }
}

