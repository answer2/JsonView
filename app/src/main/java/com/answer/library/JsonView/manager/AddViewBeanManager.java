package com.answer.library.JsonView.manager;
import com.answer.library.JsonView.utils.EmptyUtil;

/**
 * @Author AnswerDev
 * @Date 2023/03/24 22:06
 * @Describe 添加自定义Bean
 */
public class AddViewBeanManager {
    
    public static final String TAG = "AddViewBeanManager";
    
    private AddViewBeanManager Instance;
    
    //private View viewInstance;
    
    private Class<?> viewClass;
    
    private AddViewBeanManager(Class<?> clazz){
        this.viewClass = clazz;
        
    }
    
    public static AddViewBeanManager createViewBean(Class<?> clasz){
        if(EmptyUtil.isNotNull(clasz)){
            return new AddViewBeanManager(clasz);
        }
        return null;
    }
    
    /*
     * methodName:指Class里面的方法名
     * jsonName:指json的方法名字
     * 
     */
    
    public AddViewBeanManager addMethod(String methodName, String jsonName, Class<?>... type){
       
        
        return Instance;
    }
    
    
    
    
    
}
