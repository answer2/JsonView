package com.answer.library.JsonView;

import android.content.Context;
import android.view.View;
import com.answer.library.JsonView.bean.DefinitionBean;
import com.answer.library.JsonView.bean.widget.ButtonBean;
import com.answer.library.JsonView.bean.widget.EditTextBean;
import com.answer.library.JsonView.bean.widget.FrameLayoutBean;
import com.answer.library.JsonView.bean.widget.ImageViewBean;
import com.answer.library.JsonView.bean.widget.LinearLayoutBean;
import com.answer.library.JsonView.bean.widget.ScrollViewBean;
import com.answer.library.JsonView.bean.widget.TextViewBean;
import com.answer.library.JsonView.bean.widget.ViewBean;
import com.answer.library.JsonView.debug.JsonLog;
import com.answer.library.JsonView.exceptions.LoadJsonException;
import com.answer.library.JsonView.manager.GsonSingleton;
import com.answer.library.JsonView.manager.JavaScriptManager;
import com.answer.library.JsonView.manager.VarManager;
import com.answer.library.JsonView.utils.AssetUtil;
import com.answer.library.JsonView.utils.FileUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.*;
import com.answer.library.JsonView.interfaces.*;
import com.answer.library.JsonView.utils.*;

/**
 * Manages JSON-based view creation and configuration.
 */
public class JsonManager {

    public static final String TAG = "JsonManager";
    public static Context MainContext;
    public static boolean isDebug = false;
    private static final Gson gson = GsonSingleton.getInstance();

    // Initialize static variables
    public static final ArrayList<String> MENU_LIST = new ArrayList<>();
    public static final Map<String, Class<?>> VIEW_BEAN_MAP = new HashMap<>();
	public static final ArrayList<ClickCallBack> CLICK_CALLBS = new ArrayList<>();
	
    static {
        // Initialize VIEW_BEAN_MAP
        VIEW_BEAN_MAP.put("Button", ButtonBean.class);
        VIEW_BEAN_MAP.put("TextView", TextViewBean.class);
        VIEW_BEAN_MAP.put("EditText", EditTextBean.class);
        VIEW_BEAN_MAP.put("ImageView", ImageViewBean.class);
        VIEW_BEAN_MAP.put("ScrollView", ScrollViewBean.class);
        VIEW_BEAN_MAP.put("FrameLayout", FrameLayoutBean.class);
        VIEW_BEAN_MAP.put("LinearLayout", LinearLayoutBean.class);
    }

    private static String localDir;
    private static String jsonDir;

    /**
     * Static method to initialize JsonManager with context and definition.
     */
    public static void init(Context context, String definition) {
        MainContext = context;
        
        JsonLog.d(TAG, "Initializing JsonManager");

        // Initialize paths
        localDir = MainContext.getFilesDir().getAbsolutePath();
        jsonDir = localDir;

        // Decompress assets
        try {
            FileUtil.unApk(MainContext, "assets", localDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize JavaScript Manager
        new JavaScriptManager();
        JsonLog.d(TAG, "JavaScriptManager initialized");

        // Initialize definition
        if (definition != null && !definition.isEmpty()) {
            DefinitionBean definitionBean = gson.fromJson(definition, DefinitionBean.class);
            if (definitionBean.getVar() != null) {
                for (Map.Entry<String, JsonElement> entry : definitionBean.getVar().entrySet()) {
                    VarManager.add(entry.getKey(), entry.getValue());
                }
            } else {
                throw new LoadJsonException("DefinitionBean has no variables.");
            }
            JsonLog.d(TAG, "Definition initialized successfully");
        }
        JsonLog.d(TAG, "JsonManager initialized successfully");
    }

    /**
     * Initializes JsonManager with a context and a file.
     */
    public static void init(Context context, File definitionFile) throws IOException {
        init(context, AssetUtil.getAsset(context, definitionFile.toString().replace("/assets/", "")));
    }

    /**
     * Initializes JsonManager with a context only.
     * @param context For JsonView init localDir
     */
    public static void init(Context context) {
        init(context, (String) null);
    }
    
	/**
	  * 获取JsonView的文件目录
	  *
	  * @return String路径
	  */
    public static String getLocalDir(){
        return localDir;
    }
	
	/**
	  * 为了提供Click的回调
	  *
	  * @param callback 回调的对象
	  */
	public static void addClickCallback(ClickCallBack callback){
		if(EmptyUtil.isNotNull(callback)) CLICK_CALLBS.add(callback);
	}
	

    /**
     * Creates a view based on the provided JSON string.
     *
     * @param json The JSON string representing the view.
     * @return The created view.
     */
    public static View createView(String json) {
        Type viewBeanType = new TypeToken<ViewBean>() {}.getType();
        ViewBean bean = gson.fromJson(json, viewBeanType);
        String type = bean.getType();
        View view = null;

        try {
            if ("View".equals(type)) {
                view = bean.getView(json);
            } else {
                Class<?> viewClass = VIEW_BEAN_MAP.get(type);
                if (viewClass != null) {
                    Method getViewMethod = viewClass.getDeclaredMethod("getView", String.class);
                    getViewMethod.setAccessible(true);
                    view = (View) getViewMethod.invoke(null, json);
                }
            }
        } catch(InvocationTargetException err){
			err.printStackTrace();
		}catch (Exception e) {
            e.printStackTrace();
            throw new LoadJsonException("Failed to create view", e);
        }

        if (view != null) {
            return view;
        } else {
            throw new LoadJsonException("Failed to create view");
        }
    }
    
}

