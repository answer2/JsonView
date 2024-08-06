package com.answer.library.JsonView.manager;

import android.content.Context;
import android.view.View.OnClickListener;
import com.answer.library.JsonView.JsonViewApi;
import com.answer.library.JsonView.debug.JsonLog;
import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author AnswerDev
 * @Date 2023/04/05 09:27
 * @Describe QuickJsManager
 */

public class JavaScriptManager {

    public static final String TAG = "JavaScriptManager";

    private static StringWriter error = new StringWriter();

    private static V8Runtime v8Runtime;

    public JavaScriptManager() {
        try {
            v8Runtime = V8Host.getV8Instance().createV8Runtime();
            v8Runtime.allowEval(true);
            init();
        } catch (Exception e) {
            JsonLog.e("JavaScriptManager", e.getMessage());
        }
    }

    public static V8Runtime getV8Runtime() {
        return v8Runtime;
    }

    //init beas function and var
    private static void init() throws Exception {
        addJavascriptInterface("Context", Context.class);
        addJavascriptInterface("OnClickListener", OnClickListener.class);
        v8Runtime.getGlobalObject().bind(new JsonViewApi());
    }

    public static Object executeScript(String source) {
        try {
            return v8Runtime.getExecutor(source).executeObject();
        } catch (Throwable e) {
            PrintWriter printWriter = new PrintWriter(error);
            e.printStackTrace(printWriter);
            Throwable cause = e.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
        }
        return null;
    }

    public static void executeVoidScript(String source) {
        try {
            v8Runtime.getExecutor(source).executeVoid();
        } catch (Throwable e) {
            PrintWriter printWriter = new PrintWriter(error);
            e.printStackTrace(printWriter);
            Throwable cause = e.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
        }
    }

    public static void set(String key, Object value) throws JavetException {
        v8Runtime.getGlobalObject().set(key, value);
    }

    public static void set(String key, String value) throws JavetException {
        v8Runtime.getGlobalObject().setString(key, value);
    }

    public static void set(String key, boolean value) throws JavetException {
        v8Runtime.getGlobalObject().setBoolean(key, value);
    }

    public static void set(String key, int value) throws JavetException {
        v8Runtime.getGlobalObject().setInteger(key, value);
    }
    public static void set(String key, double value) throws JavetException {
        v8Runtime.getGlobalObject().setDouble(key, value);
    }

    public static void addJavascriptInterface(String name, Class<?> clazz) throws JavetException {
        v8Runtime.getGlobalObject().set(name, clazz);
    }

    public static String getError() {
        return error.toString();
    }


}
