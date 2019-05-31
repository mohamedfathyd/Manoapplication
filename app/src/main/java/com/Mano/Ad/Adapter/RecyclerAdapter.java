package com.Mano.Ad.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Mano.Ad.R;
import com.Mano.Ad.model.contact_second_home_realm;
import com.Mano.Ad.view.Show;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_second_home_realm> contactslist;
    InterstitialAd mInterstitialAd;

    public RecyclerAdapter(Context context, List<contact_second_home_realm> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/flat.ttf");

        holder.Name.setText(contactslist.get(position).getname());
        holder.Name.setTypeface(myTypeface);
        Glide.with(context).load(contactslist.get(position).getImg()).error(R.drawable.logo).into(holder.image);
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-1457700690072560/8624277785");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=contactslist.get(position).getId();
                String name=contactslist.get(position).getname();
                int point=contactslist.get(position).getPoint();
                int time=contactslist.get(position).getTime();
                String image=contactslist.get(position).getImg();
                Intent intent=new Intent(context, Show.class);
                intent.putExtra("name",name);
                intent.putExtra("image",image);
                intent.putExtra("point",point);
                intent.putExtra("time",time);
                intent.putExtra("id",id);
                intent.putExtra("text",contactslist.get(position).getText());
                context.startActivity(intent);
                mInterstitialAd.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.name);
            image=(ImageView)itemView.findViewById(R.id.photo);

        }
    }}