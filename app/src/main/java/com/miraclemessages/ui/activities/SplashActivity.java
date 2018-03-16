package com.miraclemessages.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.miraclemessages.R;
import com.miraclemessages.common.Logger;

import static com.miraclemessages.common.Constants.SPLASH_TIMER_MILLISECONDS;

/**
 * This activity just shows the splash page.
 */
public class SplashActivity extends BaseActivity {

    private static final Class TAG = SplashActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_TIMER_MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    Logger.debug(TAG, "Starting LoginActivity...");
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
