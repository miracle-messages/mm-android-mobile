package com.miraclemessages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;

public class PreCameraActivity extends Activity {

    private ViewFlipper viewFlipper, buttonViewFlipper;
    Button next, back, begin, changeUser;
    TextView vName, vEmail, vPhone, vLocation, homeLabel, bfLabel;
    SharedPreferences sharedpreferences;
    ImageView smallIcon;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";
    Animation animFadeOut, animFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precamera);
        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        buttonViewFlipper = (ViewFlipper) findViewById(R.id.buttonviewflipper);
        next = (Button)findViewById(R.id.next);
        back = (Button)findViewById(R.id.back);
        changeUser = (Button) findViewById(R.id.logout);
        vName = (TextView)findViewById(R.id.volunteer_name);
        vEmail = (TextView)findViewById(R.id.volunteer_email);
        vPhone = (TextView) findViewById(R.id.volunteer_phone);
        vLocation = (TextView) findViewById(R.id.volunteer_location);
        homeLabel = (TextView) findViewById(R.id.home_label);
        bfLabel = (TextView) findViewById(R.id.bf_label);
        begin = (Button) findViewById(R.id.begin);
        smallIcon = (ImageView) findViewById(R.id.small_icon);

        vName.setText(sharedpreferences.getString(Name, null));
        vEmail.setText(sharedpreferences.getString(Email, null));
        vPhone.setText(sharedpreferences.getString(Phone, null));
        vLocation.setText(sharedpreferences.getString(Location, null));
        homeLabel.setText("Welcome " + sharedpreferences.getString(Name, null) + "!");

        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        animFadeOut.setDuration(100);
        animFadeIn.setDuration(600);

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bfLabel.setAnimation(animFadeIn);
                bfLabel.getAnimation().start();
                bfLabel.setVisibility(View.VISIBLE);
                smallIcon.setAnimation(animFadeIn);
                smallIcon.getAnimation().start();
                smallIcon.setVisibility(View.VISIBLE);
                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                viewFlipper.showNext();
                buttonViewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                buttonViewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                buttonViewFlipper.showNext();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(next.getText().equals("Record"))
                    startActivity(new Intent(PreCameraActivity.this, Camera2Activity.class));
                Log.v("Next Click: ", viewFlipper.getDisplayedChild() + " " + (viewFlipper.getChildCount() - 1));
                if (viewFlipper.getDisplayedChild() < viewFlipper.getChildCount() - 1) {
                    viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                    viewFlipper.showNext();
                    if(viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 1)
                        next.setText("Record");
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("Back Click: ", viewFlipper.getDisplayedChild() + " " + 0);
                if (viewFlipper.getDisplayedChild() > 0) {
                    if(viewFlipper.getDisplayedChild() == 1) {
                        bfLabel.setAnimation(animFadeOut);
                        bfLabel.getAnimation().start();
                        bfLabel.setVisibility(View.INVISIBLE);
                        smallIcon.setAnimation(animFadeOut);
                        smallIcon.getAnimation().start();
                        smallIcon.setVisibility(View.GONE);
                    }
                    viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                    viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                    viewFlipper.showPrevious();
                    if(next.getText().equals("Record"))
                        next.setText("Next");
                    if(viewFlipper.getDisplayedChild() == 0) {
                        buttonViewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                        buttonViewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                        buttonViewFlipper.showPrevious();
                    }
                }
            }
        });

        changeUser.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(PreCameraActivity.this, MainActivity.class));
            }
        });
    }
}
