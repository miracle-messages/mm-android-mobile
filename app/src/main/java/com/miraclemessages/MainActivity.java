package com.miraclemessages;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    EditText textName, textEmail, textPhone, textLocation;
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
        Log.v("Tits: ", "Niqqa");
        sharedpreferences = getSharedPreferences(myPreferences,
                            Context.MODE_PRIVATE);

        Log.v("Poop: ", sharedpreferences.getAll().toString());

        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String n = textName.getText().toString();
                String e = textEmail.getText().toString();
                String p = textPhone.getText().toString();
                String l = textLocation.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Name, n);
                editor.putString(Email, e);
                editor.putString(Phone, p);
                editor.putString(Location, l);

                editor.commit();
                Toast.makeText(MainActivity.this,"Thank you!", Toast.LENGTH_LONG).show();
                Log.v("Bonjourno: ", "Hoe");
            }
        });
    }


}
