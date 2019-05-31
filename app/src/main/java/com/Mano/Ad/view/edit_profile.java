package com.Mano.Ad.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.Mano.Ad.R;
import com.Mano.Ad.model.Apiclient_home;
import com.Mano.Ad.model.apiinterface_home;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_profile extends AppCompatActivity {
    TextInputLayout textInputLayoutname,textInputLayoutaddress,textInputLayoutphone,textInputLayoutpassword,textInputLayoutcountry,textInputLayoutage;
    TextInputEditText textInputEditTextname,textInputEditTextaddress,textInputEditTextphone,textInputEditTextpassword,textInputEditTextcountry,textInputEditTextage;
    private SharedPreferences sharedpref;
    Typeface myTypeface;
    Toolbar toolbar;
    Call<ResponseBody> call = null;
    private apiinterface_home apiinterface;
    AppCompatButton regesiter;
    String oldpass;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        inisialize();
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
        sharedpref= getSharedPreferences("ManoAd", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        textInputEditTextaddress.setTypeface(myTypeface);
        textInputEditTextaddress.setText(sharedpref.getString("address","").trim());
        textInputEditTextname.setTypeface(myTypeface);
        textInputEditTextname.setText(sharedpref.getString("name","").trim());
        textInputEditTextcountry.setTypeface(myTypeface);
        textInputEditTextcountry.setText(sharedpref.getString("country","").trim());
        textInputEditTextage.setTypeface(myTypeface);
        textInputEditTextage.setText(sharedpref.getString("age","").trim());
        textInputEditTextphone.setTypeface(myTypeface);
        textInputEditTextphone.setText(sharedpref.getString("phone","").trim());
        textInputEditTextphone.setEnabled(false);
        textInputEditTextpassword.setText(sharedpref.getString("password","").trim());
        oldpass=sharedpref.getString("password","").trim();
                regesiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchInfo();
            }
        });
    }
    public void inisialize(){
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        textInputLayoutname=(TextInputLayout)findViewById(R.id.textInputLayoutName);
        textInputLayoutaddress=(TextInputLayout)findViewById(R.id.textInputLayoutaddress);
        textInputLayoutphone=(TextInputLayout)findViewById(R.id.textInputLayoutphone);
        textInputLayoutpassword=findViewById(R.id.textInputLayoutPassword);
        textInputLayoutcountry = (TextInputLayout) findViewById(R.id.textInputLayoutcountry);
        textInputLayoutage = (TextInputLayout) findViewById(R.id.textInputLayoutage);
       textInputEditTextname=(TextInputEditText)findViewById(R.id.textInputEditTextName);
        textInputEditTextphone=(TextInputEditText)findViewById(R.id.textInputEditTextphone);
        textInputEditTextaddress=(TextInputEditText)findViewById(R.id.textInputEditTextaddress);
        textInputEditTextpassword=findViewById(R.id.textInputEditTextPassword);
        textInputEditTextcountry = (TextInputEditText) findViewById(R.id.textInputEditTextcountry);
        textInputEditTextage = (TextInputEditText) findViewById(R.id.textInputEditTextage);
        regesiter=(AppCompatButton)findViewById(R.id.appCompatButtonRegister);

    }
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_updateaccount(textInputEditTextname.getText().toString(),
              textInputEditTextaddress.getText().toString()
                ,textInputEditTextphone.getText().toString(),oldpass,sharedpref.getString("phone","").trim(),textInputEditTextpassword.getText().toString(),
                textInputEditTextcountry.getText().toString(), Integer.parseInt(textInputEditTextage.getText().toString()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                edt.putString("name",textInputEditTextname.getText().toString());
                edt.putString("phone",textInputEditTextphone.getText().toString());
                edt.putString("address",textInputEditTextaddress.getText().toString());
                edt.putString("password",textInputEditTextpassword.getText().toString());
                edt.putString("country",textInputEditTextcountry.getText().toString());
                edt.putString("age",textInputEditTextage.getText().toString());
                edt.apply();
                Toast.makeText(edit_profile.this,"تم التعديل",Toast.LENGTH_LONG).show();
                 finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(edit_profile.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
