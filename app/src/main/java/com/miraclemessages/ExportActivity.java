package com.miraclemessages;

import android.app.Activity;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferType;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ExportActivity extends Activity{
    // Indicates that no upload is currently selected
    private static final int INDEX_NOT_CHECKED = -1;

    // TAG for logging;
    private static final String TAG = "UploadActivity";

    //Buttons and SharedPreferences
    Button submit, back;
    SharedPreferences sharedpreferences;
    TextView feedback;
    public static final String myPreferences = "MyPreferences";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Phone = "phone";
    public static final String Location = "location";
    public static final String FileLoc = "file";
    public static final String ACCOUNT_KEY = "accountName";
    private String mChosenAccountName;
    private static int UPLOAD_NOTIFICATION_ID = 1001;
    File file;
    TransferObserver observer;
    NotificationCompat.Builder builder;
    NotificationManager manager;

    // The TransferUtility is the primary class for managing transfer to S3
    private TransferUtility transferUtility;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        transferUtility = Util.getTransferUtility(this);
        myRef = database.getReference("MiracleMessages");

        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);


            String s  = sharedpreferences.getString(FileLoc, null).toString();
            Log.v("DEREK: ", s);


            submit = (Button) findViewById(R.id.submit);
            back = (Button) findViewById(R.id.homepage);
            feedback = (TextView) findViewById(R.id.feedback);

            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.v("OY:", "M8");
                    Toast.makeText(ExportActivity.this, "Upload started", Toast.LENGTH_LONG).show();
                    addNotification();
                    beginUpload(sharedpreferences.getString(FileLoc, null).toString());
                }

            });

            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(ExportActivity.this, PreCameraActivity.class));
                    finish();
                }
            });

            final String appPackageName = getPackageName().toString();
            feedback.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    try {
                        Log.v("Peas in a pod", appPackageName);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException notFoundException) {
                        Log.v("BEN HUR:", appPackageName);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void beginUpload(String filePath) {
        if (filePath == null) {
            Toast.makeText(this, "Could not find the filepath of the selected file",
                    Toast.LENGTH_LONG).show();
            return;
        }
        file = new File(filePath);
        observer = transferUtility.upload(com.miraclemessages.Constants.BUCKET_NAME, file.getName(),
                file);
//        Log.v("STATE: ", observer.getState().toString());
        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.v("MUFFIN:", observer.getState().toString());

                if(state.toString().equals("COMPLETED")){
                    Intent i = new Intent(getApplicationContext(), ExportActivity.class);
                    PendingIntent pendingIntent =
                            PendingIntent.getActivity(getApplicationContext(), 0, i, 0);

                    builder.setContentTitle("Miracle Message Uploaded!")
                            .setContentText("Thank you! :-)")
                            .setProgress(0, 0, false)
                            .setOngoing(false)
                            .setContentIntent(pendingIntent);

                    manager.notify(UPLOAD_NOTIFICATION_ID, builder.build());

                    DatabaseReference usersRef = myRef.child("users");
                    DatabaseReference newUsersRef = usersRef.push();

                    Map<String, User> users = new HashMap<String, User>();
                    users.put(sharedpreferences.getString(Name, null),
                            new User(sharedpreferences.getString(Email, null)
                                    , sharedpreferences.getString(Phone, null)
                                    , sharedpreferences.getString(Location, null)
                                    , sharedpreferences.getString("about_one_name", null)
                                    , sharedpreferences.getString("about_one_birth", null)
                                    , sharedpreferences.getString("about_one_live", null)
                                    , sharedpreferences.getString("about_one_hometown", null)
                                    , sharedpreferences.getString("about_one_years", null)
                                    , sharedpreferences.getString("about_one_reach", null)
                                    , sharedpreferences.getString("about_two_name", null)
                                    , sharedpreferences.getString("about_two_relationship", null)
                                    , sharedpreferences.getString("about_two_birth", null)
                                    , sharedpreferences.getString("about_two_location", null)
                                    , sharedpreferences.getString("about_two_years", null)
                                    , sharedpreferences.getString("about_two_other", null)));
                    newUsersRef.setValue(users);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.v("CURRENTO:", bytesCurrent + "");
                Log.v("TOTALO:", bytesTotal + "");
                Log.v("VALUEEE: ", ((int)(((float)bytesCurrent/bytesTotal)*100) + ""));
                builder.setContentText("Progress: " + (int)(((float)bytesCurrent/bytesTotal)*100) + "%")
                        .setProgress((int) bytesTotal, (int)(bytesCurrent), false);
                manager.notify(UPLOAD_NOTIFICATION_ID, builder.build());
            }

            @Override
            public void onError(int id, Exception ex) {
                Intent i = new Intent(getApplicationContext(), ExportActivity.class);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), 0, i, 0);

                builder.setContentTitle("Network status changed")
                        .setContentText("Please tap here and re-upload.")
                        .setOngoing(true)
                        .setContentIntent(pendingIntent)
                        .setProgress(0, 0, false);

                manager.notify(UPLOAD_NOTIFICATION_ID, builder.build());
            }
        });
    }

    private void addNotification() {
            builder =
                new NotificationCompat.Builder(this)
                        //CHANGE THE SMALL ICON LATER
                        .setSmallIcon(R.drawable.mmicon)
                        .setContentTitle("Uploading Miracle Message...")
                        .setOngoing(true);

             // Add as notification
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(UPLOAD_NOTIFICATION_ID, builder.build());
    }

    public static class User {
        public String name;
        public String email;
        public String phone;
        public String location;

        public String about_one_name;
        public String about_one_birth;
        public String about_one_live;
        public String about_one_hometown;
        public String about_one_years;
        public String about_one_reach;

        public String about_two_name;
        public String about_two_relationship;
        public String about_two_birth;
        public String about_two_location;
        public String about_two_years;
        public String about_two_others;

        public User(String email,
                    String phone,
                    String location,
                    String about_one_name,
                    String about_one_birth,
                    String about_one_live,
                    String about_one_hometown,
                    String about_one_years,
                    String about_one_reach,
                    String about_two_name,
                    String about_two_relationship,
                    String about_two_birth,
                    String about_two_location,
                    String about_two_years,
                    String about_two_others) {
            this.email = email;
            this.phone = phone;
            this.location = location;
            this.about_one_name = about_one_name;
            this.about_one_birth = about_one_birth;
            this.about_one_live = about_one_live;
            this.about_one_hometown = about_one_hometown;
            this.about_one_years = about_one_years;
            this.about_one_reach = about_one_reach;
            this.about_two_name = about_two_name;
            this.about_two_relationship = about_two_relationship;
            this.about_two_birth = about_two_birth;
            this.about_two_location = about_two_location;
            this.about_two_years = about_two_years;
            this.about_two_others = about_two_others;
        }
    }
}