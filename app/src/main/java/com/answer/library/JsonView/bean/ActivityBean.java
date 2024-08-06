package com.answer.library.JsonView.bean;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.exceptions.LoadJsonException;
import com.answer.library.JsonView.manager.VarManager;
import com.answer.library.JsonView.utils.ActivityUtils;
import com.answer.library.JsonView.utils.AssetUtil;
import com.answer.library.color.ColorLibrary;
import com.answer.library.color.roundRect;
import com.answer.library.JsonView.manager.GsonSingleton;
import android.widget.*;

/**
 * @Author AnswerDev
 * @Date 2023/02/22 22:45
 * @Describe 用于Activity的Bean
 */
public class ActivityBean {

    public static final String TAG = "ActivityBean";

    private String title;
    private String subtitle;
    private boolean showToolbar = true;
    private boolean statusBar = true;
    private boolean fullScreen = false;
    private boolean backIcon = false;
    private String varThis;
    private String toolbarColor;
    private JsonElement background;
    private JsonElement round;
    private JsonElement contentView;

    private static final Gson gson = GsonSingleton.getInstance();

    public void setBackground(JsonElement background) {
        this.background = background;
    }

    public JsonElement getBackground() {
        return background;
    }

    public void setRound(JsonElement round) {
        this.round = round;
    }

    public JsonElement getRound() {
        return round;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setContentView(JsonElement contentView) {
        this.contentView = contentView;
    }

    public JsonElement getContentView() {
        return contentView;
    }

    public void setBackIcon(boolean backIcon) {
        this.backIcon = backIcon;
    }

    public boolean isBackIcon() {
        return backIcon;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setToolbarColor(String toolbarColor) {
        this.toolbarColor = toolbarColor;
    }

    public String getToolbarColor() {
        return toolbarColor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setShowToolbar(boolean showToolbar) {
        this.showToolbar = showToolbar;
    }

    public boolean isShowToolbar() {
        return showToolbar;
    }

    public void setStatusBar(boolean statusBar) {
        this.statusBar = statusBar;
    }

    public boolean isStatusBar() {
        return statusBar;
    }

    public void setVarThis(String varThis) {
        this.varThis = varThis;
    }

    public String getVarThis() {
        return varThis;
    }

    public void Set(Activity activity) {
        ActionBar actionBar = activity.getActionBar();

        if (getTitle() != null) activity.setTitle(getTitle());
        if (getSubtitle() != null) actionBar.setSubtitle(getSubtitle());
        if (!isShowToolbar()) activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getToolbarColor() != null) ActivityUtils.setStatusBarColor(activity, Color.parseColor(getToolbarColor()));
        if (isFullScreen()) ActivityUtils.FullScreen(activity);
        if (isStatusBar()) ActivityUtils.ImmersionStatusBar(activity);
        if (isBackIcon()) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getVarThis() != null) VarManager.add(getVarThis(), activity);

        // Set toolbar background
        Object background = null;
        Object round = 0;

        // Handle round object
        if (getRound() != null) {
            JsonElement roundElement = getRound();
            if (roundElement.isJsonArray()) {
                int[] roundArray = gson.fromJson(roundElement, int[].class);
                round = roundArray;
            } else if (roundElement.isJsonPrimitive()) {
                round = roundElement.getAsInt();
            }
        }

        // Handle background object
        if (getBackground() != null) {
            JsonElement backgroundElement = getBackground();
            if (backgroundElement.isJsonArray()) {
                String[] backgroundArray = gson.fromJson(backgroundElement, String[].class);
                background = backgroundArray;
            } else if (backgroundElement.isJsonPrimitive()) {
                String backgroundString = backgroundElement.getAsString();
                if (backgroundString.trim().contains(".png")) {
                    actionBar.setBackgroundDrawable(AssetUtil.getImageFromAssetsFileD(activity, backgroundString));
                } else {
                    background = backgroundString;
                }
            }
        }

        if (background != null && !background.toString().equals("null")) {
            actionBar.setBackgroundDrawable(ColorLibrary.Portable(background, round));
        } else if (background == null && JsonManager.isDebug) {
            actionBar.setBackgroundDrawable(new roundRect(null));
        }

        // Set content view
        if (getContentView() != null) {
            String json = getContentView().toString();
            setContentView(activity, json);
        }
    }

    public void setContentView(Activity activity, String layout) {
        View view = null;
        if (layout != null) {
            view = JsonManager.createView(layout);
        } else {
            throw new LoadJsonException("setContentView function error might be json data error.");
        }
        activity.setContentView(view);
    }
}

