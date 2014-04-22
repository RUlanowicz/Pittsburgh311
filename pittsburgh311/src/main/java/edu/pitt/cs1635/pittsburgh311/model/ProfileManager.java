package edu.pitt.cs1635.pittsburgh311.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ulanowicz on 3/24/14.
 */
public class ProfileManager {
    public interface Fields {
        String FIRST_NAME = "firstName";
        String LAST_NAME = "lastName";
        String EMAIL = "email";
        String DATE_MODIFIED = "dateModified";
        String PHONE_NUMBER = "phoneNumber";
        String HOME_ADDRESS = "homeAddress";
        String REGISTERED = "registered";
    }

    private static ProfileManager instance = null;
    protected ProfileManager() {}

    public static ProfileManager getInstance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    private SharedPreferences getPreferences (Context context) {
        return context.getSharedPreferences("ProfilePreferences", Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    // Profile properties

    public void setFirstName(Context context, String firstName) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(Fields.FIRST_NAME, firstName);
        editor.putLong(Fields.DATE_MODIFIED, System.currentTimeMillis());
        editor.commit();
    }

    public String getFirstName(Context context) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(Fields.FIRST_NAME, null);
    }

    public void setLastName(Context context, String lastName) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(Fields.LAST_NAME, lastName);
        editor.putLong(Fields.DATE_MODIFIED, System.currentTimeMillis());
        editor.commit();
    }

    public String getLastName(Context context) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(Fields.LAST_NAME, null);
    }

    public void setEmail(Context context, String email) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(Fields.EMAIL, email);
        editor.putLong(Fields.DATE_MODIFIED, System.currentTimeMillis());
        editor.commit();
    }

    public String getEmail(Context context) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(Fields.EMAIL, null);
    }

    public void setPhoneNumber(Context context, String phoneNumber){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(Fields.PHONE_NUMBER, phoneNumber);
        editor.putLong(Fields.DATE_MODIFIED, System.currentTimeMillis());
        editor.commit();
    }

    public String getPhoneNumber(Context context){
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(Fields.PHONE_NUMBER, null);
    }

    public void setHomeAddress(Context context, String homeAddress){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(Fields.HOME_ADDRESS, homeAddress);
        editor.putLong(Fields.DATE_MODIFIED, System.currentTimeMillis());
        editor.commit();
    }

    public String getHomeAddress(Context context){
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(Fields.HOME_ADDRESS, null);
    }

    public void setRegistered(Context context, String homeAddress){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(Fields.REGISTERED, homeAddress);
        editor.putLong(Fields.DATE_MODIFIED, System.currentTimeMillis());
        editor.commit();
    }

    public String getRegistered(Context context){
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(Fields.REGISTERED, null);
    }
}