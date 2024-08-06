package com.answer.library.JsonView.bean.widget;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.gson.Gson;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.bean.widget.EditTextBean;
import com.answer.library.JsonView.manager.GsonSingleton;

public class EditTextBean extends TextViewBean {

    private static final Gson gson = GsonSingleton.getInstance();
    public static final String TAG = "EditTextBean";

    private String hint;
    private String hintColor;


    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public void setHintColor(String hintColor) {
        this.hintColor = hintColor;
    }

    public String getHintColor() {
        return hintColor;
    }

    @Override
    public ViewGroup.LayoutParams setParame(View view) {
        EditText view_initial = (EditText) view;
        if (getHint() != null)view_initial.setHint(getHint());
        if (getHintColor() != null)view_initial.setHintTextColor(Color.parseColor(getHintColor()));

        return super.setParame(view);
    }

    public static EditText getView(String json) {
        if (!json.isEmpty()) {
            EditTextBean varBean = gson.fromJson(json.toString(), EditTextBean.class);
            EditText editText = new EditText(JsonManager.MainContext);
            varBean.setParame(editText);
            return editText;
        }
        throw new NullPointerException();
    }

}
