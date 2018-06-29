package com.sqlite;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Context context = this;
    ListView list_delivery_boy;
    ArrayList<Model_user> arrayList = new ArrayList<>();
    Adapter_delivery_boy_list adapter_dialog_event;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);
        helper.upgrade();

        list_delivery_boy = findViewById(R.id.list_delivery_boy);
       // get_delivery_list();



        arrayList=helper.getChatBeanArrayList();
        adapter_dialog_event = new Adapter_delivery_boy_list(context, arrayList);
        list_delivery_boy.setAdapter(adapter_dialog_event);




    }

    private void get_delivery_list() {

        String url = Config.app_url + Config.deliveryboylist + "/" + "110";
        String json = "";


        HashMap<String, String> param = new HashMap<>();
        param.put("data", json);

        Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        header.put("host", Config.host);
        // header.put(Config.Language, pref.getLanguage());

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
                        for (int i = 0; i < jarray.length(); i++) {
                            Model_user model_user = new Model_user();
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            model_user.setId(jobj1.getString("id"));
                            model_user.setName(jobj1.getString("username"));
                            arrayList.add(model_user);

                            helper.insert(model_user);
                        }


                        adapter_dialog_event.notifyDataSetChanged();


                    } else {

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
        Connection.getconnectionVolley(url, param, header, context, lis_res, lis_error);

    }


}
