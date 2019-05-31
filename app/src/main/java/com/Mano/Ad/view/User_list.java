package com.Mano.Ad.view;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.Mano.Ad.Adapter.RecyclerAdapter;
import com.Mano.Ad.Adapter.RecyclerAdapter_users;
import com.Mano.Ad.R;
import com.Mano.Ad.model.Apiclient_home;
import com.Mano.Ad.model.apiinterface_home;
import com.Mano.Ad.model.contact_second_home;
import com.Mano.Ad.model.contact_second_home_realm;
import com.Mano.Ad.model.contact_users;
import com.Mano.Ad.model.contact_users_realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_list extends AppCompatActivity {


    RecyclerView recyclerView;
    ProgressBar progressBar;
    private apiinterface_home apiinterface;
    private List<contact_users> contactList;
    private RecyclerAdapter_users recyclerAdapter;
    Realm realm;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        realm = Realm.getDefaultInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        this.setTitle("");
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        if (!isNetworkAvailable(User_list.this)) {
            showdata();
            progressBar.setVisibility(View.GONE);
        } else {
            fetchInfo();
        }


    }

    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_users>> call = apiinterface.getcontacts_users();
        call.enqueue(new Callback<List<contact_users>>() {
            @Override
            public void onResponse(Call<List<contact_users>> call, Response<List<contact_users>> response) {
                contactList = response.body();
                progressBar.setVisibility(View.GONE);
                WriteTodatabase(contactList);

            }

            @Override
            public void onFailure(Call<List<contact_users>> call, Throwable t) {

            }
        });
    }
    public void WriteTodatabase(List<contact_users> contactList){
//             realm.delete(subject_content_realm.class);
        // Create a new object
        deletedata();
         if(!(contactList==null)){
        for(int i=0;i<contactList.size();i++){
            realm.beginTransaction();
            contact_users_realm images = realm.createObject(contact_users_realm.class);

            images.setName(contactList.get(i).getName());
            images.setPhone(contactList.get(i).getPhone());
            images.setPoint(contactList.get(i).getPoint());
            realm.commitTransaction();
        }
        showdata();}
    }
    public void showdata(){

        RealmResults<contact_users_realm> content_realms = realm.where(contact_users_realm.class).findAll();
        if (content_realms.isEmpty() || content_realms.equals(null)) {

        } else {    // realm.beginTransaction();
            List<contact_users_realm> result = content_realms;


            recyclerAdapter=new RecyclerAdapter_users(User_list.this,result);
            recyclerView.setAdapter(recyclerAdapter);
        }
    }

    public void deletedata(){
        realm.beginTransaction();
        RealmResults<contact_users_realm> content_realms=realm.where(contact_users_realm.class).findAll();
        content_realms.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public boolean isNetworkAvailable(Context ctx) {
        if (ctx == null)
            return false;

        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


}
