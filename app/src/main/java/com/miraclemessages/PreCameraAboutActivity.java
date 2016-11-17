package com.miraclemessages;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.File;
import java.net.URI;

public class PreCameraAboutActivity extends Activity {

    TextView back, next, navtitle;
    TextView one_name_label, one_birth_label, one_live_label, one_hometown_label, one_years_label, one_reach_label;
    TextView two_name_label, two_relationship_label, two_birth_label, two_location_label, two_years_label, two_other_label;
    EditText one_name, one_birth, one_live, one_hometown, one_years, one_reach;
    EditText two_name, two_relationship, two_birth, two_location, two_years, two_other;
    ViewFlipper about_vf;
    SharedPreferences sharedpreferences;
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
                    navtitle.setText("About the Houseless Person");
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(PreCameraAboutActivity.this);
                        builder.setMessage("Please leave your loved one a short message.\n\nNote to volunteer: Press continue to go to camera and hold your phone horizontally when recording!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        requestPermissions();
//                                        finish();
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
                }
                else {
                    if(one_name.getText().toString().equals("") || one_birth.getText().toString().equals("") || one_live.getText().toString().equals("") || one_hometown.getText().toString().equals("") || one_reach.getText().toString().equals("") || one_years.getText().toString().equals("")) {
                        Toast.makeText(PreCameraAboutActivity.this,
                                "Please fill out all fields.",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        navtitle.setText("About Their Loved One");
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
            Log.v("FINISHED RECORDING:", filepath);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(FileLoc, filepath);
            editor.commit();
            Toast.makeText(this, "Video saved to Camera album", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, ExportActivity.class));
//            finish();
//            mVideoView.setVideoURI(videoUri);
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
//        Log.d(TAG, "onRequestPermissionsResult");
        Boolean permissionsGranted = true;
        if (requestCode == REQUEST_VIDEO_CAPTURE) {
            if (grantResults.length == VIDEO_PERMISSIONS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
//                            ErrorDialog.newInstance(getString(R.string.permission_request))
//                                    .show(getChildFragmentManager(), FRAGMENT_DIALOG);
                        Toast.makeText(this, "Please grant all permissions to record.", Toast.LENGTH_LONG).show();
                        permissionsGranted = false;
//                        requestPermissions();
                        break;
                    }
                }
            } else {
                Toast.makeText(this, "Please grant all permissions to record.", Toast.LENGTH_LONG).show();
                permissionsGranted = false;
//                requestPermissions();
            }
            if(permissionsGranted)
                dispatchTakeVideoIntent();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
