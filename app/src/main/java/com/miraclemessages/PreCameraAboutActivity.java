package com.miraclemessages;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class PreCameraAboutActivity extends Activity {

    TextView back, next, navtitle;
    TextView one_name_label, one_birth_label, one_live_label, one_hometown_label, one_years_label, one_reach_label;
    TextView two_name_label, two_relationship_label, two_birth_label, two_location_label, two_years_label, two_other_label;
    EditText one_name, one_birth, one_live, one_hometown, one_years, one_reach;
    EditText two_name, two_relationship, two_birth, two_location, two_years, two_other;
    ViewFlipper about_vf;
    SharedPreferences sharedpreferences;
    public static final String myPreferences = "MyPreferences";

    Animation animFadeOut, animFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_camera_about);

        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(about_vf.getDisplayedChild() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PreCameraAboutActivity.this);
                    builder.setMessage("All changes on this page will be lost, would you still like to continue?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(PreCameraAboutActivity.this, PreCameraActivity.class));
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
                else {
                    navtitle.setText("About Participant");
                    about_vf.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                    about_vf.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                    about_vf.showPrevious();
                }
            }
        });
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(about_vf.getDisplayedChild() == 1) {
                    if(two_name.getText().toString().equals("") || two_relationship.getText().toString().equals("") || two_birth.getText().toString().equals("") || two_location.getText().toString().equals("") || two_years.getText().toString().equals("") || two_other.getText().toString().equals("")) {
                        Toast.makeText(PreCameraAboutActivity.this,
                                "Please fill out all fields.",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString("about_one_name", one_name.getText().toString());
                        editor.putString("about_one_birth", one_birth.getText().toString());
                        editor.putString("about_one_live", one_live.getText().toString());
                        editor.putString("about_one_hometown", one_hometown.getText().toString());
                        editor.putString("about_one_years", one_years.getText().toString());
                        editor.putString("about_one_reach", one_reach.getText().toString());

                        editor.putString("about_two_name", two_name.getText().toString());
                        editor.putString("about_two_relationship", two_relationship.getText().toString());
                        editor.putString("about_two_birth", two_birth.getText().toString());
                        editor.putString("about_two_location", two_location.getText().toString());
                        editor.putString("about_two_years", two_years.getText().toString());
                        editor.putString("about_two_other", two_other.getText().toString());

                        editor.commit();

                        startActivity(new Intent(PreCameraAboutActivity.this, Camera2Activity.class));
                        finish();
                    }
                }
                else {
                    if(one_name.getText().toString().equals("") || one_birth.getText().toString().equals("") || one_live.getText().toString().equals("") || one_hometown.getText().toString().equals("") || one_reach.getText().toString().equals("") || one_years.getText().toString().equals("")) {
                        Toast.makeText(PreCameraAboutActivity.this,
                                "Please fill out all fields.",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        navtitle.setText("About Your Loved One");
                        about_vf.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                        about_vf.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                        about_vf.showNext();
                    }
                }
            }
        });
        navtitle = (TextView) findViewById(R.id.navtitle);

        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        animFadeOut.setDuration(800);
        animFadeIn.setDuration(800);

        one_name_label = (TextView) findViewById(R.id.about_one_name_label);
        one_birth_label = (TextView) findViewById(R.id.about_one_birth_label);
        one_live_label = (TextView) findViewById(R.id.about_one_live_label);
        one_hometown_label = (TextView) findViewById(R.id.about_one_hometown_label);
        one_years_label = (TextView) findViewById(R.id.about_one_years_label);
        one_reach_label = (TextView) findViewById(R.id.about_one_reach_label);

        one_name = (EditText) findViewById(R.id.about_one_name);
        one_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.v("PRECAMERA", "Charsequence: " + s);
//                Log.v("PRECAMERA", "Start: " + start);
//                Log.v("PRECAMERA", "BEFORE: " + before);
//                Log.v("PRECAMERA", "Count: " + count);
//                Toast.makeText(PreCameraAboutActivity.this,
//                        "Charsequence: " + s + "\nStart: " + start + "\n Before: " + before + "\nCount" + count,
//                        Toast.LENGTH_LONG).show();
                if(one_name.getText().toString().equals("")) {
                    one_name_label.setAnimation(animFadeOut);
                    one_name_label.getAnimation().start();
                    one_name_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        one_name_label.setAnimation(animFadeIn);
                        one_name_label.getAnimation().start();
                        one_name_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        one_birth = (EditText) findViewById(R.id.about_one_birth);
        one_birth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(one_birth.getText().toString().equals("")) {
                    one_birth_label.setAnimation(animFadeOut);
                    one_birth_label.getAnimation().start();
                    one_birth_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        one_birth_label.setAnimation(animFadeIn);
                        one_birth_label.getAnimation().start();
                        one_birth_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        one_live = (EditText) findViewById(R.id.about_one_live);
        one_live.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(one_live.getText().toString().equals("")) {
                    one_live_label.setAnimation(animFadeOut);
                    one_live_label.getAnimation().start();
                    one_live_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        one_live_label.setAnimation(animFadeIn);
                        one_live_label.getAnimation().start();
                        one_live_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        one_hometown = (EditText) findViewById(R.id.about_one_hometown);
        one_hometown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(one_hometown.getText().toString().equals("")) {
                    one_hometown_label.setAnimation(animFadeOut);
                    one_hometown_label.getAnimation().start();
                    one_hometown_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        one_hometown_label.setAnimation(animFadeIn);
                        one_hometown_label.getAnimation().start();
                        one_hometown_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        one_years = (EditText) findViewById(R.id.about_one_years);
        one_years.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(one_years.getText().toString().equals("")) {
                    one_years_label.setAnimation(animFadeOut);
                    one_years_label.getAnimation().start();
                    one_years_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        one_years_label.setAnimation(animFadeIn);
                        one_years_label.getAnimation().start();
                        one_years_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        one_reach = (EditText) findViewById(R.id.about_one_reach);
        one_reach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(one_reach.getText().toString().equals("")) {
                    one_reach_label.setAnimation(animFadeOut);
                    one_reach_label.getAnimation().start();
                    one_reach_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        one_reach_label.setAnimation(animFadeIn);
                        one_reach_label.getAnimation().start();
                        one_reach_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        two_name_label = (TextView) findViewById(R.id.about_two_name_label);
        two_relationship_label = (TextView) findViewById(R.id.about_two_relationship_label);
        two_birth_label = (TextView) findViewById(R.id.about_two_birth_label);
        two_location_label = (TextView) findViewById(R.id.about_two_location_label);
        two_years_label = (TextView) findViewById(R.id.about_two_years_label);
        two_other_label = (TextView) findViewById(R.id.about_two_other_label);

        two_name = (EditText) findViewById(R.id.about_two_name);
        two_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(two_name.getText().toString().equals("")) {
                    two_name_label.setAnimation(animFadeOut);
                    two_name_label.getAnimation().start();
                    two_name_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        two_name_label.setAnimation(animFadeIn);
                        two_name_label.getAnimation().start();
                        two_name_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        two_relationship = (EditText) findViewById(R.id.about_two_relationship);
        two_relationship.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(two_relationship.getText().toString().equals("")) {
                    two_relationship_label.setAnimation(animFadeOut);
                    two_relationship_label.getAnimation().start();
                    two_relationship_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        two_relationship_label.setAnimation(animFadeIn);
                        two_relationship_label.getAnimation().start();
                        two_relationship_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        two_birth = (EditText) findViewById(R.id.about_two_birth);
        two_birth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(two_birth.getText().toString().equals("")) {
                    two_birth_label.setAnimation(animFadeOut);
                    two_birth_label.getAnimation().start();
                    two_birth_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        two_birth_label.setAnimation(animFadeIn);
                        two_birth_label.getAnimation().start();
                        two_birth_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        two_location = (EditText) findViewById(R.id.about_two_location);
        two_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(two_location.getText().toString().equals("")) {
                    two_location_label.setAnimation(animFadeOut);
                    two_location_label.getAnimation().start();
                    two_location_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        two_location_label.setAnimation(animFadeIn);
                        two_location_label.getAnimation().start();
                        two_location_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        two_years = (EditText) findViewById(R.id.about_two_years);
        two_years.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(two_years.getText().toString().equals("")) {
                    two_years_label.setAnimation(animFadeOut);
                    two_years_label.getAnimation().start();
                    two_years_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        two_years_label.setAnimation(animFadeIn);
                        two_years_label.getAnimation().start();
                        two_years_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        two_other = (EditText) findViewById(R.id.about_two_other);
        two_other.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(two_other.getText().toString().equals("")) {
                    two_other_label.setAnimation(animFadeOut);
                    two_other_label.getAnimation().start();
                    two_other_label.setVisibility(View.INVISIBLE);
                }
                else {
                    if(start == 0) {
                        two_other_label.setAnimation(animFadeIn);
                        two_other_label.getAnimation().start();
                        two_other_label.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        about_vf = (ViewFlipper) findViewById(R.id.precamera_about_vf);

    }

}
