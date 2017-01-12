package com.miraclemessages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    EditText textName, textEmail, textPhone, textLocation;
    TextView link;
    Button submit;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textName=(EditText)findViewById(R.id.name);
        textEmail=(EditText)findViewById(R.id.email);
        textPhone=(EditText)findViewById(R.id.phone_number);
        textLocation=(EditText)findViewById(R.id.location);

        submit=(Button)findViewById(R.id.submit);
        link = (TextView)findViewById(R.id.link);

        View someView = findViewById(R.id.homeback);
        View root = someView.getRootView();
        root.setBackgroundColor(getResources().getColor(android.R.color.white));

        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

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
        });
    }

    //Helper function to check if user input email is valid.
    private static boolean isValidEmail(CharSequence userInput) {
        return Patterns.EMAIL_ADDRESS.matcher(userInput).matches();
    }

    //Helper function to check if user input phone number is valid.
    private static boolean isValidPhone(CharSequence userInput) {
        return Patterns.PHONE.matcher(userInput).matches();
    }

}