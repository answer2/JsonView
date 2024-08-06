package com.answer.library.JsonView.manager;

import android.view.View.OnLongClickListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author AnswerDev
 * @Date 2023/04/01 20:22
 * @Describe OnLongClick管理
 */

public class OnLongClickIdManager {

    public static final String TAG = "OnLongClickIdManager";

    private static final Map<String, OnLongClickListener> viewLongClick = new HashMap<>();

    /**
     * Adds an OnLongClickListener for the given id if it does not already exist.
     *
     * @param id    The id for which the listener is to be added.
     * @param click The OnLongClickListener to be added.
     * @return true if the listener was added, false if it already exists.
     */
    public static boolean add(String id, OnLongClickListener click) {
        // Put the click listener only if there is no existing listener for the given id.
        if (!viewLongClick.containsKey(id)) {
            viewLongClick.put(id, click);
            return true;
        }
        return false;
    }

    /**
     * Removes the OnLongClickListener associated with the given id.
     *
     * @param id The id for which the listener is to be removed.
     * @return true if the listener was removed, false if no listener was found for the given id.
     */
    public static boolean remove(String id) {
        // Remove the listener and return true if a listener was removed.
        return viewLongClick.remove(id) != null;
    }

    /**
     * Retrieves the OnLongClickListener associated with the given id.
     *
     * @param id The id for which the listener is to be retrieved.
     * @return The OnLongClickListener associated with the id, or null if no listener is found.
     */
    public static OnLongClickListener get(String id) {
        // Return the listener associated with the given id, or null if none exists.
        return viewLongClick.get(id);
    }
    

}
