package com.Mano.Ad.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.Mano.Ad.R;


public class profile extends AppCompatActivity {
TextView name,address,phone,id;
    private SharedPreferences sharedpref;
    Typeface myTypeface;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializer();
        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/flat.ttf");
        sharedpref= getSharedPreferences("ManoAd", Context.MODE_PRIVATE);
        name.setTypeface(myTypeface);
        address.setTypeface(myTypeface);
        phone.setTypeface(myTypeface);
        id.setTypeface(myTypeface);
        name.setText(sharedpref.getString("name","").trim());
        address.setText(sharedpref.getString("address","").trim());
        phone.setText(sharedpref.getString("phone","").trim());
        id.setText(sharedpref.getInt("id",0)+"");
        this.setTitle("");
        setSupportActionBar(toolbar);
     toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);

        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
    private void initializer(){
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        name=(TextView)findViewById(R.id.username);
        address=(TextView)findViewById(R.id.address);
        phone=(TextView)findViewById(R.id.phone);
        id=findViewById(R.id.id);
    }
}
