package com.miraclemessages;

import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;

public class AuthClass {
    // Register an API key here: https://console.developers.google.com
    public static final String KEY = "AIzaSyCA41hoBvwzrILvReH0uwpBWfv0OAJtY_Q";

    public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};
}