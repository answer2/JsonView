package com.answer.library.JsonView.bean.widget;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import com.google.gson.Gson;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.bean.widget.ScrollViewBean;
import com.answer.library.JsonView.manager.GsonSingleton;
import com.answer.library.JsonView.weight.AScrollView;
import org.json.JSONObject;

public class ScrollViewBean extends FrameLayoutBean {
    private static final Gson gson = GsonSingleton.getInstance();

    private boolean overScrollMode = true;
    private boolean scrollBar = true;
    private Object items;

    public void setItems(Object items) {
        this.items = items;
    }

    public Object getItems() {
        return items;
    }

    public void setOverScrollMode(boolean overScrollMode) {
        this.overScrollMode = overScrollMode;
    }

    public boolean isOverScrollMode() {
        return overScrollMode;
    }

    public void setScrollBar(boolean scrollBar) {
        this.scrollBar = scrollBar;
    }

    public boolean isScrollBar() {
        return scrollBar;
    }


    @Override
    public ViewGroup.LayoutParams setParame(View view) {
        ViewGroup.LayoutParams layoutParams =super.setParame(view);
        ScrollView view_initial = (ScrollView) view;
        if (getItems() != null) {
            JSONObject json = (JSONObject) getItems();
            view_initial.addView(JsonManager.createView(json.toString()));
        }
        
        
        view_initial.setScrollbarFadingEnabled(isScrollBar());
        view_initial.setOverScrollMode(isOverScrollMode() ? View.OVER_SCROLL_NEVER : View.OVER_SCROLL_ALWAYS);
        return layoutParams;
    }

    public static ScrollView getView(String json) {
        if (!json.isEmpty()) {
            ScrollViewBean varBean = gson.fromJson(json.toString(), ScrollViewBean.class);
            AScrollView scrollView = new AScrollView(JsonManager.MainContext);
            varBean.setParame(scrollView);
            
            return scrollView;
        }
        throw new NullPointerException();
    }




}
