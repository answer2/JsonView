package com.answer.library.JsonView;

import dalvik.system.*;
import java.util.*;


/**
 * @Author AnswerDev
 * @Date 2023/02/19 07:37
 * @Describe DexClassLoader
 */
public class JsonDexClassLoader extends DexClassLoader {
    
    public static final String TAG = "JsonDexClassLoader";
    
    private HashMap<String,Class<?>> classCache=new HashMap<String,Class<?>>();

    private String mDexPath;

    public JsonDexClassLoader(java.lang.String dexPath, java.lang.String optimizedDirectory, java.lang.String libraryPath, java.lang.ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
        mDexPath=dexPath;
    }

    public String getDexPath() {
        return mDexPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> cls=classCache.get(name);
        if (cls == null) {
            cls = super.findClass(name);
            classCache.put(name, cls);
        }
        return cls;
	}
    
}
