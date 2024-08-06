package com.answer.library.color;

import android.content.Context;
import java.util.ArrayList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Color;
import com.answer.library.JsonView.JsonManager;

public class roundRect extends GradientDrawable {

    private static final String ORIENTATION_TOP_BOTTOM = "TB";
    private static final String ORIENTATION_LEFT_RIGHT = "LR";
    private static final String ORIENTATION_BOTTOM_LEFT_TO_TOP_RIGHT = "RT";
    private static final String ORIENTATION_TOP_LEFT_TO_BOTTOM_RIGHT = "RB";

    public roundRect(Object colors, Object cornerRadii, String orientation, Object gradientType) {
        initialize(colors, cornerRadii, parseOrientation(orientation), parseGradientType(gradientType));
    }

    public roundRect(Object colors, Object cornerRadii, String orientation) {
        initialize(colors, cornerRadii, parseOrientation(orientation), GradientDrawable.LINEAR_GRADIENT);
    }

    public roundRect(Object colors, Object cornerRadii) {
        initialize(colors, cornerRadii, GradientDrawable.Orientation.TOP_BOTTOM, GradientDrawable.LINEAR_GRADIENT);
    }

    public roundRect(Object colors) {
        initialize(colors, null, GradientDrawable.Orientation.TOP_BOTTOM, GradientDrawable.LINEAR_GRADIENT);
    }

    private void initialize(Object colors, Object cornerRadii, GradientDrawable.Orientation orientation, int gradientType) {
        setColorsAndRadii(colors, cornerRadii);
        if (JsonManager.isDebug) {
            setStroke(2, Color.BLACK, 5, 4);
        }
        setShape(GradientDrawable.RECTANGLE);
        setGradientType(gradientType);
        setOrientation(orientation);
    }

    private void setColorsAndRadii(Object colors, Object cornerRadii) {
        if (colors != null) {
            if (colors instanceof int[]) {
                setColors((int[]) colors);
            } else {
                setColor((int) colors);
            }
        }

        if (cornerRadii != null) {
            if (cornerRadii instanceof int[]) {
                setCornerRadii(convertIntArrayToFloatArray((int[]) cornerRadii));
            } else {
                setCornerRadius(Float.parseFloat(cornerRadii.toString()));
            }
        }
    }

    private GradientDrawable.Orientation parseOrientation(String orientation) {
        switch (orientation != null ? orientation : ORIENTATION_TOP_BOTTOM) {
            case ORIENTATION_LEFT_RIGHT:
                return GradientDrawable.Orientation.LEFT_RIGHT;
            case ORIENTATION_BOTTOM_LEFT_TO_TOP_RIGHT:
                return GradientDrawable.Orientation.BL_TR;
            case ORIENTATION_TOP_LEFT_TO_BOTTOM_RIGHT:
                return GradientDrawable.Orientation.TL_BR;
            case ORIENTATION_TOP_BOTTOM:
            default:
                return GradientDrawable.Orientation.TOP_BOTTOM;
        }
    }

    private int parseGradientType(Object gradientType) {
        if (gradientType instanceof Integer) {
            switch ((int) gradientType) {
                case 1:
                    return GradientDrawable.RADIAL_GRADIENT;
                case 2:
                    return GradientDrawable.SWEEP_GRADIENT;
                case 0:
                default:
                    return GradientDrawable.LINEAR_GRADIENT;
            }
        }
        return GradientDrawable.LINEAR_GRADIENT;
    }

    private float[] convertIntArrayToFloatArray(int[] intArray) {
        float[] floatArray = new float[intArray.length * 2];
        for (int i = 0; i < intArray.length; i++) {
            floatArray[2 * i] = floatArray[2 * i + 1] = intArray[i];
        }
        return floatArray;
    }
}
