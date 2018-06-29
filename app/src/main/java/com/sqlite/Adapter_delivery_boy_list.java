package com.sqlite;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_delivery_boy_list extends BaseAdapter {
    Context context;
    ArrayList<Model_user> array_list = new ArrayList<>();
    ViewHolder holder;
    LayoutInflater layoutInflater;
    public static String pass_value = "";
    private int selectedPosition = -1;

    public Adapter_delivery_boy_list(Context context, ArrayList<Model_user> array_list) {
        this.context = context;
        this.array_list = array_list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return array_list.size();
    }

    @Override
    public Object getItem(int i) {
        return array_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {

        holder = new ViewHolder();
        final Model_user model = array_list.get(i);
        LayoutInflater inflater = LayoutInflater.from(context);

        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.row_dialog_list, null);

            holder.txt_delivery_name = (TextView) v.findViewById(R.id.txt_delivery_name);
            holder.radiobutton = (RadioButton) v.findViewById(R.id.radiobutton);
            holder.ll_delivery = (LinearLayout) v.findViewById(R.id.ll_delivery);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.radiobutton.setChecked(i == selectedPosition);

        //Set the position tag to both radio button and label
        holder.radiobutton.setTag(i);
        holder.ll_delivery.setTag(i);

        holder.txt_delivery_name.setText(model.getName());

        holder.ll_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCheckChanged(view);
                Log.e("deliver_id", model.getId());
                pass_value = model.getId();
            }
        });

        holder.radiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCheckChanged(view);
                Log.e("event_id", model.getId());
                pass_value = model.getId();
            }
        });


        return v;


    }

    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }


    private class ViewHolder {
        TextView txt_delivery_name;
        RadioButton radiobutton;
        LinearLayout ll_delivery;

    }
}
