package com.Mano.Ad.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Mano.Ad.R;
import com.Mano.Ad.model.contact_second_home_realm;
import com.Mano.Ad.model.contact_users_realm;
import com.Mano.Ad.view.Show;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerAdapter_users extends RecyclerView.Adapter<RecyclerAdapter_users.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_users_realm> contactslist;

    int ad=5;
    public RecyclerAdapter_users(Context context, List<contact_users_realm> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_users,parent,false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/flat.ttf");

        holder.Name.setText(contactslist.get(position).getName());
        holder.Name.setTypeface(myTypeface);

        holder.point.setText(contactslist.get(position).getPoint()+" Pt");
        holder.point.setTypeface(myTypeface);
        int x=position+1;
        holder.num.setText(""+x);
        holder.num.setTypeface(myTypeface);

        SharedPreferences sharedpref= context.getSharedPreferences("ManoAd", Context.MODE_PRIVATE);
        if(contactslist.get(position).getPhone().equals(sharedpref.getString("phone","").trim())
                &&contactslist.get(position).getName().toString().equals(sharedpref.getString("name","").trim())){
            //  holder.relativeLayout.setBackgroundColor(Color.YELLOW);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,phone,point,num;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.name);

            point= itemView.findViewById(R.id.point);
            relativeLayout=itemView.findViewById(R.id.relative);
            num=itemView.findViewById(R.id.num);

        }
    }}