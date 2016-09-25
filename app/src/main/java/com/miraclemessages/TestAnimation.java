package com.miraclemessages;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class TestAnimation extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animation);
        ImageView i = (ImageView) findViewById(R.id.imageview_is_recording_filling);
        i.setBackgroundResource(R.drawable.is_recording_filling);
        final AnimationDrawable a =
                (AnimationDrawable) i.getBackground();
        i.post(new Runnable() {
            @Override
            public void run() {
                a.start();
            }
        });
    }
}
