package com.answer.library.JsonView.bean.widget;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.graphics.Color;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.utils.StringUtil;
import com.answer.library.JsonView.utils.ScreenSizeUtil;
import com.google.gson.Gson;
import com.answer.library.JsonView.manager.GsonSingleton;

public class TextViewBean extends ViewBean {

    public static final String TAG = "TextViewBean";
    private static final Gson gson = GsonSingleton.getInstance();
    
    private String gravity;
    private String color;
    private String text;
    private String size;

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getGravity() {
        return gravity;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    @Override
    public ViewGroup.LayoutParams setParame(View view) {
        ViewGroup.LayoutParams layoutParams = super.setParame(view);
        
        TextView view_initial = (TextView)view;
        if(getText()!=null)view_initial.setText(StringUtil.var(getText()));
        if(getSize()!=null)view_initial.setTextSize(ScreenSizeUtil.meatureWithUnitText(getSize()));
        if(getColor()!= null)view_initial.setTextColor(Color.parseColor(getColor()));
        return layoutParams;
    }

    public static TextView getView(String json) {
        if (!json.isEmpty()) {
            TextViewBean varBean = gson.fromJson(json.toString(),TextViewBean.class);
            TextView textView = new TextView(JsonManager.MainContext);
            varBean.setParame(textView);
            return textView;
        }
        throw new NullPointerException();
    }

}
