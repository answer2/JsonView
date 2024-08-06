package com.answer.library.JsonView.enums;

/**
 * @Author AnswerDev
 * @Date 2023/03/19 10:14
 * @Describe AnimationStyle
 */
public enum AnimationStyleType {
    animation(android.R.style.Animation),
    dialog(android.R.style.Animation_Dialog),
    input(android.R.style.Animation_InputMethod),
    toast(android.R.style.Animation_Toast),
    translucent(android.R.style.Animation_Translucent),
    activity(android.R.style.Animation_Activity);
    
    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    AnimationStyleType(int vlaue) {
        this.value=vlaue;
    }
    
    
}
