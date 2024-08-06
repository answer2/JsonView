package com.answer.library.JsonView.bean.widget;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.bean.widget.LinearLayoutBean;
import com.answer.library.JsonView.enums.GravityType;
import com.answer.library.JsonView.manager.GsonSingleton;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import com.google.gson.*;
import com.google.gson.internal.*;

public class LinearLayoutBean extends ViewBean {
    
    private static final Gson gson = GsonSingleton.getInstance();
    
    private int ori;
    private String gravity = "left|top";

    private List<Object> items =
            new ArrayList<Object>() {
                {
                    add(null);
                }
            };

    private List<Integer> margin =
            new ArrayList<Integer>() {
                {
                    add(0);
                    add(0);
                    add(0);
                    add(0);
                }
            };

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setMargin(List<Integer> margin) {
        this.margin = margin;
    }

    public List<Integer> getMargin() {
        return margin;
    }

    public void setOri(int ori) {
        this.ori = ori;
    }

    public int getOri() {
        return ori;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getGravity() {
        return gravity;
    }

    @Override
    public ViewGroup.LayoutParams setParame(View view) {
        android.widget.LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) super.setParame(view);
        layoutParams.leftMargin = getMargin().get(0).intValue();
        layoutParams.topMargin = getMargin().get(1).intValue();
        layoutParams.rightMargin = getMargin().get(2).intValue();
        layoutParams.bottomMargin = getMargin().get(3).intValue();
        view.setLayoutParams(layoutParams);

        LinearLayout view_initial = (LinearLayout) view;

        view_initial.setOrientation(getOri());

        int gravity_ = Gravity.NO_GRAVITY;
        if(getGravity().contains("|")){
        String[] gravityList = getGravity().split("\\|");
        for (int a = 0; gravityList.length > a; a++) {
            GravityType gravityType = GravityType.valueOf(gravityList[a]);
            gravity_ = gravity_ | gravityType.getValue();
        }
        }else{
            gravity_ = GravityType.valueOf(getGravity()).getValue();
        }
        view_initial.setGravity(gravity_);
        
        for (Object v : getItems()) {
            if (v != null) {
                JsonObject jsonObect = gson.toJsonTree(v).getAsJsonObject(); 
                String json = jsonObect.toString();
                View createView = JsonManager.createView(json);
                
                view_initial.addView(createView);
            }
        }

        return layoutParams;
    }

    public static LinearLayout getView(String json) {
        if (!json.isEmpty()) {
            LinearLayoutBean varBean = gson.fromJson(json.toString(), LinearLayoutBean.class);
            LinearLayout linearLayout = new LinearLayout(JsonManager.MainContext);
            varBean.setParame(linearLayout);
            return linearLayout;
        }
        throw new NullPointerException();
    }
}
