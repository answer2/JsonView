package com.answer.library.JsonView.utils;

import android.util.TypedValue;
import android.content.res.Resources;
import com.answer.library.JsonView.debug.JsonLog;

/**
 * @Author AnswerDev
 * @Date 2023/02/24 22:25
 * @Describe 屏幕大小工具
 */
public class ScreenSizeUtil {

    public static final String TAG = "ScreenSizeUtil";

    /**
     * convert densityPixel to pixel
     */
    public static int dip2px(float dipValue) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().getDisplayMetrics());
        return (int) v;
    }

    /**
     * convert densityPixel to pixel
     */
    public static float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());

    }

    /**
     * convert scalePixel to pixel
     */
    public static float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * convert pixel to densityPixel
     */
    public static float pxToDp(int px) {
        return (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * convert pixel to scaledDensityPixel
     */
    public static float pxToSp(int px) {
        return (px / Resources.getSystem().getDisplayMetrics().scaledDensity);

    }

    /**
     * convert densityPixel to scaledDensityPixel
     */
    public static float dpToSp(float dp) {
        return (int) (dpToPx(dp) / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    /**
     * convert screen Width
     */
    public static int Width() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * convert screen Height
     */
    public static int Height() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int meatureW(String meature) {
        if (meature == null || meature.equals("")) {
            return -2;
        }
        if (meature.equals("match") || meature.equals("match_parent") || meature.equals("-1")) {
            return -1;
        } else if (meature.equals("wrap") || meature.equals("wrap_content") || meature.equals("-2")) {
            return -2;
        } else {
            return meatureWithUnitW(meature);
        }

    }

    public static int meatureH(String meature) {
        if (meature == null || meature.equals("")) {
            return -2;
        }
        if (meature.equals("match") || meature.equals("match_parent") || meature.equals("-1")) {
            return -1;
        } else if (meature.equals("wrap") || meature.equals("wrap_content") || meature.equals("-2")) {
            return -2;
        } else {
            return meatureWithUnitH(meature);
        }

    }

    public static int meatureWithUnitH(String meature) {
        try {
            if (meature == null) {
                return 0;
            }
            if (meature.endsWith("w%")) {
                String substring = meature.substring(0, meature.length() - 2);
                return (Width() / 100) * (Integer.valueOf(substring));
            } else if (meature.endsWith("h%")) {
                String substring = meature.substring(0, meature.length() - 2);
                return (Height() / 100) * (Integer.valueOf(substring));
            } else if (meature.endsWith("%")) {
                String substring = meature.substring(0, meature.length() - 1);
                return (Height() / 100) * (Integer.valueOf(substring));        
            } else if (meature.endsWith("dp")) {
                String substring = meature.substring(0, meature.length() - 2);
                return dip2px(Integer.valueOf(substring));
            } else if (meature.endsWith("dip")) {
                String substring = meature.substring(0, meature.length() - 3);
                return dip2px(Integer.valueOf(substring));
            } else if (meature.endsWith("px")) {
                String substring = meature.substring(0, meature.length() - 2);
                return Integer.valueOf(substring);
            } else {
                return dip2px(Integer.valueOf(meature));
            }
        } catch (Exception e) {
            return -2;
        }
    }

    public static int meatureWithUnitW(String meature) {
        try {
            if (meature == null) {
                return 0;
            }
            if (meature.endsWith("w%")) {
                String substring = meature.substring(0, meature.length() - 2);
                return (Width() / 100) * (Integer.valueOf(substring));
            } else if (meature.endsWith("h%")) {
                String substring = meature.substring(0, meature.length() - 2);
                return (Height() / 100) * (Integer.valueOf(substring));
            } else if (meature.endsWith("%")) {
                String substring = meature.substring(0, meature.length() - 1);
                return (Width() / 100) * (Integer.valueOf(substring));        
            } else if (meature.endsWith("dp")) {
                String substring = meature.substring(0, meature.length() - 2);
                return dip2px(Integer.valueOf(substring));
            } else if (meature.endsWith("dip")) {
                String substring = meature.substring(0, meature.length() - 3);
                return dip2px(Integer.valueOf(substring));
            } else if (meature.endsWith("px")) {
                String substring = meature.substring(0, meature.length() - 2);
                return Integer.valueOf(substring);
            } else {
                return dip2px(Integer.valueOf(meature));
            }
        } catch (Exception e) {
            return -2;
        }
    }

    public static int meatureWithUnitText(String meature) {
        try {
            if (meature == null) {
                return 0;
            }
            if (meature.endsWith("w%")) {
                String substring = meature.substring(0, meature.length() - 2);
                return (Width() / 100) * (Integer.valueOf(substring));
            } else if (meature.endsWith("h%")) {
                String substring = meature.substring(0, meature.length() - 2);
                return (Height() / 100) * (Integer.valueOf(substring));
            } else if (meature.endsWith("dp")) {
                String substring = meature.substring(0, meature.length() - 2);
                return dip2px(Integer.valueOf(substring));
            } else if (meature.endsWith("dip")) {
                String substring = meature.substring(0, meature.length() - 3);
                return dip2px(Integer.valueOf(substring));
            } else if (meature.endsWith("px")) {
                String substring = meature.substring(0, meature.length() - 2);
                return Integer.valueOf(substring);
            } else {
                return Integer.valueOf(meature);
            }
        } catch (Exception e) {
            return 12;
        }
    }


    public static int meatureWithUnit(String meature) {
        try {
            if (meature == null) {
                return 0;
            }
            if (meature.endsWith("w%")) {
                String substring = meature.substring(0, meature.length() - 2);
                return (Width() / 100) * (Integer.valueOf(substring));
            } else if (meature.endsWith("h%")) {
                String substring = meature.substring(0, meature.length() - 2);
                return (Height() / 100) * (Integer.valueOf(substring));
            } else if (meature.endsWith("dp")) {
                String substring = meature.substring(0, meature.length() - 2);
                return dip2px(Integer.valueOf(substring));
            } else if (meature.endsWith("dip")) {
                String substring = meature.substring(0, meature.length() - 3);
                return dip2px(Integer.valueOf(substring));
            } else if (meature.endsWith("px")) {
                String substring = meature.substring(0, meature.length() - 2);
                return Integer.valueOf(substring);
            } else {
                return dip2px(Integer.valueOf(meature));
            }
        } catch (Exception e) {
            return -2;
        }
    }
}
