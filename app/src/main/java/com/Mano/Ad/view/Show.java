package com.Mano.Ad.view;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Visibility;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Mano.Ad.R;
import com.Mano.Ad.model.Apiclient_home;
import com.Mano.Ad.model.apiinterface_home;
import com.Mano.Ad.model.contact_ad;
import com.Mano.Ad.model.contact_second_home;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Show extends AppCompatActivity {
Toolbar toolbar;
Typeface myTypeface;
ImageView imageView;
TextView title,text;
    private SharedPreferences sharedpref;
    String FACEBOOK_URL = "https://www.facebook.com/Mano.Adve";
    String FACEBOOK_PAGE_ID = "Mano.Adve";
Intent intent;
String name;
    String image;
    String phone;
    String textt;
    private SharedPreferences.Editor edt;
    private List<contact_ad> contactList;
CountDownTimer countDownTimer;
    Calendar calendar;
    private apiinterface_home apiinterface;
int time,point,id;
    String currentDate;
    ImageView share,face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        inisialize();
        MobileAds.initialize(this, "ca-app-pub-1457700690072560~1120668220");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        calendar = Calendar.getInstance();
 share=findViewById(R.id.share);
 face=findViewById(R.id.face);
        sharedpref= getSharedPreferences("ManoAd", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        phone=sharedpref.getString("phone","").trim();
        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/flat.ttf");
        this.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        countDownTimer.cancel();
                    }

                }
        );
        intent=getIntent();
        name=intent.getStringExtra("name");
        image=intent.getStringExtra("image");
        time=intent.getIntExtra("time",0);
        point=intent.getIntExtra("point",0);
        id=intent.getIntExtra("id",0);
        textt=intent.getStringExtra("text");
        text.setText(textt);
        text.setTypeface(myTypeface);
         face.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                 String facebookUrl = getFacebookPageURL(Show.this);
                 facebookIntent.setData(Uri.parse(facebookUrl));
                 startActivity(facebookIntent);
             }
         });
        Glide.with(this).load(image).error(R.drawable.logo).into(imageView);
        title.setTypeface(myTypeface);
        title.setText(name);
        final int counter=(time+1)*1000;

     countDownTimer=   new CountDownTimer(counter, 1000) {

            public void onTick(long millisUntilFinished) {
                Toast.makeText(Show.this , ""+(millisUntilFinished / 1000),Toast.LENGTH_LONG).show();
                //here you can have your logic to set text to edittext
                    if((millisUntilFinished / 1000)==1){
                        fetchInfo_search();
                       // fetchInfo_add();
                    }
            }

            public void onFinish() {

            }

        }.start();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               shareContent();
            }
        });
    }
    @Override
    public void onBackPressed() {
countDownTimer.cancel();
    }
    public void inisialize(){
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        imageView=findViewById(R.id.image_view);
        title=findViewById(R.id.toolbar_title);
        text=findViewById(R.id.textin);

    }
    public void fetchInfo_search(){
        currentDate = DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH).format(calendar.getTime());
       // Toast.makeText(Show.this,currentDate,Toast.LENGTH_LONG).show();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_ad>> call  = apiinterface.getcontacts_searchadview(id,phone,currentDate);
        call.enqueue(new Callback<List<contact_ad>>() {
            @Override
            public void onResponse(Call<List<contact_ad>> call, Response<List<contact_ad>> response) {
                contactList=response.body();
                if(contactList.isEmpty()||contactList==null){

                    fetchInfo_add();
                }
                else {
                    Toast.makeText(Show.this,"لقد شاهدت هذا اليوم ",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<contact_ad>> call, Throwable t) {
                Toast.makeText(Show.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo_add(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_addtoview(id,phone,currentDate);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    fetchInfo();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Show.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_addpoint(point,phone);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(Show.this,"YOU WIN "+point+"POINTS",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Show.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void shareContent(){

        Bitmap bitmap =getBitmapFromView(imageView);
        try {
            File file = new File(this.getExternalCacheDir(),"logicchip.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, textt);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share image via"));
            fetchInfo_search_share();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            bgDrawable.draw(canvas);
        }   else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
    public void fetchInfo_search_share(){
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_ad>> call  = apiinterface.getcontacts_searchadview_share(id,phone);
        call.enqueue(new Callback<List<contact_ad>>() {
            @Override
            public void onResponse(Call<List<contact_ad>> call, Response<List<contact_ad>> response) {
                contactList=response.body();
                if(contactList.isEmpty()||contactList==null){

                    fetchInfo_add_share();
                }
                else {
                    Toast.makeText(Show.this,"لقد شاركت هذا من قبل ",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<contact_ad>> call, Throwable t) {
                Toast.makeText(Show.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo_add_share(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_addtoview_share(id,phone);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                fetchInfo_share();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Show.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo_share(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_addpoint(1,phone);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            //    Toast.makeText(Show.this,"YOU WIN "+"5"+"POINTS",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Show.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
}
