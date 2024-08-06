package com.answer.library.JsonView.manager;

import android.view.View.OnTouchListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author AnswerDev
 * @Date 2023/04/01 20:23
 * @Describe OnTouch管理
 */

public class OnTouchIdManager {

        public static final String TAG = "OnTouchIdManager";

        private static final Map<String, OnTouchListener> viewTouch = new HashMap<>();

        /**
         * Adds an OnTouchListener for the given id if it does not already exist.
         *
         * @param id    The id for which the listener is to be added.
         * @param touch The OnTouchListener to be added.
         * @return true if the listener was added, false if it already exists.
         */
        public static boolean add(String id, OnTouchListener touch) {
            // Add the listener only if it does not already exist.
            if (!viewTouch.containsKey(id)) {
                viewTouch.put(id, touch);
                return true;
            }
            return false;
        }

        /**
         * Removes the OnTouchListener associated with the given id.
         *
         * @param id The id for which the listener is to be removed.
         * @return true if the listener was removed, false if no listener was found for the given id.
         */
        public static boolean remove(String id) {
            // Remove the listener and return true if a listener was removed.
            return viewTouch.remove(id) != null;
        }

        /**
         * Retrieves the OnTouchListener associated with the given id.
         *
         * @param id The id for which the listener is to be retrieved.
         * @return The OnTouchListener associated with the id, or null if no listener is found.
         */
        public static OnTouchListener get(String id) {
            // Return the listener associated with the given id, or null if none exists.
            return viewTouch.get(id);
        }

}
