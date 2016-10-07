package com.miraclemessages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import org.w3c.dom.Text;

/**
 * Created by James Wu on 10/1/2016.
 */

public class GPlusFragment extends Fragment
    implements GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "GPlusFragent";
    private int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private ProgressDialog mProgressDialog;
    private TextView motto;

    SharedPreferences sharedpreferences;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";
    public static final String ACCOUNT_KEY = "accountName";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Configure sign-in using user's ID, email address, and
        //basic profile. ID + basic profile are from DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Create GoogleApiClient with access to Google Sign-In API
        //and other options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            Log.v("STILL HERE, CHUCK?: ", result.getSignInAccount().getEmail().toString());
            startActivity(new Intent(GPlusFragment.this.getActivity(), PreCameraActivity.class));
            GPlusFragment.this.getActivity().finish();
        }
        else{
            Log.v("RESTARTING! ", "yeep yeep");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gplus, parent, false);

//        textName=(EditText)v.findViewById(R.id.name);
//        textEmail=(EditText)v.findViewById(R.id.email);
//        textPhone=(EditText)v.findViewById(R.id.phone_number);
//        textLocation=(EditText)v.findViewById(R.id.location);

        signInButton = (SignInButton) v.findViewById(R.id.sign_in_button);
        motto = (TextView) v.findViewById(R.id.motto);
        motto.setText("Short video messages from the homeless to their long lost loved ones, recorded and delivered by you.");
        setGooglePlusButtonText(signInButton, "Sign in with Google");
        sharedpreferences = getActivity().getSharedPreferences(myPreferences,
                Context.MODE_PRIVATE);

//        if(sharedpreferences.getString(Name, null) != null
//                && sharedpreferences.getString(Email, null) != null
//                && sharedpreferences.getString(Phone, null) != null
//                && sharedpreferences.getString(Location, null) != null) {
//
//            startActivity(new Intent(GPlusFragment.this.getActivity(), PreCameraActivity.class));
//            GPlusFragment.this.getActivity().finish();
//        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        String n = textName.getText().toString();
//        if (n.equals("")) {
//            Toast.makeText(GPlusFragment.this.getActivity(),
//                    "Please fill out all fields.",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//        String e = textEmail.getText().toString();
//        if (e.equals("")) {
//            Toast.makeText(GPlusFragment.this.getActivity(),
//                    "Please fill out all fields.",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//        String p = textPhone.getText().toString();
//        if (p.equals("")) {
//            Toast.makeText(GPlusFragment.this.getActivity(),
//                    "Please fill out all fields.",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//        String l = textLocation.getText().toString();
//        if (l.equals("")) {
//            Toast.makeText(GPlusFragment.this.getActivity(),
//                    "Please fill out all fields.",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//
          SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString(Name, n);
//        editor.putString(Email, e);
//        editor.putString(Phone, p);
//        editor.putString(Location, l);
//
//        editor.commit();

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //handleSignInResult(result);
            if(result.getSignInAccount() == null)
                return;

            Log.v("BONJOUR, CHUCK: ", result.getSignInAccount().getEmail().toString());
            editor.putString(ACCOUNT_KEY, result.getSignInAccount().getEmail().toString());
            editor.putString(Name, result.getSignInAccount().getDisplayName().toString());
            editor.putString(Email, result.getSignInAccount().getEmail().toString());
            editor.commit();
            startActivity(new Intent(GPlusFragment.this.getActivity(), PreCameraActivity.class));
            Toast.makeText(GPlusFragment.this.getActivity(),"Thank you!", Toast.LENGTH_LONG).show();
            GPlusFragment.this.getActivity().finish();

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    protected void setGooglePlusButtonText(SignInButton signInButton,
                                           String text){
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setTextSize(15);
                tv.setTypeface(null, Typeface.BOLD);
                tv.setText(text);
                return;
            }
        }
    }
}
