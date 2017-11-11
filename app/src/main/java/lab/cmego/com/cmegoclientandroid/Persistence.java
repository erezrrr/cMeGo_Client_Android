package lab.cmego.com.cmegoclientandroid;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import lab.cmego.com.cmegoclientandroid.settings.DefaultValues;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Amit Ishai on 12/21/2016.
 */

public class Persistence {

    private static final String PREFS_NAME = "SharedPreferences";
    private static final String AUTHENTICATION_TOKEN = "authentication_token";
    private static final String PASSWORD = "password";
    private static final String ALL_DATA = "all_data";
    private static final String SHOW_NOTIFICATIONS = "show_notifications";

    private static Persistence sSharedInstance;
    private Context mContext;

    public static Persistence getSharedInstance(){
        if(sSharedInstance == null){
            sSharedInstance = new Persistence();
        }

        return sSharedInstance;
    }

    public void init(Context context){
        mContext = context;
    }

    public void setAuthenticationToken(String token) {
        setSecure(AUTHENTICATION_TOKEN, token);
    }

    public String getAuthenticationToken(){
        return getSecure(AUTHENTICATION_TOKEN);
    }

    public void setShowNotifications(boolean value){
        setBooleanValue(SHOW_NOTIFICATIONS, value);
    }

    public boolean getShowNotifications(){
        return getBooleanValue(SHOW_NOTIFICATIONS, DefaultValues.SHOW_NOTIFICATIONS);
    }

    private void setSecure(String key, String value) {
//        String encrypted = ConcealWrapper.getSharedInstance().encrypt(key, value);
//        setValue(key, encrypted);
    }

    private void setValue(String key, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private void setIntValue(String key, int value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private String getValue(String key) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(key, defaultValue);
    }

    private int getIntValue(String key, int defaultValue) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(key, defaultValue);
    }

    private void setValue(String key, Set<String> value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    private Set<String> getStringSet(String key) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getStringSet(key, new HashSet<String>());
    }

    private String getSecure(String key){
//        String encrypted = getValue(key);
//
//        if(TextUtils.isEmpty(encrypted)){
            return null;
//        }
//
//        return ConcealWrapper.getSharedInstance().decrypt(key, encrypted);
    }

    public String getPassword() {
        return getSecure(PASSWORD);
    }

    public void setPassword(String password){
        setSecure(PASSWORD, password);
    }

    public void setAllData(String allData){
        setValue(ALL_DATA, allData);
    }

    public String getAllData(){
        return getValue(ALL_DATA);
    }
}