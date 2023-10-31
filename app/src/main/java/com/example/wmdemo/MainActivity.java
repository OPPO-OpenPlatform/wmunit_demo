package com.example.wmdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itgsa.opensdk.wm.MultiWindowTrigger;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        //点击按钮查询SDK坂本
        Button button1_1 = (Button) findViewById(R.id.button1_1);
        button1_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        MultiWindowTrigger multiWindowTrigger = new MultiWindowTrigger();
                        Toast.makeText(getApplicationContext(), "WMUnit version: "  + multiWindowTrigger.getVersion(), Toast.LENGTH_SHORT).show();
                    } catch (NoClassDefFoundError e) {//catch NoClassDefFoundError，避免ColorOS13以前的版本出现稳定性异常
                        Toast.makeText(getApplicationContext(), "WMUnit not supported!" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );

        //点击按钮查询是否支持WMUnit能力
        Button button1_2 = (Button) findViewById(R.id.button1_2);
        button1_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     try {
                         MultiWindowTrigger multiWindowTrigger = new MultiWindowTrigger();
                         Toast.makeText(getApplicationContext(), "WMUnit support: "  + multiWindowTrigger.isDeviceSupport(getApplicationContext()), Toast.LENGTH_SHORT).show();
                     } catch (NoClassDefFoundError e) {
                         Toast.makeText(getApplicationContext(), "WMUnit not supported!" , Toast.LENGTH_SHORT).show();
                     }
                }
            }
        );

        //MainActivity点击按钮跳转到SecondActivity
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  try {
                      MultiWindowTrigger multiWindowTrigger = new MultiWindowTrigger();
                      Intent intentToSecondActivity = new Intent(MainActivity.this, SecondaryActivity.class);
                      startActivity(intentToSecondActivity);
                  } catch (NoClassDefFoundError e) {
                      Toast.makeText(getApplicationContext(), "WMUnit not supported!" , Toast.LENGTH_SHORT).show();
                  }
              }
          }
        );
    }
}
