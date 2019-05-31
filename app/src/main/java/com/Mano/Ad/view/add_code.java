package com.Mano.Ad.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.Mano.Ad.R;
import com.Mano.Ad.model.Apiclient_home;
import com.Mano.Ad.model.ad_code;
import com.Mano.Ad.model.apiinterface_home;
import com.Mano.Ad.model.contact_second_home;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_code extends AppCompatActivity {
    TextInputLayout textInputLayoutname;
    TextInputEditText textInputEditTextname;
    private SharedPreferences sharedpref;
    Typeface myTypeface;
    Toolbar toolbar;
    private List<ad_code> contactList;
    int point=0;
    private apiinterface_home apiinterface;
    AppCompatButton regesiter;
    private SharedPreferences.Editor edt;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_code);
        inisialize();
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
                    }
                }
        );
        regesiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regesiter.setClickable(false);
                fetchInfo();

            }
        });
    }
    public void inisialize(){
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        textInputLayoutname=(TextInputLayout)findViewById(R.id.textInputLayoutName);
        textInputEditTextname=(TextInputEditText)findViewById(R.id.textInputEditTextName);
        regesiter=(AppCompatButton)findViewById(R.id.appCompatButtonRegister);

    }
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<ad_code>> call = apiinterface.getcontacts_searchcode(textInputEditTextname.getText().toString());
        call.enqueue(new Callback<List<ad_code>>() {
            @Override
            public void onResponse(Call<List<ad_code>> call, Response<List<ad_code>> response) {
                contactList = response.body();
                try {


                if(!(contactList.isEmpty())||!(contactList==null)){
                 point=contactList.get(0).getPoint();
                 String code=contactList.get(0).getCode();
                    fetchInfo_share(point);
                    fetchInfo_delete(code);
                }
                else {
                    Toast.makeText(add_code.this,"كود غير صالح  ",Toast.LENGTH_LONG).show();
                }

                }catch (Exception e){
                    Toast.makeText(add_code.this,"كود غير صالح  ",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<ad_code>> call, Throwable t) {
                Toast.makeText(add_code.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo_share(final int point){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_addpoint(point,phone);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   Toast.makeText(add_code.this,"YOU WIN "+point+"POINTS",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(add_code.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo_delete(final String code){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_delcode(code);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Toast.makeText(add_code.this,"YOU WIN "+point+"POINTS",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
              //  Toast.makeText(add_code.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
