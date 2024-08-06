package com.answer.library.JsonView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.answer.library.JsonView.bean.ActivityBean;
import com.answer.library.JsonView.debug.JsonLog;
import com.answer.library.JsonView.exceptions.LoadJsonException;
import com.answer.library.JsonView.interfaces.JsonContext;
import com.answer.library.JsonView.manager.GsonSingleton;
import com.answer.library.JsonView.utils.AssetUtil;
import com.answer.library.JsonView.utils.FileUtil;
import com.answer.library.JsonView.utils.StringUtil;
import com.answer.library.color.ColorLibrary;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 @Author AnswerDev
 @Date 2023/02/19 06:42
 @Describe 通过Json来显示Activity页面，部分代码有借鉴Andlua写法
 */

public class JsonActivity extends Activity implements JsonContext {
    private static final Gson gson = GsonSingleton.getInstance();

    public static final String TAG = "JsonActivity";
    private static final String ARG = "arg";
    private static final String DATA = "data";
    private static final String NAME = "name";
    private static final String PATH = "path";
    private String pageName = "main";

    private LinearLayout layout;
    private int mWidth;
    private int mHeight;

    private static final HashMap<String, JsonActivity> sJsonActivityMap = new HashMap<String, JsonActivity>();

    private JsonDexLoader mJsonDexLoader;

    // Dir
    private String localDir;
    private String jsonDir;

    //boolen
    private boolean isCreate;
    private boolean isSetViewed;

    //Error
    private TextView status;
    private ListView list;
    private ArrayAdapter adapter;
    private static ArrayList<String> prjCache = new ArrayList<String>();
    private Handler handler;

    // Toast
    private StringBuilder toastbuilder = new StringBuilder();
    private Toast toast;
    private long lastShow;
    
    private JsonResources mResources;
    
    //setContentView
    private String contentViewValue = null;

    @Override
    public ArrayList<ClassLoader> getClassLoaders() {
        return mJsonDexLoader.getClassLoaders();
    }

    public HashMap<String, String> getLibrarys() {
        return mJsonDexLoader.getLibrarys();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        isSetViewed = true;
    }
    
    @Override
    public AssetManager getAssets() {
        if (mJsonDexLoader != null && mJsonDexLoader.getAssets() != null)
            return mJsonDexLoader.getAssets();
        return super.getAssets();
    }
    
    @Override
    public Resources getResources() {
        if (mJsonDexLoader != null && mJsonDexLoader.getResources() != null)
            return mJsonDexLoader.getResources();
        if (mResources != null)
            return mResources;
        return super.getResources();
    }
    
    public JsonResources getJsonResources() {
        Resources superRes = super.getResources();
        if (mJsonDexLoader != null && mJsonDexLoader.getResources() != null)
            superRes = mJsonDexLoader.getResources();
        mResources = new JsonResources(getAssets(), superRes.getDisplayMetrics(),
                                      superRes.getConfiguration());
        mResources.setSuperResources(superRes);
        return mResources;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set Activity Theme.
        setTheme(android.R.style.Theme_Material_Light);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(null);

        // get width and height.
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mWidth = outMetrics.widthPixels;
        mHeight = outMetrics.heightPixels;

        // initialization layout.
        layout = new LinearLayout(this);
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        status = new TextView(this);
        status.setTextColor(Color.BLACK);
        scroll.addView(status, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        status.setText("");
        status.setTextIsSelectable(true);
        list = new ListView(this);
        list.setFastScrollEnabled(true);
        adapter =
            new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);

                if (convertView == null) view.setTextIsSelectable(true);
                return view;
            }
        };
        list.setAdapter(adapter);
        layout.addView(list, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layout.addView(scroll);
		
        // initialization path.
        localDir = getFilesDir().getAbsolutePath();
        jsonDir = localDir;

        // create handler
        handler = new MainHandler();

        //Load Json Ui
        try {
            status.setText("");
            adapter.clear();
            Intent intent = getIntent();
            Object[] arg = (Object[]) intent.getSerializableExtra(ARG);
            if (arg == null) arg = new Object[0];
            Uri uri = intent.getData();
            String path = null;
            if (uri != null) {
                path = uri.getPath();
                if (!new File(path).exists() && new File(new File(localDir, path).getAbsolutePath()).exists()) {
                    path = new File(localDir, path).getAbsolutePath();
                    File f = new File(path);
                    jsonDir = new File(path).getParent();
                    pageName = f.getName().replace(JsonConstants.FILE_JSON_SUFFIX, "");
                }
            } else {
                pageName = JsonConstants.FILE_MAIN_JSON_NAME.replace(JsonConstants.FILE_JSON_SUFFIX, "");
            }

            //Load Library
            mJsonDexLoader = new JsonDexLoader(this);
            mJsonDexLoader.loadLibs();

            sJsonActivityMap.put(pageName, this);

            
            if (intent.getStringExtra(PATH) != null) {
                contentViewValue = FileUtil.readFile(intent.getStringExtra(PATH));
            } else {
                contentViewValue = AssetUtil.getAsset(this,"main.json");
            }
            
            if(contentViewValue != null){
                ActivityBean activityBean = gson.fromJson(contentViewValue, ActivityBean.class);
                activityBean.Set(this);
            }

            isCreate = true;

            if (!isSetViewed) {
                TypedArray array =
                    getTheme()
                    .obtainStyledAttributes(
                    new int[] {
                        android.R.attr.colorBackground, android.R.attr.textColorPrimary, android.R.attr.textColorHighlightInverse,
                    });
                int backgroundColor = array.getColor(0, 0xFF00FF);
                int textColor = array.getColor(1, 0xFF00FF);
                array.recycle();
                status.setTextColor(textColor);
                layout.setBackgroundColor(backgroundColor);
                setContentView(layout);
            }

        } catch (Throwable e) {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            PrintStream p = new PrintStream(b);
            e.printStackTrace(p);
			
            String s = b.toString();
            //Json错误进行检测
            if (s.contains(", json : {")) {
                int errorlength =Integer.valueOf(StringUtil.intercept(s, "pos ", ", json : {")).intValue();
                s = s.replace(StringUtil.intercept(s, "", "json : "), "").replace("json : ", "");
                String ErrorString = s.substring(0, errorlength);
                String[] ErrorArray = ErrorString.split("\n");
                ErrorString = ErrorArray[ErrorArray.length - 2];
                ErrorString = s.replaceFirst(ErrorString, "§l§h" + ErrorString + "▎§0§r").replace("  ", "");
                ErrorString = "§h§l当前Json错误在: "+(ErrorArray.length-1)+" 行§0§r"+"\n{" + StringUtil.interceptEnd(ErrorString, "{", "}");
                sendMsg(ErrorString);
                setContentView(layout);
                return;
            }


            sendMsg(e.getMessage());
            setContentView(layout);
            return;
        }
    }

    public void newActivity(String path) throws FileNotFoundException {
        newActivity(1, path, null);
    }

    public void newActivity(String path, Object[] arg) throws FileNotFoundException {
        newActivity(1, path, arg);
    }

    public void newActivity(int req, String path) throws FileNotFoundException {
        newActivity(req, path, null);
    }

    public void newActivity(int req, String path, Object[] arg) throws FileNotFoundException {
        newActivity(req, path, arg);
    }

    public void newActivity(String path, int in, int out, boolean newDocument) throws FileNotFoundException {
        newActivity(1, path, in, out, null);
    }

    public void newActivity(String path, int in, int out) throws FileNotFoundException {
        newActivity(1, path, in, out, null);
    }

    public void newActivity(String path, int in, int out, Object[] arg) throws FileNotFoundException {
        newActivity(1, path, in, out, arg);
    }

    public void newActivity(int req, String path, int in, int out) throws FileNotFoundException {
        newActivity(req, path, in, out, null);
    }

    public void newActivity(int req, String path, int in, int out, Object[] arg) throws FileNotFoundException {
        Intent intent = new Intent(this, JsonActivity.class);

        intent.putExtra(NAME, path);
        if (path.charAt(0) != '/') path = jsonDir + "/" + path;

        File f = new File(path);
        if (f.isDirectory() && new File(path + "/" + JsonConstants.FILE_MAIN_JSON_NAME + JsonConstants.FILE_JSON_SUFFIX).exists()) path = path + "/" + JsonConstants.FILE_MAIN_JSON_NAME + JsonConstants.FILE_JSON_SUFFIX;
        else if ((f.isDirectory() || !f.exists()) && !path.endsWith(JsonConstants.FILE_JSON_SUFFIX)) path = path + JsonConstants.FILE_JSON_SUFFIX;
        if (!new File(path).exists()) throw new FileNotFoundException(path);
        intent.setData(Uri.parse("file://" + path));
        intent.putExtra(PATH, path);
        //

        if (arg != null) intent.putExtra(ARG, arg);
        startActivityForResult(intent, req);
        overridePendingTransition(in, out);
    }

    public void finish(boolean finishTask) {
        if (!finishTask) {
            super.finish();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = getIntent();
            if (intent != null && (intent.getFlags() & Intent.FLAG_ACTIVITY_NEW_DOCUMENT) != 0) finishAndRemoveTask();
            else super.finish();
        } else {
            super.finish();
        }
    }

    public void setContentView(String layout) {
        View view = null;
        if (layout != null) view = JsonManager.createView(layout);
        else throw new LoadJsonException("setContentView function error might be json data error .");
        super.setContentView(view);
    }

    public void result(Object[] data) {
        Intent res = new Intent();
        res.putExtra(NAME, getIntent().getStringExtra(NAME));
        res.putExtra(DATA, data);
        setResult(0, res);
        finish();
    }

    // 显示信息
    public void sendMsg(String msg) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(DATA, msg);
        message.setData(bundle);
        message.what = 0;
        handler.sendMessage(message);
        JsonLog.i(TAG, msg);
    }

    // 显示toast
    @SuppressLint("ShowToast")
    public void showToast(String text) {
        long now = System.currentTimeMillis();
        if (toast == null || now - lastShow > 1000) {
            toastbuilder.setLength(0);
            toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
            toastbuilder.append(text);
            toast.show();
        } else {
            toastbuilder.append("\n");
            toastbuilder.append(text);
            toast.setText(toastbuilder.toString());
            toast.setDuration(Toast.LENGTH_LONG);
        }
        lastShow = now;
    }

    public class MainHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    {
                        String data = msg.getData().getString(DATA);
                       // if (JsonManager.isDebug) showToast(data);
                        status.append(data + "\n");
                        adapter.add(ColorLibrary.FontColor(data));
                    }
                    break;
            }
        }
    }
}
