package com.miraclemessages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.List;

public class ExportActivity extends Activity{
    Button submit, back;
    SharedPreferences sharedpreferences;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";
    public static final String FileLoc = "file";

    private static final String VIDEO_FILE_FORMAT = "video/*";

    private static final String SAMPLE_VIDEO_FILENAME = "sample-video.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        submit = (Button) findViewById(R.id.submit);
        back = (Button) findViewById(R.id.homepage);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                try {
                    shareViaYoutube("youtube");

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ExportActivity.this,
                            "Oh noes! Youtube is not installed.", Toast.LENGTH_SHORT).show();
                }
            }

        });

        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(ExportActivity.this, PreCameraActivity.class));
                finish();
            }
        });

    }


    private void shareViaYoutube(String type) {
        boolean found = false;
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("video/*");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()){
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type) ) {
                    share.putExtra(Intent.EXTRA_SUBJECT,  "subject");
                    share.putExtra(Intent.EXTRA_TEXT,     "your text");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(sharedpreferences.getString(FileLoc, null).toString()))); // Optional, just if you wanna share an image.
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return;

            startActivity(Intent.createChooser(share, "Upload to Youtube here~"));
        }
    }
}
