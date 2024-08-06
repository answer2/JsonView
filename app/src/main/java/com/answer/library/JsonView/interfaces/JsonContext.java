package com.answer.library.JsonView.interfaces;
import java.util.ArrayList;

/**
 * @Author AnswerDev
 * @Date 2023/02/19 08:36
 * @Describe Json上下文
 */
public interface JsonContext {
    
    public static final String TAG = "JsonContext";
    
    public ArrayList<ClassLoader> getClassLoaders();
    
    
}
