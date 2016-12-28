package com.miraclemessages;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;

public class PreCameraActivity extends Activity {

    private ViewFlipper viewFlipper, buttonViewFlipper;
    TextView about, link, faq, resources, contact, changeUser;
    TextView internalfb1, internalfb2, internalslack, ext_fb, ext_donation, ext_yt, ext_twitter, ext_ig, docs_hb, docs_int, docs_ext, docs_roles;
    SharedPreferences sharedpreferences;
    ImageView smallIcon;
    LinearLayout pcBack;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";
    Animation animFadeOut, animFadeIn;
    ImageView back, icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precamera);

        pcBack = (LinearLayout) findViewById(R.id.precamera_background);

        docs_hb = (TextView) findViewById(R.id.docs_handbook);
        docs_hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://bit.ly/mmhandbook";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        docs_int = (TextView) findViewById(R.id.docs_internal_list);
        docs_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://bit.ly/mmchapters";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        docs_ext = (TextView) findViewById(R.id.docs_external_list);
        docs_ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://miraclemessages.org/chapters";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        docs_roles = (TextView) findViewById(R.id.docs_roles);
        docs_roles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://miraclemessages.org/join";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        ext_fb = (TextView) findViewById(R.id.external_fb);
        ext_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://facebook.com/miraclemessages";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        ext_donation = (TextView) findViewById(R.id.external_donation);
        ext_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://give.miraclemessages.org";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        ext_yt = (TextView) findViewById(R.id.external_youtube);
        ext_yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://youtube.com/miraclemessages";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        ext_twitter = (TextView) findViewById(R.id.external_twitter);
        ext_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://twitter.com/miraclemsg";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        ext_ig = (TextView) findViewById(R.id.external_ig);
        ext_ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://instagram.com/miraclemessages";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        internalfb1 = (TextView) findViewById(R.id.internal_fb_1);
        internalfb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://facebook.com/groups/miraclemsg";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        internalfb2 = (TextView) findViewById(R.id.internal_fb_2);
        internalfb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://facebook.com/groups/mmdetectives";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        internalslack = (TextView) findViewById(R.id.internal_slack);
        internalslack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://joinslack.miraclemessages.org";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        about = (TextView) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                viewFlipper.setDisplayedChild(1); //1 is about
                back.setVisibility(View.VISIBLE);
                icon.setVisibility(View.VISIBLE);
            }
        });
        contact = (TextView) findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hello@miraclemessages.org"});
                emailIntent.setType("text/plain");
                startActivity(emailIntent);
            }
        });
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFlipper.getDisplayedChild() == 1 || //if about or resources, go to menu
                        viewFlipper.getDisplayedChild() == 2) {
                    viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                    viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                    viewFlipper.setDisplayedChild(0);
                    back.setVisibility(View.INVISIBLE);
                    icon.setVisibility(View.INVISIBLE);
                }
            }
        });
        icon = (ImageView) findViewById(R.id.icon);
        link = (TextView) findViewById(R.id.link);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://miraclemessages.org/getinvolved";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        faq = (TextView) findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://miraclemessages.org/faq";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        resources = (TextView) findViewById(R.id.resources);
        resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                viewFlipper.setDisplayedChild(2); //2 is resources
                back.setVisibility(View.VISIBLE);
                icon.setVisibility(View.VISIBLE);
            }
        });


        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        changeUser = (TextView) findViewById(R.id.logout);

        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        animFadeOut.setDuration(100);
        animFadeIn.setDuration(600);

//        switchLoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                builder.setTitle("Volunteer Chapter Location:");
//
//                // Set up the input
//                final EditText input = new EditText(v.getContext());
//                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                input.setInputType(InputType.TYPE_CLASS_TEXT);
//
//                LinearLayout layout = new LinearLayout(v.getContext());
//                layout.setOrientation(LinearLayout.VERTICAL);
//                layout.setGravity(Gravity.CENTER_HORIZONTAL);
//                input.setSingleLine(true);
//                layout.setPadding(100, 0, 100, 0);
//                input.setHint("ie. City, State");
//                layout.addView(input);
//
//                builder.setView(layout);
//
//                // Set up the buttons
//                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(input.getText().toString().equals("")) {
//                            Toast.makeText(PreCameraActivity.this, "Must input chapter location", Toast.LENGTH_LONG).show();
//                        }
//                        else {
//                            SharedPreferences.Editor editor = sharedpreferences.edit();
//                            editor.putString(Location, input.getText().toString());
//                            editor.commit();
//                            Toast.makeText(PreCameraActivity.this, "New Chapter Location Saved!", Toast.LENGTH_LONG).show();
//                            vLocation.setText(sharedpreferences.getString(Location, null));
//                        }
//                    }
//                });
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//
//                builder.show();
//            }
//        });


//        begin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bfLabel.setAnimation(animFadeIn);
//                bfLabel.getAnimation().start();
//                bfLabel.setVisibility(View.VISIBLE);
//                smallIcon.setAnimation(animFadeIn);
//                smallIcon.getAnimation().start();
//                smallIcon.setVisibility(View.VISIBLE);
//                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
//                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
//                pcBack.setBackgroundResource(R.drawable.a_xxxhdpi);
//                pcBack.getBackground().setAlpha(90);
//                viewFlipper.showNext();
//                buttonViewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
//                buttonViewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
//                buttonViewFlipper.showNext();
//            }
//        });

//        next.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
////                if(next.getText().equals("Record")) {
//                if(viewFlipper.getDisplayedChild() == viewFlipper.getChildCount()-1) {
//                    startActivity(new Intent(PreCameraActivity.this, PreCameraAboutActivity.class));
//                    finish();
//                }
//                Log.v("Next Click: ", viewFlipper.getDisplayedChild() + " " + (viewFlipper.getChildCount() - 1));
//                if (viewFlipper.getDisplayedChild() < viewFlipper.getChildCount() - 1) {
//                    viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
//                    viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
//                    if(viewFlipper.getDisplayedChild() == 1)
//                        pcBack.setBackgroundResource(R.drawable.b_xxxhdpi);
//                    else if(viewFlipper.getDisplayedChild() == 2)
//                        pcBack.setBackgroundResource(R.drawable.c_xxxhdpi);
//                    else if(viewFlipper.getDisplayedChild() == 3)
//                        pcBack.setBackgroundResource(R.drawable.d_xxxhdpi);
//                    else if(viewFlipper.getDisplayedChild() == 4)
//                        pcBack.setBackgroundResource(R.drawable.e_xxxhdpi);
//                    pcBack.getBackground().setAlpha(90);
//                    viewFlipper.showNext();
////                    if(viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 1)
////                        next.setText("Record");
//                }
//            }
//        });
//
//        back.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.v("Back Click: ", viewFlipper.getDisplayedChild() + " " + 0);
//                if (viewFlipper.getDisplayedChild() > 0) {
//                    if(viewFlipper.getDisplayedChild() == 1) {
//                        bfLabel.setAnimation(animFadeOut);
//                        bfLabel.getAnimation().start();
//                        bfLabel.setVisibility(View.INVISIBLE);
//                        smallIcon.setAnimation(animFadeOut);
//                        smallIcon.getAnimation().start();
//                        smallIcon.setVisibility(View.GONE);
//                    }
//                    viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
//                    viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
//                    if(viewFlipper.getDisplayedChild() == 2)
//                        pcBack.setBackgroundResource(R.drawable.a_xxxhdpi);
//                    else if(viewFlipper.getDisplayedChild() == 3)
//                        pcBack.setBackgroundResource(R.drawable.b_xxxhdpi);
//                    else if(viewFlipper.getDisplayedChild() == 4)
//                        pcBack.setBackgroundResource(R.drawable.c_xxxhdpi);
//                    else if(viewFlipper.getDisplayedChild() == 5)
//                        pcBack.setBackgroundResource(R.drawable.d_xxxhdpi);
//                    pcBack.getBackground().setAlpha(90);
//                    if(viewFlipper.getDisplayedChild() == 1) {
//                        pcBack.setBackgroundColor(Color.parseColor("#93E2FF"));
//                        pcBack.getBackground().setAlpha(0);
//                    }
//                    viewFlipper.showPrevious();
////                    if(next.getText().equals("Record"))
////                        next.setText("Next");
//                    if(viewFlipper.getDisplayedChild() == 0) {
//                        buttonViewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
//                        buttonViewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
//                        buttonViewFlipper.showPrevious();
//                    }
//                }
//            }
//        });

        changeUser.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(PreCameraActivity.this, MainActivity.class));
                finish();
            }
        });
    }


}
