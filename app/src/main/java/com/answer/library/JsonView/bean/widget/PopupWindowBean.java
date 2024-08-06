package com.answer.library.JsonView.bean.widget;
import android.view.Gravity;
import com.answer.library.JsonView.enums.GravityType;
import android.widget.PopupWindow;
import com.answer.library.JsonView.manager.VarManager;
import com.answer.library.JsonView.utils.EmptyUtil;
import com.answer.library.JsonView.JsonManager;
import android.view.Window;
import android.view.View;
import android.content.Context;
import com.answer.library.JsonView.manager.ViewIdManager;
import com.answer.library.JsonView.utils.ScreenSizeUtil;
import com.answer.library.JsonView.enums.AnimationStyleType;

/**
 * @Author AnswerDev
 * @Date 2023/03/19 10:01
 * @Describe PopupWindow
 */
public class PopupWindowBean {

    public static final String TAG = "PopupWindow";

    private String id;

    private String contentView;

    private String binding;

    private String width = "-2";

    private String height = "-2";

    private String gravity;

    private boolean focusable;

    private boolean touchable;

    private boolean clippingEnabled;

    private String offSetX;

    private String offSetY;

    private String onDismiss;
    
    private String animationStyle;

    public void setAnimationStyle(String animationStyle) {
        this.animationStyle = animationStyle;
    }

    public String getAnimationStyle() {
        return animationStyle;
    }

    public void setClippingEnabled(boolean clippingEnabled) {
        this.clippingEnabled = clippingEnabled;
    }

    public boolean isClippingEnabled() {
        return clippingEnabled;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getBinding() {
        return binding;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContentView(String contentView) {
        this.contentView = contentView;
    }

    public String getContentView() {
        return contentView;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getGravity() {
        return gravity;
    }

    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    public boolean isFocusable() {
        return focusable;
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    public boolean isTouchable() {
        return touchable;
    }

    public void setOffSetX(String offSetX) {
        this.offSetX = offSetX;
    }

    public String getOffSetX() {
        return offSetX;
    }

    public void setOffSetY(String offSetY) {
        this.offSetY = offSetY;
    }

    public String getOffSetY() {
        return offSetY;
    }

    public void setOnDismiss(String onDismiss) {
        this.onDismiss = onDismiss;
    }

    public String getOnDismiss() {
        return onDismiss;
    }

    public void setParame(PopupWindow pop) {

        if (EmptyUtil.isNotNull(getContentView()))pop.setContentView(ViewIdManager.get(getContentView()));

        if (EmptyUtil.isNotNull(getWidth())) pop.setWidth(ScreenSizeUtil.meatureWithUnitW(getWidth()));
        if (EmptyUtil.isNotNull(getHeight())) pop.setHeight(ScreenSizeUtil.meatureWithUnitH(getHeight()));

        if (EmptyUtil.isNotNull(isFocusable()))setFocusable(isFocusable());
        if (EmptyUtil.isNotNull(isTouchable()))setTouchable(isFocusable());
        if (EmptyUtil.isNotNull(isClippingEnabled()))setClippingEnabled(isClippingEnabled());

        if(EmptyUtil.isNotNull(getAnimationStyle()))pop.setAnimationStyle(AnimationStyleType.valueOf(getAnimationStyle()).getValue());
        
        int gravity_ = Gravity.NO_GRAVITY;
        int offSetX_ =0;
        int offSetY_ =0;
        View binding_ = EmptyUtil.isNotNull(getBinding()) ? 
            (View)(VarManager.get(getBinding())):
            ((Window)(JsonManager.MainContext.getSystemService(Context.WINDOW_SERVICE))).getDecorView();
        if (EmptyUtil.isNotNull(getOffSetX())) offSetX_ = ScreenSizeUtil.meatureWithUnitW(getOffSetX());
        if (EmptyUtil.isNotNull(getOffSetY())) offSetY_ = ScreenSizeUtil.meatureWithUnitH(getOffSetY());


        if (getGravity().contains("|")) {
            String[] gravityList = getGravity().split("\\|");
            for (int a = 0; gravityList.length > a; a++) {
                GravityType gravityType = GravityType.valueOf(gravityList[a]);
                gravity_ = gravity_ | gravityType.getValue();
            }
        } else {
            gravity_ = GravityType.valueOf(getGravity()).getValue();
        }
        
        //show
        pop.showAtLocation(binding_,gravity_,offSetX_,offSetY_);


    }

}
