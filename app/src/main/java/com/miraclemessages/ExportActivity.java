package com.miraclemessages;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.miraclemessages.util.NetworkSingleton;
import com.miraclemessages.util.VideoData;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExportActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
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

    public static final String ACCOUNT_KEY = "accountName";
    public static final String MESSAGE_KEY = "message";
    public static final String YOUTUBE_ID = "youtubeId";
    public static final String YOUTUBE_WATCH_URL_PREFIX = "http://www.youtube.com/watch?v=";
    static final String REQUEST_AUTHORIZATION_INTENT = "com.miraclemessages.RequestAuth";
    static final String REQUEST_AUTHORIZATION_INTENT_PARAM = "com.miraclemessages.RequestAuth.param";
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
    private static final int REQUEST_GMS_ERROR_DIALOG = 1;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final int REQUEST_AUTHORIZATION = 3;
    private static final int RESULT_PICK_IMAGE_CROP = 4;
    private static final int RESULT_VIDEO_CAP = 5;
    private static final int REQUEST_DIRECT_TAG = 6;
    private static final String TAG = "UploadActivity";
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = new GsonFactory();
    GoogleAccountCredential credential;
    private ImageLoader mImageLoader;
    private String mChosenAccountName;
    private Uri mFileURI = null;
    private VideoData mVideoData;
    private ExportActivity.UploadBroadcastReceiver broadcastReceiver;
    private UploadsListFragment mUploadsListFragment;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT > 22) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// if you want to check if permission has been given from before
//                final String[] permissions = new String[]{Manifest.permission.GET_ACCOUNTS};
//                ActivityCompat.requestPermissions(this, permissions, 0);
//            }
//        }
        getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();

        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        if (!isCorrectlyConfigured()) {
            setContentView(R.layout.developer_setup_required);
            showMissingConfigurations();
        } else {
            setContentView(R.layout.activity_export);

            ensureLoader();

            credential = GoogleAccountCredential.usingOAuth2(
                    getApplicationContext(), Arrays.asList(AuthClass.SCOPES));
            // set exponential backoff policy
            credential.setBackOff(new ExponentialBackOff());

//            if (savedInstanceState != null) {
//                mChosenAccountName = savedInstanceState.getString(ACCOUNT_KEY);
//                //Log.v("BEETCH: ", mChosenAccountName);
//            } else {
//                loadAccount();
//            }
            mChosenAccountName = sharedpreferences.getString(ACCOUNT_KEY, null).toString();
            Log.v("DEREK: ", mChosenAccountName);

            credential.setSelectedAccountName(mChosenAccountName);

            submit = (Button) findViewById(R.id.submit);
            back = (Button) findViewById(R.id.homepage);

            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                Log.v("DEREK: ", mChosenAccountName);
                if (mChosenAccountName == null) {
                    Log.v(TAG, "CHOSEN ACCOUNT IS NULL");
                    return;
                }
                // if a video is picked or recorded.
                Uri mFileUri = Uri.fromFile(new File(sharedpreferences.getString(FileLoc, null).toString()));
                if (mFileUri != null) {
                    Intent uploadIntent = new Intent(ExportActivity.this, UploadService.class);
                    uploadIntent.setData(mFileUri);
                    uploadIntent.putExtra(UploadActivity.ACCOUNT_KEY, mChosenAccountName);
                    startService(uploadIntent);
                    Toast.makeText(ExportActivity.this, "YouTube upload started; please check the notification bar for progress <3", Toast.LENGTH_LONG).show();
                }
                }

            });

            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(ExportActivity.this, PreCameraActivity.class));
                    finish();
                }
            });
        }

    }

    private boolean isCorrectlyConfigured() {
        // This isn't going to internationalize well, but we only really need
        // this for the sample app.
        // Real applications will remove this section of code and ensure that
        // all of these values are configured.
        if (AuthClass.KEY.startsWith("Replace")) {
            return false;
        }
        if (Constants.UPLOAD_PLAYLIST.startsWith("Replace")) {
            return false;
        }
        return true;
    }

    private void showMissingConfigurations() {
        List<ExportActivity.MissingConfig> missingConfigs = new ArrayList<ExportActivity.MissingConfig>();

        // Make sure an API key is registered
        if (AuthClass.KEY.startsWith("Replace")) {
            missingConfigs
                    .add(new ExportActivity.MissingConfig(
                            "API key not configured",
                            "KEY constant in Auth.java must be configured with your Simple API key from the Google API Console"));
        }

        // Make sure a playlist ID is registered
        if (Constants.UPLOAD_PLAYLIST.startsWith("Replace")) {
            missingConfigs
                    .add(new ExportActivity.MissingConfig(
                            "Playlist ID not configured",
                            "UPLOAD_PLAYLIST constant in Constants.java must be configured with a Playlist ID to submit to. (The playlist ID typically has a prexix of PL)"));
        }

        // Renders a simple_list_item_2, which consists of a title and a body
        // element
        ListAdapter adapter = new ArrayAdapter<ExportActivity.MissingConfig>(this,
                android.R.layout.simple_list_item_2, missingConfigs) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(android.R.layout.simple_list_item_2,
                            null);
                } else {
                    row = convertView;
                }

                TextView titleView = (TextView) row
                        .findViewById(android.R.id.text1);
                TextView bodyView = (TextView) row
                        .findViewById(android.R.id.text2);
                ExportActivity.MissingConfig config = getItem(position);
                titleView.setText(config.title);
                bodyView.setText(config.body);
                return row;
            }
        };

        // Wire the data adapter up to the view
        ListView missingConfigList = (ListView) findViewById(R.id.missing_config_list);
        missingConfigList.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GMS_ERROR_DIALOG:
                break;
            case RESULT_PICK_IMAGE_CROP:
                if (resultCode == RESULT_OK) {
                    mFileURI = data.getData();
                    if (mFileURI != null) {
                        Intent intent = new Intent(this, ReviewActivity.class);
                        intent.setData(mFileURI);
                        startActivity(intent);
                    }
                }
                break;

            case RESULT_VIDEO_CAP:
                if (resultCode == RESULT_OK) {
                    mFileURI = data.getData();
                    if (mFileURI != null) {
                        Intent intent = new Intent(this, ReviewActivity.class);
                        intent.setData(mFileURI);
                        startActivity(intent);
                    }
                }
                break;
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    haveGooglePlayServices();
                } else {
                    checkGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode != Activity.RESULT_OK) {
                    chooseAccount();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null
                        && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(
                            AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        mChosenAccountName = accountName;
                        credential.setSelectedAccountName(accountName);
                        saveAccount();
                    }
                }
                break;
//            case REQUEST_DIRECT_TAG:
//                if (resultCode == Activity.RESULT_OK && data != null
//                        && data.getExtras() != null) {
//                    String youtubeId = data.getStringExtra(YOUTUBE_ID);
//                    if (youtubeId.equals(mVideoData.getYouTubeId())) {
//                        directTag(mVideoData);
//                    }
//                }
//                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        if (broadcastReceiver == null)
            broadcastReceiver = new ExportActivity.UploadBroadcastReceiver();
        Log.v(TAG, broadcastReceiver.toString());
        IntentFilter intentFilter = new IntentFilter(
                REQUEST_AUTHORIZATION_INTENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                broadcastReceiver, intentFilter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACCOUNT_KEY, mChosenAccountName);
    }




//    @Override
//    public void onBackPressed() {
//        // if (mDirectFragment.popPlayerFromBackStack()) {
//        // super.onBackPressed();
//        // }
//    }

//    @Override
//    public ImageLoader onGetImageLoader() {
//        ensureLoader();
//        return mImageLoader;
//    }

    private void loadAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        mChosenAccountName = sp.getString(ACCOUNT_KEY, null);
        invalidateOptionsMenu();
    }

    private void saveAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        sp.edit().putString(ACCOUNT_KEY, mChosenAccountName).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(
                    broadcastReceiver);
        }
        if (isFinishing()) {
            // mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        runOnUiThread(new Runnable() {
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode, ExportActivity.this,
                        REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     */
    private boolean checkGooglePlayServicesAvailable() {
        final int connectionStatusCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        }
        return true;
    }

    private void haveGooglePlayServices() {
        // check if there is already an account selected
        if (credential.getSelectedAccountName() == null) {
            // ask user to choose account
            chooseAccount();
        }
    }

    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
    }

//    private void shareViaYoutube(String type) {
//        boolean found = false;
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("video/*");
//
//        // gets the list of intents that can be loaded.
//        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
//        if (!resInfo.isEmpty()){
//            for (ResolveInfo info : resInfo) {
//                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
//                        info.activityInfo.name.toLowerCase().contains(type) ) {
//                    share.putExtra(Intent.EXTRA_SUBJECT,  "subject");
//                    share.putExtra(Intent.EXTRA_TEXT,     "your text");
//                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(sharedpreferences.getString(FileLoc, null).toString()))); // Optional, just if you wanna share an image.
//                    share.setPackage(info.activityInfo.packageName);
//                    found = true;
//                    break;
//                }
//            }
//            if (!found)
//                return;
//
//            startActivity(Intent.createChooser(share, "Upload to Youtube here~"));
//        }
//    }

//    @Override
//    public ImageLoader onGetImageLoader() {
//        ensureLoader();
//        return mImageLoader;
//    }

    private void ensureLoader() {
        if (mImageLoader == null) {
            // Get the ImageLoader through your singleton class.
            mImageLoader = NetworkSingleton.getInstance(this).getImageLoader();
        }
    }

//    @Override
//    public void onVideoSelected(VideoData video) {
//        mVideoData = video;
//        Intent intent = new Intent(this, PlayActivity.class);
//        intent.putExtra(YOUTUBE_ID, video.getYouTubeId());
//        startActivityForResult(intent, REQUEST_DIRECT_TAG);
//    }

    @Override
    public void onConnected(Bundle bundle) {
//        mCallbacks.onConnected(Plus.AccountApi.getAccountName(mGoogleApiClient));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            Toast.makeText(ExportActivity.this,
                    R.string.connection_to_google_play_failed, Toast.LENGTH_SHORT)
                    .show();

            Log.e(TAG,
                    String.format(
                            "Connection to Play Services Failed, error: %d, reason: %s",
                            connectionResult.getErrorCode(),
                            connectionResult.toString()));
            try {
                connectionResult.startResolutionForResult(ExportActivity.this, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, e.toString(), e);
            }
        }
    }

    private class MissingConfig {

        public final String title;
        public final String body;

        public MissingConfig(String title, String body) {
            this.title = title;
            this.body = body;
        }
    }

    private class UploadBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "INTENT ACTION: " + intent.getAction());
            if (intent.getAction().equals(REQUEST_AUTHORIZATION_INTENT)) {
                Log.d(TAG, "Request auth received - executing the intent");
                Intent toRun = intent
                        .getParcelableExtra(REQUEST_AUTHORIZATION_INTENT_PARAM);
                startActivityForResult(toRun, REQUEST_AUTHORIZATION);
            }
        }
    }
}