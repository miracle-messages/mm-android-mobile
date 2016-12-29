package com.miraclemessages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class PreCameraActivity extends Activity {

    private ViewFlipper viewFlipper, buttonViewFlipper;
    TextView about, link, faq, resources, contact, my_profile, changeUser;
    TextView internalfb1, internalfb2, internalslack, ext_fb, ext_donation, ext_yt, ext_twitter, ext_ig, docs_hb, docs_int, docs_ext, docs_roles;
    EditText prof_name, prof_phone, prof_email, prof_loc;
    Button save_prof;
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

        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        pcBack = (LinearLayout) findViewById(R.id.precamera_background);

        prof_name = (EditText) findViewById(R.id.profile_name);
        prof_phone = (EditText) findViewById(R.id.profile_number);
        prof_email = (EditText) findViewById(R.id.profile_email);
        prof_loc = (EditText) findViewById(R.id.profile_location);
        save_prof = (Button) findViewById(R.id.profile_save);
        save_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = prof_name.getText().toString();
                if(n.equals("")) {
                    Toast.makeText(PreCameraActivity.this,
                            "Please fill out all fields.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String e = prof_email.getText().toString();
                if(e.equals("")) {
                    Toast.makeText(PreCameraActivity.this,
                            "Please fill out all fields.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String p = prof_phone.getText().toString();
                if(p.equals("")) {
                    Toast.makeText(PreCameraActivity.this,
                            "Please fill out all fields.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String l = prof_loc.getText().toString();
                if(l.equals("")) {
                    Toast.makeText(PreCameraActivity.this,
                            "Please fill out all fields.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Name, n);
                editor.putString(Email, e);
                editor.putString(Phone, p);
                editor.putString(Location, l);

                editor.commit();

                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                viewFlipper.setDisplayedChild(0);
                back.setVisibility(View.INVISIBLE);
                icon.setVisibility(View.INVISIBLE);

                Toast.makeText(PreCameraActivity.this,"New profile saved!", Toast.LENGTH_LONG).show();
            }
        });

        docs_hb = (TextView) findViewById(R.id.docs_handbook);
        docs_hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://bit.ly/mmhandbook");
            }
        });
        docs_int = (TextView) findViewById(R.id.docs_internal_list);
        docs_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://bit.ly/mmchapters");
            }
        });
        docs_ext = (TextView) findViewById(R.id.docs_external_list);
        docs_ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://miraclemessages.org/chapters");
            }
        });
        docs_roles = (TextView) findViewById(R.id.docs_roles);
        docs_roles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://miraclemessages.org/join");
            }
        });
        ext_fb = (TextView) findViewById(R.id.external_fb);
        ext_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://facebook.com/miraclemessages");
            }
        });
        ext_donation = (TextView) findViewById(R.id.external_donation);
        ext_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://give.miraclemessages.org");
            }
        });
        ext_yt = (TextView) findViewById(R.id.external_youtube);
        ext_yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://youtube.com/miraclemessages");
            }
        });
        ext_twitter = (TextView) findViewById(R.id.external_twitter);
        ext_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://twitter.com/miraclemsg");
            }
        });
        ext_ig = (TextView) findViewById(R.id.external_ig);
        ext_ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://instagram.com/miraclemessages");
            }
        });
        internalfb1 = (TextView) findViewById(R.id.internal_fb_1);
        internalfb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://facebook.com/groups/miraclemsg");
            }
        });
        internalfb2 = (TextView) findViewById(R.id.internal_fb_2);
        internalfb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://facebook.com/groups/mmdetectives");
            }
        });
        internalslack = (TextView) findViewById(R.id.internal_slack);
        internalslack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLink("https://joinslack.miraclemessages.org");
            }
        });

        my_profile = (TextView) findViewById(R.id.my_profile);
        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                prof_name.setText(sharedpreferences.getString(Name, null));
                prof_email.setText(sharedpreferences.getString(Email, null));
                prof_phone.setText(sharedpreferences.getString(Phone, null));
                prof_loc.setText(sharedpreferences.getString(Location, null));
                viewFlipper.setDisplayedChild(3); //3 is my profile
                back.setVisibility(View.VISIBLE);
                icon.setVisibility(View.VISIBLE);
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
                if(viewFlipper.getDisplayedChild() == 1 || //if about, resources, or my profile go to menu
                        viewFlipper.getDisplayedChild() == 2 ||
                        viewFlipper.getDisplayedChild() == 3) {
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
                openLink("https://miraclemessages.org/getinvolved");
            }
        });
        faq = (TextView) findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://miraclemessages.org/faq");
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

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        changeUser = (TextView) findViewById(R.id.logout);

        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        animFadeOut.setDuration(100);
        animFadeIn.setDuration(600);

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

    public void openLink(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
