package com.sqlite;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class Connection {

    public static void postconnection(String url, final Map<String, String> param, final Map<String, String> header,
                                      Context context, Response.Listener<String> lis_res, Response.ErrorListener lis_error) {
        try {
            Log.e("url", url);
            RequestQueue requestQueue = null;
            int MY_SOCKET_TIMEOUT_MS = 40000;
            StringRequest postRequest = new StringRequest(Request.Method.POST, url, lis_res, lis_error
            ) {

                @Override
                protected Map<String, String> getParams() {
                    Log.e("parameter", param + "");
                    return param;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Log.e("header", header + "");
                    return header;
                }
            };

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            requestQueue.add(postRequest);
            postRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getconnectionVolley(String url, final Map<String, String> param, final Map<String, String> header,
                                           final Context context, Response.Listener<String> lis_res, Response.ErrorListener lis_error) {
        try {

            Log.e("url", " " + url);
            RequestQueue requestQueue = null;
            StringRequest getRequest = new StringRequest(Request.Method.GET, url, lis_res, lis_error

            ) {
                @Override
                protected Map<String, String> getParams() {
                    Log.e("parameter", param + "");
                    return param;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Log.e("header", header + "");
                    return header;
                }
            };

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            requestQueue.add(getRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
