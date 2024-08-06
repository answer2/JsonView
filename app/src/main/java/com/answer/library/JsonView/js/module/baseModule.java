package com.answer.library.JsonView.js.module;
import android.widget.Toast;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.debug.JsonLog;
import com.caoccao.javet.annotations.V8Function;

/**
 * @Author AnswerDev
 * @Date 2023/07/31 15:28
 */
public class baseModule {
    
    //@V8Property
    
    @V8Function
    public void toast(String messag){
        Toast.makeText(JsonManager.MainContext, messag, Toast.LENGTH_SHORT).show();
    }
    
    @V8Function
    public void log(String messag){
        JsonLog.d("JSEngine",messag);
    }
    
}
