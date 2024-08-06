package com.answer.library.JsonView.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 @Author AnswerDev
 @Date 2023/02/19 08:42
 @Describe 有关于系统工具
 */
 
public class SystemUtil {

    public static final String TAG = "SystemUtil";

    public static boolean isDack(Context context) {
        final boolean active = (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_YES) != 0;
        return active;
    }
}
