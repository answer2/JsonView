package com.answer.library.JsonView;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import dalvik.system.DexClassLoader;
import com.answer.library.JsonView.exceptions.LoadJsonException;
import com.answer.library.JsonView.utils.FileUtil;
import android.app.Application;
import com.answer.library.JsonView.debug.JsonLog;
import java.util.Arrays;

/** 
 @Author AnswerDev
 @Date 2023/02/19 07:17
 @Describe 用来加载资源和动态调用库 
 */
public class JsonDexLoader {

    public static final String TAG = "JsonDexLoader";

    private static HashMap<String, JsonDexClassLoader> dexCache = new HashMap<String, JsonDexClassLoader>();
    private ArrayList<ClassLoader> dexList = new ArrayList<ClassLoader>();
    private HashMap<String, String> libCache = new HashMap<String, String>();

    private Context mContext;

    private String localDir;

    private AssetManager mAssetManager;

    private JsonResources mResources;
    private Resources.Theme mTheme;
    private String odexDir;

    public JsonDexLoader(Context context) {
        mContext = context;

        //setPath
        localDir = context.getFilesDir().getAbsolutePath();
        odexDir = context.getDir("odex", Context.MODE_PRIVATE).getAbsolutePath();
        
    }

    public Resources.Theme getTheme() {
        return mTheme;
    }

    public ArrayList<ClassLoader> getClassLoaders() {
        // TODO: Implement this method
        return dexList;
    }

    public JsonDexClassLoader loadApp(String pkg) {
        try {
            JsonDexClassLoader dex = dexCache.get(pkg);
            if (dex == null) {
                PackageManager manager = mContext.getPackageManager();
                ApplicationInfo info = manager.getPackageInfo(pkg, 0).applicationInfo;
                dex = new JsonDexClassLoader(info.publicSourceDir, odexDir, info.nativeLibraryDir, mContext.getClassLoader());
                dexCache.put(pkg, dex);
            }
            if (!dexList.contains(dex)) {
                dexList.add(dex);
            }
            return dex;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadLibs() throws LoadJsonException {
        File[] libs = new File(localDir + "/libs").listFiles();
        
        if (libs == null) return;
        for (File f : libs) {
            
            if (f.isDirectory()) continue;
            if (f.getAbsolutePath().endsWith(".so")) loadLib(f.getName());
            else loadDex(f.getAbsolutePath());
        }
    }

    public void loadLib(String name) throws LoadJsonException {
        String fn = name;
        int i = name.indexOf(".");
        if (i > 0) fn = name.substring(0, i);
        if (fn.startsWith("lib")) fn = fn.substring(3);
        String libDir = mContext.getDir(fn, Context.MODE_PRIVATE).getAbsolutePath();
        String libPath = libDir + "/lib" + fn + ".so";
        File f = new File(libPath);
        if (!f.exists()) {
            f = new File(localDir + "/libs/lib" + fn + ".so");
            if (!f.exists()) throw new LoadJsonException("can not find lib " + name);
            FileUtil.copyFile(localDir + "/libs/lib" + fn + ".so", libPath);
        }
        libCache.put(fn, libPath);
    }

    public HashMap<String, String> getLibrarys() {
        return libCache;
    }

    public DexClassLoader loadDex(String path) throws LoadJsonException {
        JsonDexClassLoader dex = dexCache.get(path);
        if (dex == null) dex = loadApp(path);
        if (dex == null) {
            String name = path;
            if (path.charAt(0) != '/') path = localDir + "/" + path;
            if (!new File(path).exists())
                if (new File(path + ".dex").exists()) path = path + ".dex";
                else if (new File(path + ".jar").exists()) path = path + ".jar";
                else throw new LoadJsonException(path + " not found");
            String id = FileUtil.getFileMD5(path);
            if (id != null && id.equals("0")) id = name;
            dex = dexCache.get(id);

            if (dex == null) {
                dex = new JsonDexClassLoader(path, odexDir, mContext.getApplicationInfo().nativeLibraryDir, mContext.getClassLoader());
                dexCache.put(id, dex);
            }
        }

        if (!dexList.contains(dex)) {
            dexList.add(dex);
            path = dex.getDexPath();
            if (path.endsWith(".jar")) loadResources(path);
        }
        return dex;
    }

    public void loadResources(String path) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            int ok = (int) addAssetPath.invoke(assetManager, path);
            if (ok == 0) return;
            mAssetManager = assetManager;
            Resources superRes = mContext.getResources();
            mResources = new JsonResources(mAssetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            mResources.setSuperResources(superRes);
            mTheme = mResources.newTheme();
            mTheme.setTo(mContext.getTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AssetManager getAssets() {
        return mAssetManager;
    }

    public Resources getResources() {
        return mResources;
    }
}
