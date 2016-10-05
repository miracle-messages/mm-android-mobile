package com.miraclemessages;

import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;

public class AuthClass {
    // Register an API key here: https://console.developers.google.com
    public static final String KEY = "61147001483-072jegngojqfacobapdt0vb5rgk5vgvh.apps.googleusercontent.com";

    public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};
}