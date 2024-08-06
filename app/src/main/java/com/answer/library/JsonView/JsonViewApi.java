package com.answer.library.JsonView;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.debug.JsonLog;
import com.answer.library.JsonView.exceptions.LoadJsonException;
import com.answer.library.JsonView.manager.OnClickIdManager;
import com.answer.library.JsonView.manager.OnLongClickIdManager;
import com.answer.library.JsonView.manager.OnTouchIdManager;
import com.answer.library.JsonView.manager.VarManager;
import com.answer.library.JsonView.manager.ViewIdManager;
import com.answer.library.JsonView.utils.EmptyUtil;
import com.caoccao.javet.annotations.V8Function;

/**
 * @Author AnswerDev
 * @Date 2023/03/03 22:05
 * @Describe JsonApi的接口，更方便外部调用
 */
public class JsonViewApi {

    public static final String TAG = "JsonViewApi";

    @V8Function
    public static Context getContext() {
        return JsonManager.MainContext;
    }

    //Var
    @V8Function
    public static boolean addVar(String key, Object value) {
        return VarManager.add(key, value);
    }

    @V8Function
    public static Object getVar(String key) {
        return VarManager.get(key);
    }

    @V8Function
    public static boolean removeVar(String key) {
        return VarManager.remove(key);
    }

    //View ID
    @V8Function
    public static boolean addView(String key, View value) {
        return ViewIdManager.add(key, value);
    }

    @V8Function
    public static View getView(String key) {
        return ViewIdManager.get(key);
    }

    @V8Function
    public static boolean removeView(String key) {
        return ViewIdManager.remove(key);
    }

    /*OnClick*/
    @V8Function
    public static boolean addOnClick(String key, OnClickListener value) {
        if (!(EmptyUtil.isNotNull(key)))throw new LoadJsonException("addOnClick Key is Null");

        return OnClickIdManager.add(key, value);
    }
    
    @V8Function
    public static OnClickListener getOnClick(String key) {
        return OnClickIdManager.get(key);
    }

    @V8Function
    public static boolean removeOnClick(String key) {
        return OnClickIdManager.remove(key);
    }

    /*OnLongClick*/
    @V8Function
    public static boolean addOnLongClick(String key, OnLongClickListener value) {
        return OnLongClickIdManager.add(key, value);
    }

    @V8Function
    public static OnLongClickListener getOnLongClick(String key) {
        return OnLongClickIdManager.get(key);
    }

    @V8Function
    public static boolean removeOnLongClick(String key) {
        return OnLongClickIdManager.remove(key);
    }

    /*OnTouch*/
    @V8Function
    public static boolean addOnTouch(String key, OnTouchListener value) {
        return OnTouchIdManager.add(key, value);
    }
    
    @V8Function
    public static OnTouchListener getOnTouch(String key) {
        return OnTouchIdManager.get(key);
    }

    @V8Function
    public static boolean removeOnTouch(String key) {
        return OnTouchIdManager.remove(key);
    }

    @V8Function
    public static View createView(String json) {
        return EmptyUtil.isNotNull(json) ? JsonManager.createView(json) : null;
    }

    @V8Function
    public static void info(String msg) {
        JsonLog.i(TAG, msg);
    }

    @V8Function
    public static void error(String msg) {
        JsonLog.e(TAG, msg);
    }
    
    @V8Function
    public void toast(String messag){
        Toast.makeText(JsonManager.MainContext, messag, Toast.LENGTH_SHORT).show();
    }

    @V8Function
    public void log(String messag){
        JsonLog.d("JSEngine",messag);
    }

}
