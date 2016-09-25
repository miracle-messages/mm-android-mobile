package com.miraclemessages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
    private static YouTube youtube;

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
            List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");
                try {
                    // Authorize the request.
                    Credential credential = Auth.authorize(scopes, "uploadvideo");

                    // This object is used to make YouTube Data API requests.
                    youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).setApplicationName(
                            "youtube-cmdline-uploadvideo-sample").build();

                    System.out.println("Uploading: " + SAMPLE_VIDEO_FILENAME);

                    // Add extra information to the video before uploading.
                    Video videoObjectDefiningMetadata = new Video();

                    // Set the video to be publicly visible. This is the default
                    // setting. Other supporting settings are "unlisted" and "private."
                    VideoStatus status = new VideoStatus();
                    status.setPrivacyStatus("unlisted");
                    videoObjectDefiningMetadata.setStatus(status);

                    // Most of the video's metadata is set on the VideoSnippet object.
                    VideoSnippet snippet = new VideoSnippet();

                    // This code uses a Calendar instance to create a unique name and
                    // description for test purposes so that you can easily upload
                    // multiple files. You should remove this code from your project
                    // and use your own standard names instead.
                    Calendar cal = Calendar.getInstance();
                    snippet.setTitle("Test Upload via Java on " + cal.getTime());
                    snippet.setDescription(
                            "Video uploaded via YouTube Data API V3 using the Java library " + "on " + cal.getTime());

                    // Set the keyword tags that you want to associate with the video.
                    List<String> tags = new ArrayList<String>();
                    tags.add("test");
                    tags.add("example");
                    tags.add("java");
                    tags.add("YouTube Data API V3");
                    tags.add("erase me");
                    snippet.setTags(tags);

                    // Add the completed snippet object to the video resource.
                    videoObjectDefiningMetadata.setSnippet(snippet);

                    InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT,
                            UploadVideo.class.getResourceAsStream(sharedpreferences.getString(FileLoc, null).toString()));

                    // Insert the video. The command sends three arguments. The first
                    // specifies which information the API request is setting and which
                    // information the API response should return. The second argument
                    // is the video resource that contains metadata about the new video.
                    // The third argument is the actual video content.
                    YouTube.Videos.Insert videoInsert = youtube.videos()
                            .insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

                    // Set the upload type and add an event listener.
                    MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

                    // Indicate whether direct media upload is enabled. A value of
                    // "True" indicates that direct media upload is enabled and that
                    // the entire media content will be uploaded in a single request.
                    // A value of "False," which is the default, indicates that the
                    // request will use the resumable media upload protocol, which
                    // supports the ability to resume an upload operation after a
                    // network interruption or other transmission failure, saving
                    // time and bandwidth in the event of network failures.
                    uploader.setDirectUploadEnabled(false);

                    MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                        public void progressChanged(MediaHttpUploader uploader) throws IOException {
                            switch (uploader.getUploadState()) {
                                case INITIATION_STARTED:
                                    System.out.println("Initiation Started");
                                    break;
                                case INITIATION_COMPLETE:
                                    System.out.println("Initiation Completed");
                                    break;
                                case MEDIA_IN_PROGRESS:
                                    System.out.println("Upload in progress");
                                    System.out.println("Upload percentage: " + uploader.getProgress());
                                    break;
                                case MEDIA_COMPLETE:
                                    System.out.println("Upload Completed!");
                                    break;
                                case NOT_STARTED:
                                    System.out.println("Upload Not Started!");
                                    break;
                            }
                        }
                    };
                    uploader.setProgressListener(progressListener);

                    // Call the API and upload the video.
                    Video returnedVideo = videoInsert.execute();

                    // Print data about the newly inserted video from the API response.
                    System.out.println("\n================== Returned Video ==================\n");
                    System.out.println("  - Id: " + returnedVideo.getId());
                    System.out.println("http://www.youtube.com/watch?v=" + returnedVideo.getId());
                    System.out.println("  - Title: " + returnedVideo.getSnippet().getTitle());
                    System.out.println("  - Tags: " + returnedVideo.getSnippet().getTags());
                    System.out.println("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
                    System.out.println("  - Video Count: " + returnedVideo.getStatistics().getViewCount());

                } catch (GoogleJsonResponseException e) {
                    System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                            + e.getDetails().getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.err.println("IOException: " + e.getMessage());
                    e.printStackTrace();
                } catch (Throwable t) {
                    System.err.println("Throwable: " + t.getMessage());
                    t.printStackTrace();
                }

//                try {
//                    //startActivity(Intent.createChooser(i, "Send mail..."));
//                    shareViaYoutube("youtube");
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(ExportActivity.this,
//                            "Oh noes! Youtube is not installed.", Toast.LENGTH_SHORT).show();
//                }
            }

        });

        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(ExportActivity.this, PreCameraActivity.class));
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
