package com.sqlite;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 5/2/2018.
 */

public class Dash_order extends RecyclerView.Adapter<Dash_order.Viewholder> {

    FragmentActivity activity;
    ArrayList<Model_order> arrayList = new ArrayList<>();
    String val = "";
    SecondActivity dashboard;


    public Dash_order(SecondActivity dashboard, FragmentActivity activity, ArrayList<Model_order> arraylist) {
        super();
        this.activity = activity;
        this.arrayList = arraylist;
        this.dashboard = dashboard;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_dash_order, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final Viewholder holder, int position) {
        final Model_order model = arrayList.get(position);

        holder.order_no.setText(model.getOrder_no());
        holder.cust_name.setText(model.getFname() + " " + model.getLname());
        holder.order_amount.setText(model.getAmount());
        holder.txt_order_date.setText(model.getOrder_date());


    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {

        TextView order_no, cust_name, order_amount, txt_order_date, tv_order_distance;
        CardView card_order;


        public Viewholder(View view) {
            super(view);

            order_no = view.findViewById(R.id.order_no);
            cust_name = view.findViewById(R.id.cust_name);
            order_amount = view.findViewById(R.id.order_amount);
            txt_order_date = view.findViewById(R.id.txt_order_date);
            tv_order_distance = view.findViewById(R.id.tv_order_distance);
            card_order = view.findViewById(R.id.card_order);


        }
    }

}
