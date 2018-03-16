package com.miraclemessages.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miraclemessages.BuildConfig;
import com.miraclemessages.R;
import com.miraclemessages.common.Logger;
import com.miraclemessages.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.miraclemessages.common.Settings.DEV_MM_CREATE_ACCOUNT_URL;
import static com.miraclemessages.common.Settings.GOOGLE_SIGN_IN_REQUEST_ID_TOKEN;
import static com.miraclemessages.common.Settings.MM_CREATE_ACCOUNT_URL;

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 9001;
    private static final Class TAG = LoginActivity.class;

    @BindView(R.id.login_spinner)
    ProgressBar spinner;

    @BindView(R.id.submit)
    SignInButton submit;

    @BindView(R.id.homeback)
    LinearLayout llParentLoginView;

    SharedPreferences sharedpreferences;


    public static final String myPreferences = "MyPreferences";
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseRef;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // Set button text to proper one.
        setGooglePlusButtonText(submit, getString(R.string.sign_in_with_google));
        initFirebaseAndGoogle();
/*
        if(sharedpreferences.getString(Name, null) != null
                && sharedpreferences.getString(Email, null) != null
                && sharedpreferences.getString(Phone, null) != null
                && sharedpreferences.getString(Location, null) != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
*/
    }

    private void initFirebaseAndGoogle() {
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_SIGN_IN_REQUEST_ID_TOKEN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
    }

    @OnClick(R.id.submit)
    public void signInButtonClicked() {
        Logger.debug(TAG, "User clicked the sign in button...");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Logger.debug(TAG, "Result of SignIn request- ResultCode:" + resultCode);
            Logger.debug(TAG, "Google SignIn Result: StatusCode: " + result.getStatus().getStatusCode());
            if (result.isSuccess()) {
                Logger.debug(TAG, "SUCCESS: Google SignIn successful.");
                //Google sign in was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                if (account != null) {
                    Logger.debug(TAG, "Google account used to signin: " + account.getEmail());
                    firebaseAuthWithGoogle(account);
                } else {
                    Logger.error(TAG, "ERROR: Google SignIn Failed. No account details present.");
                }
            } else {
                Logger.error(TAG, "ERROR: Google SignIn Failed.");
            }
        }
    }

    /**
     * Helper function to sign in to Firebase using the selected Google Account
     *
     * @param account GoogleSignInAccount
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Logger.debug(TAG, "firebaseAuthWithGoogle: Start Firebase Authentication ... ");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Sign in was successful!
                    Logger.debug(TAG, "SUCCESS: Firebase Authentication Successful. Id:" + task.getResult().getUser().getUid());
                    // Check if profile is complete with Firebase database.
                    // Current user Id
                    String currentUserId = task.getResult().getUser().getUid();
                    // Get reference to the Database
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("users/" + currentUserId + "/profileComplete/");
                    Logger.debug(TAG, "Check if user's profile is complete or not.");
                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Logger.debug(TAG, dataSnapshot.toString());

                            // Checking profile completion.
                            Boolean isProfileComplete = (Boolean) dataSnapshot.getValue();
                            if (isProfileComplete != null && isProfileComplete) {
                                //Else, bypass the login screen
                                Logger.debug(TAG, "User profile is complete. Take to next screen to select menu items.");
                                startMainActivity();
                            } else {
                                //Turn user to data creation page
                                Logger.debug(TAG, "User profile is NOT complete. Open link to complete the profile.");
                                startActivityCompleteProfile();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Logger.error(TAG, databaseError.toException());
                        }
                    });

                } else {
                    ToastUtil.showToast(LoginActivity.this, R.string.error_authentication_failed);
                    Logger.error(TAG, "ERROR: Firebase Authentication Failed: " + task.getResult().toString());
                    Logger.error(TAG, task.getException());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Logger.debug(TAG, "mGoogleApiClient Connect.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            Logger.debug(TAG, "mGoogleApiClient disconnect.");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not be available.
        Logger.error(TAG, "onConnectionFailed:" + connectionResult.toString());
        ToastUtil.showToast(this, getString(R.string.play_services_error_fmt, connectionResult.getErrorCode()));
    }

    //Helper function for users to create new account
    private void startActivityCompleteProfile() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (BuildConfig.DEBUG) {
            i.setData(Uri.parse(DEV_MM_CREATE_ACCOUNT_URL));
        } else {
            i.setData(Uri.parse(MM_CREATE_ACCOUNT_URL));
        }
        startActivity(i);
    }

    private void startMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View view = signInButton.getChildAt(i);

            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setText(buttonText);
                return;
            }
        }
    }

}