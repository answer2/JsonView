package com.answer.library.JsonView.manager;
import java.util.HashMap;
import android.animation.ObjectAnimator;

/**
 * @Author AnswerDev
 * @Date 2023/02/25 21:49
 * @Describe 动画管理
 */
public class AnimationManager {
    
    public static final String TAG = "AnimationManager";
    
    public static final HashMap<String,ObjectAnimator> AnimationId = new HashMap<String,ObjectAnimator>();
    
    public static boolean add(String key, ObjectAnimator value) {
        if (!AnimationId.containsKey(key)) {
            AnimationId.put(key, value);
            return true;
        }
        return false;
    }

    public static boolean remove(String key) {
        if (AnimationId.containsKey(key)) {
            AnimationId.remove(key);
            return true;
        }
        return false;
    }

    public static Object get(String key) {
        if (AnimationId.containsKey(key)) {
            return AnimationId.get(key);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Name:" + TAG + "\n"
               +"Size:" + AnimationId.size();
    }
    
    public AnimationManager(){ }
}
