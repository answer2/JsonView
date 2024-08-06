package com.answer.library.JsonView.bean.widget;

import android.widget.FrameLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.enums.GravityType;
import com.answer.library.JsonView.utils.AssetUtil;
import com.answer.library.color.ColorLibrary;
import com.answer.library.JsonView.manager.GsonSingleton;

public class FrameLayoutBean extends ViewBean {

    private static final Gson gson = GsonSingleton.getInstance();

    private String foregroundGravity = "left|top";
    private Object foreground;
    private Object round;

    // Getters and Setters
    public String getForegroundGravity() {
        return foregroundGravity;
    }

    public void setForegroundGravity(String foregroundGravity) {
        this.foregroundGravity = foregroundGravity;
    }

    public Object getForeground() {
        return foreground;
    }

    public void setForeground(Object foreground) {
        this.foreground = foreground;
    }

    public Object getRound() {
        return round;
    }

    public void setRound(Object round) {
        this.round = round;
    }

    @Override
    public ViewGroup.LayoutParams setParame(View view) {
        FrameLayout frameLayout = (FrameLayout) view;

        int gravity = Gravity.NO_GRAVITY;
        if (foregroundGravity.contains("|")) {
            String[] gravityList = foregroundGravity.split("\\|");
            for (String gravityStr : gravityList) {
                GravityType gravityType = GravityType.valueOf(gravityStr);
                gravity |= gravityType.getValue();
            }
        } else {
            gravity = GravityType.valueOf(foregroundGravity).getValue();
        }
        frameLayout.setForegroundGravity(gravity);

        Object background = null;
        Object roundCorners = 0;

        if (round != null) {
            if (round instanceof JsonArray) {
                JsonArray roundJsonArray = (JsonArray) round;
                roundCorners = jsonArrayToIntArray(roundJsonArray);
            } else {
                roundCorners = round;
            }
        }

        if (foreground != null) {
            if (foreground instanceof JsonArray) {
                JsonArray foregroundJsonArray = (JsonArray) foreground;
                background = jsonArrayToStringArray(foregroundJsonArray);
            } else {
                String foregroundStr = (String) foreground;
                if (foregroundStr.trim().contains(".png")) {
                    frameLayout.setBackground(AssetUtil.getImageFromAssetsFileD(JsonManager.MainContext, foregroundStr));
                } else {
                    background = foregroundStr;
                }
            }
        }

        if (background != null) {
            frameLayout.setForeground(ColorLibrary.Portable(background, roundCorners));
        }

        return super.setParame(view);
    }

    public static FrameLayout getView(String json) {
        if (json != null && !json.isEmpty()) {
            FrameLayoutBean frameLayoutBean = gson.fromJson(json, FrameLayoutBean.class);
            FrameLayout frameLayout = new FrameLayout(JsonManager.MainContext);
            frameLayoutBean.setParame(frameLayout);
            return frameLayout;
        }
        throw new NullPointerException("JSON string is null or empty");
    }

    private int[] jsonArrayToIntArray(JsonArray jsonArray) {
        int[] result = new int[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            result[i] = jsonArray.get(i).getAsInt();
        }
        return result;
    }

    private String[] jsonArrayToStringArray(JsonArray jsonArray) {
        String[] result = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            result[i] = jsonArray.get(i).getAsString();
        }
        return result;
    }
}

