package com.miraclemessages;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExportActivity extends Activity{

    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        b = (Button) findViewById(R.id.submit);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"jw3qz@virginia.edu"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Miracle Messages Recording");
                i.putExtra(Intent.EXTRA_TEXT   , "Add a comment here!");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ExportActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
