package com.miraclemessages.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.miraclemessages.R;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 9001;

    SharedPreferences sharedpreferences;
    EditText textName, textEmail, textPhone, textLocation;
    ProgressBar spinner;
    boolean found;
    TextView link;
    SignInButton submit;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        textName=(EditText)findViewById(R.id.name);
//        textEmail=(EditText)findViewById(R.id.email);
//        textPhone=(EditText)findViewById(R.id.phone_number);
//        textLocation=(EditText)findViewById(R.id.location);

        found = false;
        spinner = (ProgressBar) findViewById(R.id.login_spinner);
        spinner.setVisibility(View.GONE);

        submit = (SignInButton) findViewById(R.id.submit);
//        submit.setColorScheme(SignInButton.COLOR_DARK);
        TextView textView = (TextView) submit.getChildAt(0);
        textView.setText("Sign in with Google");
        link = (TextView) findViewById(R.id.link);

        View someView = findViewById(R.id.homeback);
        View root = someView.getRootView();
        root.setBackgroundColor(getResources().getColor(android.R.color.white));

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("287199868982-irpk0ijlughs1nq0c55nn7p1hvqbq3mi.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
//        GoogleSignInOp
/*
        if(sharedpreferences.getString(Name, null) != null
                && sharedpreferences.getString(Email, null) != null
                && sharedpreferences.getString(Phone, null) != null
                && sharedpreferences.getString(Location, null) != null) {
            startActivity(new Intent(MainActivity.this, PreCameraActivity.class));
            finish();
        }

        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String n = textName.getText().toString();
                if(n.equals("")) {
                    Toast.makeText(MainActivity.this,
                            "Please fill out all fields.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String e = textEmail.getText().toString();
                if(e.equals("")) {
                    Toast.makeText(MainActivity.this,
                            "Please fill out all fields.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!isValidEmail(e)){
                    Toast.makeText(MainActivity.this,
                            "Please enter a valid email address.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String p = textPhone.getText().toString();
                if(p.equals("")) {
                    Toast.makeText(MainActivity.this,
                            "Please fill out all fields.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!isValidPhone(p)){
                    Toast.makeText(MainActivity.this,
                            "Please enter a valid phone number.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String l = textLocation.getText().toString();
                if(l.equals("")) {
                    Toast.makeText(MainActivity.this,
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

                Toast.makeText(MainActivity.this,"Thank you!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, PreCameraActivity.class));
                finish();
            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String url = "https://miraclemessages.org/getinvolved";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d("TAG", "Result: Code: " + result.getStatus().getStatusCode() + ", message: " + result.getStatus().getStatusMessage());
            Log.v("HAAAY", "BAAAY");
            if (result.isSuccess()) {
                Log.v("BEFOREE", "POOP");
                //Google sign in was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Log.v("AFTERRR", "POOP");
            } else {
                Log.e("FAILURE", "HOBOBOBOBO");
            }
        }
    }

    //Helper function to sign in to Firebase using the selected Google Account
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.v("IN HEREEEE", "Potato");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        System.out.println("MAH MAN: " + account.getEmail());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign in was successful!
                            Log.v("LEGGGO", "MAN");
                            System.out.println("A NEWBS LIFE");
                            String currentUser = mAuth.getCurrentUser().getUid();
                            mRef = FirebaseDatabase.getInstance().getReference("users/"
                                    + currentUser + "/profileComplete/");
                            mRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    System.out.println("SNAPPY: " + (dataSnapshot.getValue() == null));
                                    if (dataSnapshot.getValue() == null || dataSnapshot.getValue().equals("true")) {
                                        //Turn user to data creation page
                                        createNewAccount();
                                    } else {
                                        //Else, bypass the login screen
                                        Intent i = new Intent(getApplicationContext(), PreCameraActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                            System.out.println("NAW BRUH");
                            Log.d("TAG", "" + task.getResult().toString() + "" + task.getException().getMessage());
                        }
                    }
                });
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
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }
        System.out.println("BBBBBBBBB");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("HAY", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    //Helper function to check if user input email is valid.
    private static boolean isValidEmail(CharSequence userInput) {
        return Patterns.EMAIL_ADDRESS.matcher(userInput).matches();
    }

    //Helper function to check if user input phone number is valid.
    private static boolean isValidPhone(CharSequence userInput) {
        return Patterns.PHONE.matcher(userInput).matches();
    }

    //Helper function for users to create new account
    private void createNewAccount() {
        String url = "https://my.miraclemessages.org/#!/register?platform=Android";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}