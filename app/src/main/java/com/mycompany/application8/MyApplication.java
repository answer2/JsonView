package com.mycompany.application8;
import android.app.Application;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.utils.FileUtil;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.V8Host;

/**
 * @Author AnswerDev
 * @Date 2023/02/22 22:33
 * @Describe MyApplication
 */
public class MyApplication extends Application {

    public static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        String localDir = getFilesDir().getAbsolutePath();
        // initialization JsonView
        JsonManager.init(this, FileUtil.readFile(localDir + "/definition.json"));


    }

}
