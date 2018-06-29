package com.sqlite;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    Context context = this;
    RecyclerView recycle_order;
    ArrayList<Model_order> arry_order = new ArrayList<>();
    Dash_order adapter;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        helper = new DBHelper(this);
        helper.upgrade();


        recycle_order = (RecyclerView) findViewById(R.id.recycle_order);


      //  get_all_order();

       arry_order = helper.getorderlist();
        int numberOfColumns = 1;
        recycle_order.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        adapter = new Dash_order(SecondActivity.this, this, arry_order);
        recycle_order.setAdapter(adapter);


    }


    public void get_all_order() {

        //obj_dialog.show();


        String url = Config.app_url + "getallorder";
        JSONObject jobj_loginuser = new JSONObject();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("did", "15");

            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("getallorder", jarray_loginuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        HashMap<String, String> param = new HashMap<>();
        param.put("data", "" + jobj_loginuser.toString().replaceAll("\\\\", ""));
        Log.e("deliver_status_request", ":" + param.toString());


        final Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        header.put("host", Config.host);

        Response.Listener<String> lis_res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                // obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {
                        JSONArray jarray = new JSONArray(jobj.getString("data"));

                        arry_order.clear();

                        for (int i = 0; i < jarray.length(); i++) {
                            Model_order model = new Model_order();
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            model.setOrder_id(jobj1.getString("orderid"));
                            model.setOrder_no(jobj1.getString("orderno"));
                            model.setFname(jobj1.getString("fname"));
                            model.setLname(jobj1.getString("lname"));
                            model.setAmount(jobj1.getString("amount"));
                            model.setUserlat(jobj1.getString("userlat"));
                            model.setUserlong(jobj1.getString("userlong"));
                            model.setAddress(jobj1.getString("address"));
                            model.setStatus(jobj1.getString("status"));
                            model.setOrder_date(jobj1.getString("orderdatetime"));

                            arry_order.add(model);

                            helper.insertt(model);

                        }
                        adapter.notifyDataSetChanged();


                    }
                    //  Log.e("Sizeee", ":" + res_list.size());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener lis_error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  signup.setClickable(true);
                // obj_dialog.dismiss();
            }
        };
        Connection.postconnection(url, param, header, context, lis_res, lis_error);


    }


}
