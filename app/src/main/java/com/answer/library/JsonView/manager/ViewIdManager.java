package com.answer.library.JsonView.manager;
import android.view.View;
import java.util.HashMap;

/**
 * @Author AnswerDev
 * @Date 2023/02/26 23:10
 * @Describe 处理定义ID
 */
public class ViewIdManager {
    
    public static final String TAG = "ViewIdManager";
    
    public static HashMap<String, View> ViewIdLsit = new HashMap<String, View>();
    
    public static boolean add(String id, View view) {
        if (!ViewIdLsit.containsKey(id)) {
            ViewIdLsit.put(id, view);
            return true;
        }
        return false;
    }

    public static boolean remove(String id) {
        if (ViewIdLsit.containsKey(id)) {
            ViewIdLsit.remove(id);
            return true;
        }
        return false;
    }

    public static View get(String id) {
        if (ViewIdLsit.containsKey(id)) {
            return ViewIdLsit.get(id);
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "Name:" + TAG + "\n"
            +"Size:" + ViewIdLsit.size();
    }
    
    public ViewIdManager(){ }
    
}
