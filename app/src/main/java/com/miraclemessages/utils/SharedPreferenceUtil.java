package com.miraclemessages.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.miraclemessages.models.VolunteerUserDetails;

import static com.miraclemessages.common.Constants.Email;
import static com.miraclemessages.common.Constants.Location;
import static com.miraclemessages.common.Constants.Name;
import static com.miraclemessages.common.Constants.Phone;
import static com.miraclemessages.common.Constants.myPreferences;

/**
 * Created by shobhit on 2017-08-06.
 */

public class SharedPreferenceUtil {

    private static final Class TAG = SharedPreferenceUtil.class;

    public static boolean saveUserDetails(Context context, VolunteerUserDetails volunteerUserDetails) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, volunteerUserDetails.getName());
        editor.putString(Email, volunteerUserDetails.getEmail());
        editor.putString(Phone, volunteerUserDetails.getPhone());
        editor.putString(Location, volunteerUserDetails.getLocation());
        return editor.commit();
    }

    public static VolunteerUserDetails getVolunteerUserDetails(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        VolunteerUserDetails volunteerUserDetails = new VolunteerUserDetails();
        volunteerUserDetails.setName(sharedpreferences.getString(Name, null));
        volunteerUserDetails.setEmail(sharedpreferences.getString(Email, null));
        volunteerUserDetails.setPhone(sharedpreferences.getString(Phone, null));
        volunteerUserDetails.setLocation(sharedpreferences.getString(Location, null));
        return volunteerUserDetails;
    }

    public static String getVolunteerUserName(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Name, null);
    }

    /**
     * Deletes all the shared preferences for the app. Useful when changing URL's.
     */
    public static boolean deleteAllSharedPreferences(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        return editor.clear().commit();
    }


}
