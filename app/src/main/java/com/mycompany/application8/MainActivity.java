package com.mycompany.application8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.answer.library.JsonView.JsonActivity;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.JsonViewApi;
import com.answer.library.JsonView.utils.FileUtil;
import org.json.JSONObject;

public class MainActivity extends Activity { 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout ll=new LinearLayout(this);
        ll.setGravity(Gravity.CENTER);
        ll.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        setContentView(ll);

        try {
            JsonViewApi.addOnClick("test", new OnClickListener(){
                    @Override
                    public void onClick(View p1) {
                        Intent intent = new Intent(MainActivity.this, JsonActivity.class);
                        startActivity(intent);
                    }
                });
                

            final JSONObject ButtonJson = new JSONObject();
            ButtonJson.put("type", "Button");
            ButtonJson.put("text", "我是按钮");
            ButtonJson.put("size", "20");
            ButtonJson.put("color", "#000000");
            ButtonJson.put("fun", "test");
                
            ll.addView(JsonManager.createView(ButtonJson.toString()));





        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
