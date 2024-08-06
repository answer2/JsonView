package com.answer.library.JsonView.bean.widget;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.gson.Gson;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.bean.widget.ButtonBean;
import com.answer.library.JsonView.manager.GsonSingleton;

public class ButtonBean extends TextViewBean {
    private static final Gson gson = GsonSingleton.getInstance();
    
    public static final String TAG = "ButtonBean";

    @Override
    public ViewGroup.LayoutParams setParame(View view) {
        
        Button view_initial = (Button) view;
        
        return super.setParame(view);
    }
    
    public static Button getView(String json) {
        if (!json.isEmpty()) {
            ButtonBean varBean = gson.fromJson(json.toString(),ButtonBean.class);
            Button button = new Button(JsonManager.MainContext);
            varBean.setParame(button);
            return button;
        }
        throw new NullPointerException();
    }
    
}
