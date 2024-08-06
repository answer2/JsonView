package com.answer.library.JsonView.utils;

/**
 * @Author AnswerDev
 * @Date 2023/03/04 22:29
 * @Describe Asset工具
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.answer.library.JsonView.debug.JsonLog;

public class AssetUtil {
    
    public static final String TAG = "AssetUtil";
    
    public static Bitmap getImageFromAssetsFile(Context MainContext,String fileName) {
        Bitmap bitmap = null;
        try {
            InputStream is = MainContext.getAssets().open(fileName);
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
        } catch (IOException e) {
            JsonLog.e(TAG, "getImageFromAssetsFile: Could not get assets " + e.getMessage());
        }
        return bitmap;
    }

    public static Drawable getImageFromAssetsFileD(Context MainContext,String fileName) {
        return new BitmapDrawable(getImageFromAssetsFile(MainContext,fileName));
    }

    public static InputStream getAssetPath(Context MainContext,String FileName) {
        if (MainContext != null) return MainContext.getClass().getResourceAsStream("/assets/" + FileName);
        return null;
    }
    
    public static String getAsset(Context context, String FileName) throws IOException {
        if (context != null) {
            InputStream abpath = context.getClass().getResourceAsStream("/assets/" + FileName);
            String path = new String(InputStreamToByte(abpath));
            return path;
        }
        return null;
    }

    private static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }
    
}
