package com.answer.library.JsonView.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 * @Author AnswerDev
 * @Date 2023/02/22 22:58
 * @Describe 处理Activity的类
 */

public class ActivityUtils {

    public static final String TAG = "ActivityUtils";

    //-function 窗口全屏()
    public static void FullScreen(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //-function 取消全屏()
    public static void CancelFullScreen(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    //-function 状态栏颜色(a)
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(color);
        }
    }

    //-function 沉浸状态栏()
    public static void ImmersionStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void setStatusBarIconColor(Activity activity, Boolean blackIcon) {
        int option = 0;
        if (blackIcon) {
            option = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //白底黑字
        } else {
            option = View.SYSTEM_UI_FLAG_VISIBLE;     //默认，黑底白字
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(option);
    }

    public static boolean getIsNiNight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            int uiMode = activity.getResources().getConfiguration().uiMode;
            if ((uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
                return true;
            } else {
                return false;
            }
        }
        return false;
	}

}
