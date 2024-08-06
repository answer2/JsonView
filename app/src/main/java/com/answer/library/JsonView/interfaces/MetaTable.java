package com.answer.library.JsonView.interfaces;
import com.answer.library.JsonView.exceptions.LoadJsonException;

/**
 * @Author AnswerDev
 * @Date 2023/02/19 07:23
 * @Describe 原标签
 */
public interface MetaTable {
    
   public static final String TAG = "MetaTable";
    
    public Object __call(Object...arg) throws LoadJsonException;

    public Object __index(String key); 

	public void __newIndex(String key,Object value); 
    
}
