package com.Mano.Ad.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Mano.Ad.R;
import com.Mano.Ad.model.contact_annonce_realm;
import com.Mano.Ad.model.contact_home_realm;
import com.Mano.Ad.view.first_second_category;
import com.bumptech.glide.Glide;

import java.util.List;


public class RecyclerAdapter_first_annonce extends RecyclerView.Adapter<RecyclerAdapter_first_annonce.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_annonce_realm> contactslist;
    RecyclerView recyclerView;

    public RecyclerAdapter_first_annonce(Context context, List<contact_annonce_realm> contactslist,RecyclerView recyclerView){
        this.contactslist=contactslist;
        this.context=context;
        this.recyclerView=recyclerView;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.annonce_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {


            myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/flat.ttf");
            Glide.with(context).load(contactslist.get(position).getImage()).error(R.drawable.logo).into(holder.image);
        }
        catch (Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          /*      Intent intent=new Intent(context, first_second_category.class);
                intent.putExtra("name",contactslist.get(position).getname());
                intent.putExtra("id",contactslist.get(position).getId());
                context.startActivity(intent);*/
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.photo);

        }
    }}