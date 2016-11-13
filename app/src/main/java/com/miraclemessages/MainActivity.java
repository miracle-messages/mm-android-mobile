package com.miraclemessages;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    EditText textName, textEmail, textPhone, textLocation;
    Button submit;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";
    LinearLayout homeback;
    TextView policy;
    PopupWindow popupWindow;
    RelativeLayout relativeLayout;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRef = database.getReference("MiracleMessages");
        homeback = (LinearLayout) findViewById(R.id.homeback);
        homeback.setBackgroundResource(R.drawable.homeback);
        homeback.getBackground().setAlpha(120);
        textName=(EditText)findViewById(R.id.name);
        textEmail=(EditText)findViewById(R.id.email);
        textPhone=(EditText)findViewById(R.id.phone_number);
        textLocation=(EditText)findViewById(R.id.location);
        submit=(Button)findViewById(R.id.submit);
        policy = (TextView)findViewById(R.id.policy);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl);
        //Log.v("Tits: ", "Niqqa");
        sharedpreferences = getSharedPreferences(myPreferences,
                Context.MODE_PRIVATE);

        if(sharedpreferences.getString(Name, null) != null
                && sharedpreferences.getString(Email, null) != null
                && sharedpreferences.getString(Phone, null) != null
                && sharedpreferences.getString(Location, null) != null)
            startActivity(new Intent(MainActivity.this, PreCameraActivity.class));

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
                String p = textPhone.getText().toString();
                if(p.equals("")) {
                    Toast.makeText(MainActivity.this,
                            "Please fill out all fields.",
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

                DatabaseReference usersRef = myRef.child("users");
                DatabaseReference newUsersRef = usersRef.push();

                Map<String, User> users = new HashMap<String, User>();
                users.put(sharedpreferences.getString(Name, null),
                        new User(sharedpreferences.getString(Email, null)
                        , sharedpreferences.getString(Phone, null)
                        , sharedpreferences.getString(Location, null)));
                newUsersRef.setValue(users);

                Toast.makeText(MainActivity.this,"Thank you!", Toast.LENGTH_LONG).show();
                Log.v("Bonjourno: ", "Hoe");
                startActivity(new Intent(MainActivity.this, PreCameraActivity.class));
            }
        });

        policy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.privacy_policy,null);
                popupWindow = new PopupWindow(
                        customView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(false);
                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    popupWindow.setElevation(5.0f);
                }
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER,0,0);
            }
        });
    }

    public static class User {
        public String name;
        public String email;
        public String phone;
        public String location;

        public User(String email, String phone, String location) {
            this.email = email;
            this.phone = phone;
            this.location = location;
        }

    }

}