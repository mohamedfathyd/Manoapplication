package com.Mano.Ad.view;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.Mano.Ad.Adapter.RecyclerAdapter_first;
import com.Mano.Ad.Adapter.RecyclerAdapter_first_annonce;
import com.Mano.Ad.R;
import com.Mano.Ad.model.Apiclient_home;
import com.Mano.Ad.model.apiinterface_home;
import com.Mano.Ad.model.contact_annonce;
import com.Mano.Ad.model.contact_annonce_realm;
import com.Mano.Ad.model.contact_home;
import com.Mano.Ad.model.contact_home_realm;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private RecyclerView recyclerView,recyclerView2;
private RecyclerView.LayoutManager layoutManager;
CountDownTimer countDownTimer;
private RecyclerAdapter_first recyclerAdapter;
        private RecyclerAdapter_first_annonce recyclerAdapter_annonce;
private List<contact_home> contactList;
        private List<contact_annonce> contactList_annonce;
private apiinterface_home apiinterface;
int x=0;
int y=0;
        ProgressBar progressBar;
        Realm realm;
        TextView textView;
private SharedPreferences sharedpref;
        Typeface myTypeface;
private SharedPreferences.Editor edt;
TextView points;
    login__ login__;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("ManoAD");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        textView=(TextView)headerView.findViewById(R.id.textnav);
        points=headerView.findViewById(R.id.points);

        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/flat.ttf");
        sharedpref= getSharedPreferences("ManoAd", Context.MODE_PRIVATE);
             edt = sharedpref.edit();
    login__=new login__();
    login__.fetchInfo(this,sharedpref.getString("phone","").trim(),sharedpref.getString("password","").trim());

    textView.setText(sharedpref.getString("name","").trim());
        textView.setTypeface(myTypeface);
    points.setTypeface(myTypeface);
    points.setText(sharedpref.getString("points","").trim()+" Pt");
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerview2);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);

        if (!isNetworkAvailable(MainActivity.this)) {
        showdata();
        showdata_annonce();
        progressBar.setVisibility(View.GONE);
        } else {
       fetchInfo();
                fetchInfo_annonce();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
try {


    final int counter=100*5000;

    countDownTimer=   new CountDownTimer(counter, 5000) {

        public void onTick(long millisUntilFinished) {
           // Toast.makeText(MainActivity.this , ""+(millisUntilFinished / 1000),Toast.LENGTH_LONG).show();
            recyclerView2.smoothScrollToPosition(y);
                  y++;
                  if(y>x){
                      y=0;
                  }
            //here you can have your logic to set text to edittext

        }

        public void onFinish() {

        }

    }.start();}
    catch (Exception e){}
        navigationView.setNavigationItemSelectedListener(this);


        }

@Override
public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
        } else {
        super.onBackPressed();
        }
        }


@SuppressWarnings("StatementWithEmptyBody")
@Override
public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id== R.id.who){
            startActivity(new Intent(MainActivity.this,About.class));
        }
        if(id==R.id.about){
            startActivity(new Intent(MainActivity.this,Who_us.class));

        }
          if(id == R.id.main){
              login__=new login__();
              login__.fetchInfo(this,sharedpref.getString("phone","").trim(),sharedpref.getString("password","").trim());


                  startActivity(new Intent(MainActivity.this,MainActivity.class));
                  finish();
          }
          if(id==R.id.user_list){
                  startActivity(new Intent(MainActivity.this,User_list.class));
          }
    if(id==R.id.extra){
        startActivity(new Intent(MainActivity.this,add_code.class));
    }
        if (id == R.id.profile) {
        // Handle the camera action
      startActivity(new Intent(MainActivity.this,profile.class));
        } else if (id == R.id.edit) {
       startActivity(new Intent(MainActivity.this,edit_profile.class));
        } else if (id == R.id.exit) {
        edt.putString("name","");
        edt.putString("address","");
        edt.putString("phone","");
        edt.putString("remember","");
        edt.apply();
        finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }
public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_home>> call = apiinterface.getcontacts_first();
        call.enqueue(new Callback<List<contact_home>>() {
@Override
public void onResponse(Call<List<contact_home>> call, Response<List<contact_home>> response) {
        contactList = response.body();
        progressBar.setVisibility(View.GONE);
        WriteTodatabase(contactList);

        }

@Override
public void onFailure(Call<List<contact_home>> call, Throwable t) {

        }
        });
        }
public void WriteTodatabase(List<contact_home> contactList){
//             realm.delete(subject_content_realm.class);
        // Create a new object
        deletedata();
        if(!(contactList==null)){
        for(int i=0;i<contactList.size();i++){
        realm.beginTransaction();
        contact_home_realm images = realm.createObject(contact_home_realm.class);
        images.setId(contactList.get(i).getId());
        images.setname(contactList.get(i).getname());
        images.setImg(contactList.get(i).getImg());
        realm.commitTransaction();
        }
        showdata();}

        }
public void showdata(){

        RealmResults<contact_home_realm> content_realms = realm.where(contact_home_realm.class).findAll();
        if (content_realms.isEmpty() || content_realms.equals(null)) {

        } else {    // realm.beginTransaction();
        List<contact_home_realm> result = content_realms;


        recyclerAdapter=new RecyclerAdapter_first(MainActivity.this,result);
        recyclerView.setAdapter(recyclerAdapter);
        }
        }

public void deletedata(){
        realm.beginTransaction();
        RealmResults<contact_home_realm> content_realms=realm.where(contact_home_realm.class).findAll();
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
        public void fetchInfo_annonce(){
                apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
                Call<List<contact_annonce>> call = apiinterface.getcontacts_annonce();
                call.enqueue(new Callback<List<contact_annonce>>() {
                        @Override
                        public void onResponse(Call<List<contact_annonce>> call, Response<List<contact_annonce>> response) {
                                contactList_annonce = response.body();
                                progressBar.setVisibility(View.GONE);
                                WriteTodatabase_annonce(contactList_annonce);

                        }

                        @Override
                        public void onFailure(Call<List<contact_annonce>> call, Throwable t) {

                        }
                });
        }
        public void WriteTodatabase_annonce(List<contact_annonce> contactList_){
//             realm.delete(subject_content_realm.class);
                // Create a new object
                deletedata_annonce();
            if(!(contactList_==null)){
                for(int i=0;i<contactList_.size();i++){
                        realm.beginTransaction();
                        contact_annonce_realm images = realm.createObject(contact_annonce_realm.class);

                        images.setImage(contactList_.get(i).getImage());

                        realm.commitTransaction();
                }
                showdata_annonce();}
        }
        public void showdata_annonce(){

                RealmResults<contact_annonce_realm> content_realms = realm.where(contact_annonce_realm.class).findAll();
                if (content_realms.isEmpty() || content_realms.equals(null)) {

                } else {    // realm.beginTransaction();
                        List<contact_annonce_realm> result2 = content_realms;

                             x=result2.size();
                        recyclerAdapter_annonce=new RecyclerAdapter_first_annonce(MainActivity.this,result2,recyclerView2);
                        recyclerView2.setAdapter(recyclerAdapter_annonce);
                }
        }

        public void deletedata_annonce(){
                realm.beginTransaction();
                RealmResults<contact_annonce_realm> content_realms=realm.where(contact_annonce_realm.class).findAll();
                content_realms.deleteAllFromRealm();
                realm.commitTransaction();
        }


        }
