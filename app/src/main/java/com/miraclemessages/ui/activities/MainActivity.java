package com.miraclemessages.ui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miraclemessages.R;
import com.miraclemessages.common.Logger;
import com.miraclemessages.models.VolunteerUserDetails;
import com.miraclemessages.ui.adapters.MessagesListAdapter;
import com.miraclemessages.utils.GenericUtil;
import com.miraclemessages.utils.SharedPreferenceUtil;
import com.miraclemessages.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.miraclemessages.common.Settings.GOOGLE_SIGN_IN_REQUEST_ID_TOKEN;
import static com.miraclemessages.common.Settings.MM_CHAPTERS_URL;
import static com.miraclemessages.common.Settings.MM_CHAPTER_SHORT_URL;
import static com.miraclemessages.common.Settings.MM_DONATE_URL;
import static com.miraclemessages.common.Settings.MM_FACEBOOK_DETECTIVE_GROUP_URL;
import static com.miraclemessages.common.Settings.MM_FACEBOOK_GROUP_URL;
import static com.miraclemessages.common.Settings.MM_FACEBOOK_PAGE_URL;
import static com.miraclemessages.common.Settings.MM_FAQ_URL;
import static com.miraclemessages.common.Settings.MM_GET_INVOLVED_URL;
import static com.miraclemessages.common.Settings.MM_HANDBOOK_SHORT_URL;
import static com.miraclemessages.common.Settings.MM_INSTAGRAM_URL;
import static com.miraclemessages.common.Settings.MM_JOIN_URL;
import static com.miraclemessages.common.Settings.MM_SLACK_URL;
import static com.miraclemessages.common.Settings.MM_TWITTER_URL;
import static com.miraclemessages.common.Settings.MM_YOUTUBE_URL;

public class MainActivity extends BaseActivity {
    private static final Class TAG = MainActivity.class;
    boolean exitApp = true;

    PopupWindow popupWindow;
    public ArrayList<HashMap<String, String>> myMessagesList = new ArrayList<>();

    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;

    @BindView(R.id.viewflipper)
    ViewFlipper viewFlipper;

    @BindView(R.id.script_hello)
    TextView script_hello;

    @BindView(R.id.policy)
    TextView privacy_policy;

    @BindView(R.id.rateApp)
    TextView leave_rating;

    @BindView(R.id.profile_name)
    EditText prof_name;

    @BindView(R.id.profile_number)
    EditText prof_phone;

    @BindView(R.id.profile_email)
    EditText prof_email;

    @BindView(R.id.profile_location)
    EditText prof_loc;

    @BindView(R.id.precamera_background)
    LinearLayout pcBack;

    @BindView(R.id.navbar)
    RelativeLayout nav_bar;

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.list_messages)
    ListView my_messages_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_SIGN_IN_REQUEST_ID_TOKEN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()) //Use app context to prevent leaks using activity
                //.enableAutoManage(this /* FragmentActivity */, connectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();

        String userID = mAuth.getCurrentUser().getUid();

        DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference("users/" + userID + "/cases");
        Log.v("My ref: ", myDatabaseRef.getRoot().toString());
        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("Data snapshot: ", Long.toString(dataSnapshot.getChildrenCount()));
                for (DataSnapshot cases : dataSnapshot.getChildren()) {
                    Logger.debug(TAG, "Case:");
                    Logger.debug(TAG, cases.toString());
                    HashMap<String, String> myCase = new HashMap<String, String>();
                    for (DataSnapshot d : cases.getChildren()) {
                        //CHECKS FOR NAME
                        if (d.getKey().equals("firstName")) {
                            myCase.put("firstName", d.getValue().toString());
                        }
                        if (d.getKey().equals("lastName")) {
                            myCase.put("lastName", d.getValue().toString());
                        }
                        if (d.getKey().equals("photo")) {
                            myCase.put("photo", d.getValue().toString());
                        }
                        if (d.getKey().equals("caseStatus")) {
                            myCase.put("caseStatus", d.getValue().toString());
                        }
                        if (d.getKey().equals("messageStatus")) {
                            myCase.put("messageStatus", d.getValue().toString());
                        }
                        if (d.getKey().equals("nextStep")) {
                            myCase.put("nextStep", d.getValue().toString());
                        }
                        if (d.getKey().equals("caseID")) {
                            myCase.put("caseID", d.getValue().toString());
                        }
                    }
                    myMessagesList.add(myCase);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        my_messages_list.setAdapter(new MessagesListAdapter(this, myMessagesList));

        if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            nav_bar.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.profile_save)
    public void onClickSaveProfile(View v) {
        // If empty show empty error.
        String name = prof_name.getText().toString();
        String email = prof_email.getText().toString();
        String phone = prof_phone.getText().toString();
        String location = prof_loc.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(location)) {
            ToastUtil.showToast(this, R.string.error_incomplete_fields);
            return;
        }

        // Else do validation now
        if (!GenericUtil.isValidEmail(email)) {
            ToastUtil.showToast(this, R.string.error_invalid_email);
            return;
        }
        if (!GenericUtil.isValidPhone(phone)) {
            ToastUtil.showToast(this, R.string.error_invalid_number);
            return;
        }

        // Save
        VolunteerUserDetails volunteerUserDetails = new VolunteerUserDetails(name, email, phone, location);
        SharedPreferenceUtil.saveUserDetails(this, volunteerUserDetails);

        moveFlipper(true);
        viewFlipper.setDisplayedChild(0);
        back.setVisibility(View.INVISIBLE);
        icon.setVisibility(View.INVISIBLE);

        ToastUtil.showToast(this, R.string.toast_profile_saved);
    }

    @OnClick(R.id.policy)
    public void onPrivacyPolicyClick(View v) {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View policyView = inflater.inflate(R.layout.privacy_policy, null);
        popupWindow = new PopupWindow(policyView,
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
        ImageButton closeButton = (ImageButton) policyView.findViewById(R.id.ib_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        popupWindow.showAtLocation(pcBack, Gravity.CENTER, 0, 0);
    }

    @OnClick(R.id.rateApp)
    public void onClickRateApp() {
        startPlayStore();
    }

    @OnClick(R.id.about)
    public void onClickAbout(View v) {
        moveFlipper(false);
        viewFlipper.setDisplayedChild(1); //1 is about
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.VISIBLE);
        exitApp = false;
    }

    @OnClick(R.id.logout)
    public void onClickLogout(View v) {
        // Remove all local prefs
        SharedPreferenceUtil.deleteAllSharedPreferences(this);
        // Firebase sign out.
        mAuth.signOut();
        // Google revoke access
        signOut();
        // Kill activity
        finish();
    }

    @OnClick(R.id.resources)
    public void onClickResources(View v) {
        moveFlipper(false);
        viewFlipper.setDisplayedChild(2); //2 is resources
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.VISIBLE);
        exitApp = false;
    }

    @OnClick(R.id.my_profile)
    public void onClickProfile(View v) {
        moveFlipper(false);
        VolunteerUserDetails volunteerUserDetails = SharedPreferenceUtil.getVolunteerUserDetails(this);
        prof_name.setText(volunteerUserDetails.getName());
        prof_email.setText(volunteerUserDetails.getEmail());
        prof_phone.setText(volunteerUserDetails.getPhone());
        prof_loc.setText(volunteerUserDetails.getLocation());
        viewFlipper.setDisplayedChild(3); //3 is my profile
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.VISIBLE);
        exitApp = false;
    }

    @OnClick(R.id.my_messages)
    public void onClickMessages(View v) {
        moveFlipper(false);
        viewFlipper.setDisplayedChild(4); //4 is my messages
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.VISIBLE);
        exitApp = false;
    }

    @OnClick(R.id.record)
    public void onClickRecord(View v) {
        moveFlipper(false);
        script_hello.setText("Hi " + mAuth.getCurrentUser().getDisplayName() + ",\nReady to record a message?");
        viewFlipper.setDisplayedChild(5); //5 is script
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.VISIBLE);
        exitApp = false;
    }

    @OnClick(R.id.contact)
    public void onClickContact(View v) {
        CharSequence contactOptions[] = new CharSequence[]{"By email", "By phone call"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        //Setting title color below is not good practice, but it'll do for now.
        builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>How would you like to contact Miracle Messages?</font>"));
        builder.setItems(contactOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selection) {
                if (selection == 0) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hello@miraclemessages.org"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Message from " + SharedPreferenceUtil.getVolunteerUserName(MainActivity.this));
                    emailIntent.setType("message/rfc822");

                    startActivity(Intent.createChooser(emailIntent, "Choose an email client:"));
                } else if (selection == 1) {
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

    @OnClick(R.id.back)
    public void onClickBack(View v) {
        if (viewFlipper.getDisplayedChild() >= 1 &&
                viewFlipper.getDisplayedChild() <= 4) {
            moveFlipper(true);
            back.setVisibility(View.INVISIBLE);
            icon.setVisibility(View.INVISIBLE);
            viewFlipper.setDisplayedChild(0);
        } else if (viewFlipper.getDisplayedChild() == 5 ||
                viewFlipper.getDisplayedChild() == 6) {
            moveFlipper(true);
            viewFlipper.showPrevious();
        }
    }

    //If user selects "Record a message" from the home screen...
    @OnClick(R.id.script_start)
    public void startScript(View v) {
        moveFlipper(false);
        viewFlipper.showNext();
        exitApp = false;
    }

    @OnClick(R.id.script_next_xx)
    public void scriptStage2Click(View v) {
        moveFlipper(false);
        viewFlipper.showNext();
        exitApp = false;
    }

    @OnClick(R.id.script_next_xxx)
    public void scriptStage3Click() {
        /*moveFlipper(false);
        viewFlipper.showNext();
        exitApp = false;*/
        startActivity(new Intent(MainActivity.this, PreCameraAboutActivity.class));
        finish();
    }


    @OnClick({R.id.internal_fb_1, R.id.internal_fb_2, R.id.external_ig, R.id.external_twitter,
            R.id.external_youtube, R.id.external_donation, R.id.external_fb, R.id.docs_roles,
            R.id.docs_external_list, R.id.docs_internal_list, R.id.faq, R.id.docs_handbook,
            R.id.internal_slack, R.id.link})
    public void onClickSocialMediaViews(View v) {
        String linkToGoTo = null;
        switch (v.getId()) {
            case R.id.internal_fb_1:
                linkToGoTo = MM_FACEBOOK_GROUP_URL;
                break;
            case R.id.internal_fb_2:
                linkToGoTo = MM_FACEBOOK_DETECTIVE_GROUP_URL;
                break;
            case R.id.external_ig:
                linkToGoTo = MM_INSTAGRAM_URL;
                break;
            case R.id.external_twitter:
                linkToGoTo = MM_TWITTER_URL;
                break;
            case R.id.external_youtube:
                linkToGoTo = MM_YOUTUBE_URL;
                break;
            case R.id.external_donation:
                linkToGoTo = MM_DONATE_URL;
                break;
            case R.id.external_fb:
                linkToGoTo = MM_FACEBOOK_PAGE_URL;
                break;
            case R.id.docs_roles:
                linkToGoTo = MM_JOIN_URL;
                break;
            case R.id.docs_external_list:
                linkToGoTo = MM_CHAPTERS_URL;
                break;
            case R.id.docs_internal_list:
                linkToGoTo = MM_CHAPTER_SHORT_URL;
                break;
            case R.id.faq:
                linkToGoTo = MM_FAQ_URL;
                break;
            case R.id.docs_handbook:
                linkToGoTo = MM_HANDBOOK_SHORT_URL;
                break;
            case R.id.internal_slack:
                linkToGoTo = MM_SLACK_URL;
                break;
            case R.id.link:
                linkToGoTo = MM_GET_INVOLVED_URL;
                break;
        }
        openLink(linkToGoTo);
    }

    //Method that checks screen orientation changes. Landscape orientation hides navigation bar.
    @Override
    public void onConfigurationChanged(Configuration updatedConfig) {
        super.onConfigurationChanged(updatedConfig);

        // Checks the orientation of the screen
        if (updatedConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            nav_bar.setVisibility(View.GONE);

        } else if (updatedConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            nav_bar.setVisibility(View.VISIBLE);
        }
    }

    private void openLink(String url) {
        if (url == null) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void startPlayStore() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException notFoundException) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
    }

    private void moveFlipper(boolean leftToRight) {
        if (leftToRight) {
            viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
            viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
        } else {
            viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
            viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
        }
    }

    /**
     * Pressing the back button does not close the application.
     * Done because of the use of viewflippers; back button seems to only go back from activity
     * to activity.
     */
    @Override
    public void onBackPressed() {
        if (exitApp) {
            super.onBackPressed();
        } else {
            if (viewFlipper.getDisplayedChild() >= 1 &&
                    viewFlipper.getDisplayedChild() <= 4) {
                moveFlipper(true);
                back.setVisibility(View.INVISIBLE);
                icon.setVisibility(View.INVISIBLE);
                viewFlipper.setDisplayedChild(0);
                exitApp = true;
            } else if (viewFlipper.getDisplayedChild() == 5 ||
                    viewFlipper.getDisplayedChild() == 6) {
                moveFlipper(true);
                viewFlipper.showPrevious();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}
