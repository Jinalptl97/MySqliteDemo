package com.sqlite;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 12/5/2016.
 */

public class Config {

    //public static String app_url = "http://gopickapp.com/webservices/index.php/";
    // public static String app_url = "http://gopickapp.com/beta/webservices/index.php/";
    public static String app_url = "http://api.foodskart.club/index.php/";


    public static String headunm = "parshwa";
    public static String headkey = "ABD789QUat5864";
    public static String host = "api.foodskart.club";
    public static String Language = "userlanguage";
    // public static String image_url = "http://app.sendantech.com/";

    public static String Message = "Server Error....Please try again...!!!";
    public static String Key_status = "";

    public final static class Fragmentt_ID {
        public final static int One = 1;
        public final static int two = 2;
        public final static int three = 3;
        public final static int four = 4;

    }

    public static String Login = "loginvendor";
    public static String Logoutvendor = "logoutvendor";
    public static String Dashboard = "dashboard";
    public static String Vendororderlist = "vendororderlist";
    public static String Changestatus = "changestatus";
    public static String Vendororderdetail = "vendororderdetail";
    public static String Vendornotification = "vendornotification";
    public static String Vendorsidemenu = "vendorsidemenu";
    public static String Forgotpassword = "forgotpassword";
    public static String deliveryboylist = "deliveryboylist";
    public static String orderassign = "orderassign";


    // code to define map header
    public static Map<String, String> getHeaderParam() {
        Map<String, String> header = new HashMap<>();
        header.put("username", Config.headunm);
        header.put("apikey", Config.headkey);
        return header;
    }



    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public void Change_Language(Context context, String lang) {
        //0 = English 1 = Arabic
        Locale locale = new Locale(lang.equals("1") ? "ar" : "en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static String Check_Language() {
        // 0 = English , 1 = Arabic
        return Locale.getDefault().getDisplayLanguage().equals("Arabic") ? "1" : "0";
    }


    public static String MobilePattern = "[0-9]{10}";

    public static class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static boolean checkPermission(final Context context) {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

//    public static void Select_Status(ArrayList<LinearLayout> ll, int pos, ArrayList<ImageView> Arr_Chk_cat) {
//        for (int i = 0; i < ll.size(); i++) {
//            Arr_Chk_cat.get(i).setImageResource((i == pos) ? R.drawable.radio_selected : R.drawable.radio_unselected);
//        }
//
//    }
}
