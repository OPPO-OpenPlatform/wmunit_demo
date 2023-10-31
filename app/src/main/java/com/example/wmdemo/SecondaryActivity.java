package com.example.wmdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itgsa.opensdk.wm.ActivityMultiWindowAllowance;
import com.itgsa.opensdk.wm.ActivityMultiWindowAllowanceObserver;
import com.itgsa.opensdk.wm.MultiWindowTrigger;
import com.itgsa.opensdk.wm.SplitScreenParams;

public class SecondaryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    MultiWindowTrigger mMultiWindowTrigger;
    Button mRequestFullscreenBtn;
    Button mSplitWithIntentBtn;
    Button mSplitWithSelfBtn;

    ActivityMultiWindowAllowanceObserver mAllowanceObserver = new ActivityMultiWindowAllowanceObserver() {
        @Override
        public void onMultiWindowAllowanceChanged(ActivityMultiWindowAllowance allowance) {
        SecondaryActivity.this.runOnUiThread(() -> updateBtnVisibility(allowance));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        this.mSplitWithSelfBtn = (Button) findViewById(R.id.button2_1);;
        mSplitWithSelfBtn.setOnClickListener(this);
        this.mSplitWithIntentBtn = (Button) findViewById(R.id.button2_2);
        mSplitWithIntentBtn.setOnClickListener(this);
        this.mRequestFullscreenBtn = (Button) findViewById(R.id.button2_3);
        mRequestFullscreenBtn.setOnClickListener(this);

        try {
            MultiWindowTrigger multiWindowTrigger = new MultiWindowTrigger();
            this.mMultiWindowTrigger = multiWindowTrigger;
            if (!multiWindowTrigger.isDeviceSupport(this)) {
                Log.d(TAG, "MultiWindow not supported!");
                this.mMultiWindowTrigger = null;
            }
        } catch (Exception e) {
            this.mMultiWindowTrigger = null;
        }

        MultiWindowTrigger multiWindowTrigger2 = this.mMultiWindowTrigger;
        if (multiWindowTrigger2 != null) {
            multiWindowTrigger2.registerActivityMultiWindowAllowanceObserver(this, this.mAllowanceObserver);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MultiWindowTrigger multiWindowTrigger = this.mMultiWindowTrigger;
        if (multiWindowTrigger != null) {
            multiWindowTrigger.unregisterActivityMultiWindowAllowanceObserver(this, this.mAllowanceObserver);
        }
    }

    @Override
    public void onClick(View view) {
        MultiWindowTrigger multiWindowTrigger = this.mMultiWindowTrigger;
        if (multiWindowTrigger != null) {
            if (this.mSplitWithSelfBtn == view) {
                SplitScreenParams.Builder builder = new SplitScreenParams.Builder();
                builder.setSelfSplit().setLaunchPosition(0);
                this.mMultiWindowTrigger.requestSwitchToSplitScreen(this, builder.build());
            } else if (this.mSplitWithIntentBtn == view) {
                SplitScreenParams.Builder builder2 = new SplitScreenParams.Builder();
                builder2.setLaunchIntent(new Intent(this, ThirdActivity.class)).setLaunchPosition(0);
                this.mMultiWindowTrigger.requestSwitchToSplitScreen(this, builder2.build());
            } else if (this.mRequestFullscreenBtn == view) {
                multiWindowTrigger.requestSwitchToFullScreen(this);
            }
        } else {
            Toast.makeText(this, "MultiWindow ability not supported or " +
                    "MultiWindowTrigger initialization error！", Toast.LENGTH_SHORT).show();
        }
    }

    //根据分屏状态更新按钮可见性
    private void updateBtnVisibility(ActivityMultiWindowAllowance allowance) {
        this.mSplitWithSelfBtn.setVisibility(View.GONE);
        this.mSplitWithIntentBtn.setVisibility(View.GONE);
        this.mRequestFullscreenBtn.setVisibility(View.GONE);
        if (allowance.allowSwitchToFullScreen) {
            this.mRequestFullscreenBtn.setVisibility(View.VISIBLE);
            return;
        }
        if (allowance.allowSelfSplitToSplitScreen) {
            this.mSplitWithSelfBtn.setVisibility(View.VISIBLE);
        }
        if (allowance.allowSwitchToSplitScreen) {
            this.mSplitWithIntentBtn.setVisibility(View.VISIBLE);
        }
    }
}
