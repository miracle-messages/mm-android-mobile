package com.miraclemessages;

import android.*;
import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;

public class PreCameraActivity extends Activity {

    private ViewFlipper viewFlipper;
    TextView about, link, faq, resources, contact, my_profile, record, changeUser;
    TextView internalfb1, internalfb2, internalslack, ext_fb, ext_donation, ext_yt, ext_twitter, ext_ig, docs_hb, docs_int, docs_ext, docs_roles;
    TextView script_hello;
    TextView privacy_policy, leave_rating;
    EditText prof_name, prof_phone, prof_email, prof_loc;
    Button save_prof, script_start, script_next_xx, script_next_xxx;
    SharedPreferences sharedpreferences;
    LinearLayout pcBack;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";
    Animation animFadeOut, animFadeIn;
    ImageView back, icon;
    boolean exitApp = true;
    PopupWindow popupWindow;
    LinearLayout linearLayout;
    private String appPackageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precamera);
        appPackageName = getPackageName().toString();
        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        pcBack = (LinearLayout) findViewById(R.id.precamera_background);

        icon = (ImageView) findViewById(R.id.icon);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);

        /*If user selects "Record a message" from the home screen...*/
        script_hello = (TextView) findViewById(R.id.script_hello);
        script_start = (Button) findViewById(R.id.script_start);
        script_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                viewFlipper.showNext();
                exitApp = false;
            }
        });
        script_next_xx = (Button) findViewById(R.id.script_next_xx);
        script_next_xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                viewFlipper.showNext();
                exitApp = false;
            }
        });
        script_next_xxx = (Button) findViewById(R.id.script_next_xxx);
        script_next_xxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreCameraActivity.this, PreCameraAboutActivity.class));
                finish();
            }
        });

        /*If user selects "My Profile" from the home screen...*/
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
                else if(!isValidEmail(e)){
                    Toast.makeText(PreCameraActivity.this,
                            "Please enter a valid email address.",
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
                else if(!isValidPhone(p)){
                    Toast.makeText(PreCameraActivity.this,
                            "Please enter a valid phone number.",
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

        /*If user selects Resources*/
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

        about = (TextView) findViewById(R.id.about);
        privacy_policy = (TextView) findViewById(R.id.policy);
        leave_rating = (TextView) findViewById(R.id.rateApp);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                viewFlipper.setDisplayedChild(1); //1 is about
                back.setVisibility(View.VISIBLE);
                icon.setVisibility(View.VISIBLE);
                exitApp = false;

                privacy_policy.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        View policyView = inflater.inflate(R.layout.privacy_policy, null);
                        popupWindow = new PopupWindow(
                                policyView,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        popupWindow.setTouchable(true);
                        popupWindow.setFocusable(true);
                        popupWindow.setOutsideTouchable(false);
                        // Set an elevation value for popup window
                        // Call requires API level 21
                        if(Build.VERSION.SDK_INT>=21){
                            popupWindow.setElevation(5.0f);
                        }
                        ImageButton closeButton = (ImageButton) policyView.findViewById(R.id.ib_close);
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Dismiss the popup window
                                popupWindow.dismiss();
                            }
                        });
                        popupWindow.showAtLocation(pcBack, Gravity.CENTER, 0, 0);
                    }
                });

                leave_rating.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        try {
                            Log.v("Peas in a pod", appPackageName);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException notFoundException) {
                            Log.v("BEN HUR:", appPackageName);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });

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
                exitApp = false;
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
                exitApp = false;
            }
        });

        record = (TextView) findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_left);
                String name = sharedpreferences.getString(Name, null);
                String[] name_array = name.split(" ");
                script_hello.setText("Hi " + name_array[0] + ",\nReady to record a message?");
                viewFlipper.setDisplayedChild(4); //4 is script
                back.setVisibility(View.VISIBLE);
                icon.setVisibility(View.VISIBLE);
                exitApp = false;
            }
        });

        contact = (TextView) findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence contactOptions[] = new CharSequence[]{"By email", "By phone call"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PreCameraActivity.this);

                //Setting title color below is not good practice, but it'll do for now.
                builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>How would you like to contact Miracle Messages?</font"));
                builder.setItems(contactOptions, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int selection){
                        if(selection == 0){
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hello@miraclemessages.org"});
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Message from " + sharedpreferences.getString(Name, null));
                            emailIntent.setType("message/rfc822");

                            startActivity(Intent.createChooser(emailIntent, "Choose an email client:"));
                        }
                        else if(selection == 1){
                            Uri number = Uri.parse("tel:4155458406");
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            startActivity(callIntent);
                        }
                    }
                });
                Dialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.color.flat_blue);
                dialog.show();
                exitApp = false;
            }
        });

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFlipper.getDisplayedChild() >= 1 &&
                        viewFlipper.getDisplayedChild() <= 4) {
                    viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                    viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                    back.setVisibility(View.INVISIBLE);
                    icon.setVisibility(View.INVISIBLE);
                    viewFlipper.setDisplayedChild(0);
                }
                else if(viewFlipper.getDisplayedChild() == 5 ||
                        viewFlipper.getDisplayedChild() == 6) {
                    viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                    viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);
                    viewFlipper.showPrevious();
                }
            }
        });

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

    private void openLink(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private static boolean isValidEmail(CharSequence userInput) {
        return Patterns.EMAIL_ADDRESS.matcher(userInput).matches();
    }

    private static boolean isValidPhone(CharSequence userInput) {
        return Patterns.PHONE.matcher(userInput).matches();
    }

    /*Override onBackPressed() so pressing the backbutton does not close the application.
    Done because of the use of viewflippers; back button seems to only go back from activity
    to activity.
    */
    public void onBackPressed() {
        if (exitApp) {
            super.onBackPressed();
        } else {
            if(viewFlipper.getDisplayedChild() >= 1 &&
                    viewFlipper.getDisplayedChild() <= 4) {
                viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_from_left);
                viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_to_right);
                back.setVisibility(View.INVISIBLE);
                icon.setVisibility(View.INVISIBLE);
                viewFlipper.setDisplayedChild(0);
                exitApp = true;
            }
            else if(viewFlipper.getDisplayedChild() == 5 ||
                    viewFlipper.getDisplayedChild() == 6) {
                viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_from_left);
                viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_to_right);
                viewFlipper.showPrevious();
            }
        }
    }
}
