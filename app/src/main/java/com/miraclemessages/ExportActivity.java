package com.miraclemessages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ExportActivity extends Activity{
    Button b;
    SharedPreferences sharedpreferences;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        b = (Button) findViewById(R.id.submit);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"jw3qz@virginia.edu"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Miracle Messages Recording");
                i.putExtra(Intent.EXTRA_TEXT   ,
                        "Volunteer: " + sharedpreferences.getString(Name, null).toString() + "\n" +
                        "Email: " + sharedpreferences.getString(Email, null).toString() + "\n" +
                        "Phone number: " + sharedpreferences.getString(Phone, null).toString()
                                + "\n" +
                        "Location: " + sharedpreferences.getString(Location, null).toString() + "\n"
                );
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ExportActivity.this,
                            "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
