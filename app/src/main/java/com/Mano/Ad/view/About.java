package com.Mano.Ad.view;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.Mano.Ad.R;

public class About extends AppCompatActivity {
    TextView textView;
    Typeface myTypeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        this.setTitle("ManoAd");

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
        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/flat.ttf");
        textView=findViewById(R.id.txt);
        textView.setTypeface(myTypeface);
        textView.setText("شركة مانو آد للتسويق الالكتروني والاعلانات هدفها الوصول باكبر استفادة للمعلن والمشاهد من خلال افكار تسويقية رائعة وترحب بالتعاون معك .");
    }
}
