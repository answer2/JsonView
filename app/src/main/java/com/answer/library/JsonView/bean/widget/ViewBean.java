package com.answer.library.JsonView.bean.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.JsonViewApi;
import com.answer.library.JsonView.bean.ReflectBean;
import com.answer.library.JsonView.exceptions.LoadJsonException;
import com.answer.library.JsonView.manager.JavaScriptManager;
import com.answer.library.JsonView.manager.ViewIdManager;
import com.answer.library.JsonView.utils.AssetUtil;
import com.answer.library.JsonView.utils.EmptyUtil;
import com.answer.library.JsonView.utils.MediaUtil;
import com.answer.library.JsonView.utils.ScreenSizeUtil;
import com.answer.library.color.ColorLibrary;
import com.answer.library.color.roundRect;
import com.caoccao.javet.exceptions.JavetException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import com.answer.library.JsonView.debug.JsonLog;
import com.answer.library.JsonView.utils.*;
import com.answer.library.JsonView.interfaces.*;

public class ViewBean {

    private static final Gson gson = new GsonBuilder().create();

    private String type = "View";
    private String width = "-2";
    private String height = "-2";
    private Object background;
    private Object round;
    private List<Integer> padding = new ArrayList<Integer>() {{
            add(0);
            add(0);
            add(0);
            add(0);
        }};
    private String alpha = "1";
    private String elevation = "0";
    private boolean hide = false;
    private String fun;
    private String touch;
    private String longClick;
    private String id;
    private String viewClass;
    private ReflectBean reflect;
    private String sound;
    private boolean enabled = true;
    private String click;

    public void setClick(String click) {
        this.click = click;
    }

    public String getClick() {
        return click;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }

    public void setReflect(ReflectBean reflect) {
        this.reflect = reflect;
    }

    public ReflectBean getReflect() {
        return reflect;
    }

    public void setTouch(String touch) {
        this.touch = touch;
    }

    public String getTouch() {
        return touch;
    }

    public void setLongClick(String longClick) {
        this.longClick = longClick;
    }

    public String getLongClick() {
        return longClick;
    }

    public void setViewClass(String viewClass) {
        this.viewClass = viewClass;
    }

    public String getViewClass() {
        return viewClass;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setElevation(float elevation) {
        this.elevation = String.valueOf(elevation);
    }

    public float getElevation() {
        return Float.valueOf(elevation).floatValue();
    }

    public void setAlpha(float alpha) {
        this.alpha = String.valueOf(alpha);
    }

    public float getAlpha() {
        return Float.valueOf(alpha).floatValue();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFun(String fun) {
        this.fun = fun;
    }

    public String getFun() {
        return fun;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public boolean getHide() {
        return hide;
    }

    public void setRound(Object round) {
        this.round = round;
    }

    public Object getRound() {
        return round;
    }

    public void setBackground(Object background) {
        this.background = background;
    }

    public Object getBackground() {
        return background;
    }

    public void setPadding(List<Integer> padding) {
        this.padding = padding;
    }

    public List<Integer> getPadding() {
        return padding;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public ViewGroup.LayoutParams setParame(View view) {
        view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (getClick() != null)JavaScriptManager.getV8Runtime().getExecutor(getClick()).executeVoid();
                    } catch (JavetException e) {
                        JsonLog.e("ViewBean", e.getMessage());
                    }
                    
                    if (EmptyUtil.isNotNull(fun)) {
                        JsonViewApi.getOnClick(fun).onClick(v);
						for(ClickCallBack callback : JsonManager.CLICK_CALLBS) if(EmptyUtil.isNotNull(callback))callback.callback(getFun());
                    }
                    if (EmptyUtil.isNotNull(reflect) && reflect.isOnClick()) {
                        reflect.start();
                    }
                    if (EmptyUtil.isNotNull(sound)) {
                        MediaUtil.playMusic(sound);
                    }
                }
            });

        view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (EmptyUtil.isNotNull(longClick)) {
                        JsonViewApi.getOnLongClick(longClick).onLongClick(v);
                        return false;
                    }
                    return true;
                }
            });

        view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (EmptyUtil.isNotNull(touch)) {
                        JsonViewApi.getOnTouch(touch).onTouch(v, event);
                        return true;
                    }
                    return false;
                }
            });

        if (EmptyUtil.isNotNull(id)) {
            ViewIdManager.add(id, view);
        }

        view.setVisibility(hide ? View.GONE : View.VISIBLE);
        view.setPadding(padding.get(0), padding.get(1), padding.get(2), padding.get(3));
        view.setAlpha(Float.parseFloat(alpha));
        view.setElevation(Float.parseFloat(elevation));
        view.setEnabled(enabled);

        Object backgroundObject = null;
        Object roundObject = 0;
        
        if (EmptyUtil.isNotNull(round)) {
            if (round instanceof JsonArray) {
                roundObject = gson.fromJson((JsonArray) round, int[].class);
            } else if (round instanceof Number) {
                roundObject = ((Number) round).intValue();
            }
        }
        if (EmptyUtil.isNotNull(background)) {
            if (background instanceof List) {
                String[] backgrounds = ListUtil.listToArray((List)background, String.class);
                backgroundObject = backgrounds;
            } else if (background instanceof String) {
                String backgroundStr = (String) background;
                if (backgroundStr.trim().contains(".png")) {
                    view.setBackground(AssetUtil.getImageFromAssetsFileD(JsonManager.MainContext, backgroundStr));
                } else {
                    backgroundObject = backgroundStr;
                }
            }
        }

        if (EmptyUtil.isNotNull(background)) {
            if (backgroundObject != null) {
                view.setBackground(ColorLibrary.Portable(backgroundObject, roundObject));
            } else if (JsonManager.isDebug) {
                view.setBackground(new roundRect(null));
            }
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            ScreenSizeUtil.meatureW(width), ScreenSizeUtil.meatureH(height));
        view.setLayoutParams(layoutParams);

        if (reflect != null && !reflect.isOnClick()) {
			JsonLog.d("ViewBean", "isOnClick  false");
            reflect.start();
        }

        return layoutParams;
    }

    public View getView(String json) {
        if (EmptyUtil.isNotNull(json)) {
            if (EmptyUtil.isNull(viewClass)) {
                ViewBean varBean = gson.fromJson(json, ViewBean.class);
                View view = new View(JsonManager.MainContext);
                varBean.setParame(view);
                return view;
            } else {
                try {
                    ViewBean varBean = gson.fromJson(json, ViewBean.class);
                    Class<?> viewClassObj = Class.forName(viewClass);
                    Constructor<?> constructor = viewClassObj.getConstructor(Context.class);
                    View view = (View) constructor.newInstance(JsonManager.MainContext);
                    varBean.setParame(view);
                    return view;
                } catch (Exception e) {
                    throw new LoadJsonException("ReflectLoadView", e);
                }
            }
        }
        throw new LoadJsonException();
    }
}

