package com.miraclemessages;

import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;

public class AuthClass {
    // Register an API key here: https://console.developers.google.com
    public static final String KEY = "61147001483-hce219qvpuvm9g9863oph6fiddigkh2r.apps.googleusercontent.com";

    public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};
}