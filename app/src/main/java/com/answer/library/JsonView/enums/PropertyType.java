package com.answer.library.JsonView.enums;

/**
 * @Author AnswerDev
 * @Date 2023/02/25 21:12
 * @Describe ObjectAnimator的动画类型
 */
public enum PropertyType {
    /*水平平移(左右)*/
    translationX("translationX"),
    /*垂直平移(上下)*/
    translationY("translationY"),
    /*缩放(左右)*/
    scaleX("scaleX"),
    /*缩放(上下)*/
    scaleY("scaleY"),
    /*旋转*/
    rotation("rotation"),
    /*旋转X*/
    rotationX("rotationX"),
    /*旋转Y*/
    rotationY("rotationY"),
    /*透明度*/
    alpha("alpha")
    ;

    private String property;

    public void setProperty(String property){
        this.property = property;
    }

    public String getProperty(){
        return property;
    }

    
    PropertyType(String property) {
        this.property=property;
    }

}
