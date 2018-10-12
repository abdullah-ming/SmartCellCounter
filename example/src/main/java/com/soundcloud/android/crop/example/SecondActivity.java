package com.soundcloud.android.crop.example;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SecondActivity extends Activity {
    private Bitmap bitmap=null;

    private String Text1 = null;
    private String Text2 = "";
    private String Text3 = "";
    private ImageView resultView;
    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private Button button1;
    private static Toast mToast = null;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Object synObject = new Object();
    public static void showToastByThread(Context context, String msg){
        showToastByThread(context, msg, Toast.LENGTH_SHORT);
    }
    public static void showToastByThread(final Context context, final CharSequence msg, final int length){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObject) {
                            if (mToast != null){
                                mToast.setText(msg);
                                mToast.setDuration(length);
                            }else{
                                mToast = Toast.makeText(context, msg, length);
                            }
                            mToast.show();
                        }
                    }
                });
            }
        }).start();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ActionBar actionBar=getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        button1 = (Button) findViewById(R.id.secondButton);
        editText = (EditText) findViewById(R.id.totalCellValue);
        editText2 = (EditText) findViewById(R.id.liveCellValue);
        editText3 = (EditText) findViewById(R.id.deadCellValue);
        editText.setSaveEnabled(false);
        editText2.setSaveEnabled(false);
        editText3.setSaveEnabled(false);
        resultView = (ImageView) findViewById(R.id.result2_image);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        bitmap = (Bitmap)extras.get("extra_bitmap");
        resultView.setImageBitmap(bitmap);
        Text1 = intent.getStringExtra("totalCellData");
        Text2 = intent.getStringExtra("liveCellData");
        Text3 = intent.getStringExtra("deadCellData");
//        Toast.makeText(SecondActivity.this, Text1, Toast.LENGTH_SHORT).show();
//        if(Text1 != null)
        editText.setText(Text1);
        editText2.setText(Text2);
        editText3.setText(Text3);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                SecondActivity.this.finish();
            }
        });

    }

}
