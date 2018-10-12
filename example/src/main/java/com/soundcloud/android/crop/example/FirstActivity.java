package com.soundcloud.android.crop.example;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class FirstActivity extends Activity {
    private Button button1;
    private Button button2;
    private List<Activity> activityList = new LinkedList();
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first);
        ActionBar actionBar=getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);


        ExitApplication.getInstance().addActivity(this);
        button1 = (Button) findViewById(R.id.firstbutton_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(FirstActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        button2 = (Button) findViewById(R.id.firstbutton_3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitApplication.getInstance().exit();
            }
        });

    }


}
