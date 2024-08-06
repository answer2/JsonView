package com.answer.library.JsonView.bean.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.manager.GsonSingleton;
import com.answer.library.JsonView.utils.AssetUtil;

public class ImageViewBean extends ViewBean {
    private static final Gson gson = GsonSingleton.getInstance();
    
    private String image;
    private String scaleType;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setScaleType(String scaleType) {
        this.scaleType = scaleType;
    }

    public String getScaleType() {
        return scaleType;
    }

    @Override
    public ViewGroup.LayoutParams setParame(View view) {
        ImageView view_initial = (ImageView) view;
        if(getImage()!=null)view_initial.setImageBitmap(AssetUtil.getImageFromAssetsFile(JsonManager.MainContext,getImage()));
        if(getScaleType()!=null)view_initial.setScaleType(ImageView.ScaleType.valueOf(getScaleType()));
        return super.setParame(view);
    }
    
    public static ImageView getView(String json) {
        if (!json.isEmpty()) {
            ImageViewBean varBean = gson.fromJson(json.toString(),ImageViewBean.class);
            ImageView imageView = new ImageView(JsonManager.MainContext);
            varBean.setParame(imageView);
            return imageView;
        }
        throw new NullPointerException();
    }
    
}
