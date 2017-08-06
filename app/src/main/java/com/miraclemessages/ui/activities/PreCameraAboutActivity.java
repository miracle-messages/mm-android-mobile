package com.miraclemessages.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.miraclemessages.R;

import java.util.ArrayList;

public class PreCameraAboutActivity extends Activity {

    ImageView back;
    Button next;
    TextView one_name_label, one_birth_label, one_live_label, one_hometown_label, one_years_label, one_reach_label;
    TextView two_name_label, two_relationship_label, two_birth_label, two_location_label, two_years_label, two_other_label;
    TextView review_one_name, review_one_birth, review_one_location, review_one_hometown, review_one_years, review_one_contact;
    TextView review_two_name, review_two_relationship, review_two_birth, review_two_location, review_two_years, review_two_other;
    TextView direction, who;
    EditText one_name, one_birth, one_live, one_hometown, one_years, one_reach;
    EditText two_name, two_relationship, two_birth, two_location, two_years, two_other;
    ImageView edit_from, edit_to;
    ViewFlipper about_vf;
    SharedPreferences sharedpreferences;
    RelativeLayout nav_bar;
    PopupWindow popupWindow;
    LinearLayout pcBack;

    public static final String myPreferences = "MyPreferences";
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final String[] VIDEO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final String FileLoc = "file";

    Animation animFadeOut, animFadeIn;

    RelativeLayout signatureLayout;
    Paint paint;
    View view;
    Path path2;
    Bitmap bitmap;
    Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_camera_about);

        signatureLayout = (RelativeLayout) findViewById(R.id.signatureLayout);

        view = new SketchSheetView(PreCameraAboutActivity.this);
        paint = new Paint();
        path2 = new Path();

        pcBack = (LinearLayout) findViewById(R.id.precamera_about);

        signatureLayout.addView(view, new ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        paint.setDither(true);
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(10);

        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        nav_bar = (RelativeLayout) findViewById(R.id.navbar);

        if(this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            nav_bar.setVisibility(View.GONE);
        }

        direction = (TextView) findViewById(R.id.direction);
        who = (TextView) findViewById(R.id.who);
        who.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(who.getText().toString().equals("Consent and Release Form")) {
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View consentView = inflater.inflate(R.layout.consent, null);
                    popupWindow = new PopupWindow(
                            consentView,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    popupWindow.setTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(false);
                    // Set an elevation value for popup window
                    // Call requires API level 21
                    if (Build.VERSION.SDK_INT >= 21) {
                        popupWindow.setElevation(5.0f);
                    }
                    ImageButton closeButton = (ImageButton) consentView.findViewById(R.id.ib_close);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Dismiss the popup window
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAtLocation(pcBack, Gravity.CENTER, 0, 0);
                }
            }
        });

        edit_from = (ImageView) findViewById(R.id.edit_from);
        edit_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about_vf.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                about_vf.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                direction.setText("From");
                who.setText("Homeless individual");
                next.setText("Next");
                about_vf.setDisplayedChild(0);
            }
        });
        edit_to = (ImageView) findViewById(R.id.edit_to);
        edit_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about_vf.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                about_vf.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                direction.setText("To");
                who.setText("Loved one");
                next.setText("Next");
                about_vf.setDisplayedChild(1);
            }
        });

        review_one_name = (TextView) findViewById(R.id.review_one_name);
        review_one_birth = (TextView) findViewById(R.id.review_one_birth);
        review_one_location = (TextView) findViewById(R.id.review_one_location);
        review_one_hometown = (TextView) findViewById(R.id.review_one_hometown);
        review_one_years = (TextView) findViewById(R.id.review_one_years);
        review_one_contact = (TextView) findViewById(R.id.review_one_contact);
        review_two_name = (TextView) findViewById(R.id.review_two_name);
        review_two_relationship = (TextView) findViewById(R.id.review_two_relationship);
        review_two_birth = (TextView) findViewById(R.id.review_two_birth);
        review_two_location = (TextView) findViewById(R.id.review_two_location);
        review_two_years = (TextView) findViewById(R.id.review_two_years);
        review_two_other = (TextView) findViewById(R.id.review_two_other);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(about_vf.getDisplayedChild() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PreCameraAboutActivity.this);
                    builder.setMessage("All changes on this page will be lost, would you still like to go back to the home page?")
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
                    about_vf.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                    about_vf.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                    if(about_vf.getDisplayedChild() == 1) {
                        direction.setText("From");
                        who.setText("Homeless individual");
                    }
                    else if(about_vf.getDisplayedChild() == 2){
                        direction.setText("To");
                        who.setText("Loved one");
                    }
                    else {
                        direction.setText("Review");
                        who.setText("Contact info");
                        who.setTextColor(Color.parseColor("#000000"));
                        next.setText("Next");
                    }
                    about_vf.showPrevious();
                }
            }
        });
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(about_vf.getDisplayedChild() == 2) {
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


                    direction.setText("Digital Signature");
                    who.setText("Consent and Release Form");
                    who.setTextColor(Color.parseColor("#3498db"));
                    next.setText("Record");
                    about_vf.showNext();
//                        about_vf.setDisplayedChild(3);
//                    AlertDialog.Builder builder = new AlertDialog.Builder(PreCameraAboutActivity.this);
//                    builder.setMessage("Please leave your loved one a short message.\n\nNote to volunteer: Press continue to go to camera then hold your phone horizontally, hit record, and reconfirm permission on camera. 'Do we have your permission to record and share this video?' Once they say 'yes,' invite them to look at the camera, and speak to their loved one as if they were there.")
//                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
////                                    requestPermissions();
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // User cancelled the dialog
//                                }
//                            });
//                    // Create the AlertDialog object and return it
//                    builder.create();
//                    builder.show();
                }
                else {
                    if(about_vf.getDisplayedChild() == 0) {
                        if (one_name.getText().toString().equals("") || one_birth.getText().toString().equals("") || one_live.getText().toString().equals("") || one_hometown.getText().toString().equals("") || one_reach.getText().toString().equals("") || one_years.getText().toString().equals("")) {
                            Toast.makeText(PreCameraAboutActivity.this,
                                    "Please fill out all fields.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            about_vf.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                            about_vf.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                            direction.setText("To");
                            who.setText("Loved one");
                            about_vf.showNext();
                        }
                    }
                    else {
                        if(two_name.getText().toString().equals("") || two_relationship.getText().toString().equals("") || two_birth.getText().toString().equals("") || two_location.getText().toString().equals("") || two_years.getText().toString().equals("") || two_other.getText().toString().equals("")) {
                            Toast.makeText(PreCameraAboutActivity.this,
                                    "Please fill out all fields.",
                                    Toast.LENGTH_LONG).show();
                        } else if(about_vf.getDisplayedChild() == 1){
                            about_vf.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                            about_vf.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                            direction.setText("Review");
                            who.setText("Contact info");
                            next.setText("Next");
                            review_one_name.setText("From: " + one_name.getText().toString());
                            review_one_birth.setText("Date of birth: " + one_birth.getText().toString());
                            review_one_location.setText("Location: " + one_live.getText().toString());
                            review_one_hometown.setText("Hometown: " + one_hometown.getText().toString());
                            review_one_years.setText("Years away from home: " + one_years.getText().toString());
                            review_one_contact.setText("Best way to reach you: " + one_reach.getText().toString());
                            review_two_name.setText("To: " + two_name.getText().toString());
                            review_two_relationship.setText("Relationship: " + two_relationship.getText().toString());
                            review_two_birth.setText("Age: " + two_birth.getText().toString());
                            review_two_location.setText("Location: " + two_location.getText().toString());
                            review_two_years.setText("Years apart: " + two_years.getText().toString());
                            review_two_other.setText("Other info: " + two_other.getText().toString());
                            about_vf.showNext();
                        } else if(about_vf.getDisplayedChild() == 3) {
                            requestPermissions();
                        }
                    }
                }
            }
        });

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

    //Method that checks screen orientation changes. Landscape orientation hides navigation bar.
    @Override
    public void onConfigurationChanged(Configuration updatedConfig) {
        super.onConfigurationChanged(updatedConfig);

        // Checks the orientation of the screen
        if (updatedConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            nav_bar.setVisibility(View.GONE);

        } else if (updatedConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            nav_bar.setVisibility(View.VISIBLE);
        }
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, VIDEO_PERMISSIONS, REQUEST_VIDEO_CAPTURE);
        }
        else {
            dispatchTakeVideoIntent();
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            String filepath = getRealPathFromURI(getApplicationContext(), videoUri);
//            Log.v("FINISHED RECORDING:", filepath);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(FileLoc, filepath);
            editor.commit();
            Toast.makeText(this, "Video saved to Camera album", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, ExportActivity.class));
            finish();
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Boolean permissionsGranted = true;
        if (requestCode == REQUEST_VIDEO_CAPTURE) {
            if (grantResults.length == VIDEO_PERMISSIONS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Please grant all permissions to record.", Toast.LENGTH_LONG).show();
                        permissionsGranted = false;
                        break;
                    }
                }
            } else {
                Toast.makeText(this, "Please grant all permissions to record.", Toast.LENGTH_LONG).show();
                permissionsGranted = false;
            }
            if(permissionsGranted)
                dispatchTakeVideoIntent();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //Update the back button
    public void onBackPressed() {
        if(about_vf.getDisplayedChild() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PreCameraAboutActivity.this);
            builder.setMessage("All changes on this page will be lost, would you still like to go back to the home page?")
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
            about_vf.setInAnimation(getApplicationContext(), R.anim.slide_in_from_left);
            about_vf.setOutAnimation(getApplicationContext(), R.anim.slide_out_to_right);
            if(about_vf.getDisplayedChild() == 1) {
                direction.setText("From");
                who.setText("Homeless individual");
            }
            else {
                direction.setText("To");
                who.setText("Loved one");
                next.setText("Next");
            }
            about_vf.showPrevious();
        }
    }

    class SketchSheetView extends View {

        public SketchSheetView(Context context) {

            super(context);

            bitmap = Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_4444);

            canvas = new Canvas(bitmap);

            this.setBackgroundColor(Color.LTGRAY);
        }

        private ArrayList<PreCameraAboutActivity.DrawingClass> DrawingClassArrayList = new ArrayList<PreCameraAboutActivity.DrawingClass>();

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            PreCameraAboutActivity.DrawingClass pathWithPaint = new PreCameraAboutActivity.DrawingClass();

            canvas.drawPath(path2, paint);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                path2.moveTo(event.getX(), event.getY());

                path2.lineTo(event.getX(), event.getY());
            }
            else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                path2.lineTo(event.getX(), event.getY());

                pathWithPaint.setPath(path2);

                pathWithPaint.setPaint(paint);

                DrawingClassArrayList.add(pathWithPaint);
            }

            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (DrawingClassArrayList.size() > 0) {

                canvas.drawPath(
                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPath(),

                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPaint());
            }
        }
    }


    public class DrawingClass {

        Path DrawingClassPath;
        Paint DrawingClassPaint;

        public Path getPath() {
            return DrawingClassPath;
        }

        public void setPath(Path path) {
            this.DrawingClassPath = path;
        }


        public Paint getPaint() {
            return DrawingClassPaint;
        }

        public void setPaint(Paint paint) {
            this.DrawingClassPaint = paint;
        }
    }

    public void clearDrawing(View v) {
        path2.reset();
        view.invalidate();
    }
}
