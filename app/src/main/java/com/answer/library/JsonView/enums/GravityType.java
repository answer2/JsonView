package com.answer.library.JsonView.enums;
import android.view.Gravity;

public enum GravityType {
    
    center(Gravity.CENTER),
    left(Gravity.LEFT),
    right(Gravity.RIGHT),
    top(Gravity.TOP),
    bottom(Gravity.BOTTOM);
    
    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    GravityType(int vlaue) {
        this.value=vlaue;
    }
}
